package com.vego.vego.model;

import java.util.ArrayList;

public class meal {

    int cal;
    String name;
    ArrayList<elements> element;
    ArrayList<ingredients> ingrs;

    public meal(int cal, String name, ArrayList<elements> element, ArrayList<ingredients> ingrs) {

        this.cal = cal;
        this.name = name;
        this.element = element;
        this.ingrs = ingrs;
    }

    public meal(){


    }

    public int getCal() {
        return cal;
    }

    public void setCal(int cal) {
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

    public ArrayList<ingredients> getIngrs() {
        return ingrs;
    }

    public void setIngrs(ArrayList<ingredients> ingrs) {
        this.ingrs = ingrs;
    }
}
