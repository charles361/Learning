package cn.zlh.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 线程互斥及线程间通信(利用阻塞队列实现线程通信)
 * 实现功能：子线程首先循环10次，然后主线程循环100次，然后子线程再循环10次，然后主线程再循环100，如此循环50次
 * @author 朱林虎
 *
 */
public class BlockingQueueCommunication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final Bussiness bussiness = new Bussiness();
		
		//子线程
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				for(int i = 1; i <= 50; i ++){
					bussiness.sub(i);
				}
				
			}
		}).start();
		
		//主线程
		//写一个50次的循环，因为子循环需要进行50次
		for(int i = 1; i <= 50; i ++){
			bussiness.main(i);
			
		}
	}
	
	static class Bussiness{
		
		BlockingQueue<Integer> queue1 = new ArrayBlockingQueue<Integer>(1);
		BlockingQueue<Integer> queue2 = new ArrayBlockingQueue<Integer>(1);
		{
			try {
				queue2.put(1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		public void sub(int i){
			try {
				queue1.put(1);//此时队列1为空，可以放入数据，所以不阻塞
				for(int j = 1; j <= 10; j ++){
					System.out.println("sub thread sequeue of " + j + " loop of " + i);
				}
				queue2.take();//从队列2取出数据，释放队列2的阻塞状态
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public synchronized void main(int i){
			
			try {
				queue2.put(1);//此时队列2有数据，所以处于阻塞状态
				for(int j = 1; j <= 100; j ++){
					System.out.println("main thread sequeue of " + j + " loop of " + i);
				}
				queue1.take();//从队列1取出数据，释放队列1的阻塞状态
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

