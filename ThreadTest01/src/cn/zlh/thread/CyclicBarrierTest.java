/**
 * 
 */
package cn.zlh.thread;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**           
 * 功能：等待集合工具类:即等待所需线程全部运行到指定位置才可以继续运行
 * 创建人：朱林虎    
 * QQ:279562102
 * Email:skysea361@163.com
 * 创建时间：2015-1-27 上午10:33:35      
 */
public class CyclicBarrierTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ExecutorService pool = Executors.newCachedThreadPool();
		final CyclicBarrier cb = new CyclicBarrier(3);//参数为需要等待的线程的数量
		for(int i = 0; i < 3; i ++){
			Runnable runnable = new Runnable() {
				
				@Override
				public void run() {
					try {
						Thread.sleep((long)(Math.random() *1000));
						System.out.println("线程" + Thread.currentThread().getName() + "即将到达集合点1，目前已有" + (cb.getNumberWaiting() + 1) + "个线程已经到达，"  + (cb.getNumberWaiting() == 2 ? "全部线程已经到达，走啊！":"继续等候"));
						cb.await();//等待
						
						Thread.sleep((long)(Math.random() *1000));
						System.out.println("线程" + Thread.currentThread().getName() + "即将到达集合点2，目前已有" + (cb.getNumberWaiting() + 1) + "个线程已经到达，"  + (cb.getNumberWaiting() == 2 ? "全部线程已经到达，走啊！":"继续等候"));
						cb.await();
						
						Thread.sleep((long)(Math.random() *1000));
						System.out.println("线程" + Thread.currentThread().getName() + "即将到达集合点3，目前已有" + (cb.getNumberWaiting() + 1) + "个线程已经到达，"  + (cb.getNumberWaiting() == 2 ? "全部线程已经到达，走啊！":"继续等候"));
						cb.await();
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			};
			pool.execute(runnable);
		}
	}

}
