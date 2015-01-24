package cn.zlh.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 并发包中基本变量的多线程原子操作
 * 使用原子包装后的变量操作可以避免多线程并发时对同一变量操作时的加锁
 * @author 朱林虎
 *
 */
public class ConcurrentAtomicTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		final ShareData shareData = new ShareData();
		
		for(int i = 0; i < 2; i ++){
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					shareData.increment();
				}
			}).start();
		}	
		
		for(int j = 0; j < 2; j ++){
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					shareData.decrement();
				}
			}).start();
		}
	}
	
	static class ShareData{
		
		private AtomicInteger atomicData  = new AtomicInteger(5);
		private int j = 5;
		
		public void increment(){
			//System.out.println(Thread.currentThread().getName() + " before increment atomicData:" + atomicData.get() + ",j:" + j);
			int i = atomicData.incrementAndGet();
			j ++;
			System.out.println(Thread.currentThread().getName() + " after increment atomicData:" + i + ",j:" + j);
		}
		
		public void decrement(){
			//System.out.println(Thread.currentThread().getName() + " before decrement atomicData:" + atomicData.get() + ",j:" + j);
			int i = atomicData.addAndGet(-2);
			j -= 2;
			System.out.println(Thread.currentThread().getName() + " after decrement atomicData:" + i + ",j:" + j);
		}
	}

}
