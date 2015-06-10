package com.satan.cimchat.model;

import java.io.Serializable;

/**
 * 消息对象
 *
 * @author @author 3979434@qq.com
 */
public class Message implements Serializable {

    /**
     * @author 3979434@qq.com
     * 消息对象
     */
    private static final long serialVersionUID = 1L;


    /**
     * 消息id
     */
    private int mid;


    /**
     * 消息类型，用户自定义消息类别
     */
    private String type;
    /**
     * 消息标题
     */
    private String title;
    /**
     * 消息类容，于type 组合为任何类型消息，content 根据 format 可表示为 text,json ,xml数据格式
     */
    private String content;

    /**
     * 消息发送者账号
     */
    private String sender;
    /**
     * 消息发送者接收者
     */
    private String receiver;

    /**
     * 文件 url
     */
    private String file;
    /**
     * 文件类型
     */
    private String fileType;


    private long timestamp;

    private int status;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }


    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }


}
