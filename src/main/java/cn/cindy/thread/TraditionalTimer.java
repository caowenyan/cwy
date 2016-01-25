package cn.cindy.thread;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TraditionalTimer {
	public static int count = 0;
	
	public static void main(String[] args) {
//		baseTimer();
		timerLoop();
	}
	
	/**
	 * 在n毫秒后调度定时器
	 */
	public static void baseTimer(){
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println("timer is running after 5 sec");
			}
		},5000);;
	}

	/**
	 * 在n毫秒后每隔m毫秒秒调度定时器
	 */
	public static void timerLoop(){
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println("timer is running every 2s after 5 sec");
			}
		},5000,2000);;
	}
	
	/**
	 * 在2s~3s之间调度定时器
	 */
	public static void advanceTimer() throws InterruptedException {
		class MyIimerTask extends TimerTask{
//			static int count = 0;//在非静态类的内部不能定义静态变量
			@Override
			public void run() {
				System.out.println("bumbing");
				count^=1;
//				if(count==0)
//				 	new Timer().schedule(new MyIimerTask(), 3000);
//				else
//					new Timer().schedule(new MyIimerTask(), 2000);
				new Timer().schedule(new MyIimerTask(), 2000+1000*count);//优化被注释的代码
			}
			
		}
		new Timer().schedule(new MyIimerTask(), 2000);
		while(true){
			System.out.println(new Date().getSeconds());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
