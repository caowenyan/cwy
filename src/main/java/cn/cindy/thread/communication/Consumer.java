package cn.cindy.thread.communication;

public class Consumer implements Runnable{

	Q q;
	public Consumer(Q q) {
		this.q = q;
	}
	
	@Override
	public void run() {
		while(true){
			synchronized(q){
				if(!q.full)//必须是同步字段的wait与notify
					try {
						q.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				System.out.print(q.name );
				System.out.println( " : " + q.sex );
				q.full=false;
				q.notify();
			}
		}
	}

}
