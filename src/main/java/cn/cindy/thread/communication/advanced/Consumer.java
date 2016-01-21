package cn.cindy.thread.communication.advanced;

public class Consumer implements Runnable{

	Q q;
	public Consumer(Q q) {
		this.q = q;
	}
	
	@Override
	public void run() {
		while(true){
			q.get();
		}
	}

}
