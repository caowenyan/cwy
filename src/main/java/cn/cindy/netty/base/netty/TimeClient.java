package cn.cindy.netty.base.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TimeClient {

	 public void connect(int port, String host) throws Exception {
	        // 配置客户端NIO线程组
	        EventLoopGroup group = new NioEventLoopGroup();
	        try {
	            Bootstrap b = new Bootstrap();
	            b.group(group).channel(NioSocketChannel.class)
	                    .option(ChannelOption.TCP_NODELAY, true)
	                    .handler(new ChannelInitializer<SocketChannel>() {
	                        @Override
	                        protected void initChannel(SocketChannel socketChannel)
	                                throws Exception {
	                            System.out.println("client initChannel..");
	                            String order = "query time";
//	                            String order = "query time1";
	                            socketChannel.pipeline().addLast(new TimeClientHandler(order));
	                        }
	                    });
	            // 发起异步连接操作
	            ChannelFuture f = b.connect(host, port).sync();
	            // 等待客户端链路关闭
	            f.channel().closeFuture().sync();
	        } finally {
	            // 优雅退出，释放NIO线程组
	            group.shutdownGracefully();
	        }
	    }

	    public static void main(String[] args) throws Exception {
	        int port = 8080;
	        if (args != null && args.length > 0) {
	            try {
	                port = Integer.parseInt(args[0]);
	            } catch (Exception e) {
	            }
	        }
	        new TimeClient().connect(port, "127.0.0.1");
	    }
}