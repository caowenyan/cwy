package cn.cindy.thread;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Semaphore也是一个线程同步的辅助类，可以维护当前访问自身的线程个数，并提供了同步机制。
 * 使用Semaphore可以控制同时访问资源的线程个数，例如，实现一个文件允许的并发访问数。
 */
public class SemaphoreTest {

	public static void main(String[] args) {
		ExecutorService service = Executors.newCachedThreadPool();
		final Semaphore sp = new Semaphore(3);//创建Semaphore信号量，初始化许可大小为3
		for(int i=0;i<10;i++){
			try {
				Thread.sleep(new Random().nextInt(100));
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			Runnable runnable = new Runnable(){
				public void run() {
					try {
						sp.acquire();//请求获得许可，如果有可获得的许可则继续往下执行，许可数减1。否则进入阻塞状态
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("线程"+Thread.currentThread().getName()+"进入,当前已有"+(3-sp.availablePermits())+"并发");
					try {
						Thread.sleep(new Random().nextInt(10000));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("线程"+Thread.currentThread().getName()+"即将离开");
					sp.release();//释放许可，许可数加1
					//下面代码有时候执行不准确，因为其没有和上面的代码合成原子单元
					System.out.println("线程"+Thread.currentThread().getName()+"已离开,当前已有"+(3-sp.availablePermits())+"并发");
				};
			};
			service.execute(runnable);
		}
	}

}
