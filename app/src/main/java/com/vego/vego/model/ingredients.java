package com.vego.vego.model;

import java.io.Serializable;

public class ingredients implements Serializable {

    String type;
    String quantity;


    public ingredients() {

    }

    public ingredients(String quantity, String type) {
        this.quantity = quantity;
        this.type = type;


    }

    public String getQuantity() {
        return quantity;
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
