package protocal.response;

import command.Command;
import protocal.Packet;

public class UpFileResponsePacket extends Packet {
    String fileName;
    boolean ready;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    @Override
    public Byte getCommand() {
        return Command.UPFILE_RESPONSE;
    }
}
