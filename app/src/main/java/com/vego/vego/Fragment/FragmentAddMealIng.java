package com.vego.vego.Fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.vego.vego.Adapters.NewElementAdapter;
import com.vego.vego.R;
import com.vego.vego.model.elements;
import com.vego.vego.model.ingredients;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentAddMealIng.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class FragmentAddMealIng extends Fragment {

    private RecyclerView recyclerview;
    NewElementAdapter newElementAdapter;
    private ArrayList<elements> eleList;
    private ArrayList<ingredients> ingrList;
    ArrayList<elements> list = new ArrayList<>();
    private Button addNewTypeBtn, updateCalsBtn;
    private TextView totalCals;
    double sum;
    passArrayListEle listner;
    boolean checker = true;


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentAddMealIng() {
        // Required empty public constructor
    }

    public interface passArrayListEle {
        public void passArrayListEle(ArrayList<elements> ele,String totalCal);
    }


    // TODO: Rename and change types and number of parameters
//    public static FragmentAddMealIng newInstance(String param1, String param2) {
//        FragmentAddMealIng fragment = new FragmentAddMealIng();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_meal_ing, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerview = view.findViewById(R.id.recyclerview);

        addNewTypeBtn = view.findViewById(R.id.addNewIngBtn);

        updateCalsBtn = view.findViewById(R.id.updateCalsBtn);

        totalCals = view.findViewById(R.id.totalcals);

        updateCals();



//        mealindex = view.findViewById(R.id.mealIndex);
//        for(int i = 1; i < 8; i++) {
//            mealindex.setText(String.valueOf(i));
//        }

                elements e1 = new elements("","بروتين");
                elements e2 = new elements("","دهون");
                elements e3 = new elements("","كربوهيدرات");

//        elements ele = new elements();
//        list.add(ele);
        eleList = new ArrayList<>();


        eleList = populateList();
        eleList.add(e1);
        eleList.add(e2);
        eleList.add(e3);
        newElementAdapter = new NewElementAdapter(eleList, getContext());
        recyclerview.setAdapter(newElementAdapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));


    }
    private ArrayList<elements> populateList() {


        addNewTypeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elements ele = new elements();
                list.add(ele);
                newElementAdapter.notifyDataSetChanged();
            }
        });
        return list;
    }

    public void updateCals(){

        updateCalsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newElementAdapter.getElementssArray() != null) {
                    for (int i = 0; i < newElementAdapter.getElementssArray().size(); i++) {
                        if (newElementAdapter.getElementssArray().get(i).getName().isEmpty() ||
                                newElementAdapter.getElementssArray().get(i).getAmount().isEmpty()) {
                            Toast.makeText(getContext(), "please enter all details",
                                    Toast.LENGTH_SHORT).show();
                            checker = false;
                            break;
                        }
                    }
                    if(checker){
                        sum = 0;
                        for (int i = 0; i < newElementAdapter.getElementssArray().size(); i++) {
                            double totalCals = Double.valueOf(newElementAdapter.getElementssArray().get(i).getAmount());
                            sum = sum + totalCals;
                        }

                        }
                        if(checker) {
                            totalCals.setText(String.valueOf(sum));
                            listner.passArrayListEle(newElementAdapter.getElementssArray(), String.valueOf(sum));
                            Toast.makeText(getContext(), "تم التحديث",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
            }
        });

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;

        listner = (FragmentAddMealIng.passArrayListEle) activity;
        try {

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "must override passArrayListIng...");
        }


    }
}
