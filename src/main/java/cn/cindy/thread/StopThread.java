package cn.cindy.thread;

public class StopThread {

	public static void main(String[] args) {
		RunnableStop run = new RunnableStop();
		new Thread(run).start();
		
		for(int i=0;i<100;i++){
			if(i==50){
				run.stop = true;
			}
			System.out.println(Thread.currentThread().getName() + "----" + i);
		}
	}

}
class RunnableStop implements Runnable{
	boolean stop = false;
	@Override
	public void run() {
		while(!stop){
			System.out.println(Thread.currentThread().getName());
		}
	}
}