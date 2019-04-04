package client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocal.response.LSResponsePacket;

import java.io.File;
import java.util.Scanner;

public class LSResponseHandler extends SimpleChannelInboundHandler<LSResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LSResponsePacket lsResponsePacket) throws Exception {
        if(lsResponsePacket.isEmpty()){
            Scanner scanner=new Scanner(System.in);
        }else {
            File[] files=lsResponsePacket.getFiles();
            for (int i=0;i<files.length;i++){
                System.out.print(files[i].getName()+" ");
            }
            System.out.println();
        }
    }
}
