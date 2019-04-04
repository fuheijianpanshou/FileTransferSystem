package client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocal.response.LoginResponsePacket;
import session.Session;
import util.SessionUtil;

public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginResponsePacket loginResponsePacket) throws Exception {
        if(loginResponsePacket.isSuccess()){
            SessionUtil.bindSession(new Session(loginResponsePacket.getUserName(),loginResponsePacket.getUserPassword()),channelHandlerContext.channel());
            System.out.println("登录成功！");
        }else {
            System.out.println("登录失败，"+loginResponsePacket.getReason());
        }

    }
    public void channelInactive(ChannelHandlerContext ctx){
        System.out.println("客户端连接被关闭！");
    }
}
