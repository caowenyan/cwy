package cn.cindy.netty.base.io.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 传统的BIO编程:同步阻塞I/O编程
 *  使用客户端发送数据时,是telnet ip port,中间没有":"
 */
public class TimeServer {
	
	private static int port = 8080;
	
	public static void main(String[] args) {
		ServerSocket server = null;
		try {
			server = new ServerSocket(port);
			System.out.println("the time server is start in the port in : "+port);
			Socket socket = null;
			//通过循环来监听客户端连接,若没有客户端连接,则阻塞在accet方法上
			while(true){
				socket = server.accept();
				new Thread(new TimeServerHandler(socket)).start();
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
