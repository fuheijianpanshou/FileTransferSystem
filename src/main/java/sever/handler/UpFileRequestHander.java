package sever.handler;

import client.filesystem.UdpSender;
import config.Config;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocal.request.UpFileRequstPacket;
import protocal.response.UpFileResponsePacket;
import sever.filesystem.UdpServer;
import sun.nio.ch.ThreadPool;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@ChannelHandler.Sharable
public class UpFileRequestHander extends SimpleChannelInboundHandler<UpFileRequstPacket> {
    public static final UpFileRequestHander IN=new UpFileRequestHander();
    ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, UpFileRequstPacket upFileRequstPacket) throws Exception {
        UpFileResponsePacket upFileResponsePacket=new UpFileResponsePacket();
        upFileResponsePacket.setFileName(upFileRequstPacket.getFileName());
        UdpServer udpServer=new UdpServer("192.168.43.186",8001,8002, Config.serverFileRootPath+"\\"+upFileRequstPacket.getFileName());
        executor.execute(udpServer);
        upFileResponsePacket.setReady(true);
        channelHandlerContext.writeAndFlush(upFileResponsePacket);

    }
}
