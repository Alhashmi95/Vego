package com.vego.vego.model;

import java.util.ArrayList;

public class Muscles {
    ArrayList muscleName;
    ArrayList ExerciseiceName;

    public Muscles(ArrayList muscleName, ArrayList exerciseiceName) {
        this.muscleName = muscleName;
        this.ExerciseiceName = exerciseiceName;
    }

    public ArrayList getMuscleName() {
        return muscleName;
    }

    public void setMuscleName(ArrayList muscleName) {
        this.muscleName = muscleName;
    }

    public ArrayList getExerciseiceName() {
        return ExerciseiceName;
    }

    public void setExerciseiceName(ArrayList exerciseiceName) {
        ExerciseiceName = exerciseiceName;
    }
}
