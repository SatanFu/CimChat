package com.satan.cimchat.model;

/**
 * Created by Administrator on 2015/5/31.
 */
public class ServerMessage {

    private String status;
    private String message;
    private String data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
