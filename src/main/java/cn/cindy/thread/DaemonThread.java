package cn.cindy.thread;

/**
 * 使用Thread建立的线程默认情况下是前台线程，在进程中，只要有一个前台线程未退出，进程就不会终止。
 * 主线程就是一个前台线程。
 * 后台线程不管线程是否结束，只要所有的前台线程都退出（包括正常退出和异常退出）后，进程就会自动终止
 * @author Caowenyan
 */
public class DaemonThread {
	public static void main(String[] args) {
		Thread thread = new SimpleThread();
		thread.setDaemon(true);//后台线程,若前台程序全部结束就退出;
											//若注释此句即为前台线程,则线程会一直运行,不管其他前台线程是否停止,默认是前台线程
		thread.start();
		System.out.println("我要结束了");
	}
}
