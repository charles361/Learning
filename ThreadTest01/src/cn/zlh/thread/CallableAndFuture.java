package cn.zlh.thread;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 线程执行结束的结果通知 
 * @author 朱林虎
 *
 */
public class CallableAndFuture {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ExecutorService threadPool = Executors.newSingleThreadExecutor();
		//向线程池提交任务，其中Callable相当于Runnable，call相当于run方法
		//返回值Future对象用于获取线程执行的结果
		Future<String> future = threadPool.submit(new Callable<String>() {
			@Override
			public String call() throws Exception {
				Thread.sleep(2000);
				return Thread.currentThread().getName() + " is called!";
			}
		});
		
		try {
			System.out.println("线程：" + future.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ExecutorService ThreadPool1 = Executors.newFixedThreadPool(10);
		CompletionService<Integer> completionService = new ExecutorCompletionService<Integer>(ThreadPool1);
		for(int i = 1; i <= 10; i ++){
			final int task = i;
			//向线程池提交一组任务
			completionService.submit(new Callable<Integer>() {
				@Override
				public Integer call() throws Exception {
					Thread.sleep(new Random().nextInt(5000));
					return task;
				}
			});
		}
		
		for(int i = 0; i <= 10; i ++){
			try {
				//等待获取任务
				Future<Integer> future2 = completionService.take();
				Integer result = future2.get();
				System.out.println(result);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
