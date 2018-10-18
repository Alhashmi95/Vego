package com.vego.vego.model;

import java.util.ArrayList;

public class Chat {
    String name;
    String msg;
    String userUID;
    String createdAt;
    String date;


    public Chat() {

    }

    public Chat(String name,String msg,String userUID, String createdAt, String date) {
        this.name = name;
        this.msg = msg;
        this.userUID = userUID;
        this.createdAt = createdAt;
        this.date = date;

    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
