package cn.cindy.thread;

/**
 * 多个线程之间共享数据
 *
 */
public class MutilThreadSharedata {

	public static void main(String[] args) {
//		share1();
		share2();
	}
	
	public static void share1(){
		final ShareData data = new ShareData();
		new Thread(new Runnable() {
			
			public void run() {
				data.increment();
			}
		}).start();
		new Thread(new Runnable() {
			
			public void run() {
				data.decrement();
			}
		}).start();
	}
	
	public static void share2(){
		ShareData data = new ShareData();
		new Thread(new MyRunnable1(data)).start();
		new Thread(new MyRunnable2(data)).start();
	}
	
	static class ShareData{
		int j;
		public synchronized void increment(){
			j++;
			System.out.println(Thread.currentThread().getName()+" : "+j);
		}
		public synchronized void decrement(){
			j--;
			System.out.println(Thread.currentThread().getName()+" : "+j);
		}
	}
	static class MyRunnable1 implements Runnable{
		ShareData data;
		public MyRunnable1(ShareData data) {
			this.data = data;
		}
		public void run() {
			data.increment();
		}
	}
	static class MyRunnable2 implements Runnable{
		ShareData data;
		public MyRunnable2(ShareData data) {
			this.data = data;
		}
		public void run() {
			data.decrement();
		}
	}
}
