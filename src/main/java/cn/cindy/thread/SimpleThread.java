package cn.cindy.thread;

public class SimpleThread extends Thread{
	@Override
	public void run() {
		while(true){
			System.out.println("run in " + Thread.currentThread().getName());
		}
	}
}
