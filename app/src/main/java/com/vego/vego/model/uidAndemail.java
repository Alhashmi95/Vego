package com.vego.vego.model;

import java.util.ArrayList;

public class uidAndemail {
    ArrayList<String> uid;
    ArrayList<String> email;


    public uidAndemail() {

    }

    public uidAndemail(ArrayList<String> uid, ArrayList<String> email) {
        this.uid = uid;
        this.email = email;

    }

    public ArrayList<String> getUid() {
        return uid;
    }

    public void setUid(ArrayList<String> uid) {
        this.uid = uid;
    }

    public ArrayList<String> getEmail() {
        return email;
    }

    public void setEmail(ArrayList<String> email) {
        this.email = email;
    }
}
