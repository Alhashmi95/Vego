package com.vego.vego.model;

import java.util.ArrayList;

public class Chat {
    String name;
    String msg;
    String userUID;
    String createdAt;


    public Chat() {

    }

    public Chat(String name,String msg,String userUID, String createdAt) {
        this.name = name;
        this.msg = msg;
        this.userUID = userUID;
        this.createdAt = createdAt;

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
}
