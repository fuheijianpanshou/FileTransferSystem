package protocal.request;

import command.Command;
import protocal.Packet;

public class DownFileRequestPacket extends Packet {
    String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Byte getCommand() {
        return Command.DOWNFILE_REQUEST;
    }
}
