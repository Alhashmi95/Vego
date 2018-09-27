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

    public String getnameChat() {
        return name;
    }

    public void setNameChat(String name) {
        this.name = name;
    }

    public String getMessageChat() {
        return msg;
    }

    public void setMessageChat(String msg) {
        this.msg = msg;
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

    public String getDateChat() {
        return date;
    }

    public void setDateChat(String date) {
        this.date = date;
    }
}
