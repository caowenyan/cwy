package cn.cindy.netty.base.io.fakenio;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TimeServerHandlerExecutePool {

	private ExecutorService executorService;
	
	public TimeServerHandlerExecutePool(int maxPoolSize, int queueSize) {
		//第一个参数:Returns the number of processors available to the Java virtual machine.
		//本机是4,所以池大小必须大于等于4才可以
		executorService = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), 
				maxPoolSize, 120l, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(queueSize));
	}

	public void execute(TimeServerHandler timeServerHandler) {
		executorService.submit(timeServerHandler);
	}

}
