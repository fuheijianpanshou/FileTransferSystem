package client.console;

import config.Config;
import io.netty.channel.Channel;

import java.io.File;
import java.util.Scanner;

public class LSClientConsoleCommand implements ConsoleCommand{
    @Override
    public void exec(Scanner scanner, Channel channel) {
        File[] files=getFileList(Config.clientFileRootPath);
        for(int i=0;i<files.length;i++){
            System.out.print(files[i].getName()+" ");
        }
        System.out.println();

    }
    public File[] getFileList(String path){
        File file=new File(path);
        File[] arry=file.listFiles();
        return arry;
    }
}
