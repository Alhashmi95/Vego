package com.vego.vego.model;

import java.io.Serializable;

public class elements implements Serializable {
    String name;
    String amount;


    public elements() {

    }

    public elements(String amount, String name) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
