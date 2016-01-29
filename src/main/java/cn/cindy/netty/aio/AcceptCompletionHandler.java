package cn.cindy.netty.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AsyncTimeServerHandle> {

	@Override
	public void completed(AsynchronousSocketChannel result, AsyncTimeServerHandle attachment) {
		//当我们调用AsynchronousServerSocketChannel的accept方法之后,
		//如果有新的客户端连接接入,系统将要回调我们传入的CompletionHandler实例的completed,表示新的客户端已经创建成功
		attachment.asynchronousServerSocketChannel.accept(attachment, this);
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		result.read(buffer, buffer, new ReadCompletionHandler(result));
	}

	@Override
	public void failed(Throwable exc, AsyncTimeServerHandle attachment) {
		exc.printStackTrace();
		attachment.latch.countDown();

	}
}
