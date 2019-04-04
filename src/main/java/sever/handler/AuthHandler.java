package sever.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import util.SessionUtil;

@ChannelHandler.Sharable
public class AuthHandler extends ChannelInboundHandlerAdapter {

    public static final AuthHandler INSTANCE=new AuthHandler();

    private AuthHandler(){

    }
   public void channelRead(ChannelHandlerContext channelHandlerContext,Object msg)throws Exception{
       if(!SessionUtil.hasLogin(channelHandlerContext.channel())){
           channelHandlerContext.channel().close();
       }else {
           channelHandlerContext.pipeline().remove(this);
           super.channelRead(channelHandlerContext,msg);
       }
   }
}
