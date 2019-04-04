package client;


import client.console.ConsoleCommandManager;
import client.console.LoginConsoleCommand;
import client.handler.DownFileResponseHandler;
import client.handler.LSResponseHandler;
import client.handler.LoginResponseHandler;
import client.handler.UpFileResponseHandler;
import codeC.PacketCodecHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import protocal.PacketCodeC;
import protocal.response.LoginResponsePacket;
import util.SessionUtil;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ClientMain {
    static int MAX_RETRY=5;
    static String HOST="127.0.0.1";
    static int POST=8000;
    public static void main(String[] args) {
        NioEventLoopGroup worker=new NioEventLoopGroup();
        Bootstrap bootstrap=new Bootstrap();
        bootstrap.group(worker)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY,true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,5000)
                .option(ChannelOption.SO_KEEPALIVE,true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(PacketCodecHandler.INSTANCE);
                        socketChannel.pipeline().addLast(new LoginResponseHandler());
                        socketChannel.pipeline().addLast(new LSResponseHandler());
                        socketChannel.pipeline().addLast(new DownFileResponseHandler());
                        socketChannel.pipeline().addLast(new UpFileResponseHandler());
                    }
                });
        connect(bootstrap,HOST,POST,MAX_RETRY);


    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry){
        bootstrap.connect(host,port).addListener(future->{
            if(future.isSuccess()){
                System.out.println("服务器连接建立成功！");
                Channel channel=((ChannelFuture)future).channel();
                startConsoleThread(channel);
            }else if(retry==0){
                System.out.println("服务器连接建立失败！");
            }else {
                int order = (MAX_RETRY - retry) + 1;
                int delay = 1 << order;
                System.err.println(new Date() + ":连接失败，第" + order + "次重连...");
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
            }
        });
    }

    private static void startConsoleThread(Channel channel) {

        ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
        Scanner scanner = new Scanner(System.in);
        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (!SessionUtil.hasLogin(channel)) {
                    loginConsoleCommand.exec(scanner, channel);
                } else {
                    consoleCommandManager.exec(scanner, channel);
                }
            }
        }).start();
    }



}
