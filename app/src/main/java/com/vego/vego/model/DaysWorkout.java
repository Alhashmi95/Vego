package com.vego.vego.model;

public class DaysWorkout {

    String day;
    String muscles;
    String count;

    public DaysWorkout(String day, String muscles, String count) {
        this.day = day;
        this.muscles = muscles;
        this.count = count;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMuscles() {
        return muscles;
    }

    public void setMuscles(String muscles) {
        this.muscles = muscles;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
