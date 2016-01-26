package cn.cindy.thread.lock;

import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁:多个读锁不互斥,读锁与写锁互斥,写锁与写锁互斥
 */
public class ReadWriteLockTest {

	public static void main(String[] args) {
		final QueueData queue = new QueueData();
		for (int i = 0; i < 3; i++) {
			new Thread(new Runnable() {
				public void run() {
					while(true){
						queue.get();
					}
				}
			}).start();
			new Thread(new Runnable() {
				public void run() {
					while(true){
						queue.put(new Random().nextInt(10000));
					}
				}
			}).start();
		}
	}

}
class QueueData{
	Object data = null;
	ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	
	public void get() {
		readWriteLock.readLock().lock();
		try {
			System.out.println(Thread.currentThread().getName()+" be ready to read data!");
			Thread.sleep(10);
			System.out.println(Thread.currentThread().getName()+" have read data : "+data);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			readWriteLock.readLock().unlock();
		}
	}

	public void put(int data) {
		readWriteLock.writeLock().lock();
		try {
			System.out.println(Thread.currentThread().getName()+" be ready to write data!");
			Thread.sleep(10);
			this.data = data;
			System.out.println(Thread.currentThread().getName()+" have write data : "+data);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			readWriteLock.writeLock().unlock();
		}
	}
	
}