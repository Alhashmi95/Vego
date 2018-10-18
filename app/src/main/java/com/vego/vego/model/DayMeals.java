package com.vego.vego.model;

import java.io.Serializable;
import java.util.ArrayList;

public class DayMeals implements Serializable {

    String name,cal;

    DietDay[] dietDays;
    String image;
    ArrayList<ingredients> ingredients;
    ArrayList<elements> elements;



    public DayMeals(){

    }

    public DayMeals(String name, String cal, String image, ArrayList<ingredients> ingredients,
                    ArrayList<elements> elements) {
        this.name = name;
        this.cal = cal;
        this.image = image;
        this.ingredients = ingredients;
        this.elements = elements;
    }

    public ArrayList<elements> getElements() {
        return elements;
    }

    public void setElements(ArrayList<elements> elements) {
        this.elements = elements;
    }

    public DietDay[] getDietDays() {


            return dietDays;
        }






    public void setDietDays(DietDay[] dietDays) {
        this.dietDays = dietDays;
    }

    public ArrayList<ingredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<ingredients> ingredients) {
        this.ingredients = ingredients;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getCal() {
        return cal;
    }

    public void setCal(String cal) {
        this.cal = cal;
    }
}
