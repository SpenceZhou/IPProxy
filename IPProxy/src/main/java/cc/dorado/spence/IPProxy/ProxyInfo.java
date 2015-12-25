package cc.dorado.spence.IPProxy;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * @author spence
 *
 * Email zsp@dorado.cc
 * Date 2015年12月24日
 * Time 上午10:38:26
 */
public class ProxyInfo {

	public String ip;
	public int port;
	/**
	 * 代理ip的时延，单位为ms
	 * 如果为-1时表示未知
	 * 默认值为-1
	 */
	public int delay = -1;
	/**
	 * 0：未知
	 * 1：HTTP
	 * 2：HTTPS
	 * 3：SOCKET
	 */
	public int type;
	
	/**
	 * 0：未知
	 * 1：透明
	 * 2：普匿
	 * 3：高匿
	 */
	public int anonymousType;

	/**
	 * 探测时间
	 */
	public Date gropeTime = new Date();
	
	@Override
	public String toString() {
		return "ip:"+ip+",port:"+port+",gropeTime:"+DateFormatUtils.format(gropeTime, "yyyy-MM-dd HH:mm:ss");
	}
	
}
