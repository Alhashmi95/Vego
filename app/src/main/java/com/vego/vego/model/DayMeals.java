package com.vego.vego.model;

import java.io.Serializable;
import java.util.ArrayList;

public class DayMeals implements Serializable {

    String mealName,mealCal;
<<<<<<< HEAD

    DietDay[] dietDays;
    int img;
    ArrayList<MealIngr> mealIngrs;

    public DietDay[] getDietDays() {
        return dietDays;
    }

    public void setDietDays(DietDay[] dietDays) {
        this.dietDays = dietDays;
    }


=======
    int img;
    DietDay[] dietDays;
    ArrayList<MealIngr> mealIngrs;
>>>>>>> d4f814dd5f3eda10bfe0b8b1c1ae553cf0c278b5


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
<<<<<<< HEAD

=======
>>>>>>> d4f814dd5f3eda10bfe0b8b1c1ae553cf0c278b5
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
