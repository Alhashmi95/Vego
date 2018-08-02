package com.vego.vego.model;

import java.io.Serializable;
import java.util.ArrayList;

public class exercise implements Serializable {
    private String image;
    private String exername;
    private String targetedmuscle;
    private ArrayList<sets> sets;


    public exercise(){

    }

    public exercise(String exername, String targetedmuscle, String image) {
        this.exername = exername;
        this.targetedmuscle = targetedmuscle;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getExername() {
        return exername;
    }

    public void setExername(String exername) {
        this.exername = exername;
    }

    public String getTargetedmuscle() {
        return targetedmuscle;
    }

    public void setTargetedmuscle(String targetedmuscle) {
        this.targetedmuscle = targetedmuscle;
    }

    public ArrayList<sets> getSets() {
        return sets;
    }

    public void setSets(ArrayList<sets> sets) {
        this.sets = sets;
    }
}
