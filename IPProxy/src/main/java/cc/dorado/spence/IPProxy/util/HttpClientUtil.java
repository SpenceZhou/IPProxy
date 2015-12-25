package cc.dorado.spence.IPProxy.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * @author spence
 *
 * Email zsp@dorado.cc
 * Date 2015年12月23日
 * Time 下午9:09:19
 */
public class HttpClientUtil {
	
	public static CloseableHttpClient httpclient = HttpClients.createDefault();
	
	public static String getPostResponseString(String url,List <NameValuePair> params,String chartset){		
		HttpPost post = new HttpPost(url);
		post.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.130 Safari/537.36");
		post.addHeader("Content-Type", "application/x-www-form-urlencoded");
		try {
			post.setEntity(new UrlEncodedFormEntity(params,chartset));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
		try {
			CloseableHttpResponse response = httpclient.execute(post);
			if(response.getStatusLine().getStatusCode() == 200){
				return EntityUtils.toString(response.getEntity());
			}
			return String.valueOf(response.getStatusLine().getStatusCode());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}	
	}
	
	public static String getResponseString(String url){
		HttpGet get = new HttpGet(url);
		get.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.130 Safari/537.36");
		CloseableHttpResponse response;
		try {
			response = httpclient.execute(get);
			if(response.getStatusLine().getStatusCode() == 200){
				return EntityUtils.toString(response.getEntity());
			}
			return String.valueOf(response.getStatusLine().getStatusCode());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getResponseString(String url,HttpHost proxy){
		HttpGet get = new HttpGet(url);
		get.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.130 Safari/537.36");
		RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
		get.setConfig(config);
		CloseableHttpResponse response;
		try {
			response = httpclient.execute(get);
			if(response.getStatusLine().getStatusCode() == 200){
				return EntityUtils.toString(response.getEntity());
			}
			return String.valueOf(response.getStatusLine().getStatusCode());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static String getResponseString(String url, List<Cookie> cookies){
		
		HttpClientContext context = new HttpClientContext();
		CookieStore store = new BasicCookieStore();
		for(Cookie cookie:cookies){
			store.addCookie(cookie);
		}
		context.setCookieStore(store);
		
		HttpGet get = new HttpGet(url);
		get.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.130 Safari/537.36");
		
		CloseableHttpResponse response;
		try {
			response = httpclient.execute(get, context);
			if(response.getStatusLine().getStatusCode() == 200){
				return EntityUtils.toString(response.getEntity());
			}
			return String.valueOf(response.getStatusLine().getStatusCode());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
