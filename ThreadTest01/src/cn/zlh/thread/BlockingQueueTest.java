package cn.zlh.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 阻塞队列使用
 * 添加函数：add添加成功正常返回，添加失败（队列满了）抛出异常，offer添加成功返回true，添加失败返回false，put如果队列已满，则阻塞等待
 * 删除函数：remove删除成功正常返回，删除失败（队列为空）抛出异常，poll可以取出元素返回元素对象，如果队列为空则返回null，take如果队列为空则阻塞等待
 * @author 朱林虎
 *
 */
public class BlockingQueueTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//定义一个阻塞队列
		final BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(3);
		for(int i = 0; i < 2 ; i ++){
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					while (true) {
						try {
							Thread.sleep((long)(Math.random() * 10000));
							System.out.println("线程" + Thread.currentThread().getName() + "准备放数据");
							queue.put(1);//向队列中放入数据，阻塞式，如果队列已满，则等待直到队列可以放数据为止
							System.out.println("线程" + Thread.currentThread().getName() + "已经放了数据，现在队列有" + queue.size() + "个数据");
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}).start();
		}
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(100);
						System.out.println("线程" + Thread.currentThread().getName() + "准备取数据");
						queue.take();//从队列中取数据，阻塞式，如果队列为空，则等待直到队列有数据为止
						System.out.println("线程" + Thread.currentThread().getName() + "已经取到了数据，现在队列有" + queue.size() + "个数据");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

}
