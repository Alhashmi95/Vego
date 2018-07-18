package com.vego.vego.model;

public class DayMeals {

    String mealName,mealCal;

    public DayMeals(String mealName, String mealCal) {
        this.mealName = mealName;
        this.mealCal = mealCal;
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
