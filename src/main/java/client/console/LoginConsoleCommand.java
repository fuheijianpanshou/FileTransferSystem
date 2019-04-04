package client.console;

import io.netty.channel.Channel;
import protocal.request.LoginRequestPacket;

import java.util.Scanner;

public class LoginConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        LoginRequestPacket loginResponsePacket=new LoginRequestPacket();

        System.out.println("输入用户名：");
        loginResponsePacket.setUserName(scanner.nextLine());
        System.out.println("请输入密码：");
        loginResponsePacket.setUserPassword(scanner.nextLine());

        //发送登录包
        channel.writeAndFlush(loginResponsePacket);
        waitForLoginResponse();
    }

    private void waitForLoginResponse() {

        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){

        }
    }
}
