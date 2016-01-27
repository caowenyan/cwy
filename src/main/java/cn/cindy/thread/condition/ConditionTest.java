package cn.cindy.thread.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 子线程循环10次，接着主线程循环100次，接着又回到了子线程循环10次，接着又回到主线程循环100次，如此循环50次.
 * 注意（永远在循环（loop）里调用 wait 和 notify，不是在 If 语句）
 */
public class ConditionTest {
	static boolean sub = true;
	public static void main(String[] args) {
		advance();
	}
	
	private static void advance(){
		final Business business   = new Business();
		new Thread(new Runnable() {
			
			public void run() {
				for(int i=0;i<50;i++){
					business.sub(i);
				}
			}
		}).start();
		for(int i=0;i<50;i++){
			business.main(i);
		}
	}
	static class Business{
		boolean sub = true;
		Lock lock = new ReentrantLock();
		Condition condition = lock.newCondition();//通信的方法是await(而不是wait),signal(而不是notify)
		
		public void sub(int i){
			lock.lock();
			try {
				while(!sub){
					try {
						condition.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				for(int j=0;j<10;j++){
					System.out.println("sub thread sequece of "+j+",loop of "+i);
				}
				sub = false;
				condition.signal();
			} finally {
				lock.unlock();
			}
		}
		
		public void main(int i){
			lock.lock();
			try {
				while(sub){
					try {
						condition.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				for(int j=0;j<20;j++){
					System.out.println("main thread sequece of "+j+",loop of "+i);
				}
				sub = true;
				condition.signal();
			} finally {
				lock.unlock();
			}
		}
	}
}
