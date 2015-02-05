package cn.zlh.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CountDownLatch倒计时器使用,倒计时器从某个数值开始运行，到0结束
 * @author 朱林虎
 *
 */
public class CountDownLatchTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ExecutorService pool = Executors.newCachedThreadPool();
		//创建倒计时为1的倒计时器
		final CountDownLatch cdOrder = new CountDownLatch(1);
		final CountDownLatch cdAnswer = new CountDownLatch(3);
		
		for(int i = 0; i < 3; i ++){
			Runnable runnable = new Runnable() {
				
				@Override
				public void run() {
					
					try {
						System.out.println("线程" + Thread.currentThread().getName() + "等待接受运行命令...");
						cdOrder.await();//等待接受可以运行命令
						System.out.println("线程" + Thread.currentThread().getName() + "开始运行...");
						Thread.sleep((long)(Math.random() * 10000));
						System.out.println("线程" + Thread.currentThread().getName() + "运行完毕...");
						cdAnswer.countDown();//倒计时器减一
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
			pool.execute(runnable);
		}
		
		try {
			Thread.sleep((long)(Math.random() * 10000));
			System.out.println("线程" + Thread.currentThread().getName() + "准备发布运行命令...");
			cdOrder.countDown();
			System.out.println("线程" + Thread.currentThread().getName() + "等待接受结果...");
			cdAnswer.await();
			System.out.println("线程" + Thread.currentThread().getName() + "接收到结果...");
		} catch (Exception e) {
			e.printStackTrace();
		}
		pool.shutdown();

	}

}
