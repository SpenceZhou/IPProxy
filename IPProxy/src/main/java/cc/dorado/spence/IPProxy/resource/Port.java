package cc.dorado.spence.IPProxy.resource;

/**
 * @author spence
 *
 * 常为代理的端口	
 *
 * Email zsp@dorado.cc
 * Date 2015年12月24日
 * Time 下午12:04:33
 */
public enum Port {
	/**
	 * 据统计最常用的代理端口超过总和超过80%
	 */
	COMMONPORTS_1(new int[]{80,8080}),
	
	/**
	 * 统计300个代理ip中超过20的端口号
	 */
	COMMONPORTS_2(new int[]{8123,3128,8090,9999}),
	
	/**
	 * 有可能为代理ip的端口
	 */
	COMMONPORTS_3(new int[]{9797,8088,81,82,8081,8118,8888});
	
	private int[] ports;
	
	private Port(int[] ports){
		this.ports = ports;
	}
	
	public int[] getPorts(){
		return this.ports;
	}
}
