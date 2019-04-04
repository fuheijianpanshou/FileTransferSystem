package protocal.response;

import command.Command;
import protocal.Packet;

public class DownFileResponsePacket extends Packet {
    String fileName;
    int fileLength;
    byte[] file;
    boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getFileLength() {
        return fileLength;
    }

    public void setFileLength(int fileLength) {
        this.fileLength = fileLength;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    @Override
    public Byte getCommand() {
        return Command.DOWNFILE_RESPONSE;
    }
}
