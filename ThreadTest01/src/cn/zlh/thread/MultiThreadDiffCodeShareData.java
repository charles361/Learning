package cn.zlh.thread;

/**
 * 多线程通过不同代码访问共享数据
 * 可以共享数据及对共享数据的操作封装在一个类中，然后用run方法去调用封装的操作方法，操作方法需要加锁
 * @author 朱林虎
 *
 */
public class MultiThreadDiffCodeShareData {

	private static ShareData data1 = new ShareData();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		final ShareData shareData = new ShareData();
		
		for(int j = 0; j < 2; j ++){
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					shareData.increment();
				}
			}).start();
		}
		
		for(int x = 0; x < 2; x ++){
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					shareData.decrement();
				}
			}).start();
		}
		
//		for(int j = 0; j < 2; j ++){
//			new Thread(new MyRunnable1(data1)).start();
//			new Thread(new MyRunnable2(data1)).start();
//		}
		
	}
	
	static class ShareData{
		private int i = 5;
		
		public synchronized void increment(){
			i ++;
			System.out.println(Thread.currentThread().getName() + " after increment i:" + i);
		}
		
		public synchronized void decrement(){
			i -= 2;
			System.out.println(Thread.currentThread().getName() + " after decrement i:" + i);
		}
	}
	
	static class MyRunnable1 implements Runnable{
		
		private ShareData data1;
		
		public MyRunnable1(ShareData data1){
			this.data1 = data1;
		}
		
		@Override
		public void run() {
			data1.increment();
		}
	}
	
	static class MyRunnable2 implements Runnable{
		
		private ShareData data1;
		
		public MyRunnable2(ShareData data1){
			this.data1 = data1;
		}
		
		@Override
		public void run() {
			data1.decrement();
		}
	}

}
