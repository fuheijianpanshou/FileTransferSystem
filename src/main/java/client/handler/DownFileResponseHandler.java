package client.handler;

import config.Config;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocal.response.DownFileResponsePacket;
import serializer.JSONSerializer;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
public class DownFileResponseHandler extends SimpleChannelInboundHandler<DownFileResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DownFileResponsePacket downFileResponsePacket) throws Exception {
        if(downFileResponsePacket.isSuccess()){
            byte2File(downFileResponsePacket.getFile(),Config.clientFileRootPath,downFileResponsePacket.getFileName());
            System.out.println("yes");
        }else {
            System.out.println("文件下载失败！");
        }

    }


    /**
     * 根据byte数组，生成文件
     */
    public static void byte2File(byte[] bfile,String filePath,String fileName){
        BufferedOutputStream bos=null;
        FileOutputStream fos=null;
        File file=null;
        try{
            File dir=new File(filePath);
            if(!dir.exists() && !dir.isDirectory()){//判断文件目录是否存在
                dir.mkdirs();
            }
            file=new File(filePath+"\\"+fileName);
            fos=new FileOutputStream(file);
            bos=new BufferedOutputStream(fos);
            bos.write(bfile);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        finally{
            try{
                if(bos != null){
                    bos.close();
                }
                if(fos != null){
                    fos.close();
                }
            }
            catch(Exception e){
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }


}
