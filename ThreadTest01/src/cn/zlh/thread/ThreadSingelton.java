/**
 * 
 */
package cn.zlh.thread;

import cn.zlh.bean.Singleton;

/**           
 * 创建人：朱林虎    
 * QQ:279562102
 * Email:skysea361@163.com
 * 创建时间：2015-1-23 上午11:31:59      
 */
public class ThreadSingelton {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		for(int i = 0; i < 2; i ++){
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					System.out.println(Thread.currentThread().getName() + " create singleton is:" + Singleton.getInstance());
				}
			}).start();
		}
	}
	
}
