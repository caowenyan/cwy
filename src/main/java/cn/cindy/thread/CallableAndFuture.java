package cn.cindy.thread;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CallableAndFuture {

	public static void main(String[] args) {
//		single();
		
		completionService();
		
		System.out.println("结束了!");
	}
	
	public static void single(){
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		Future<String> future = executorService.submit(new Callable<String>() {
			@Override
			public String call() throws Exception {
				Thread.sleep(5000);
				return "hello world";
			}
		});
		//阻塞等待结果
//		get(future);
		
		//若等待时间超时还未有结果,则抛出异常
		getAtTime(future);

	}
	
	/**
	 * CompletionService是Executor和BlockingQueue的结合体
	 */
	public static void completionService(){
		ExecutorService executorService = Executors.newFixedThreadPool(3);
		CompletionService<Integer> completionService = new ExecutorCompletionService<Integer>(executorService);
		for(int i=0;i<10;i++){
			final int task = i;
			completionService.submit(new Callable<Integer>() {
				@Override
				public Integer call() throws Exception {
					Thread.sleep(new Random().nextInt(10000));
					return task;
				}
			});
		}
		for(int i=0;i<10;i++){
			try {
				 //谁最先执行完成就返回,并不需要按任务提交的顺序执行
				System.out.println(completionService.take().get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void get(Future<String> future){
		try {
			System.out.println(future.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	public static void getAtTime(Future<String> future){
		try {
			System.out.println(future.get(2, TimeUnit.SECONDS));
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}
}
