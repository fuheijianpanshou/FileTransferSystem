package serializer;



public interface Serializer {
    Serializer DEFAULT= new JSONSerializer();
    byte JSON_SERIALIZER=1;

    /**
     * 序列化算法
     *
     */
    byte getSerializerAlogrithm();

    /**
     * java对象转二进制
     */
    byte[] serialize(Object object);

    /**
     * 二进制转java对象
     */
    <T> T deserialize(Class<T> clazz,byte[] bytes);
}
