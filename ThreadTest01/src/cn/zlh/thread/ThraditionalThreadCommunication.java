package cn.zlh.thread;

/**
 * 线程互斥及线程间通信
 * 实现功能：子线程首先循环10次，然后主线程循环100次，然后子线程再循环10次，然后主线程再循环100，如此循环50次
 * @author 朱林虎
 *
 */
public class ThraditionalThreadCommunication {

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
					//写一个可以循环10次的子循环
//					synchronized (ThraditionalThreadCommunication.class) {
//						for(int j = 1; j <= 10; j ++){//在不加锁的情况下，主线程和子线程会互相打断，不能顺序执行，所以需要加锁
//							System.out.println("sub thread sequeue of " + j + " loop of " + i);
//						}
//					}
					bussiness.sub(i);
				}
				
			}
		}).start();
		
		//主线程
		//写一个50次的循环，因为子循环需要进行50次
		for(int i = 1; i <= 50; i ++){
			//此处加锁后虽然主线程和子线程未互相打扰，但是输出间没有互相通信，造成输出没有层次
//			synchronized (ThraditionalThreadCommunication.class) {
//				//写一个可以循环100次的子循环
//				for(int j = 1; j <= 100; j ++){
//					System.out.println("main thread sequeue of " + j + " loop of " + i);
//				}
//			}
			bussiness.main(i);
			
		}
	}
	
	

}

class Bussiness{
	
	//线程间通信的信号量，信号量的意义在于如果子线程运行结束了不会立即去抢锁而是唤醒主线线程，自己进入等待状态，这样就保证了线程的顺序进行
	private boolean bShouldSub = true;
	
	//synchronized不仅可以锁相同的代码段，也可以锁不同的代码段，保证同一时刻只能进入一个代码段
	public synchronized void sub(int i){
		
		while(bShouldSub){
			try {
				//等待主线程输出完成，子线程再进行输出
				this.wait();//此处的this必须针对同一个对象
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for(int j = 1; j <= 10; j ++){
			System.out.println("sub thread sequeue of " + j + " loop of " + i);
		}
		bShouldSub = true;
		//此处的this必须针对同一个对象
		this.notify();//子线程循环完成，唤醒主线程
	}
	
	public synchronized void main(int i){
		
		//此处使用while而不使用if的意义在于，如果线程被虚假唤醒时等待就会结束，此时信号量仍然不符合停止等待要求
		//如果使用if则会继续执行下去，造成混乱，如果使用使用while即使本次等待结束如果信号量没有变化仍然会进入下次等待
		while(!bShouldSub){
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for(int j = 1; j <= 100; j ++){
			System.out.println("main thread sequeue of " + j + " loop of " + i);
		}
		bShouldSub = false;
		this.notify();//等待主线程输出完成，再唤醒子线程
	}
}