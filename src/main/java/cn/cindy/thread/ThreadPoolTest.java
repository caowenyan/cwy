package cn.cindy.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 线程池
 * shutdown()方法并不是终止线程的执行，而是禁止在这个Executor中添加新的任务。
 */
public class ThreadPoolTest {

	public static void main(String[] args) {
//		fixed();
//		cached();
//		single();
		schedule();
	}
	
	/**
	 *  创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
	 */
	public static void fixed(){
		ExecutorService service = Executors.newFixedThreadPool(3);
		for(int i=0;i<10;i++){
			final int task = i;
			service.execute(new Runnable() {
				@Override
				public void run() {
					for(int j=0;j<10;j++){
						System.out.println(Thread.currentThread().getName()+" is looping of "+j+" for task of "+task);
					}
				}
			});
		}
		service.shutdown();
	}
	
	public static void cached(){
		ExecutorService service = Executors.newCachedThreadPool();
		for(int i=0;i<10;i++){
			final int task = i;
			service.execute(new Runnable() {
				@Override
				public void run() {
					for(int j=0;j<10;j++){
						System.out.println(Thread.currentThread().getName()+" is looping of "+j+" for task of "+task);
					}
				}
			});
		}
		service.shutdown();
	}
	
	public static void single(){
		ExecutorService service = Executors.newSingleThreadExecutor();
		for(int i=0;i<10;i++){
			final int task = i;
			service.execute(new Runnable() {
				@Override
				public void run() {
					for(int j=0;j<10;j++){
						System.out.println(Thread.currentThread().getName()+" is looping of "+j+" for task of "+task);
					}
				}
			});
		}
	}

	/**
	 * 创建一个线程池，它可安排在给定延迟后运行命令或者定期地执行
	 */
	public static void schedule(){
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		for(int i=0;i<10;i++){
			final int task = i;
			if(task>5)
				//使用延迟执行风格的方法 
				service.schedule(new Runnable() {
					@Override
					public void run() {
						for(int j=0;j<10;j++){
							System.out.println(Thread.currentThread().getName()+" is looping of "+j+" for task of "+task);
						}
					}
				},5,TimeUnit.SECONDS);
			else
				//将线程放入池中进行执行
				service.submit(new Runnable() {
					@Override
					public void run() {
						for(int j=0;j<10;j++){
							System.out.println(Thread.currentThread().getName()+" is looping of "+j+" for task of "+task);
						}
					}
				});
		}
	}

}
