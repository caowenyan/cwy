package cn.cindy.netty.base.io.fakenio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *  伪NIO编程:伪异步阻塞I/O编程
 *  相比于BIO:他不用每次都创建一个线程(特别耗费资源)来实现客户端的连接,
 *  	但是若是n个客户端不释放连接,他就会阻塞排队,下一个客户就无法正常交流
 *  Netty权威指南中讲的伪I/O的缺点有待深入了解
 */
public class TimeServer {
	
	private static int port = 8080;
	
	public static void main(String[] args) {
		ServerSocket server = null;
		try {
			server = new ServerSocket(port);
			System.out.println("the time server is start in the port in : "+port);
			Socket socket = null;
			//创建i/o任务线程池(为什么设置maximumPoolSize为3就死亡)
			TimeServerHandlerExecutePool singleExecute = new TimeServerHandlerExecutePool(4,10);
			while(true){
				socket = server.accept();
				singleExecute.execute(new TimeServerHandler(socket));
			}
		} catch(Exception e){
			e.printStackTrace();
		} finally  {
			if(server!=null)
				System.out.println("the time server close");
			try {
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			server = null;
		}
	}
}
