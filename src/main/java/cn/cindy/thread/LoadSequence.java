package cn.cindy.thread;

/**
 * 父类的静态代码块—>子类的静态代码块—>主方法（执行哪个程序就执行哪个程序的主方法）
 * 		—>父类的非静态代码块—>父类的无参构造函数—>子类的非静态代码块
 * 		—>子类的无参构造函数（若实际子类执行的是有参构造函数，则不执行无参构造函数）
 * 		—>成员函数（指定执行哪个就执行哪个成员函数，若重写了父类成员函数，则只执行子类的成员函数）
 */
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
					//不能用this.getName(),因为这个对象是Runnable,而不是Thread,所以一般不会使用this来获取当前线程的名称
				}
			}
		});
		thread2.start();
		
		//子类Thread有run方法,就不会调用父类的run方法,若子类无此方法就会调用父类的方法
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