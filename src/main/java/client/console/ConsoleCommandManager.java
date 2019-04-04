package client.console;

import io.netty.channel.Channel;
import util.SessionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleCommandManager implements ConsoleCommand{

    private Map<String,ConsoleCommand> consoleCommandMap;
    public ConsoleCommandManager(){
        consoleCommandMap=new HashMap<>();
        consoleCommandMap.put("ls",new LSConsoleCommand());
        consoleCommandMap.put("down",new DownFileConsoleCommand());
        consoleCommandMap.put("up",new UpFileConsoleCommand());
        consoleCommandMap.put("lsclient",new LSClientConsoleCommand());
    }
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print(SessionUtil.getSession(channel).getUserName()+":");
        String command=scanner.next();
        if (!SessionUtil.hasLogin(channel)){
            return;
        }
        ConsoleCommand consoleCommand=consoleCommandMap.get(command);

        if (consoleCommand!=null){
            consoleCommand.exec(scanner,channel);
        }else {
            System.err.println("无法识别["+command+"]指令，请重新输入！");
        }

    }
}
