package cn.cindy.netty.base.io.nio;

public class TimeClient {

	private static int port = 8080;
	
	public static void main(String[] args) {
		new Thread(new TimeClientHandle("localhost",port)).start();
	}
}