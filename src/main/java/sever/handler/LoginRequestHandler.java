package sever.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocal.request.LoginRequestPacket;
import protocal.response.LoginResponsePacket;
import session.Session;
import util.SessionUtil;

public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginRequestPacket loginRequestPacket) throws Exception {
        String userName=loginRequestPacket.getUserName();
        String userPassword=loginRequestPacket.getUserPassword();
        LoginResponsePacket loginResponsePacket=new LoginResponsePacket();
        if(userName.equals("root")&&userPassword.equals("123456")){
            SessionUtil.bindSession(new Session(userName,userPassword),channelHandlerContext.channel());
            loginResponsePacket.setSuccess(true);
            loginResponsePacket.setUserName(userName);
            loginResponsePacket.setUserPassword(userPassword);
        }else {
            loginResponsePacket.setSuccess(false);
            loginResponsePacket.setReason("账号或密码不正确！");
        }
        channelHandlerContext.writeAndFlush(loginResponsePacket);
    }
}
