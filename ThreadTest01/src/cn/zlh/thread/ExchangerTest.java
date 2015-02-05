package cn.zlh.thread;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程数据交换工具Exchanger使用
 * @author 朱林虎
 *
 */
public class ExchangerTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExecutorService service = Executors.newCachedThreadPool();
		final Exchanger<String> exchanger = new Exchanger<String>();
		
		service.execute(new Runnable() {
			
			@Override
			public void run() {
				
				try {
					String data1 = "leijun";
					System.out.println("线程" + Thread.currentThread().getName() + "正准备把" + data1 + "交换出去...");
					Thread.sleep((long)(Math.random() * 10000));
					String data= exchanger.exchange(data1);//交换数据并返回交换后的数据
					System.out.println("线程" + Thread.currentThread().getName() + "交换后的数据是：" + data);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		service.execute(new Runnable() {
			
			@Override
			public void run() {
				try {
					String data2 = "zhouhongyi";
					System.out.println("线程" + Thread.currentThread().getName() + "正准备把" + data2 + "交换出去...");
					Thread.sleep((long)(Math.random() * 10000));
					String data= exchanger.exchange(data2);
					System.out.println("线程" + Thread.currentThread().getName() + "交换后的数据是：" + data);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});

	}

}
