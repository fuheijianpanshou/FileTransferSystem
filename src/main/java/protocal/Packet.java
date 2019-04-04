package protocal;

import com.alibaba.fastjson.annotation.JSONField;

public abstract class  Packet {

    public void setVersion(){
        this.version=1;
    }
    public Byte getVersion(){
        return version;
    }
    @JSONField(deserialize = false, serialize = false)
    private Byte version = 1;
    @JSONField(serialize = false)
    public abstract Byte getCommand();
}
