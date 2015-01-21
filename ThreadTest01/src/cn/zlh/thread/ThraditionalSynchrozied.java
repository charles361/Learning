package cn.zlh.thread;

/**
 * 线程互斥同步技术
 * @author 朱林虎
 *
 */
public class ThraditionalSynchrozied {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new ThraditionalSynchrozied().init();
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
					outputer.output2("liuqiangdong");
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
		public void output(String name){
			//java中可使用synchronized(obj)的方式对一段代码加锁，其中obj为一个对象，此对象对于所有访问线程必须是同一个才能实现锁的机制
			synchronized(Outputer.class){
			//synchronized(this){
				for(int i = 0; i < name.length(); i ++){
					System.out.print(name.charAt(i));
				}
				System.out.println();
			}
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
