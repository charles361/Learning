/**
 * 
 */
package cn.zlh.thread;

import java.util.Random;

/**           
 * 创建人：朱林虎    
 * QQ:279562102
 * Email:skysea361@163.com
 * 创建时间：2015-1-23 上午10:29:44      
 * 功能：
 */
public class ThreadLocalTest {
	
	//Java提供了可以为线程进行数据帮的类ThreadLocal
	//ThreadLocal实际上是一个键值对集合，可以将数据保存成以线程为键，以线程数据为值的键值对
	//ThreadLocal可以自动获取当前线程作为键
	private static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		for(int i = 0; i < 2; i ++){
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					int data = new Random().nextInt();
					threadLocal.set(data);
					System.out.println(Thread.currentThread().getName() + " has put data:" + data);
					MyThreadShareData.getInstance().setName("name" + data);
					MyThreadShareData.getInstance().setAge(data);
					new A().get();
					new B().get();
				}
			}).start();
		}
	}
	
	
	/**
	 * 创建人：朱林虎    
	 * QQ:279562102
	 * Email:skysea361@163.com
	 * 创建时间：2015-1-23 上午11:20:00
	 * 功能说明:对于多个线程需要共享访问的多个数据可以使用类将其封装起来，然后将类的对象放入ThreadLocal
	 * 此时类设计成单例模式，但是在数据绑定的情况下单例只是对于单个线程而言，不是整个jvm
	 */
	static class MyThreadShareData{
		
		private MyThreadShareData(){}
		
		//在线程数据绑定隔离的情况下，单例会创建多个对象，每个对象对于线程是单例
		public static synchronized MyThreadShareData getInstance(){
			MyThreadShareData myThreadShareData = shareDataThreadLocal.get();
			if(myThreadShareData == null){
				myThreadShareData = new MyThreadShareData();
				shareDataThreadLocal.set(myThreadShareData);
			}
			return myThreadShareData;
		}
		
		private static ThreadLocal<MyThreadShareData> shareDataThreadLocal = new ThreadLocal<MyThreadShareData>();
		private String name;
		private Integer age;
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Integer getAge() {
			return age;
		}
		public void setAge(Integer age) {
			this.age = age;
		}

		@Override
		public String toString() {
			return "MyThreadShareData [name=" + name + ", age=" + age + "] HashCode:" + hashCode();
		}
		
	}
	
	static class A{
		public void get(){
			int data = threadLocal.get();
			System.out.println("A:" + Thread.currentThread().getName() + " get data:" + data);
			MyThreadShareData  myThreadShareData = MyThreadShareData.getInstance();
			System.out.println("A:" + Thread.currentThread().getName() +" myThreadShareData:" + myThreadShareData);
		}
	}
	
	static class B{
		public void get(){
			int data = threadLocal.get();
			System.out.println("B:" + Thread.currentThread().getName() + " get data:" + data);
			MyThreadShareData  myThreadShareData = MyThreadShareData.getInstance();
			System.out.println("B:" + Thread.currentThread().getName() +" myThreadShareData:" + myThreadShareData);
		}
	}

}
