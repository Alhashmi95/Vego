package com.vego.vego.model;

public class sets {

    String reps;
    String rm1;
    String volume;
    String weight;

    public sets(){

    }

    public sets(String reps, String rm1, String volume, String weight) {
        this.reps = reps;
        this.rm1 = rm1;
        this.volume = volume;
        this.weight = weight;
    }

    public String getReps() {
        return reps;
    }

    public void setReps(String reps) {
        this.reps = reps;
    }

    public String getRM1() {
        return rm1;
    }

    public void setRM1(String RM1) {
        this.rm1 = RM1;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
