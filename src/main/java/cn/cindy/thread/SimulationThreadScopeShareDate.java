package cn.cindy.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 模拟线程范围内共享数据
 */
public class SimulationThreadScopeShareDate {

	private static int data = 0;
	private static Map<Thread,Integer>threadMap = new HashMap<Thread,Integer>();
	
	public static void main(String[] args) {
		for(int i=0;i<2;i++){
			new Thread(new Runnable() {
				
				public void run() {
					int data = new Random().nextInt();//若使用静态的data会使threadMap和打印出来的不是一个对象,可能被其他线程修改了
					threadMap.put(Thread.currentThread(), data);
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName()+" has put data : "+data);
					new A().get();
					new B().get();
				}
			}).start();
		}
	}
	static class A{
		public void get(){
			int data = threadMap.get(Thread.currentThread());
			System.out.println("A "+Thread.currentThread().getName()+" get data : "+data);
		}
	}
	static class B{
		public void get(){
			int data = threadMap.get(Thread.currentThread());
			System.out.println("B "+Thread.currentThread().getName()+" get data : "+data);
		}
	}
}
