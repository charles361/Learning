package cn.zlh.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 线程池操作
 * @author 朱林虎
 *
 */
public class ThreadPoolTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//创建固定线程数量的连接池
		ExecutorService pool = Executors.newFixedThreadPool(3);
		//创建缓存线程池，根据需要分配和销毁线程
		//ExecutorService pool = Executors.newCachedThreadPool();
		//创建单一线程池，在池内只有一个线程，在线程死亡后可以重新生成新的线程自动接受任务
		//ExecutorService pool = Executors.newSingleThreadExecutor();
		
		for(int i = 1; i <= 100; i ++){
			final int task = i;
			//使用线程池执行任务，每个任务分配一个线程,此时最多只有3个线程同时执行任务，其他任务需要等待
			pool.execute(new Runnable() {
				
				@Override
				public void run() {
					for(int i = 1; i <= 10; i ++){
						System.out.println(Thread.currentThread().getName() + " is looping of " + i + " and task of " + task);
					}
					
				}
			});
		}
		//当任务执行完毕后关闭线程池
		pool.shutdown();
		//立即关闭线程池
		//pool.shutdownNow();
		
		//执行定时任务的线程池，2秒后执行
//		Executors.newScheduledThreadPool(3).schedule(new Runnable() {
//			
//			@Override
//			public void run() {
//				System.out.println(Thread.currentThread().getName() + " bombing!!");
//			}
//		}, 2, TimeUnit.SECONDS);
		
		//执行定时任务的线程池，5秒后执行，然后每隔2秒执行一次
		Executors.newScheduledThreadPool(3).scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + " bombing!!");
			}
		}, 5, 2, TimeUnit.SECONDS);

	}

}
