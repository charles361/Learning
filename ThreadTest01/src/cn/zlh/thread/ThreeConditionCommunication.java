package cn.zlh.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

/**
 * Condition的多路等待和通信
 * 说明：并发库Condition不仅可以实现和wait、notify同样的通信功能，还可以继续多路、多信号通信
 * 实现功能：子线程1首先循环10次，然后主线程循环20次，然后子线程2再循环30次，如此循环50次
 * @author 朱林虎
 *
 */
public class ThreeConditionCommunication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final Bussiness bussiness = new Bussiness();
		
		//子线程
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				//写一个50次的循环，因为子循环需要进行50次
				for(int i = 1; i <= 50; i ++){
					bussiness.sub1(i);
				}
				
			}
		}).start();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				//写一个50次的循环，因为子循环需要进行50次
				for(int i = 1; i <= 50; i ++){
					bussiness.sub2(i);
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
		
		//线程间通信的信号量，信号量的意义在于如果子线程运行结束了不会立即去抢锁而是唤醒主线线程，自己进入等待状态，这样就保证了线程的顺序进行
		private int shouldSub = 1;
		Lock lock = new ReentrantLock(); 
		//定义三个通信信号量
		Condition condition1 = lock.newCondition();
		Condition condition2 = lock.newCondition();
		Condition condition3 = lock.newCondition();
		public void sub1(int i){
			lock.lock();//为访问共同数据的代码段加锁
				try{
				while(shouldSub != 1){//信号量不为1线程1需要等待
					try {
						condition1.await();//子线程1等待
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				for(int j = 1; j <= 10; j ++){
					System.out.println("sub1 thread sequeue of " + j + " loop of " + i);
				}
				shouldSub = 2;//将信号量置为主线程可以运行的状态
				condition2.signal();//子线程循环完成，唤醒主线程
			}finally{
				lock.unlock();
			}
		}
		
		public void sub2(int i){
			lock.lock();//为访问共同数据的代码段加锁
				try{
				while(shouldSub != 3){
					try {
						condition3.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				for(int j = 1; j <= 10; j ++){
					System.out.println("sub2 thread sequeue of " + j + " loop of " + i);
				}
				shouldSub = 1;
				condition1.signal();//子线程2循环完成，唤醒线程1
			}finally{
				lock.unlock();
			}
		}
		
		public void main(int i){
			
			lock.lock();
			try {
				while(shouldSub != 2){
					try {
						condition2.await();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				for(int j = 1; j <= 20; j ++){
					System.out.println("main thread sequeue of " + j + " loop of " + i);
				}
				shouldSub = 3;//将状态置为子线程2可以运行的状态
				condition3.signal();//唤醒子线程2
			} finally {
				lock.unlock();
			}
			
		}
	}

}

