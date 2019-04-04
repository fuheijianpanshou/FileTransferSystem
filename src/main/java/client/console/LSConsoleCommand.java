package client.console;

import config.Config;
import io.netty.channel.Channel;
import protocal.request.LSRequestPacket;

import java.util.Scanner;

public class LSConsoleCommand implements ConsoleCommand{
    @Override
    public void exec(Scanner scanner, Channel channel) {
        LSRequestPacket lsRequestPacket=new LSRequestPacket();
        lsRequestPacket.setCurrentPath(Config.currentPath);
        channel.writeAndFlush(lsRequestPacket);
        waitForResponse();
    }
    private void waitForResponse() {
        try {
            Thread.sleep(1500);
        }catch (InterruptedException e){

        }
    }
}
