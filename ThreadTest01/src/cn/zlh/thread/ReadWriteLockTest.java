package cn.zlh.thread;

import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 并发库读写锁技术
 * @author 朱林虎
 *
 */
public class ReadWriteLockTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final Queue1 queue1 = new Queue1();
		
		for(int i = 0; i < 3; i ++){
			final int j = i;
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					queue1.put("" + j);
				}
			}).start();
		}

		for(int i = 0; i < 3; i ++){
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					String data = queue1.get();
				}
			}).start();
		}
		
		
	}
	
}

class Queue1{
	
	private String data = null;
	private ReadWriteLock rwl = new ReentrantReadWriteLock();
	
	public String get(){
		//为读方法上读锁，读锁间不互斥，但是读锁与写锁互斥，写锁和写锁也互斥
		//两个代码段使用同一个锁时读写锁互斥才起作用
		//当读锁开始锁定时，线程不能进入写锁锁定的代码段，只有当读锁完全释放时，才能开启写锁
		rwl.readLock().lock();
		try {
			System.out.println(Thread.currentThread().getName() + " ready read data");
			Thread.sleep(new Random().nextInt(5000));
			System.out.println(Thread.currentThread().getName() + " have read data");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{//使用finally保证无论程序是否发生异常，锁都会在相应的时刻释放
			rwl.readLock().unlock();
		}
		return data;
	}
	
	public void put(String data){
		//当线程进入写程序时，会检查读锁是否锁定，如果读锁已经锁定此时将不能进入写代码，直到读锁释放才能进入
		//对于写程序，如果检测到写锁已经锁定，则也不能进入写程序
		rwl.writeLock().lock();
		try {
			System.out.println(Thread.currentThread().getName() + " ready put data");
			Thread.sleep(new Random().nextInt(5000));
			System.out.println(Thread.currentThread().getName() + " have put data");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{//使用finally保证无论程序是否发生异常，锁都会在相应的时刻释放
			rwl.writeLock().unlock();
		}
		this.data = data;
	}
}
