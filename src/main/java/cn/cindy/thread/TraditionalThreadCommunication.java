package cn.cindy.thread;

/**
 * 子线程循环10次，接着主线程循环100次，接着又回到了子线程循环10次，接着又回到主线程循环100次，如此循环50次.
 * 注意（永远在循环（loop）里调用 wait 和 notify，不是在 If 语句）
 */
public class TraditionalThreadCommunication {
	static boolean sub = true;
	public static void main(String[] args) {
//		base();
		advance();
	}
	
	private static void base(){
		new Thread(new Runnable() {
			
			public void run() {
				for(int i=0;i<50;i++){
					synchronized(TraditionalThreadCommunication.class){
						if(!sub){
							try {
								TraditionalThreadCommunication.class.wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						for(int j=0;j<10;j++){
							System.out.println("sub thread sequece of "+j+",loop of "+i);
						}
						sub = false;
						TraditionalThreadCommunication.class.notify();
					}
				}
			}
		}).start();
		for(int i=0;i<50;i++){
			synchronized(TraditionalThreadCommunication.class){
				if(sub){
					try {
						TraditionalThreadCommunication.class.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				for(int j=0;j<20;j++){
					System.out.println("main thread sequece of "+j+",loop of "+i);
				}
				sub = true;
				TraditionalThreadCommunication.class.notify();
			}
		}
	}

	/**
	 * 要用到共同数据（包括同步锁）或共同算法的若干个方法应该归在同一个类的方法上，这种设计正好体验了高类聚和程序的健壮性
	 */
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
}

class Business{
	boolean sub = true;
	
	public synchronized void sub(int i){
		//一个线程可以同时醒来，没有被通知，中断，超时，那么calledspurious唤醒。
		//虽然这将很少发生在实践中，应用程序必须防止它通过测试的情况下，应该已经导致线程被唤醒，并继续等待
		while(!sub){
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for(int j=0;j<10;j++){
			System.out.println("sub thread sequece of "+j+",loop of "+i);
		}
		sub = false;
		this.notify();
	}
	
	public synchronized void main(int i){
		while(sub){
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for(int j=0;j<20;j++){
			System.out.println("main thread sequece of "+j+",loop of "+i);
		}
		sub = true;
		this.notify();
	}
}
