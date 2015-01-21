package cn.zlh.thread;

/**
 * 传统线程测试
 * @author 朱林虎
 * 注意：多线程不会提高效率，反而降低程序运行的效率，因为单cpu需要在不同的线程间切换，增加运行时间
 * 但是如果在多cpu情况下，多线程会使程序运行效率提高，也提高了多cpu的利用率
 */
public class ThraditionalThread {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Thread thread1 = new Thread(){//由于线程实际运行的是线程的run方法，所以要想使线程运行我们的业务代码，就要使用子类覆盖线程类的run方法
			@Override
			public void run() {
				while(true){
					try {
						Thread.sleep(500);//让当前线程暂停0.5秒
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName() + "运行了");
				}
			}
		};
		thread1.start();
		
		//在Thread的run方法中运行的实际上是一个Runnable的对象，如果此对象不为空，则运行此对象run里面的内容
		//所以我们可以将业务代码放入此对象的run方法中，然后将Runnable的对象通过构造方法传给要运行的线程
		//此方式是常用方式，并且比较符合面向对象思想，因为我们本身要运行就是一个对象
		Thread thread2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(500);//让当前线程暂停0.5秒
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName() + "运行了");
				}
			}
		});
		thread2.start();
		
		Thread thread3 = new Thread(new Runnable() {//此处为本类对象通过Runnable初始化了相应的业务代码
			
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(500);//让当前线程暂停0.5秒
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("Runnable:" + Thread.currentThread().getName() + "运行了");
				}
			}
		}){
			@Override
			public void run() {// 此处通过子类覆盖了父类的run方法，所以在运行时执行此代码
				while (true) {
					try {
						Thread.sleep(500);//让当前线程暂停0.5秒
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("Thread:" + Thread.currentThread().getName() + "运行了");
				}
			}
		};
		thread3.start();

	}

}
