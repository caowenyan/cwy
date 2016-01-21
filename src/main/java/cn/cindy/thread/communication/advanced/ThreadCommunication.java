package cn.cindy.thread.communication.advanced;

/**
 * 主要是为了能够将对对象的操作挪到对象中
 */
public class ThreadCommunication {
	
	public static void main(String[] args) {
		Q q = new Q();
		new Thread(new Producer(q)).start();
		new Thread(new Consumer(q)).start();
	}
}
