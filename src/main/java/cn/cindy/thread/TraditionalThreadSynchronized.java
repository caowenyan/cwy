package cn.cindy.thread;


public class TraditionalThreadSynchronized {

	public static void main(String[] args) {
		final Outputer outputer = new Outputer();
		new Thread(new Runnable() {

			public void run() {
				while(true){
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					outputer.outpet("caowenyan");
				}
			}
			
			
		}).start();
		new Thread(new Runnable() {

			public void run() {
				while(true){
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					outputer.outpet("hema");
					//换成一个新的对象就无法实现同步了
//					new Outputer().outpet("liwei");
				}
			}
			
			
		}).start();
	}

	static class Outputer {
		/*public void outpet(String value) {
			synchronized(this){
				for (int i = 0; i < value.length(); i++) {
					System.out.print(value.charAt(i));
				}
				System.out.println();
			}
		}*/
		public synchronized void outpet(String value) {
			for (int i = 0; i < value.length(); i++) {
				System.out.print(value.charAt(i));
			}
			System.out.println();
		}
	}

}
