package com.vego.vego;

public class UserInfo {
    String name;
    String weight;
    String height;
    String age;
    String userActivity;
    String userGoal;


    public UserInfo(){

    }



    public UserInfo(String name, String weight, String height, String age, String userActivity, String userGoal) {
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.age = age;
        this.userActivity = userActivity;
        this.userGoal = userGoal;
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


}
