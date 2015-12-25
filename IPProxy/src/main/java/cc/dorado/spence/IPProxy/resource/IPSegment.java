package cc.dorado.spence.IPProxy.resource;

/**
 * @author spence
 *
 * Email zsp@dorado.cc
 * Date 2015年12月23日
 * Time 下午10:15:44
 */
public class IPSegment {
	public String startIP;
	public String endIP;
	public int num;
	public String desp;
	@Override
	public String toString() {
		return "startIP:"+startIP+",endIP:"+endIP+",num:"+num+",desp:"+desp;
	}
}
