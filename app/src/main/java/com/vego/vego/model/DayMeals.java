package com.vego.vego.model;

import java.io.Serializable;

public class DayMeals implements Serializable {

    String mealName,mealCal;
    int img;



    public DayMeals(){

    }

    public DayMeals(String mealName, String mealCal, int img) {
        this.mealName = mealName;
        this.mealCal = mealCal;
        this.img = img;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getMealCal() {
        return mealCal;
    }

    public void setMealCal(String mealCal) {
        this.mealCal = mealCal;
    }
}
