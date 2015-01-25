package cn.zlh.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 并发库线程锁实现互斥同步技术
 * @author 朱林虎
 *
 */
public class LockTest {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new LockTest().init();
	}
	
	private void init(){
		final Outputer outputer  = new Outputer();
		//两个或两个以上线程访问同一段代码会出现同步问题，即当一个线程正在一段代码运行时，cpu突然切换到另一个线程运行，这就造成数据的混乱
		//要想保证同一时刻只有一个线程在同一代码运行，即一个线程运行时另一个线程不可以进来，只能等待，就需要为此段代码加上一个锁，当一个线程
		//进入代码则加上锁，执行完毕再释放锁，其他线程可以获取锁进入代码
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					outputer.output("liyanhong");
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					outputer.output("liuqiangdong");
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		}).start();
	}
	
	
	static class Outputer{
		
		Lock lock = new ReentrantLock();
				
		public void output(String name){
			//在共享代码段上加锁可以实现线程间的互斥，避免数据混乱
			lock.lock();
			for(int i = 0; i < name.length(); i ++){
				System.out.print(name.charAt(i));
			}
			System.out.println();
			lock.unlock();
		}
		
		/**
		 * 也可以在函数上加锁，保证同一时间只有一个线程进入此函数
		 * 如果函数与其他代码使用的锁是同一个对象，则他们也是互斥的
		 * 一般情况函数的锁即本函数所属对象
		 * @param name
		 */
		public synchronized void output1(String name){
			
			for(int i = 0; i < name.length(); i ++){
				System.out.print(name.charAt(i));
			}
			System.out.println();
		}
		
		/**
		 * 对于静态方法，它的锁就是class对象即XXX.class,所以如果某段代码想与静态方法同步，则可以使用XXX.class作为同步锁
		 * @param name
		 */
		public synchronized static void output2(String name){
			
			for(int i = 0; i < name.length(); i ++){
				System.out.print(name.charAt(i));
			}
			System.out.println();
		}
	}

}
