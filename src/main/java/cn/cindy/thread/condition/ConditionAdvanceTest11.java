package cn.cindy.thread.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程1循环10次，接着线程2循环20次，接着线程3循环30次，如此循环50次.
 * 不同于1,他也是sub1~sub3交替运行,<b>但是经常出现一下的情况</b>:
 * sub2 thread sequece of 19,loop of 0
 * sub3 thread sequece of 0,loop of 1
 * 注意（永远在循环（loop）里调用 wait 和 notify，不是在 If 语句）
 */
public class ConditionAdvanceTest11 {
	public static void main(String[] args) {
		final Business business   = new Business();
		for(int i=0;i<50;i++){
			final int task = i;
			new Thread(new Runnable() {
				
				public void run() {
						business.sub2(task);
				}
			}).start();
			new Thread(new Runnable() {
				
				public void run() {
						business.sub3(task);
				}
			}).start();
			business.sub1(i);
		}
	}
	static class Business{
		int sub = 1;
		Lock lock = new ReentrantLock();
		Condition condition1 = lock.newCondition();
		Condition condition2 = lock.newCondition();
		Condition condition3 = lock.newCondition();
		
		public void sub1(int i){
			lock.lock();
			try {
				while(sub!=1){
					try {
						condition1.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				for(int j=0;j<10;j++){
					System.out.println("sub1 thread sequece of "+j+",loop of "+i);
				}
				sub = 2;
				condition2.signal();
			} finally {
				lock.unlock();
			}
		}
		public void sub2(int i){
			lock.lock();
			try {
				while(sub!=2){
					try {
						condition2.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				for(int j=0;j<20;j++){
					System.out.println("sub2 thread sequece of "+j+",loop of "+i);
				}
				sub = 3;
				condition3.signal();
			} finally {
				lock.unlock();
			}
		}
		public void sub3(int i){
			lock.lock();
			try {
				while(sub!=3){
					try {
						condition3.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				for(int j=0;j<30;j++){
					System.out.println("sub3 thread sequece of "+j+",loop of "+i);
				}
				sub = 1;
				condition1.signal();
			} finally {
				lock.unlock();
			}
		}
	}
}

