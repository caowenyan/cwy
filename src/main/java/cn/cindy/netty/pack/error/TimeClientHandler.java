package cn.cindy.netty.pack.error;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeClientHandler extends ChannelInboundHandlerAdapter {
	private int counter;
	private final ByteBuf firstMessage;
	private byte[] req;
	
	public TimeClientHandler(String message) {
		message = message+System.getProperty("line.separator");
		req = message.getBytes();
        firstMessage = Unpooled.buffer(req.length);
        firstMessage.writeBytes(req);
	}
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		 //与服务端建立连接后
		ByteBuf buf = null;
		for (int i = 0; i < 100; i++) {
			buf = Unpooled.buffer(req.length);
			buf.writeBytes(req);
			ctx.writeAndFlush(buf);
		}
	}

	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //服务端返回消息后
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, "UTF-8");
        System.out.println("Now is : " + body + " ; the counter is : " + ++counter);
	}
	
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// 释放资源
		cause.printStackTrace();
		ctx.close();
	}

}
