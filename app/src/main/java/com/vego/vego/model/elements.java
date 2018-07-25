package com.vego.vego.model;

import java.io.Serializable;

public class elements implements Serializable {
    String name;
    double amount;


    public elements() {

    }

    public elements(String name, double amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
