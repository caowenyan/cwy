package cn.cindy.thread;

public class ExceptionTest {
	public static void main(String[] args) {
		int count = 0;
		new Thread(new SimpleRunnable()).start();
		while(true){
			if(count++>100){
				//若是主线程抛出异常则不会影响子线程的运行
				throw new RuntimeException();
				//若是强行退出,则子线程也会停止
//				System.exit(0);
				//子线程若是设置相同的参数也会产生同样的效果
			}
			System.out.println("main:"+Thread.currentThread().getName());
		}
	}
}

class SimpleRunnable implements Runnable{
	@Override
	public void run() {
		int count = 0;
		while(true){
			if(count++>100){
				//throw new RuntimeException();
				System.exit(0);
			}
			System.out.println("runnable:"+Thread.currentThread().getName());
		}
	}
}