package com.vego.vego.model;

import android.widget.ImageView;

import java.util.ArrayList;

public class Artist {
    private String name;
    private String vol;

    public Artist(String name,String vol) {
        this.name = name;
        this.vol= vol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVol() {
        return vol;
    }

    public void setVol(String vol) {
        this.vol = vol;
    }
}