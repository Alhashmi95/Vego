package com.vego.vego.model;

import java.io.Serializable;

public class MealIngr implements Serializable {

    String type;
    double quantity;


    public MealIngr() {

    }

    public MealIngr(int quantity, String type) {
        this.quantity = quantity;
        this.type = type;


    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
