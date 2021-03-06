package com.vego.vego.model;

import java.io.Serializable;
import java.util.ArrayList;

public class meal implements Serializable {

    String cal;
    String name;
    String image;
    ArrayList<elements> elements;
    ArrayList<ingredients> ingredients;

    public meal(String cal, String name, ArrayList<elements> elements, ArrayList<ingredients> ingredients, String image) {
        //List<ingredients> ingrList
        this.cal = cal;
        this.name = name;
        this.elements = elements;
        this.ingredients = ingredients;
        this.image = image;
    }

    public meal(){


    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public ArrayList<elements> getElements() {
        return elements;
    }

    public void setElements(ArrayList<elements> elements) {
        this.elements = elements;
    }

    public ArrayList<ingredients> getingredients() {
        return ingredients;
    }

    public void setingredients(ArrayList<ingredients> ingredients) {
        this.ingredients = ingredients;
    }
}
