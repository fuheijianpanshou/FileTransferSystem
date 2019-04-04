package protocal.request;

import command.Command;
import protocal.Packet;

public class LSRequestPacket extends Packet {
    String currentPath;

    public String getCurrentPath() {
        return currentPath;
    }

    public void setCurrentPath(String currentPath) {
        this.currentPath = currentPath;
    }

    @Override
    public Byte getCommand() {
        return Command.LS_REQUEST;
    }
}
