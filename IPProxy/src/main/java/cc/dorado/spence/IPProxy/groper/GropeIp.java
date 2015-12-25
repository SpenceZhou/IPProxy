package cc.dorado.spence.IPProxy.groper;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cc.dorado.spence.IPProxy.resource.Site;
import cc.dorado.spence.IPProxy.util.HttpClientUtil;

/**
 * @author spence
 *
 * Email zsp@dorado.cc
 * Date 2015年12月24日
 * Time 上午11:34:27
 */

public class GropeIp {

	private static Logger logger = LoggerFactory.getLogger(GropeIp.class);

	private String defaultUrl = Site.SINA.getUrl();

	private static GropeIp instance;

	private GropeIp() {

	}

	public static synchronized GropeIp getInstance() {
		if (instance == null) {
			return new GropeIp();
		} else {
			return instance;
		}
	}

	/**
	 * 测试ip是否可以ping通
	 * 
	 * @param ip
	 * @return
	 */
	public boolean pingResult(String ip) {
		Runtime runtime = Runtime.getRuntime();
		Process process;
		try {
			process = runtime.exec("ping -n 1 " + ip);
			String result = IOUtils.toString(process.getInputStream());
			logger.debug("ping 命令运行结果：" + result);
			if (result.contains("TTL")) {
				return true;
			}
		} catch (IOException e) {
			// e.printStackTrace();
		}
		return false;
	}

	/**
	 * 用 httpclient测试代理是否可用
	 * 
	 * @param ip
	 * @param port
	 * @return
	 */
	public boolean proxyResult(String ip, int port) {
		HttpHost proxy = new HttpHost(ip, port);
		return proxyResult(proxy);
	}

	public boolean proxyResult(HttpHost proxy) {
		return proxyResult(proxy, defaultUrl, "User-agent", "Disallow", "Sitemap");
	}

	public boolean proxyResult(HttpHost proxy, Site site){
		return proxyResult(proxy, site.getUrl(), "User-agent", "Disallow", "Sitemap");
	}
	
	public boolean proxyResult(String ip, int port, String url, String... contains) {
		HttpHost proxy = new HttpHost(ip, port);
		return proxyResult(proxy, url, contains);
	}
	
	public boolean proxyResult(HttpHost proxy, String url, String... contains) {
		String result = HttpClientUtil.getResponseString(url, proxy);
		return StringUtils.containsAny(result, contains);
	}
	
	public static void main(String[] args) {
		System.out.println(new GropeIp().proxyResult("119.188.115.27", 80));
	}
}
