package sever.handler;

import config.Config;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocal.request.DownFileRequestPacket;
import protocal.response.DownFileResponsePacket;
import serializer.JSONSerializer;

import java.io.*;
import java.net.Socket;


@ChannelHandler.Sharable
public class DownFileRequestHandler extends SimpleChannelInboundHandler<DownFileRequestPacket> {
    public static final DownFileRequestHandler IN=new DownFileRequestHandler();
    public DownFileRequestHandler(){

    }
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DownFileRequestPacket downFileRequestPacket) throws Exception {
        String fileName= Config.currentPath+"\\"+downFileRequestPacket.getFileName();
        System.out.println("开始传输"+downFileRequestPacket.getFileName());
        waitForResponse();
        SenderFile(fileName,downFileRequestPacket,channelHandlerContext);



    }
    /**
     * 获得指定文件的byte数组
     */
//    public static byte[] file2Byte(String filePath){
//        ByteArrayOutputStream bos=null;
//        BufferedInputStream in=null;
//        try{
//            File file=new File(filePath);
//            if(!file.exists()){
//                throw new FileNotFoundException("file not exists");
//            }
//            bos=new ByteArrayOutputStream((int)file.length());
//            in=new BufferedInputStream(new FileInputStream(file));
//            int buf_size=1024;
//            byte[] buffer=new byte[buf_size];
//            int len=0;
//            while(-1 != (len=in.read(buffer,0,buf_size))){
//                bos.write(buffer,0,len);
//            }
//            return bos.toByteArray();
//        }
//        catch(Exception e){
//            System.out.println(e.getMessage());
//            e.printStackTrace();
//            return null;
//        }
//        finally{
//            try{
//                if(in!=null){
//                    in.close();
//                }
//                if(bos!=null){
//                    bos.close();
//                }
//            }
//            catch(Exception e){
//                System.out.println(e.getMessage());
//                e.printStackTrace();
//            }
//        }
//    }

    public static void SenderFile(String filePath,DownFileRequestPacket downFileRequestPacket,ChannelHandlerContext channelHandlerContext){
        File file=new File(filePath);
        DownFileResponsePacket downFileResponsePacket=new DownFileResponsePacket();
        downFileResponsePacket.setStatus(1);
        downFileResponsePacket.setSuccess(true);
        downFileResponsePacket.setFileLength((int)file.length());
        downFileResponsePacket.setFileName(downFileRequestPacket.getFileName());
        channelHandlerContext.writeAndFlush(downFileResponsePacket);
        try {
            if(!file.exists()){
                downFileResponsePacket.setSuccess(false);
                downFileResponsePacket.setFileLength((int)file.length());
                downFileResponsePacket.setFileName(downFileRequestPacket.getFileName());
                channelHandlerContext.writeAndFlush(downFileResponsePacket);
                throw new FileNotFoundException("file not exists");
            }
            Socket socket=new Socket("127.0.0.1",8999);
            BufferedInputStream bufferedInputStream=new BufferedInputStream(new FileInputStream(filePath));
            OutputStream outputStream=socket.getOutputStream();
            byte[] bytes=new byte[1024];
            int len=0;
            while ((len=bufferedInputStream.read(bytes))!=-1){
                outputStream.write(bytes,0,len);
            }
            System.out.println("文件传输完成！");
            socket.shutdownInput();
            outputStream.close();
            bufferedInputStream.close();
            socket.close();
            downFileResponsePacket.setStatus(2);
            channelHandlerContext.writeAndFlush(downFileResponsePacket);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void waitForResponse() {
        try {
            Thread.sleep(500);
        }catch (InterruptedException e){

        }
    }

}
