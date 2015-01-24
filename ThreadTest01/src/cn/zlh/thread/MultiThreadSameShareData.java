package cn.zlh.thread;

/**
 * 多线程执行相同代码访问共享数据
 * 对于多线程执行相同代码访问共享数据可以通过使用同一个Runnable对象，将相同代码放入Runnable的实现方法run中
 * 此时自动实现的共享数据的访问互斥，同一时刻只有一个线程可以访问共享数据
 * @author 朱林虎
 *
 */
public class MultiThreadSameShareData {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		ShareData shareData = new ShareData();
//		for(int i = 0; i < 1000; i ++){
//			new Thread(shareData).start();
//		}
		
		ShareData1 shareData1 = new ShareData1();
		for(int i = 0; i < 100; i ++){
			new Thread(shareData1).start();
		}
	}
	/**
	 * 直接通过在实现Runnable接口创建线程，在方法run内直接实现相关业务多线程可以对相同资源进行处理访问
	 * 此时相同资源是互斥访问的，在同一时刻只有一个线程可以操作此资源
	 * @author 朱林虎
	 *
	 */
	static class ShareData implements Runnable{
		private int count = 1000;
		
		@Override
		public void run() {
			//System.out.println(Thread.currentThread().getName() + "执行前count的值是：" + count);
			count --;
			System.out.println(Thread.currentThread().getName() + "执行后count的值是：" + count);
		}
	}
	
	/**
	 * 通过覆盖Thread中run方法的方式创建的对象，其中的run方法不能实现对共享资源的互斥访问，所以不可以作为相同代码访问共享数据的实现方式
	 * @author 朱林虎
	 *
	 */
	static class ShareData1 extends Thread{
		
		private int count = 1000;
		
		@Override
		public void run() {
			//System.out.println(Thread.currentThread().getName() + "执行前count的值是：" + count);
			count --;
			System.out.println(Thread.currentThread().getName() + "执行后count的值是：" + count);
		}
	}

}
