package cn.cindy.thread.communication;

public class Producer implements Runnable{

	Q q;
	public Producer(Q q) {
		this.q = q;
	}
	
	@Override
	public void run() {
		int i = 0;
		while(true){
			synchronized(q){
				if(q.full)
					try {
						q.wait();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				if(i==0){
					q.name="张三";
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					q.sex="男";
				}else{
					q.name="李四";
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					q.sex="女";
				}
				i^=1;
				q.full = true;
				q.notify();
			}
		}
	}

}
