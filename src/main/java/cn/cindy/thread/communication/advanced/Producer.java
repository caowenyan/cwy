package cn.cindy.thread.communication.advanced;

public class Producer implements Runnable {

	Q q;

	public Producer(Q q) {
		this.q = q;
	}

	@Override
	public void run() {
		int i = 0;
		while (true) {
			if (i == 0) {
				q.put("张三", "男");
			} else {
				q.put("李四", "女");
			}
			i ^= 1;
		}
	}

}
