package cn.cindy.netty.pack.fixed;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeServerHandle extends ChannelInboundHandlerAdapter {
	private int counter;
	
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String body = (String) msg;
		System.out.println("The time server receive order : " + body + " ; the counter is : " + ++counter);
		String currentTime = "QUERY TIME".equalsIgnoreCase(body)
				? new java.util.Date(System.currentTimeMillis()).toString() : "BAD ORDER";
		ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
		ctx.writeAndFlush(resp);
	}

	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();// 刷新后才将数据发出到SocketChannel
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
	}
}
