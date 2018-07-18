package com.vego.vego.model;

public class DietDay {
    private String day, totalCals,mealsCount;

    public DietDay(String day, String totalCals, String mealsCount) {
        this.day = day;
        this.totalCals = totalCals;
        this.mealsCount = mealsCount;
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
