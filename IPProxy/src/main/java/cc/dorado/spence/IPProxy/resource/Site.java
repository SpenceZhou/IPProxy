package cc.dorado.spence.IPProxy.resource;

/**
 * @author spence
 * 暂时支持的验证的网站
 * 注：凡是网站中包含robots.txt文件的并且此文件不为空都可验证
 *
 * Email zsp@dorado.cc
 * Date 2015年12月24日
 * Time 上午11:45:50
 */
public enum Site {
	
	TENCENT("http://www.qq.com/robots.txt"),
	SINA("http://news.sina.com.cn/robots.txt"),
	BAIDU("http://baidu.com/robots.txt"),
	TAOBAO("https://www.taobao.com/robots.txt"),
	JD("http://www.jd.com/robots.txt"),
	PEOPLE("http://people.com.cn/robots.txt"),
	YOUKU("http://www.youku.com/robots.txt");
	
	private String url;
	
	private Site(String url){
		this.url = url;
	}
	
	public String getUrl(){
		return this.url;
	}
	
}
