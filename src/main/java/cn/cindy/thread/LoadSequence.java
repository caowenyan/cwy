package cn.cindy.thread;

public class LoadSequence {
	public static void main(String[] args) {
	
		Thread thread = new Thread(){
			@Override
			public void run() {
				while(true){
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("1:"+Thread.currentThread().getName());
	//				System.out.println("1:"+this.getName());//与上边显示结果等价
				}
			}
		};
		thread.start();
		
		Thread thread2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("2:"+Thread.currentThread().getName());
				}
			}
		});
		thread2.start();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("runnable:"+Thread.currentThread().getName());
				}
			}
		}){
			public void run() {
				while(true){
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("thread:"+Thread.currentThread().getName());
				}
			};
		}.start();
	}
}