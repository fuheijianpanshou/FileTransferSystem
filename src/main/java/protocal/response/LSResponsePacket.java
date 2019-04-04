package protocal.response;

import command.Command;
import protocal.Packet;

import java.io.File;

public class LSResponsePacket extends Packet {
    File[] files;
    boolean empty;

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public File[] getFiles() {
        return files;
    }

    public void setFiles(File[] files) {
        this.files = files;
    }

    @Override
    public Byte getCommand() {
        return Command.LS_RESPONSE;
    }
}
