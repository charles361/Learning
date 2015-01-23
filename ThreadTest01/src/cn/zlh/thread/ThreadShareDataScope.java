/**
 * 
 */
package cn.zlh.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**     
 * 创建人：朱林虎    
 * QQ:279562102
 * Email:skysea361@163.com
 * 创建时间：2015-1-23 上午09:20:43      
 * 功能：线程数据共享
 */
public class ThreadShareDataScope {
	
	//private static int data = 0;
	private static Map<String, Integer> threadMap = new HashMap<String, Integer>();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		for(int i = 0; i < 2; i ++){
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					//在本线程中为共享数据赋值，此时如果不做线程数据绑定，则两个线程共享一个数据，后一个线程对数据的赋值会覆盖前一个线程的赋值
//					if("Thread-0".equals(Thread.currentThread().getName())){
//						data = 123456;
//					}
//					
//					if("Thread-1".equals(Thread.currentThread().getName())){
//						data = 654321;
//					}
					Integer data = new Random().nextInt(10000);
					//使用一个Map为线程的数据各自做了数据存储，实现了线程的数据隔离和绑定
					threadMap.put(Thread.currentThread().getName(), data);
					System.out.println(Thread.currentThread().getName() + " has put data:" + data);
					new A().get();
					new B().get();
				}
			}).start();
		}
	}
	
	static class A{
		
		public void get(){
			int data = threadMap.get(Thread.currentThread().getName());
			System.out.println("A:" + Thread.currentThread().getName() + " get data:" + data);
		}
	}
	
	static class B{
		
		public void get(){
			int data = threadMap.get(Thread.currentThread().getName());
			System.out.println("B:" + Thread.currentThread().getName() + " get data:" + data);
		}
	}

}
