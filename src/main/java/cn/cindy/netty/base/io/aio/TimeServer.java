package cn.cindy.netty.base.io.aio;

public class TimeServer {
	
	private static int port = 8080;
	
	public static void main(String[] args) {
		AsyncTimeServerHandle timeServer = new AsyncTimeServerHandle(port);
		new Thread(timeServer).start();
	}
}
