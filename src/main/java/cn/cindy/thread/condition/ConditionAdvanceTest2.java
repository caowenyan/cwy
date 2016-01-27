package cn.cindy.thread.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程1循环10次，接着线程2循环20次，接着线程3循环30次，如此循环50次.
 * 只用了一个Condition来实现的,但是是通过sub和condition同时控制才行,而且唤醒要用signalAll,负责会造成死锁
 * 注意（永远在循环（loop）里调用 wait 和 notify，不是在 If 语句）
 */
public class ConditionAdvanceTest2 {
	public static void main(String[] args) {
		final Business business   = new Business();
		new Thread(new Runnable() {
			
			public void run() {
				for(int i=0;i<50;i++){
					business.sub2(i);
				}
			}
		}).start();
		new Thread(new Runnable() {
			
			public void run() {
				for(int i=0;i<50;i++){
					business.sub3(i);
				}
			}
		}).start();
		for(int i=0;i<50;i++){
			business.sub1(i);
		}
	}
	static class Business{
		int sub = 1;
		Lock lock = new ReentrantLock();
		Condition condition = lock.newCondition();
		
		public void sub1(int i){
			lock.lock();
			try {
				while(sub!=1){
					try {
						condition.await();//如果notify在while中，那么必须while完后，才会notify ,await也是一样的
						//所以使用if就有问题,但是对于ConditionAdvanceTest1就不一样了,因为他使用的是多个Condition,唤醒的就是一个线程
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				for(int j=0;j<10;j++){
					System.out.println("sub1 thread sequece of "+j+",loop of "+i);
				}
				sub = 2;
				condition.signalAll();
			} finally {
				lock.unlock();
			}
		}
		public void sub2(int i){
			lock.lock();
			try {
				while(sub!=2){
					try {
						condition.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				for(int j=0;j<20;j++){
					System.out.println("sub2 thread sequece of "+j+",loop of "+i);
				}
				sub = 3;
				condition.signalAll();
			} finally {
				lock.unlock();
			}
		}
		public void sub3(int i){
			lock.lock();
			try {
				while(sub!=3){
					try {
						condition.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				for(int j=0;j<30;j++){
					System.out.println("sub3 thread sequece of "+j+",loop of "+i);
				}
				sub = 1;
				condition.signalAll();
			} finally {
				lock.unlock();
			}
		}
	}
}

