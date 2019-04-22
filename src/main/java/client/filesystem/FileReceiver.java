package client.filesystem;

import config.Config;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import protocal.request.DownFileRequestPacket;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FileReceiver {
    private String fileName;
    private Channel channel;
    DownFileRequestPacket downFileRequestPacket;
    public FileReceiver(String fileName,Channel channel,DownFileRequestPacket downFileRequestPacket){
        this.downFileRequestPacket=downFileRequestPacket;
        this.channel=channel;
        this.fileName=fileName;
        this.channel.writeAndFlush(this.downFileRequestPacket);
    }


    public void ReceiveFile(){
        try {
            ServerSocket serverSocket=new ServerSocket(8999);
            Socket socket=serverSocket.accept();
            new rcecive(socket).run();
            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    class rcecive implements Runnable{
        private Socket s;
        public rcecive(Socket s){
            this.s=s;
        }

        @Override
        public void run() {
            try {
                BufferedInputStream bin = new BufferedInputStream(s.getInputStream());
                File dir = new File(Config.clientFileRootPath);

                if(!dir.exists()){
                    dir.mkdir();//文件夹不存在,创建mypic文件夹
                }
                FileOutputStream fileOutputStream=new FileOutputStream(new File(Config.clientFileRootPath+"\\"+fileName));
                byte[] bytes=new byte[1024];
                int len=0;
                while ((len=bin.read(bytes))!=-1){
                    fileOutputStream.write(bytes,0,len);
                }
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}
