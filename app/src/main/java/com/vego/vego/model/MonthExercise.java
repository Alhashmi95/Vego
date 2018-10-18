package com.vego.vego.model;

import java.util.ArrayList;

public class MonthExercise {
    String Week;
    ArrayList<Exercises> Exercises;


    public MonthExercise() {

    }

    public MonthExercise(String Week, ArrayList<Exercises> Exercises) {
        this.Week = Week;
        this.Exercises =Exercises;


    }
}
