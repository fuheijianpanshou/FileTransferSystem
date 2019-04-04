package client.handler;

import client.filesystem.UdpSender;
import config.Config;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocal.response.UpFileResponsePacket;

public class UpFileResponseHandler extends SimpleChannelInboundHandler<UpFileResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, UpFileResponsePacket upFileResponsePacket) throws Exception {
        if(upFileResponsePacket.isReady()){
            waitForResponse();
            UdpSender udpSender=new UdpSender(8001,8002, Config.clientFileRootPath+"\\"+upFileResponsePacket.getFileName(),"192.168.43.186");
            udpSender.run();
        }else {
            System.out.println("服务器就绪失败！");
        }
    }
    private void waitForResponse() {
        try {
            Thread.sleep(2000);
        }catch (InterruptedException e){

        }
    }
}
