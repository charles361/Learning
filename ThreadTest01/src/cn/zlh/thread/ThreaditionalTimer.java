package cn.zlh.thread;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 传统定时器
 * @author 朱林虎
 *
 */
public class ThreaditionalTimer {
	
	private static int count = 0;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		new Timer().schedule(new TimerTask() {//定义一个定时器，10秒后开始执行,两个参数时是几秒后执行，然后再隔几秒执行一次
//			
//			@Override
//			public void run() {
//				System.out.println("bombing!!!");
//			}
//		}, 10000,2000);

		
		class MyTimerTask extends TimerTask{//创建一个定时任务类，在此定时任务对象执行任务时不断new新的任务对象，使任务不断进行，类似递归
			@Override
			public void run() {
				count = (count + 1) % 2;
				System.out.println("bombing!!!");
				new Timer().schedule(new MyTimerTask(), 2000 + 2000 * count);
			}
		}
		
		new Timer().schedule(new MyTimerTask(), 2000);
		
		while(true){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(new Date().getSeconds());
		}
	}

}
