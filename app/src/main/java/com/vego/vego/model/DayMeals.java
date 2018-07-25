package com.vego.vego.model;

import java.io.Serializable;
import java.util.ArrayList;

public class DayMeals implements Serializable {

    String mealName,mealCal;

    DietDay[] dietDays;
    int img;
    ArrayList<ingredients> ingredients;
    ArrayList<elements> elements;






    public DayMeals(){

    }

    public DayMeals(String mealName, String mealCal, int img, ArrayList<ingredients> ingredients,
                    ArrayList<elements> elements) {
        this.mealName = mealName;
        this.mealCal = mealCal;
        this.img = img;
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

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getMealCal() {
        return mealCal;
    }

    public void setMealCal(String mealCal) {
        this.mealCal = mealCal;
    }
}
