package com.vego.vego.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vego.vego.Adapters.toolbarAdapter;
import com.vego.vego.Adapters.toolbarAdapterWeek;
import com.vego.vego.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DietFragmentAdmin.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DietFragmentAdmin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DietFragmentAdmin extends Fragment {

    TabLayout tabLayout, tabLayoutWeek;

    toolbarAdapter toolbarAdapter;
    toolbarAdapterWeek toolbarAdapterWeek;

    Button addMonth, removeMonth;

    ViewPager viewPager;

    int counterMonth =2;

    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

    ArrayList<String> fragmentArrayListTitles = new ArrayList<>();


    private OnFeedItemClickListener onFeedItemClickListener;

    int position =0;




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DietFragmentAdmin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DietFragmentAdmin.
     */
    // TODO: Rename and change types and number of parameters
    public static DietFragmentAdmin newInstance(String param1, String param2) {
        DietFragmentAdmin fragment = new DietFragmentAdmin();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

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
        return inflater.inflate(R.layout.fragment_diet_fragment_admin, container, false);

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //adding toolbar
//        toolbar = view.findViewById(R.id.app_bar);

//        tabLayout = view.findViewById(R.id.tablayout);
//
//        tabLayoutWeek = view.findViewById(R.id.tablayoutWeek);
//
//        viewPager = view.findViewById(R.id.viewpager);
//
//        addMonth = view.findViewById(R.id.btn_addMonth);
//
//        removeMonth = view.findViewById(R.id.btn_removeMonth);
//
//        tabLayoutWeek.setupWithViewPager(viewPager);
//
////        toolbarAdapter = new toolbarAdapter(getContext());
//
//        toolbarAdapterWeek = new toolbarAdapterWeek(getChildFragmentManager(), getContext());
//
//
//
//        tabLayout.addTab(tabLayout.newTab().setText("month 1"));
//
//
//
//        if(toolbarAdapterWeek.getCount() < 4) {
//            toolbarAdapterWeek.addFragment(new AddMealsFragment(), "week 1");
//
//            toolbarAdapterWeek.addFragment(new AddMealsFragment(), "week 2");
//
//
//            toolbarAdapterWeek.addFragment(new AddMealsFragment(), "week 3");
//
//
//            toolbarAdapterWeek.addFragment(new AddMealsFragment(), "week 4");
//        } if(toolbarAdapterWeek.getCount() > 4){
//            toolbarAdapterWeek.getFragmentList().clear();
//            toolbarAdapterWeek.addFragment(new AddMealsFragment(), "week 1");
//
//            toolbarAdapterWeek.addFragment(new AddMealsFragment(), "week 2");
//
//
//            toolbarAdapterWeek.addFragment(new AddMealsFragment(), "week 3");
//
//
//            toolbarAdapterWeek.addFragment(new AddMealsFragment(), "week 4");
//
//        }
//
//
//
//        viewPager.setAdapter(toolbarAdapterWeek);
//
//
//        addNewMonth();
//
//        removeMonth();
//
//        tabListener();

    }

    private void tabListener() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                onFeedItemClickListener.onButtonClick();

//                if(tabLayout.getSelectedTabPosition() != 0) {
//                    final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
//                    alert.setMessage("ستفقد جميع البيانات .. الرجاء التأكد من الضغط على زر (حفظ الوجبات) قبل المتابعة");
//                    alert.setTitle("تنبيه");
//
//
//                    alert.setPositiveButton("موافق", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int whichButton) {
//
//                        }
//
//
//                    });
//                    alert.setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int whichButton) {
//                            int tabIndex = tabLayout.getSelectedTabPosition() - 1;
//
//                          //  tabLayout.getTabAt(tabIndex);
//
//                            viewPager.setCurrentItem(tabIndex);
//
//                            toolbarAdapter.notifyDataSetChanged();
//
//                        }
//                    });
//
//                    alert.show();
//                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //================================================ tab weeks
        tabLayoutWeek.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                onFeedItemClickListener.onButtonClick();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void removeMonth() {
        removeMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tabLayout.getTabCount() == 1){
                    Toast.makeText(getContext(),
                            "يجب ان تحتوي على عنصر واحد على الاقل",Toast.LENGTH_SHORT).show();

                }else{
                    int index = tabLayout.getTabCount();
                    index--;
                    //fragmentArrayList = (ArrayList<Fragment>) toolbarAdapter.getFragmentList();

                   // fragmentArrayListTitles = (ArrayList<String>) toolbarAdapter.getFragmentTitles();

                    tabLayout.removeTabAt(index);

                    //toolbarAdapter.notifyDataSetChanged();


                }
            }
        });
    }

    private void addNewMonth() {
        addMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabLayout.addTab(tabLayout.newTab().setText("month "+String.valueOf(counterMonth)));
                //toolbarAdapter.notifyDataSetChanged();
                counterMonth++;
            }
        });

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
    public void setOnFeedItemClickListener(OnFeedItemClickListener onFeedItemClickListener) {
        this.onFeedItemClickListener = onFeedItemClickListener;
        this.position = tabLayout.getSelectedTabPosition();
    }


    public interface OnFeedItemClickListener {
        void onButtonClick();

    }
    public int getCount(){
        return tabLayout.getSelectedTabPosition();
    }
    public int getCountWeek(){
        return tabLayoutWeek.getSelectedTabPosition();
    }
}
