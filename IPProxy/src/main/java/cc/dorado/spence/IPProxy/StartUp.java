package cc.dorado.spence.IPProxy;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cc.dorado.spence.IPProxy.groper.GropeIp;
import cc.dorado.spence.IPProxy.resource.IPResource;
import cc.dorado.spence.IPProxy.resource.IPSegment;
import cc.dorado.spence.IPProxy.resource.Port;
import cc.dorado.spence.IPProxy.resource.URL;

public class StartUp {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(1000);
	IPResource resource = new IPResource();
	private int defaultThreadNum = 100;
	private long totalScanIp;
	private long availableIp;
	
	private File file = new File("E:\\proxy.txt");
	
	private synchronized void writeProxyFile(String proxy){
		try {
			FileUtils.writeStringToFile(file, proxy, true);
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}
	
	private synchronized void addTotalScanIp(){
		this.totalScanIp = totalScanIp+1;
		logger.info("已经扫描ip数量为："+totalScanIp+"可用ip数量为："+getAvailableIp());
	}
	
	public synchronized long getAvailableIp() {
		return availableIp;
	}

	public synchronized void addAvailableIp() {
		this.availableIp = availableIp+1;
	}

	public void addQueue(){
		List<IPSegment> IPSegment = resource.getIpResource(URL.CHINANET);
		for(IPSegment segment:IPSegment){
			List<String> shortIPSegments = resource.shortIPSegment(segment);
			for(String shortIPSegment:shortIPSegments){
				try {
					queue.put(shortIPSegment);
					logger.debug(shortIPSegment);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public void gorpe(String shortSegment) {

		List<String> ips = resource.shortSegment2ip(shortSegment);
		GropeIp groperIp = GropeIp.getInstance();
		for (String ip : ips) {
			addTotalScanIp();
			if (groperIp.pingResult(ip)) {
				logger.debug("ip为：" + ip + "的ipPing通，开始代理ip检测！");
				int[] ports = (int[]) ArrayUtils.addAll(Port.COMMONPORTS_1.getPorts(), Port.COMMONPORTS_2.getPorts());
				for (int port : ports) {
					HttpHost proxy = new HttpHost(ip, port);
					if (groperIp.proxyResult(proxy)) {
						writeProxyFile(ip + "\t" + port + "\n");
						addAvailableIp();
					}
				}
			}
		}

	}
	
	public void start(){	
		
		//生产者
		new Thread(new Runnable() {
			@Override
			public void run() {
				addQueue();
			}
		}).start();
		
		
		//消费者
		
		ThreadPoolExecutor executor = new ThreadPoolExecutor(defaultThreadNum, defaultThreadNum+10, 200, TimeUnit.MILLISECONDS,
				new ArrayBlockingQueue<Runnable>(defaultThreadNum/2));
		
		for(int i=0;i<defaultThreadNum;i++){
			executor.execute(new Runnable() {
				
				@Override
				public void run() {
					while(true){
						try {
							String ipSegment = queue.take();
							if (StringUtils.isNoneBlank(ipSegment)) {
								logger.info("开始测试ip段："+ipSegment);
								gorpe(ipSegment);
							}
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							//e.printStackTrace();
						}
					}
					
				}
			});
		}
		
	}
	
	public static void main(String[] args) {
		new StartUp().start();
	}
}
