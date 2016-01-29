package cn.cindy.netty.base.io.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class TimeServerHandler implements Runnable{
	private Socket socket;
	public TimeServerHandler(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			out = new PrintWriter(this.socket.getOutputStream(),true);
			String currentTime = null;
			String body = null;
			while(true){
				body = in.readLine();
				if(body==null){
					break;
				}
				System.out.println(Thread.currentThread().getName()+" -- the time server receive data : "+body);
				currentTime = "QUERY TIME".equalsIgnoreCase(body)? new Date(System.currentTimeMillis()).toString():"BAD ORDER";
				out.println(currentTime);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(in!=null)
					in.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				if(out!=null)
					out.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			try {
				if(socket!=null){
					socket.close();
					socket = null;
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

}
