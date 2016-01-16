package cn.cindy.thread;

public class LiftOff implements Runnable{
	
	protected int countDown = 10;//default
	private static int taskCount = 0;
	public final static int id = taskCount++;
	
	public LiftOff() {
	}
	
	public LiftOff(int countDown) {
		this.countDown = countDown;
	}
	
	public String status(){
		return "#" + id + "(" + (countDown>0? countDown:"LiftOff!") + ").";
	}
	
	@Override
	public void run() {
		while(countDown-- > 0){
			System.out.print(status());
			Thread.yield();
		}
	}
}
