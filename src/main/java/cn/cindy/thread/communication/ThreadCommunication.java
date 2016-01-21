package cn.cindy.thread.communication;

public class ThreadCommunication {
	public static void main(String[] args) {
		Q q = new Q();
		new Thread(new Producer(q)).start();
		new Thread(new Consumer(q)).start();
	}
}
