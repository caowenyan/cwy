package cn.cindy.thread;

import java.util.Timer;
import java.util.TimerTask;

public class TraditionalTimer {

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
}
