package com.vego.vego.model;

import java.io.Serializable;
import java.util.ArrayList;

public class DietDay implements Serializable {
    private String day, totalCals,mealsCount;
    ArrayList<meal> dayMeals;



    //this empty constructor is for retreving data form firebase
    public DietDay(){

    }

    public DietDay(String day, String totalCals, String mealsCount, ArrayList<meal> dayMeals) {
        this.day = day;
        this.totalCals = totalCals;
        this.mealsCount = mealsCount;
        this.dayMeals = dayMeals;
    }

    public ArrayList<meal> getDayMeals() {
        return dayMeals;
    }

    public void setDayMeals(ArrayList<meal> dayMeals) {
        this.dayMeals = dayMeals;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTotalCals() {
        return totalCals;
    }

    public void setTotalCals(String totalCals) {
        this.totalCals = totalCals;
    }

    public String getMealsCount() {
        return mealsCount;
    }

    public void setMealsCount(String mealsCount) {
        this.mealsCount = mealsCount;
    }
}
