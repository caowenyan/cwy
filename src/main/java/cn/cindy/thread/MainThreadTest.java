package cn.cindy.thread;


public class MainThreadTest {
	/*public static void main(String[] args) {
		LiftOff liftOff = new LiftOff();
		liftOff.run();
	}*/
	
	/*public static void main(String[] args) {
		Thread thread = new Thread(new LiftOff());
		thread.start();
		System.out.println("Wait for LiftOff!");
	}*/
	public static void main(String[] args) {
		for(int i=0;i<5;i++){
			new Thread(new LiftOff()).start();
		}
		System.out.println("Wait for LiftOff!");
	}
}
