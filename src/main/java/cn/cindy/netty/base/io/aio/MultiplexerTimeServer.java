package cn.cindy.netty.base.io.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class MultiplexerTimeServer implements Runnable{
	private Selector selector;
	private ServerSocketChannel serverSocketChannel;
	private volatile boolean stop;
	
	/**
	 * 初始化多路复用器,绑定监听端口
	 */
	public MultiplexerTimeServer(int port) {
		try {
			selector = Selector.open();
			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.configureBlocking(false);
			serverSocketChannel.socket().bind(new InetSocketAddress(port),1024);//1024:请求队列的最大长度
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			System.out.println("the time server is start in port : "+port);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void stop() {
		this.stop = true;
	}
	
	public void run() {
		while(!stop){
			try {
				//设置休眠时间,无论是否有读写事件发生,selector每隔1s被唤醒一次
				//selector也提供了一个无参的select方法
				selector.select(1000);
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

	private void handleInput(SelectionKey key) throws Exception{
		if(key.isValid()){
			//处理新接入的请求消息
			if(key.isAcceptable()){
				ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
				SocketChannel sc = ssc.accept();
				sc.configureBlocking(false);
				sc.register(selector, SelectionKey.OP_READ);
			}
			if(key.isReadable()){
				//读数据
				SocketChannel sc = (SocketChannel) key.channel();
				ByteBuffer readBuffer = ByteBuffer.allocate(1024);
				int readBytes = sc.read(readBuffer);
				if(readBytes>0){
					readBuffer.flip();
					byte[]bytes = new byte[readBuffer.remaining()];
					readBuffer.get(bytes);
					String body = new String(bytes,"UTF-8");
					System.out.println("the time server receive order : "+body);
					String currentTime = "QUERY TIME".equalsIgnoreCase(body)? new Date(System.currentTimeMillis()).toString():"BAD ORDER";
					doWriter(sc,currentTime);
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

	private void doWriter(SocketChannel sc, String currentTime) throws Exception{
		if(currentTime!=null){
			byte[]bytes = currentTime.getBytes();
			ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
			writeBuffer.put(bytes);
			writeBuffer.flip();
			sc.write(writeBuffer);
		}
	}
}
