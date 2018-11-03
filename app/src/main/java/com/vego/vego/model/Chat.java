package com.vego.vego.model;

import java.util.ArrayList;

public class Chat {
    String sendername;
    String msg;
    String senderId;
    String date;
    String tokenSender;
    String tokenReceiver;


    public Chat() {

    }

    public Chat(String sendername,String msg,String senderId, String date,String tokenReceiver
                ,String tokenSender) {
        this.sendername = sendername;
        this.msg = msg;
        this.senderId = senderId;
        this.date = date;
        this.tokenSender = tokenSender;
        this.tokenReceiver = tokenReceiver;

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

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }



    public String getSendername() {
        return sendername;
    }

    public void setSendername(String sendername) {
        this.sendername = sendername;
    }

    public String getTokenReceiver() {
        return tokenReceiver;
    }

    public void setTokenReceiver(String tokenReceiver) {
        this.tokenReceiver = tokenReceiver;
    }

    public String getTokenSender() {
        return tokenSender;
    }

    public void setTokenSender(String tokenSender) {
        this.tokenSender = tokenSender;
    }
}
