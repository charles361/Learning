/**
 * 
 */
package cn.zlh.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**       
 * 功能：java并发库中的信号灯控制，利用信号灯可以控制资源可以被多少线程同时访问，
 * 它与线程池的区别是，线程池是空访问资源线程的数量，是从资源外部控制线程
 * 数量，而信号灯是由资源内部控制线程可以同时访问的数量
 * 创建人：朱林虎    
 * QQ:279562102
 * Email:skysea361@163.com
 * 创建时间：2015-1-27 上午10:19:47      
 */
public class SemaphoreTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ExecutorService threadPool = Executors.newCachedThreadPool();
		final Semaphore sp = new Semaphore(3);//定义固定数量的信号灯
		for(int i = 0; i < 10; i ++){
			Runnable runnable = new Runnable() {
				
				@Override
				public void run() {
					
					try {
						sp.acquire();//线程进入，请求一个信号灯
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("线程" + Thread.currentThread().getName() + "进入，当前有" + (3 - sp.availablePermits()) + "个线程并发");
					
					try {
						Thread.sleep((long)(Math.random()*1000));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					System.out.println("线程" + Thread.currentThread().getName() + "即将离开，当前有" + (3 - sp.availablePermits()) + "个线程并发");
					
				    sp.release();//线程离开，释放一个信号灯
				    
				    System.out.println("线程" + Thread.currentThread().getName() + "已经离开，当前有" + (3 - sp.availablePermits()) + "个线程并发");
				}
			};
			
			threadPool.execute(runnable);
		}

	}

}
