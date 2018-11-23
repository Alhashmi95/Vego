package com.vego.vego.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vego.vego.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CopyExerciseFragment extends Fragment {


    public CopyExerciseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_copy_exercise, container, false);
    }

}
