package cn.cindy.netty.nio;

import java.net.InetSocketAddress;

public class TimeServer {
	
	private static int port1 = 8080;
//	private static int port2= 8081;
	
	public static void main(String[] args) {
		System.out.println(new InetSocketAddress(port1));
		//多路复用器,他是一个独立的线程,负责轮询多路复用器Selector,可以处理多个客户端的并发继入
		MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port1);
		new Thread(timeServer).start();
	}
}
