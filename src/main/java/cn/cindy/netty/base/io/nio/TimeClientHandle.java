package cn.cindy.netty.base.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class TimeClientHandle implements Runnable{
	private String ip;
	private Integer port;
	private Selector selector;
	private SocketChannel socketChannel;
	private volatile boolean stop;
	
	/**
	 * 初始化多路复用器,绑定监听端口
	 */
	public TimeClientHandle(String ip, int port) {
		try {
			this.ip = ip;
			this.port = port;
			selector = Selector.open();
			socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(false);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void stop() {
		this.stop = true;
	}
	
	public void run() {
		try {
			doConnection();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		while(!stop){
			try {
				selector.select(1000);//设置超时时间
				Set<SelectionKey> set = selector.selectedKeys();
				Iterator<SelectionKey>iterable = set.iterator();
				SelectionKey key = null;
				while(iterable.hasNext()){
					key = iterable.next();
					iterable.remove();
					try{
						handleInput(key);
					}catch(Exception e){
						if(key!=null){
							key.cancel();
							if(key.channel()!=null)
								key.channel().close();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//多路复用器关闭之后,所有注册在上边的channel和pipe等资源都会去被自动去注册或是关闭,所以不需要重复释放资源
		if(selector!=null){
			try {
				selector.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void doConnection() throws Exception{
		//如果直接连接成功,则注册到多路复用器上, 发送请求消息,读应答
		if(socketChannel.connect(new InetSocketAddress(ip, port))){
			socketChannel.register(selector, SelectionKey.OP_READ);
			doWrite(socketChannel);
		}else{
			socketChannel.register(selector, SelectionKey.OP_CONNECT);
		}
	}

	private void handleInput(SelectionKey key) throws Exception{
		if(key.isValid()){
			//判断是否连接成功
			SocketChannel sc = (SocketChannel) key.channel();
			if(key.isConnectable()){
				if(sc.finishConnect()){
					sc.register(selector, SelectionKey.OP_READ);
					doWrite(sc);
				}else{
					System.exit(1);//连接失败,进程退出
				}
			}
			if(key.isReadable()){
				//读数据
				ByteBuffer readBuffer = ByteBuffer.allocate(1024);
				int readBytes = sc.read(readBuffer);
				if(readBytes>0){
					readBuffer.flip();
					byte[]bytes = new byte[readBuffer.remaining()];
					readBuffer.get(bytes);
					String body = new String(bytes,"UTF-8");
					System.out.println("now is : "+body);
					this.stop = true;
				}else if(readBytes<0){
					//对链路端关闭
					key.cancel();
					sc.close();
				}else{
					//读到0字节,忽略
				}
			}
		}
	}

	private void doWrite(SocketChannel socketChannel2)  throws Exception{
		byte[]req = "QUERY TIME".getBytes();
		ByteBuffer writerBuffer = ByteBuffer.allocate(req.length);
		writerBuffer.put(req);
		writerBuffer.flip();
		socketChannel2.write(writerBuffer);
		if(!writerBuffer.hasRemaining()){
			System.out.println("send order to server success");
		}
	}
}
