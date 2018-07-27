package com.vego.vego.model;

import java.io.Serializable;
import java.util.ArrayList;

public class meal implements Serializable {

    String cal;
    String name;
    ArrayList<elements> element;
    ArrayList<ingredients> ingredients;

    public meal(String cal, String name, ArrayList<elements> element, ArrayList<ingredients> ingredients) {

        this.cal = cal;
        this.name = name;
        this.element = element;
        this.ingredients = ingredients;
    }

    public meal(){


    }

    public String getCal() {
        return cal;
    }

    public void setCal(String cal) {
        this.cal = cal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<elements> getElement() {
        return element;
    }

    public void setElement(ArrayList<elements> element) {
        this.element = element;
    }

    public ArrayList<ingredients> getingredients() {
        return ingredients;
    }

    public void setingredients(ArrayList<ingredients> ingredients) {
        this.ingredients = ingredients;
    }
}
