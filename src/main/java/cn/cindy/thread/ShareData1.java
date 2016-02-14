package cn.cindy.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * 目的：初步了解线程范围内共享变量的概念
 * 有点问题：
 *      有时会输出Null
 * 问题的原因：
 *         可能HashMap线程不安全导致的
 * 但是一直为测试出来,也没想得到
 */
public class ShareData1 {
	// static int data = 0;
	static Map<Thread, Integer> map = new HashMap<Thread, Integer>();
	public static void main(String[] args) {
		ExecutorService ex = Executors.newFixedThreadPool(2);
		for (int i = 0; i < 10000; i++) {
			ex.submit(new Runnable() {
				public void run() {
					int data = new Random().nextInt();
                    System.out.println(Thread.currentThread().getName()+":"+data);
                    map.put(Thread.currentThread(), data);
                    new A().getDataA();
                    try{
                    	Thread.sleep(1);
                    }catch(Exception e){
                    	e.printStackTrace();
                    }
                    new B().getDataB();
				}
			});
		}
	}

	static class A {
		void getDataA() {
			System.out.println(Thread.currentThread().getName() + "A:"
					+ map.get(Thread.currentThread()));
		}
	}

	static class B {
		void getDataB() {
			System.out.println(Thread.currentThread().getName() + "B:"
					+ map.get(Thread.currentThread()));
		}
	}
}