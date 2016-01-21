package cn.cindy.thread.communication.advanced;

public class Q {
	String name = "unknow";
	String sex = "unknow";
	boolean full = false;
	
	public synchronized void put(String name, String sex){
		if(full)
			try {
				wait();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		this.name = name;
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.sex = sex;
		full = true;
		notify();
	}
	
	public synchronized void get(){
		if(!full)
			try {
				wait();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		System.out.print(name);
		System.out.println(" : "+sex);
		full = false;
		notify();
	}
}
