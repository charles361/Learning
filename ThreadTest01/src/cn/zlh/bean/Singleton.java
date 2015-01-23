/**
 * 
 */
package cn.zlh.bean;

/**           
 * 创建人：朱林虎    
 * QQ:279562102
 * Email:skysea361@163.com
 * 创建时间：2015-1-23 上午11:37:05      
 */
public class Singleton {
	
	private static Singleton singleton = null;
	
	private Singleton(){}
	
	//多线程并发情况下需要加synchronized关键字或者直接使用懒汉模式，否则将产生多个
	public static synchronized Singleton getInstance(){
		if(singleton == null){
			singleton = new Singleton();
		}
		return singleton;
	}
}
