package cn.cindy.thread.threadlocal;

import java.util.Random;

/**
 * 一个ThreadLocal代表一个变量，故其中只能放一个数据，你有两个变量都要线程范围内共享，则定义两个ThreadLocal变量
 * 若有多个变量需要线程共享，那就定义一个对象来封装这些变量，在ThreadLocal中放此对象即可。
 */
public class ThreadLocalObject {
	
	public static void main(String[] args) {
		objectInstance();
	}
	
	
	public static void objectInstance(){
		for(int i=0;i<2;i++){
			new Thread(new Runnable() {
				
				public void run() {
					int data = new Random().nextInt();//若使用开始的data会使threadMap和打印出来的不是一个对象
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					MyThreadScopeData.getThreadInstance().setAge(data);
					MyThreadScopeData.getThreadInstance().setName("name"+data);
					System.out.println(Thread.currentThread().getName()+" has put data : "+data);
					new A().get();
					new B().get();
				}
			}).start();
		}
	}
	static class A{
		public void get(){
			MyThreadScopeData data = MyThreadScopeData.getThreadInstance();
			System.out.println("A "+Thread.currentThread().getName()+" get name : "+data.getName()+", get age : "+data.getAge());
		}
	}
	static class B{
		public void get(){
			MyThreadScopeData data = MyThreadScopeData.getThreadInstance();
			System.out.println("B "+Thread.currentThread().getName()+" get name : "+data.getName()+", get age : "+data.getAge());
		}
	}

}
