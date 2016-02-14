package cn.cindy.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 交替循环
 */
public class BlockingQueueCommunication {

	public static void main(String[] args) {
		ExecutorService service = Executors.newSingleThreadExecutor();
		final Business business = new Business();

		service.execute(new Runnable() {
			public void run() {
				for (int i = 0; i < 50; i++) {
					business.sub();
				}
			}
		});

		for (int i = 0; i < 50; i++) {
			business.main();
		}
	}

	static class Business {
		BlockingQueue subQueue = new ArrayBlockingQueue(1);
		BlockingQueue mainQueue = new ArrayBlockingQueue(1);

		// 这里是匿名构造方法，只要new一个对象都会调用这个匿名构造方法，它与静态块不同，静态块只会执行一次，
		// 在类第一次加载到JVM的时候执行
		// 这里主要是让main线程首先put一个，就有东西可以取，如果不加这个匿名构造方法put一个的话程序就死锁了
		{
			try {
				mainQueue.put(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		public void sub() {
			try {
				mainQueue.take();
				for (int i = 0; i < 10; i++) {
					System.out.println(Thread.currentThread().getName() + " : " + i);
				}
				subQueue.put(1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void main() {
			try {
				subQueue.take();
				for (int i = 0; i < 5; i++) {
					System.out.println(Thread.currentThread().getName() + " : " + i);
				}
				mainQueue.put(1);
			} catch (Exception e) {
			}
		}
	}
}
