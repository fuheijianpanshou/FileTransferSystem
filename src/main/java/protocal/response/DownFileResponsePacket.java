package protocal.response;

import command.Command;
import protocal.Packet;

public class DownFileResponsePacket extends Packet {
    String fileName;
    int status=0;//0 发生未知错误 1 文件正在下载 2 文件下载完成
    int fileLength;
    byte[] file;
    boolean success;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

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
