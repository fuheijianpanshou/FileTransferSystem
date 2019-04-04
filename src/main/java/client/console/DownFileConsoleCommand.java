package client.console;

import io.netty.channel.Channel;
import protocal.request.DownFileRequestPacket;

import java.util.Scanner;

public class DownFileConsoleCommand implements ConsoleCommand{
    @Override
    public void exec(Scanner scanner, Channel channel) {
        String fileName=scanner.next();
        DownFileRequestPacket downFileRequestPacket=new DownFileRequestPacket();
        if(fileName!=null){
            downFileRequestPacket.setFileName(fileName);
        }
        channel.writeAndFlush(downFileRequestPacket);
        waitForResponse();
    }
    private void waitForResponse() {
        try {
            Thread.sleep(1500);
        }catch (InterruptedException e){

        }
    }
}
