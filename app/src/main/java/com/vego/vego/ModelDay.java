package com.vego.vego;

public class ModelDay {
    private String day,calories,mealsCount;

    public ModelDay(String day, String calories, String mealsCount) {
        this.day = day;
        this.calories = calories;
        this.mealsCount = mealsCount;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getMealsCount() {
        return mealsCount;
    }

    public void setMealsCount(String mealsCount) {
        this.mealsCount = mealsCount;
    }
}
