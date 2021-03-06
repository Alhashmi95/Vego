package com.vego.vego.model;

import android.os.Parcelable;

import com.vego.vego.model.DietDay;

import java.io.Serializable;
import java.util.ArrayList;

public class UserInfo implements Serializable {
    public String name;
    public String weight;
    public String height;
    public String age;
    String userActivity;
    String userGoal;
    ArrayList<DietDay> dietDay;
    String isAdmin;
    private String uid, previousWeight, adminBrief, userEmail;
    private String image;

    public UserInfo() {
    }





    public UserInfo(String name, String weight, String height, String age, String userActivity, String userGoal,
                    ArrayList<DietDay> dietDay, String isAdmin, String uid,String previousWeight
                    ,String adminBrief , String userEmail) {
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.age = age;
        this.userActivity = userActivity;
        this.userGoal = userGoal;
        this.dietDay = dietDay;
        this.isAdmin = isAdmin;
        this.uid = uid;
        this.previousWeight = previousWeight;
        this.adminBrief = adminBrief;
        this.userEmail = userEmail;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPreviousWeight() {
        return previousWeight;
    }

    public void setPreviousWeight(String previousWeight) {
        this.previousWeight = previousWeight;
    }

    public String getAdminBrief() {
        return adminBrief;
    }

    public void setAdminBrief(String adminBrief) {
        this.adminBrief = adminBrief;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setDietDay(ArrayList<DietDay> dietDay) {
        this.dietDay = dietDay;
    }


    public ArrayList<DietDay> getDietDay() {
        return dietDay;
    }

    public String getAdmin() {
        return isAdmin;
    }

    public void setAdmin(String admin) {
        isAdmin = admin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getUserActivity() {
        return userActivity;
    }

    public void setUserActivity(String userActivity) {
        this.userActivity = userActivity;
    }

    public String getUserGoal() {
        return userGoal;
    }

    public void setUserGoal(String userGoal) {
        this.userGoal = userGoal;
    }

    public String getImage() { return image; }

    public void setImage(String image) { this.image = image; }
}
