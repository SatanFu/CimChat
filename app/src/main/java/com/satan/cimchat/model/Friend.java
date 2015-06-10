package com.satan.cimchat.model;

/**
 * Created by Administrator on 2015/6/10.
 */
public class Friend {
    private int uid;
    private String account;
    private String userName;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
