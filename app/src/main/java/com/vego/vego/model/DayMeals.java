package com.vego.vego.model;

public class DayMeals {

    String mealName,mealCal;
    DietDay[] dietDays;

    public DietDay[] getDietDays() {
        return dietDays;
    }

    public void setDietDays(DietDay[] dietDays) {
        this.dietDays = dietDays;
    }

    public DayMeals(String mealName, String mealCal, DietDay[] dietDays) {
        this.mealName = mealName;
        this.mealCal = mealCal;

        this.dietDays = dietDays;
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
