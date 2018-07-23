package com.vego.vego.model;

import java.io.Serializable;
import java.util.ArrayList;

public class DayMeals implements Serializable {

    String mealName,mealCal;
    int img;
    DietDay[] dietDays;
    ArrayList<MealIngr> mealIngrs;


    public DayMeals(){

    }

    public DayMeals(String mealName, String mealCal, int img, ArrayList<MealIngr> mealIngrs) {
        this.mealName = mealName;
        this.mealCal = mealCal;
        this.img = img;
        this.mealIngrs = mealIngrs;
    }
        public DietDay[] getDietDays() {
            return dietDays;
        }

        public void setDietDays(DietDay[] dietDays) {
            this.dietDays = dietDays;
        }

    public ArrayList<MealIngr> getMealIngrs() {
        return mealIngrs;
    }

    public void setMealIngrs(ArrayList<MealIngr> mealIngrs) {
        this.mealIngrs = mealIngrs;
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
