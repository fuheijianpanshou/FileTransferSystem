package sever.handler;

import config.Config;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocal.request.DownFileRequestPacket;
import protocal.response.DownFileResponsePacket;
import serializer.JSONSerializer;

import java.io.*;


@ChannelHandler.Sharable
public class DownFileRequestHandler extends SimpleChannelInboundHandler<DownFileRequestPacket> {
    public static final DownFileRequestHandler IN=new DownFileRequestHandler();
    public DownFileRequestHandler(){

    }
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DownFileRequestPacket downFileRequestPacket) throws Exception {
        String fileName= Config.currentPath+"\\"+downFileRequestPacket.getFileName();
        byte[] files=file2Byte(fileName);
        DownFileResponsePacket downFileResponsePacket=new DownFileResponsePacket();
        if(files.length>0){
            downFileResponsePacket.setSuccess(true);
            downFileResponsePacket.setFileLength(files.length);
            downFileResponsePacket.setFileName(downFileRequestPacket.getFileName());
            downFileResponsePacket.setFile(files);
            System.out.println("传输文件"+downFileRequestPacket.getFileName());
        }else {
            downFileResponsePacket.setSuccess(false);
        }
        channelHandlerContext.writeAndFlush(downFileResponsePacket);


    }
    /**
     * 获得指定文件的byte数组
     */
    public static byte[] file2Byte(String filePath){
        ByteArrayOutputStream bos=null;
        BufferedInputStream in=null;
        try{
            File file=new File(filePath);
            if(!file.exists()){
                throw new FileNotFoundException("file not exists");
            }
            bos=new ByteArrayOutputStream((int)file.length());
            in=new BufferedInputStream(new FileInputStream(file));
            int buf_size=1024;
            byte[] buffer=new byte[buf_size];
            int len=0;
            while(-1 != (len=in.read(buffer,0,buf_size))){
                bos.write(buffer,0,len);
            }
            return bos.toByteArray();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
        finally{
            try{
                if(in!=null){
                    in.close();
                }
                if(bos!=null){
                    bos.close();
                }
            }
            catch(Exception e){
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

}
