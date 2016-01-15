package cn.cindy.thread;

/**
 * Thread.join():调用该方法的thread完成run方法里面的东西后， 再执行join()方法后面的代码.
 * Thread.join(long millis):调用该方法的thread执行run方法里面的东西millis后， 再执行join()方法后面的代码.
 * Thread.join(long millis, int nanos):调用该方法的thread执行run方法里面的东西millis+(0/1)后， 再执行join()方法后面的代码.
 * @author Caowenyan
 */
public class JoinThread {

	static int count = 0;
	
	public static void main(String[] args) {
		Thread thread = new SimpleThread();
		thread.start();
		while(true){
			if(count++== 100){
				try {
					thread.join(5000,5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("main() in " + Thread.currentThread().getName());
		}
	}
}

//以下是网上的例子,感觉出发点很新奇
class ThreadTesterA implements Runnable {

	private int counter;

	public void run() {
		while (counter <= 10) {
			System.out.print("Counter = " + counter + " ");
			counter++;
		}
		System.out.println();
	}
}

class ThreadTesterB implements Runnable {

	private int i;

	public void run() {
		while (i <= 10) {
			System.out.print("i = " + i + " ");
			i++;
		}
		System.out.println();
	}
}

class ThreadTester {
	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(new ThreadTesterA());
		Thread t2 = new Thread(new ThreadTesterB());
		t1.start();
		t1.join(); // wait t1 to be finished
		t2.start();
		t2.join(); // in this program, this may be removed
	}
}