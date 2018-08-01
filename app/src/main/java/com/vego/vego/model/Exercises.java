package com.vego.vego.model;

public class Exercises {

    String day;
    String targetedmuscles;
    String exercisecount;
    exercise exercise;

    public Exercises(){

    }

    public Exercises(String day, String exercisecount, String targetedmuscles, exercise exercise) {
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

    public com.vego.vego.model.exercise getExercise() {
        return exercise;
    }

    public void setExercise(com.vego.vego.model.exercise exercise) {
        this.exercise = exercise;
    }
}
