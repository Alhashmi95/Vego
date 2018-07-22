package com.vego.vego.model;

import java.io.Serializable;

public class MealIngr implements Serializable {

    String quantity,type,cals,protien,carbs,fat;


    public MealIngr() {

    }

    public MealIngr(String quantity, String type, String cals, String protien, String carbs,
                    String fat) {
        this.quantity = quantity;
        this.type = type;
        this.cals = cals;
        this.protien = protien;
        this.carbs = carbs;
        this.fat = fat;

    }

    public String getQuantity() {
        return quantity;
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

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
