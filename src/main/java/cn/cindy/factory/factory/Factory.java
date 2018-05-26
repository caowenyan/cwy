package cn.cindy.factory.factory;

import cn.cindy.factory.service.AbstractMessage;
import cn.cindy.factory.service.impl.TextMessage;
import cn.cindy.factory.service.impl.VoiceMessage;
import cn.cindy.factory.bean.Bean;

/**
 * @author 曹文艳   (caowy@cloud-young.com)
 * @version V1.0
 * @description
 * @date 2018年03月30日 下午5:59
 */
public class Factory {
    public static AbstractMessage getFactoryMessageTwo(Object o){
        Bean bean = (Bean)o;
        if("text".equals(bean.getMsgType()+bean.getEvent()))
            return new TextMessage();
        if("voice".equals(bean.getMsgType()+bean.getEvent()))
            return new VoiceMessage();
        return null;
    }
    public static AbstractMessage getFactoryMessageOne(Object o){
        Bean bean = (Bean)o;
        if("text".equals(bean.getMsgType()) && "".equals(bean.getEvent()))
            return new TextMessage();
        if("voice".equals(bean.getMsgType()) && "".equals(bean.getEvent()))
            return new VoiceMessage();
        return null;
    }
}
