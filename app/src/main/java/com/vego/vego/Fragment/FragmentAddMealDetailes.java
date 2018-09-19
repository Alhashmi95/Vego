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
import android.widget.EditText;
import android.widget.Toast;

import com.vego.vego.Adapters.NewElementAdapter;
import com.vego.vego.Adapters.NewIngredientAdapter;
import com.vego.vego.R;
import com.vego.vego.model.ingredients;

import java.util.ArrayList;


public class FragmentAddMealDetailes extends Fragment {


    private RecyclerView recyclerview;
    NewIngredientAdapter newIngredientAdapter;
    NewElementAdapter newElementAdapter;
    private ArrayList<ingredients> ingrList;
    ArrayList<ingredients> list = new ArrayList<>();
    private Button addNewTypeBtn, saveBtn;
    private EditText etmealName;
    private String mealName;
    //step 3
    passArrayListIng listner;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentAddMealDetailes() {
        // Required empty public constructor
    }

    //step 1 to copy array to activity
    public interface passArrayListIng {
        public void passArrayListIng(ArrayList<ingredients> ing, String mealName);

        }


//    public static FragmentAddMealDetailes newInstance(String param1, String param2) {
//        FragmentAddMealDetailes fragment = new FragmentAddMealDetailes();
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
        return inflater.inflate(R.layout.fragment_add_meal_detailes, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerview = view.findViewById(R.id.recyclerview);

        addNewTypeBtn = view.findViewById(R.id.addNewTypeBtn);

        saveBtn = view.findViewById(R.id.saveBtn);

        etmealName = view.findViewById(R.id.mealName);


//        mealindex = view.findViewById(R.id.mealIndex);
//        for(int i = 1; i < 8; i++) {
//            mealindex.setText(String.valueOf(i));
//        }

        ingredients ing = new ingredients();
        list.add(ing);


        ingrList = populateList();
        newIngredientAdapter = new NewIngredientAdapter(ingrList, getContext());
        recyclerview.setAdapter(newIngredientAdapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));

//        newIngredientAdapter.getIngredientsArray().get(0).getQuantity();
//        newElementAdapter.getElementssArray().get(0).getName();

        saveMeal();


    }


    private ArrayList<ingredients> populateList() {


        addNewTypeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingredients ing = new ingredients();
                list.add(ing);
                newIngredientAdapter.notifyDataSetChanged();
            }
        });
        return list;
    }

    public void saveMeal() {
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mealName = etmealName.getText().toString();
                //declare boolean checker to see if there are null edit text
                boolean checker = false;
                //check if meal edit text is null
                if (mealName.isEmpty()) {
                    etmealName.setError("please enter the name of the meal");
                }
                //first we check if arraylist of elements is not null
                //check if there's any null edit text in all cardviews of meal elements3
                checker = true;
                for (int i = 0; i < newIngredientAdapter.getIngredientsArray().size(); i++) {
                    //check if there's any null edit text in all cardviews of meal elements
                    if (newIngredientAdapter.getIngredientsArray().get(i).getType().isEmpty() ||
                            newIngredientAdapter.getIngredientsArray().get(i).getQuantity().isEmpty()) {
                        Toast.makeText(getContext(), "please enter all ingr details",
                                Toast.LENGTH_SHORT).show();
                        checker = false;
                        break;

                    }

                }

                if (newIngredientAdapter.getIngredientsArray() != null && checker) {
                    Toast.makeText(getContext(), "تم الحفظ",
                            Toast.LENGTH_SHORT).show();
                    listner.passArrayListIng(newIngredientAdapter.getIngredientsArray(), mealName);

                }
            }
        });
    }

    public double totalcal() {
        double sum = 0;
        for (int i = 0; i < newElementAdapter.getElementssArray().size(); i++) {
            double totalCals = Double.valueOf(newElementAdapter.getElementssArray().get(i).getAmount());
            sum = sum + totalCals;

        }
        return sum;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    //step 2 ....
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;

        listner = (passArrayListIng) activity;
        try {

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "must override passArrayListIng...");
        }
    }
}
