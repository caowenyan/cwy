package cn.cindy.thread;
public class SaleTicketThread {
	public static void main(String[] args) {
		//这只是一个线程，不是多线程
		/*Thread thread = new TestThread();
		thread.start();
		thread.start();
		thread.start();
		thread.start();*/
		
		//多线程执行
		/*Runnable runnable = new TestRunnable();
		new Thread(runnable).start();
		new Thread(runnable).start();
		new Thread(runnable).start();
		new Thread(runnable).start();*/
		
		SimulationTicketRunnable runnable = new SimulationTicketRunnable();
		new Thread(runnable).start();
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		runnable.str = "method";
		new Thread(runnable).start();
//		new Thread(runnable).start();
//		new Thread(runnable).start();
	}
}
/**
 * 模拟卖票
 * @author Caowenyan
 */
class SimulationTicketRunnable implements Runnable{
	int tickets = 100;
	String str = new String();
	@Override
	public void run() {
		if(str.equals("method")){
			while(true){
				sale();
			}
		}
		else{
			while(true){
				//参数this表示的这个类
				synchronized (this) {
	
					if(tickets>0){
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println(Thread.currentThread().getName()+":"+tickets--);
					}
				} 
			}
		}
	}
	public synchronized void sale(){
		if(tickets>0){
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.print("sale():");
			System.out.println(Thread.currentThread().getName()+":"+tickets--);
		}
	}
}