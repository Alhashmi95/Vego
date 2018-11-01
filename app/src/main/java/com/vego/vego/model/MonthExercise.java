package com.vego.vego.model;

import com.mikepenz.materialize.holder.StringHolder;

import java.util.ArrayList;

public class MonthExercise {
    ArrayList<Exercises> week1,week2,week3,week4;


    public MonthExercise() {

    }

    public ArrayList<Exercises> getWeek1() {
        return week1;
    }

    public void setWeek1(ArrayList<Exercises> week1) {
        this.week1 = week1;
    }

    public ArrayList<Exercises> getWeek2() {
        return week2;
    }

    public void setWeek2(ArrayList<Exercises> week2) {
        this.week2 = week2;
    }

    public ArrayList<Exercises> getWeek3() {
        return week3;
    }

    public void setWeek3(ArrayList<Exercises> week3) {
        this.week3 = week3;
    }

    public ArrayList<Exercises> getWeek4() {
        return week4;
    }

    public void setWeek4(ArrayList<Exercises> week4) {
        this.week4 = week4;
    }

    public MonthExercise(ArrayList<Exercises> week1, ArrayList<Exercises> week2, ArrayList<Exercises> week3,
                         ArrayList<Exercises> week4) {
        this.week1 = week1;
        this.week2 = week2;
        this.week3 = week3;
        this.week4 = week4;


    }
}
