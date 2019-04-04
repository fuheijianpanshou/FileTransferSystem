package client.console;

import io.netty.channel.Channel;
import protocal.request.UpFileRequstPacket;

import java.io.File;
import java.util.Scanner;

public class UpFileConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        String fileName=scanner.next();
        UpFileRequstPacket upFileRequstPacket=new UpFileRequstPacket();
        upFileRequstPacket.setFileName(fileName);
        channel.writeAndFlush(upFileRequstPacket);
        waitForResponse();
    }
    private void waitForResponse() {
        try {
            Thread.sleep(800);
        }catch (InterruptedException e){

        }
    }

}
