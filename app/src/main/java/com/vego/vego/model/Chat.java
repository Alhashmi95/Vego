package com.vego.vego.model;

import java.util.ArrayList;

public class Chat {
    String name;
    String msg;


    public Chat() {

    }

    public Chat(String name,String msg) {
        this.name = name;
        this.msg = msg;

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

}
