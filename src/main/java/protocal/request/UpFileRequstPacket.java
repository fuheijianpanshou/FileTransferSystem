package protocal.request;

import command.Command;
import protocal.Packet;

public class UpFileRequstPacket extends Packet {
    String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Byte getCommand() {
        return Command.UPFILE_REQUEST;
    }
}
