package com.vego.vego.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Exercises implements Serializable {

    String day;
    String targetedmuscles;
    String exercisecount;
    ArrayList<exercise> exercise;

    public Exercises(){

    }

    public Exercises(String day, String exercisecount, String targetedmuscles, ArrayList<exercise> exercise) {
        this.day = day;
        this.targetedmuscles = targetedmuscles;
        this.exercisecount = exercisecount;
        this.exercise = exercise;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTargetedmuscles() {
        return targetedmuscles;
    }

    public void setTargetedmuscles(String targetedmuscles) {
        this.targetedmuscles = targetedmuscles;
    }

    public String getExercisecount() {
        return exercisecount;
    }

    public void setExercisecount(String exercisecount) {
        this.exercisecount = exercisecount;
    }

    public ArrayList<com.vego.vego.model.exercise> getExercise() {
        return exercise;
    }

    public void setExercise(ArrayList<com.vego.vego.model.exercise> exercise) {
        this.exercise = exercise;
    }
}
