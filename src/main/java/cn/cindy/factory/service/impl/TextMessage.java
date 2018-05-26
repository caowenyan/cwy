package cn.cindy.factory.service.impl;

import cn.cindy.factory.annotation.Message;
import cn.cindy.factory.service.AbstractMessage;

/**
 * @author 曹文艳   (caowy@cloud-young.com)
 * @version V1.0
 * @description
 * @date 2018年03月30日 下午6:22
 */
@Message(MsgType = "text")
public class TextMessage extends AbstractMessage {
    @Override
    public void f() {
        System.out.println("text");
    }
}
