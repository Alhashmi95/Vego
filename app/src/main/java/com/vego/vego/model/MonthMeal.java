package com.vego.vego.model;

import java.util.ArrayList;

public class MonthMeal {
    ArrayList<DietDay> week1,week2,week3,week4;

    public ArrayList<DietDay> getWeek1() {
        return week1;
    }

    public void setWeek1(ArrayList<DietDay> week1) {
        this.week1 = week1;
    }

    public ArrayList<DietDay> getWeek2() {
        return week2;
    }

    public void setWeek2(ArrayList<DietDay> week2) {
        this.week2 = week2;
    }

    public ArrayList<DietDay> getWeek3() {
        return week3;
    }

    public void setWeek3(ArrayList<DietDay> week3) {
        this.week3 = week3;
    }

    public ArrayList<DietDay> getWeek4() {
        return week4;
    }

    public void setWeek4(ArrayList<DietDay> week4) {
        this.week4 = week4;
    }

    public MonthMeal(ArrayList<DietDay> week1, ArrayList<DietDay> week2, ArrayList<DietDay> week3, ArrayList<DietDay> week4) {

        this.week1 = week1;
        this.week2 = week2;
        this.week3 = week3;
        this.week4 = week4;
    }
}
