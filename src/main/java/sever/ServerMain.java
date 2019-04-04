package sever;

import codeC.PacketCodecHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import sever.handler.*;

public class ServerMain {
    public static void main(String[] args) {
        NioEventLoopGroup bosser = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bosser, worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel channel) throws Exception {
                        channel.pipeline().addLast(PacketCodecHandler.INSTANCE);
                        channel.pipeline().addLast(new LoginRequestHandler());
                        channel.pipeline().addLast(AuthHandler.INSTANCE);
                        channel.pipeline().addLast(LSRequestHandler.IN);
                        channel.pipeline().addLast(DownFileRequestHandler.IN);
                        channel.pipeline().addLast(UpFileRequestHander.IN);
                    }
                });
        bind(bootstrap,8000);
    }

    private static void bind(final ServerBootstrap serverBootstrap, final int port){
        serverBootstrap.bind(port).addListener(new GenericFutureListener() {
            public void operationComplete(Future future) throws Exception {
                if(future.isSuccess()){
                    System.out.println("绑定端口："+port+" 成功！");

                }else {
                    System.out.println("端口绑定失败！");
                }
            }
        });
    }

}
