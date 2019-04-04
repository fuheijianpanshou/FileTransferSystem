package protocal;

import io.netty.buffer.ByteBuf;
import protocal.request.DownFileRequestPacket;
import protocal.request.LSRequestPacket;
import protocal.request.LoginRequestPacket;
import protocal.request.UpFileRequstPacket;
import protocal.response.DownFileResponsePacket;
import protocal.response.LSResponsePacket;
import protocal.response.LoginResponsePacket;
import protocal.response.UpFileResponsePacket;
import serializer.JSONSerializer;
import serializer.Serializer;

import java.util.HashMap;
import java.util.Map;

import static command.Command.*;

public class PacketCodeC {
    public static final int MAGIC_NUMBER = 0x12345678;
    private  final Map<Byte,Class<? extends Packet>> packetTypeMap ;
    private  final  Map<Byte, Serializer> seializerMap;
    public static  final PacketCodeC INSTANCE=new PacketCodeC();
    private PacketCodeC(){
        packetTypeMap=new HashMap<>();
        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(LS_REQUEST, LSRequestPacket.class);
        packetTypeMap.put(LS_RESPONSE, LSResponsePacket.class);
        packetTypeMap.put(DOWNFILE_REQUEST, DownFileRequestPacket.class);
        packetTypeMap.put(DOWNFILE_RESPONSE, DownFileResponsePacket.class);
        packetTypeMap.put(UPFILE_REQUEST, UpFileRequstPacket.class);
        packetTypeMap.put(UPFILE_RESPONSE,UpFileResponsePacket.class);

        seializerMap=new HashMap<>();
        Serializer serializer=new JSONSerializer();
        seializerMap.put(serializer.getSerializerAlogrithm(),serializer);
    }
    public void encode(ByteBuf byteBuf, Packet packet){
        //1.创建ByteBuf对象
        //  ByteBuf byteBuf= byteBufAllocator.ioBuffer();
        //2.序列化java对象
        byte[] bytes=Serializer.DEFAULT.serialize(packet);
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlogrithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }

    public Packet decode(ByteBuf byteBuf){
        //跳过 magic number

        byteBuf.skipBytes(4);

        //跳过版本号
        byteBuf.skipBytes(1);

        //序列化算法
        byte serializeAlgorithm=byteBuf.readByte();

        byte command=byteBuf.readByte();

        //数据包长度

        int lenth=byteBuf.readInt();

        byte[] bytes=new byte[lenth];

        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType=getRequestType(command);

        Serializer serializer=getSerializer(serializeAlgorithm);

        if (requestType!=null&&serializer!=null){
            return serializer.deserialize(requestType,bytes);
        }

        return null;

    }

    private Serializer getSerializer(byte serializeAlgorithm) {
        return seializerMap.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {

        return packetTypeMap.get(command);
    }
}
