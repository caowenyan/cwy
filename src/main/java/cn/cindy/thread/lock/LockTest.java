package cn.cindy.thread.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest {

	public static void main(String[] args) {
		final Outputer outputer = new Outputer();
		new Thread(new Runnable() {

			public void run() {
				while (true) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					outputer.outpet("caowenyan");
				}
			}

		}).start();
		new Thread(new Runnable() {

			public void run() {
				while (true) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					outputer.outpet("hema");
				}
			}

		}).start();
	}

	static class Outputer {
		Lock lock = new ReentrantLock();

		public void outpet(String value) {
			lock.lock();
			for (int i = 0; i < value.length(); i++) {
				System.out.print(value.charAt(i));
			}
			System.out.println();
			lock.unlock();
		}
	}
}