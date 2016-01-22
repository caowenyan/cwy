package cn.cindy.io.properties;

import org.aeonbits.owner.ConfigFactory;

public class LoadProperties {
	public static void main(String[] args) {
		while(true){
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			OwnerProperties config = ConfigFactory.create(OwnerProperties.class);
			System.out.println("--------------------start-----------------");
			System.out.println(config.sessionFrontName());
			System.out.println(config.sessionActualName());
			System.out.println(config.sessionActionName());
			System.out.println(config.sessionMaxBorrowCount());
			System.out.println(config.sessionPoolMaxIdle());
			System.out.println(config.sessionPoolMaxTotal());
			System.out.println(config.sessionPoolMinIdle());
			System.out.println(config.sessionPoolKeptCount());
			System.out.println(config.sessionBorrowTimeout());
			System.out.println("--------------------end-----------------");
		}
	}
}
