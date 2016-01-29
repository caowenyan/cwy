package cn.cindy.netty.base.io.nio;

public class TimeServer {
	
	private static int port = 8080;
	
	public static void main(String[] args) {
		//多路复用器,他是一个独立的线程,负责轮询多路复用器Selector,可以处理多个客户端的并发继入
		MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);
		new Thread(timeServer).start();
	}
}
