package com.vego.vego.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Mucsle implements Serializable {
    String mucsleName;
    int mucsleImage;
    ArrayList<exercise> exerciseArrayList;

    public Mucsle() {
    }

    public Mucsle(String mucsleName, int mucsleImage, ArrayList<exercise> exerciseArrayList) {
        this.mucsleName = mucsleName;
        this.mucsleImage = mucsleImage;
        this.exerciseArrayList = exerciseArrayList;
    }

    public String getMucsleName() {
        return mucsleName;
    }

    public void setMucsleName(String mucsleName) {
        this.mucsleName = mucsleName;
    }

    public int getMucsleImage() {
        return mucsleImage;
    }

    public void setMucsleImage(int mucsleImage) {
        this.mucsleImage = mucsleImage;
    }

    public ArrayList<exercise> getExerciseArrayList() {
        return exerciseArrayList;
    }

    public void setExerciseArrayList(ArrayList<exercise> exerciseArrayList) {
        this.exerciseArrayList = exerciseArrayList;
    }
}
