package cc.dorado.spence.IPProxy.resource;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;

import cc.dorado.spence.IPProxy.util.HttpClientUtil;

/**
 * @author spence
 *
 * Email zsp@dorado.cc
 * Date 2015年12月23日
 * Time 下午3:20:17
 */
public class IPResource {
	
	public List<IPSegment> getIpResource(URL url){
		List<IPSegment> ipSegmentList = Lists.newArrayList();
		String result = HttpClientUtil.getResponseString(url.getUrl());
		String[] lines = StringUtils.split(result,"\n");
		for(String line:lines){
			String[] segments = StringUtils.split(line);
			if(segments.length==3){
				IPSegment segment = new IPSegment();
				segment.startIP = segments[0];
				segment.endIP = segments[1];
				segment.num = Integer.parseInt(segments[2]);
				ipSegmentList.add(segment);
			}
			
		}		
		return ipSegmentList;
	} 
	
	/**
	 * 将59.192.0.0  59.255.255.255  4194304 这样的ip段转换为
	 * 59.192.0.0(含义为59.192.0.0/24)
	 * 59.193.0.0
	 * 59.194.0.0
	 * ....
	 * 59.255.255.0
	 * @param segment
	 * @return
	 */
	public List<String> shortIPSegment(IPSegment segment){
		List<String> list = Lists.newArrayList();
		
		String startIP = segment.startIP;
		String endIP = segment.endIP;
		
		String[] startNum = StringUtils.split(startIP,".");
		String[] endNum = StringUtils.split(endIP,".");
		int startA = Integer.parseInt(startNum[0]);
		
		int startB = Integer.parseInt(startNum[1]);
		int endB = Integer.parseInt(endNum[1]);
		int startC = Integer.parseInt(startNum[2]);
		int endC = Integer.parseInt(endNum[2]);
		
		List<Integer> bList = Lists.newArrayList();
		for(int i=startB;i<=endB;i++){
			bList.add(i);
		}
		
		List<Integer> cList = Lists.newArrayList();
		for(int i=startC;i<=endC;i++){
			cList.add(i);
		}
		
		for(int b:bList){
			for(int c:cList){
				list.add(startA+"."+b+"."+c+"."+0);
			}
		}
		
		return list;
	}
	
	
	/**
	 * 将输入的ip段转换为ip组
	 * 例如输入：211.148.5.0 含义为（211.148.5.0/24）
	 * 输出：211.148.5.0
	 * 	211.148.5.1
	 * 	211.148.5.2
	 * 	....
	 * 	211.148.5.255
	 * 输出的list的size为256
	 * @param shortSegment
	 * @return
	 */
	public List<String> shortSegment2ip(String shortSegment){
		List<String> list = Lists.newArrayList();
		String[] ip = StringUtils.split(shortSegment,".");
		int a = Integer.parseInt(ip[0]);
		int b = Integer.parseInt(ip[1]);
		int c = Integer.parseInt(ip[2]);
				
		for(int i=0;i<=255;i++){
			list.add(a+"."+b+"."+c+"."+i);
		}
		return list;
	}
	
	
	public static void main(String[] args) {
		IPResource resource = new IPResource();
		
		List<IPSegment> ipSegments = resource.getIpResource(URL.CHINANET);
		System.out.println(ipSegments.size());
		
		IPSegment segment = ipSegments.get(50);
		
		System.out.println(segment);
		
		List<String> result = resource.shortIPSegment(segment);
		
		System.out.println(result.size());

		String shortSegment = result.get(1);
		
		List<String> list = resource.shortSegment2ip(shortSegment);
		System.out.println(list.size());
		for(String s:list){
			System.out.println(s);
		}
	}
}