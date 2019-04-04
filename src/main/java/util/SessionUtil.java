package util;

import attributes.Attributes;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import session.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionUtil {

    private static final Map<String, Channel> userIdChannelMap=new ConcurrentHashMap<>();

    public static void bindSession(Session session, Channel channel){
        userIdChannelMap.put(session.getUserName(),channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    public static void unBindSession(Channel channel){
        if (hasLogin(channel)){
            Session session=getSession(channel);
            userIdChannelMap.remove(getSession(channel).getUserName());
            channel.attr(Attributes.SESSION).set(null);
            System.out.println(session+"退出登录");
        }
    }

    public static boolean hasLogin(Channel channel){
        return channel.hasAttr(Attributes.SESSION);
    }

    public static Session getSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }

}
