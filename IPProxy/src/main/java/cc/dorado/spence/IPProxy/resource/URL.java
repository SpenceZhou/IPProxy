package cc.dorado.spence.IPProxy.resource;

/**
 * @author spence
 *
 * 获取中国各个运营商的ip端的url
 * 
 * Email zsp@dorado.cc
 * Date 2015年12月24日
 * Time 下午12:02:25
 */
public enum URL {

	/**
	 * 中国电信  123,499,776
	 */
	CHINANET("http://ipcn.chacuo.net/down/t_txt=c_CHINANET"),
	
	/**
	 * 中国联通 54,835,456
	 */
	UNICOM("http://ipcn.chacuo.net/down/t_txt=c_UNICOM"),
	
	/**
	 * 中国移动  35,160,064
	 */
	CMNET("http://ipcn.chacuo.net/down/t_txt=c_CMNET"),
	
	/**
	 * 中国教育网 16,830,976
	 */
	CERNET("http://ipcn.chacuo.net/down/t_txt=c_CERNET"),
	
	/**
	 * 中国铁通 15,533,056
	 */
	CRTC("http://ipcn.chacuo.net/down/t_txt=c_CRTC"),
	
	/**
	 * 中国网通 7,632,896
	 */
	CNCGROUP("http://ipcn.chacuo.net/view/i_CNCGROUP"),
	
	/**
	 * 长城宽带 2,495,488
	 */
	GWBN("http://ipcn.chacuo.net/down/t_txt=c_GWBN"),
	
	/**
	 * 中科网络 1,311,744
	 */
	CSTN("http://ipcn.chacuo.net/down/t_txt=c_CSTN"),
	
	/**
	 * 广电宽带 1,275,904
	 */
	BCN("http://ipcn.chacuo.net/down/t_txt=c_BCN");
	
	private String url;
	private URL(String url){
		this.url = url;
	}
	
	public String getUrl(){
		return this.url;
	}
}
