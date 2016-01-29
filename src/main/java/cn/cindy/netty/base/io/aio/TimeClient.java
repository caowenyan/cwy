package cn.cindy.netty.base.io.aio;

public class TimeClient {

	private static int port = 8080;
	
	public static void main(String[] args) {
		new Thread(new AsyncTimeClientHandler("localhost",port)).start();
	}
}