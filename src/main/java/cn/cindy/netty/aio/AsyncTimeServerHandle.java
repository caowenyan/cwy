package cn.cindy.netty.aio;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

public class AsyncTimeServerHandle implements Runnable {

	CountDownLatch latch;
	AsynchronousServerSocketChannel asynchronousServerSocketChannel;
	
	public AsyncTimeServerHandle(int port) {
		try {
			asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
			asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
			System.out.println("the time server is start in port : "+port);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public void run() {
		//完成正在执行的操作之前,允许当前的线程一直阻塞,本例中为了防止服务器退出,实际的例子中不会启动新的线程
		latch = new CountDownLatch(1);
		doAccept();
		try {
			//
			latch.await();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void doAccept() {
		asynchronousServerSocketChannel.accept(this, new AcceptCompletionHandler());
	}

}
