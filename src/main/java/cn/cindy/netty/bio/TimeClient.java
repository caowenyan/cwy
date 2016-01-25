package cn.cindy.netty.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TimeClient {

	private static int port = 8080;
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		Socket socket = null;
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			Scanner scanner = new Scanner(System.in);
			socket = new Socket("localhost", port);
			while(true){
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(),true);
				System.out.println("sned order to server!");
				out.println(scanner.nextLine());
				String result = in.readLine();
				System.out.println("now is "+result);
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
