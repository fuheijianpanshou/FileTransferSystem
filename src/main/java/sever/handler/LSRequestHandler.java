package sever.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocal.request.LSRequestPacket;
import protocal.response.LSResponsePacket;
import sever.filesystem.ServerFileDirs;

import java.io.File;
@ChannelHandler.Sharable
public class LSRequestHandler extends SimpleChannelInboundHandler<LSRequestPacket> {
    public static final LSRequestHandler IN=new LSRequestHandler();
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LSRequestPacket lsRequestPacket) throws Exception {
        File[] files=ServerFileDirs.IN.getFileList(lsRequestPacket.getCurrentPath());
        LSResponsePacket responsePacket=new LSResponsePacket();
        if(files!=null){
            responsePacket.setEmpty(false);
            responsePacket.setFiles(files);
        }else {
            responsePacket.setEmpty(true);
        }
        channelHandlerContext.writeAndFlush(responsePacket);
    }
}
