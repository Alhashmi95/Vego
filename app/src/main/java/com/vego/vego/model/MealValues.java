package com.vego.vego.model;

public class MealValues {
    String cals,protien,carbs,fat;

    public MealValues(String cals, String protien, String carbs, String fat) {
        this.cals = cals;
        this.protien = protien;
        this.carbs = carbs;
        this.fat = fat;
    }

    public String getCals() {
        return cals;
    }

    public void setCals(String cals) {
        this.cals = cals;
    }

    public String getProtien() {
        return protien;
    }

    public void setProtien(String protien) {
        this.protien = protien;
    }

    public String getCarbs() {
        return carbs;
    }

    public void setCarbs(String carbs) {
        this.carbs = carbs;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }
}
