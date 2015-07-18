package com.satan.cimchat.util;

import com.satan.cimchat.model.Message;

/**
 * 转换工具类
 * Created by satan on 2015/7/18.
 */
public class ChangeUtil {


    /**
     * 将我自定义的消息转换成Nio消息
     *
     * @param message
     * @return
     */
    public static Message NioMsgToMyMsg(com.satan.cimchat.core.nio.mutual.Message message) {
        Message msg = new Message();
        if (!StringUtil.isEmpty(message.getMid())) {
            msg.setId(Long.valueOf(message.getMid()));
        }
        msg.setContent(message.getContent());
        msg.setFile(message.getFile());
        msg.setFileType(message.getFileType());
        msg.setReceiver(message.getReceiver());
        msg.setSender(message.getSender());
        msg.setTime(message.getTimestamp());
        msg.setTitle(message.getTitle());
        msg.setType(message.getType());
        return msg;
    }

    /**
     * 将Nio消息转换成我自定义的消息
     *
     * @param message
     * @return
     */
    public static com.satan.cimchat.core.nio.mutual.Message MyMsgToNioMsg(Message message) {
        com.satan.cimchat.core.nio.mutual.Message msg = new com.satan.cimchat.core.nio.mutual.Message();
        msg.setMid(String.valueOf(message.getId()));
        msg.setContent(message.getContent());
        msg.setFile(message.getFile());
        msg.setFileType(message.getFileType());
        msg.setReceiver(message.getReceiver());
        msg.setSender(message.getSender());
        msg.setTimestamp(message.getTime());
        msg.setTitle(message.getTitle());
        msg.setType(message.getType());
        return msg;
    }

}
