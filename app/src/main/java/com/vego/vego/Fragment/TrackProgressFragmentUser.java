package com.vego.vego.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.spyhunter99.supertooltips.ToolTip;
import com.spyhunter99.supertooltips.ToolTipManager;
import com.squareup.picasso.Picasso;
import com.vego.vego.Activity.MainActivity;
//import com.vego.vego.Adapters.ExpandableAdapter;
import com.vego.vego.Adapters.ExpandableRecyclerAdapter;
import com.vego.vego.Adapters.PeopleAdapter;
import com.vego.vego.R;
import com.vego.vego.model.Artist;
import com.vego.vego.model.Exercises;
//import com.vego.vego.model.Genere;
import com.vego.vego.model.MonthExercise;
import com.vego.vego.model.UserInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TrackProgressFragmentUser.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TrackProgressFragmentUser#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrackProgressFragmentUser extends Fragment {
    private EditText currentWeight;
    private TextView previousWeight, perfectWeight;
    private Button updateWeights;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    String previousW;
    ProgressDialog p;
    
    ScrollView scrollView;

    RecyclerView recyclerView;


    List<Object> childList = new ArrayList<>();





    //variables
    private RecyclerView mRecyclerView;
//    private ExpandableAdapter mAdapter;
//    private ArrayList<Genere> genres;
    ArrayList<Artist> artists = new ArrayList<>();
    ArrayList<Artist> weeks = new ArrayList<>();


    ArrayList<Exercises> exList1 = new ArrayList<>();
    ArrayList<Exercises> exList2 = new ArrayList<>();
    ArrayList<Exercises> exList3 = new ArrayList<>();
    ArrayList<Exercises> exList4 = new ArrayList<>();

    double tot1,tot2,tot3,tot4;

    double rm1tot1,rm1tot2,rm1tot3,rm1tot4;


    double weighttot1,weighttot2,weighttot3,weighttot4;

    ArrayList<String> mupic1= new ArrayList<>();
    ArrayList<String> mupic2= new ArrayList<>();
    ArrayList<String> mupic3= new ArrayList<>();
    ArrayList<String> mupic4= new ArrayList<>();


    ArrayList<Integer> img = new ArrayList<>();

    PeopleAdapter adapter;

    List<PeopleAdapter.PeopleListItem> items = new ArrayList<>();

    List<PeopleAdapter.PeopleListItem> week = new ArrayList<>();


    PeopleAdapter.PeopleListItem p1= new PeopleAdapter.PeopleListItem("9348", String.format("الاسبوع #%d", 1),"847","843","kfjdi",mupic1);
    PeopleAdapter.PeopleListItem p2= new PeopleAdapter.PeopleListItem("9348", String.format("الاسبوع #%d", 2),"4837","ifjd","jfidk",mupic2);
    PeopleAdapter.PeopleListItem p3= new PeopleAdapter.PeopleListItem("9348", String.format("الاسبوع #%d", 3),"ligfiod","kgdf","gkjdfh",mupic3);
    PeopleAdapter.PeopleListItem p4= new PeopleAdapter.PeopleListItem("9348", String.format("الاسبوع #%d", 4),"klfjd","vkihd","fio",mupic4);





    ToolTipManager tooltips;
    ToolTip toolTip = new ToolTip();





    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private int counterMonth, counterWeek;
    private int position=0;

    public TrackProgressFragmentUser() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TrackProgressFragmentUser.
     */
    // TODO: Rename and change types and number of parameters
    public static TrackProgressFragmentUser newInstance(String param1, String param2) {
        TrackProgressFragmentUser fragment = new TrackProgressFragmentUser();
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
        View view = inflater.inflate(R.layout.fragment_track_progress_fragment_user,
                container, false);
        mRecyclerView = view.findViewById(R.id.myRecyclerView);

        getGenres();
        return view;

    }
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        ((ExpandableAdapter)recyclerView.getAdapter()).onSaveInstanceState(outState);
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currentWeight = view.findViewById(R.id.tvCurrentWeight);
        previousWeight = view.findViewById(R.id.tvPreviousWeight);
        perfectWeight = view.findViewById(R.id.tvPerfectWeight);
        
        scrollView = view.findViewById(R.id.scrollView);

        tooltips = new ToolTipManager(Objects.requireNonNull(getActivity()));


        scrollInsideScroll();

        getMonthTabs();
        

        perfectWeight.setMovementMethod(new ScrollingMovementMethod());

        updateWeights = view.findViewById(R.id.btnUpdateWeight);

        p = new ProgressDialog(getContext());
        p.setTitle("Loading");
        p.setMessage("Fetching data...");
        p.show();


        getWeights();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference2 = firebaseDatabase.getReference().child("users")
                .child(firebaseAuth.getUid()).child("Profile");



        databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                UserInfo userinfo = dataSnapshot.getValue(UserInfo.class);



                previousW =userinfo.getWeight();

                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databasaeReference = firebaseDatabase.getReference();


                databasaeReference.child("users").child(firebaseAuth.getUid()).child("Profile")
                        .child("previousWeight")
                        .setValue(previousW);




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(TrackProgressFragmentUser.this.getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });



        updateWeight();


        //=======================================
        mRecyclerView = view.findViewById(R.id.myRecyclerView);

        adapter = new PeopleAdapter(getContext());
        adapter.setMode(ExpandableRecyclerAdapter.MODE_ACCORDION);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);

        ArrayList<String> s = new ArrayList<>();
        s.add("gkjfkld");
        s.add("glkdfjgkld");
        s.add("gfdkljgilkd");

        ArrayList<String> s2 = new ArrayList<>();
        s2.add("gkdfgierjdd");
        s2.add("glk44erese");
        s2.add("gfdkljdfgijdfkjgnikdf");

        items = new ArrayList<>();
        items.add(new PeopleAdapter.PeopleListItem("kljvdcbffd"));

        items.add(new PeopleAdapter.PeopleListItem("Friends"));
        adapter.setItems(items);

        adapter.notifyDataSetChanged();

        getGenres();


    }
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        mAdapter.onSaveInstanceState(outState);
//    }

    @SuppressLint("DefaultLocale")
    public void getGenres() {
//        genres = new ArrayList<>();
        artists = new ArrayList<>();
        artists.add(new Artist("الاسبوع 1","kgfj"));
        artists.add(new Artist("الاسبوع 2","kfj"));
        artists.add(new Artist("الاسبوع 3","f,j"));
        artists.add(new Artist("الاسبوع 4","kfd,mcn"));
        items = new ArrayList<>();
        for(int i=0;i< counterMonth;i++)
        {
            items.add(new PeopleAdapter.PeopleListItem(String.format("الشهر #%d", i + 1)));
            items.add(p1);
            items.add(p2);
            items.add(p3);
            items.add(p4);



//            parentObject = new ArrayList<>();
            if(weeks.isEmpty()) {
  //              genres.add(new Genere(String.format("الشهر #%d", i + 1), artists));
            }else {
//                genres.add(new Genere(String.format("الشهر #%d", i + 1), weeks));
            }


        }
        adapter = new PeopleAdapter(getContext());
        //adapter.setMode(ExpandableRecyclerAdapter.MODE_ACCORDION);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);

        //adapter.setItems(items);
        adapter.notifyDataSetChanged();

        adapter.setOnItemClickListener(new ExpandableRecyclerAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int pos) {
                tooltips.closeTooltipImmediately();
                position = pos;
                getWeeks();
                //getmonthData();
            }
        });


//        mAdapter = new ExpandableAdapter(genres);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        mRecyclerView.setAdapter(mAdapter);


     //getWeeks();

//        mAdapter.setOnGroupClickListener(new OnGroupClickListener() {
//            @Override
//            public boolean onGroupClick(int flatPos) {
//                position = flatPos;
//                getWeeks();
//                return true;
//            }
//        });

//        mAdapter.setOnGroupExpandCollapseListener(new GroupExpandCollapseListener() {
//            @Override
//            public void onGroupExpanded(ExpandableGroup group) {
//                mAdapter.setOnGroupClickListener(new OnGroupClickListener() {
//                    @Override
//                    public boolean onGroupClick(int flatPos) {
//                        position = flatPos;
//                        getWeeks();
//                        return false;
//                    }
//                });
//            }
//
//            @Override
//            public void onGroupCollapsed(ExpandableGroup group) {
//
//            }
//        });


//        genres = new ArrayList<>(6);
//        for (int i = 0; i < 6; i++) {
//            artists.add(new Artist("Paramore"));
//            artists.add(new Artist("Flyleaf"));
//            artists.add(new Artist("The Script"));
//            genres.add(new Genere("Rock_ " + i, artists));
//        }

    }




//    private List<ParentObject> initData() {
//        //_titleParents = new ArrayList<>();
//        for(int i=0;i< counterMonth;i++)
//        {
////            parentObject = new ArrayList<>();
//            title = new TitleParent(String.format("الشهر #%d",i+1));
//            _titleParents.add(title);
//            parentObject.add(title);
//
//        }
//
//        /////=====================================================
//        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
//        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//        DatabaseReference monthCountRef = rootRef.child("users").child(firebaseAuth.getUid()).child("Exercises");
//
//        monthCountRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
////                for(int i =0; i < _titleParents.size(); i++) {
////                    if (_titleParents.get(i).getTitle().equals("الشهر #1"))
////                        counterWeek = (int) dataSnapshot.child("Month 1").getChildrenCount();
////                    if (_titleParents.get(i).getTitle().equals("الشهر #2"))
////                        counterWeek = (int) dataSnapshot.child("Month 2").getChildrenCount();
////                    if (_titleParents.get(i).getTitle().equals("الشهر #3"))
////                        counterWeek = (int) dataSnapshot.child("Month 3").getChildrenCount();
////                }
//
//                adapter.setOnItemClickListener(new ExpandableAdapter.OnItemClickListener() {
//                    @Override
//                    public void OnItemClick(int position) {
//                        childList = new ArrayList<>();
//                        if(position == 0){
//                            childList = new ArrayList<>();
//
//                            counterWeek = (int) dataSnapshot.child("Month 1").getChildrenCount();
//                            for(int i =0; i < counterWeek; i++){
//                                childList.add(new TitleChild(String.format("الاسبوع #%d",i+1)));
//                                for(int j =0; j < _titleParents.size(); j++) {
//                                    _titleParents.get(j).setChildObjectList(childList);
//                                }
//                            }
//                        }else if(position == 1){
//                            childList = new ArrayList<>();
//
//                            counterWeek = (int) dataSnapshot.child("Month 2").getChildrenCount();
//                            for(int i =0; i < counterWeek; i++){
//                                childList.add(new TitleChild(String.format("الاسبوع #%d",i+1)));
//                                for(int j =0; j < _titleParents.size(); j++) {
//                                    _titleParents.get(j).setChildObjectList(childList);
//                                }
//                            }
//                        }else if(position == 2){
//                            childList = new ArrayList<>();
//
//                            counterWeek = (int) dataSnapshot.child("Month 3").getChildrenCount();
//                            for(int i =0; i < counterWeek; i++){
//                                childList.add(new TitleChild(String.format("الاسبوع #%d",i+1)));
//                                for(int j =0; j < _titleParents.size(); j++) {
//                                    _titleParents.get(j).setChildObjectList(childList);
//                                }
//                            }
//                        }
//
//                    }
//                });
//
//            }
//
//
//
//
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//        return parentObject;
//
//    }

    @SuppressLint("ClickableViewAccessibility")
    private void scrollInsideScroll() {
        scrollView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                perfectWeight.getParent().requestDisallowInterceptTouchEvent(false);

                return false;
            }
        });

        perfectWeight.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                perfectWeight.getParent().requestDisallowInterceptTouchEvent(true);

                return false;
            }
        });
    }

    public void getWeights(){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference2 = firebaseDatabase.getReference().child("users")
                .child(firebaseAuth.getUid()).child("Profile");



        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                UserInfo userinfo = dataSnapshot.getValue(UserInfo.class);


             //   currentWeight.setText(userinfo.getPreviousWeight());
                perfectWeight.setText(userinfo.getAdminBrief());
                previousWeight.setText(userinfo.getPreviousWeight());

                p.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(TrackProgressFragmentUser.this.getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void updateWeight(){
        updateWeights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cuWeight = currentWeight.getText().toString().trim();

              //  previousWeight.setText(cuWeight);

                    //set admin = true to specific user
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference databasaeReference = firebaseDatabase.getReference();



                    databasaeReference.child("users").child(firebaseAuth.getUid()).child("Profile").child("weight")
                            .setValue(cuWeight);


                    Toast.makeText(TrackProgressFragmentUser.this.getContext(),"تم تحديث الوزن",Toast.LENGTH_LONG).show();



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
    public void getMonthTabs() {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference monthCountRef = rootRef.child("users").child(firebaseAuth.getUid()).child("Exercises");

        monthCountRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                counterMonth = (int) dataSnapshot.getChildrenCount();

                getGenres();




//                for(int i =0; i < _titleParents.size(); i++) {
//                    counterWeek = (int) dataSnapshot.child(_titleParents.get(i).getTitle()).getChildrenCount();
//                    if(_titleParents.get(i).getTitle().equals("Month 1")){
//                        for(TitleParent title:_titleParents)
//                        {
//                            List<Object> childList = new ArrayList<>();
//                            childList.add(new TitleChild(String.format("الشهر #%d",i+1)));
//                            title.setChildObjectList(childList);
//                            parentObject.add(title);
//                        }
                    }





            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void getWeeks() {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference monthCountRef = rootRef.child("users").child(firebaseAuth.getUid()).child("Exercises");

        monthCountRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
//                for (int i = 0; i < genres.size(); i++) {
//                    if (position == 0)
//                        counterWeek = (int) dataSnapshot.child("Month 1").getChildrenCount();
//                    if (position == 1)
//                        counterWeek = (int) dataSnapshot.child("Month 2").getChildrenCount();
//                    if (position ==2)
//                        counterWeek = (int) dataSnapshot.child("Month 3").getChildrenCount();
//                }
//                    for (int j = 0; j < counterWeek; j++) {
//
//                        items.add(new PeopleAdapter.PeopleListItem("9348", String.format("الاسبوع #%d", j + 1)));
//
//                }
//                adapter.notifyDataSetChanged();
//                weeks = new ArrayList<>();
//                    weeks.add(new Artist(String.format("الاسبوع #%d",i+1)));
//
//
//                }
                //genres.add(new Genere(String.format("الشهر #%d",1),artists));
                //mAdapter.notifyDataSetChanged();
  //              genres = new ArrayList<>();
                for(int i=0;i< counterMonth;i++)
                {
//            parentObject = new ArrayList<>();
//                    genres.add(new Genere(String.format("الشهر #%d",i+1),weeks));
                }
            //    genres.size();
                artists.size();
//                ExpandableAdapter e =new ExpandableAdapter(genres);
//                mRecyclerView.setAdapter(e);

//                mAdapter.setOnGroupClickListener(new OnGroupClickListener() {
//                    @Override
//                    public boolean onGroupClick(final int flatPos) {
//                        position =0;
//                        getmonthData();
//                        mAdapter.setOnGroupExpandCollapseListener(new GroupExpandCollapseListener() {
//                            @Override
//                            public void onGroupExpanded(ExpandableGroup group) {
//                                if(flatPos != 0)
//                                position = flatPos;
//                            }
//
//                            @Override
//                            public void onGroupCollapsed(ExpandableGroup group) {
////                                if(position >= 0 && flatPos != 0) {
////                                    position = flatPos - artists.size();
////                                }else {
////                                    position = 0;
////                                }
//                            }
//                        });
////                        position =0;
////
//                        getWeeks();
//                        return false;
//                    }
//                });


//                mAdapter.setOnGroupClickListener(new OnGroupClickListener() {
//                    @Override
//                    public boolean onGroupClick(int flatPos) {
//                        position = flatPos;
//                        getWeeks();
//                        return false;
//                    }
//                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void getmonthData(){
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final DatabaseReference monthCountRef = rootRef.child("users").child(firebaseAuth.getUid()).child("Exercises");

        for(int i=1; i< adapter.getItemCount(); i++){
            if(adapter.isExpanded(i)){
                position = position +4;
                break;
            }
        }

        monthCountRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (position == 0) {

                    monthCountRef.child("Month 1").child("Week 1").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            exList1 = new ArrayList<>();
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
                                exList1.add(ds.getValue(Exercises.class));
                                exList1.size();
                            }
                            tot1 = 0;
                            weighttot1 = 0;
                            rm1tot1 = 0;
                            for (int i = 0; i < exList1.size(); i++) {
                                for (int j = 0; j < exList1.get(i).getExercise().size(); j++) {
                                    if (!exList1.get(i).getExercise().
                                            get(j).getSets().get(j).getVolume().isEmpty()) {
                                        //for getting vol
                                        tot1 = tot1 + Double.parseDouble(exList1.get(i).getExercise().
                                                get(j).getSets().get(j).getVolume());
                                        //for getting rm1
                                        rm1tot1 = rm1tot1 + Double.parseDouble(exList1.get(i).getExercise().
                                                get(j).getSets().get(j).getRM1());
                                        //for getting weight
                                        weighttot1 = weighttot1 + Double.parseDouble(exList1.get(i).getExercise().
                                                get(j).getSets().get(j).getWeight());
                                        //for getting mupic1s
                                        mupic1.add(exList1.get(i).getExercise().
                                                get(j).getTargetedmuscle());
                                    }
                                }
                            }
                            displaytargetedmucsles();

//                            for (int i =0; i< mupic1.size(); i++){
//                                if(mupic1.get(i).equals("صدر")){
//                                    img.add(R.drawable.mu_chest);
//                                }
//                                if(mupic1.get(i).equals("يطن")) {
//                                    img.add(R.drawable.mu_abs);
//                                }
//                                    if(mupic1.get(i).equals("قدم")) {
//                                        img.add(R.drawable.mu_quadriceps);
//                                    }
//                                        if(mupic1.get(i).equals("صدر")) {
//                                            img.add(R.drawable.mu_chest);
//                                        }
//                                            if(mupic1.get(i).equals("ترابيس")) {
//                                                img.add(R.drawable.mu_trapeze);
//                                            }
//                                                if(mupic1.get(i).equals("اكتاف")) {
//                                                    img.add(R.drawable.mu_shoulders);
//                                                }
//                                                    if(mupic1.get(i).equals("باي")) {
//                                                        img.add(R.drawable.mu_biceps);
//                                                    }
//                                                        if(mupic1.get(i).equals("عضلة السمانة")) {
//                                                            img.add(R.drawable.mu_gastrocnemius);
//                                                        }
//                                                            if(mupic1.get(i).equals("ظهر")) {
//                                                                img.add(R.drawable.mu_bavk);
//                                                            }
//                                p1.imgList = img;
//                            }
                            p1.vol = String.valueOf(tot1);
                            p1.rm1 = String.valueOf(rm1tot1);
                            p1.weight = String.valueOf(weighttot1);
                            adapter.notifyDataSetChanged();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    monthCountRef.child("Month 1").child("Week 2").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            exList2 = new ArrayList<>();
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
                                exList2.add(ds.getValue(Exercises.class));
                                exList2.size();
                            }
                            tot2 = 0;
                            weighttot2 = 0;
                            rm1tot2 = 0;
                            for (int i = 0; i < exList2.size(); i++) {
                                for (int j = 0; j < exList2.get(i).getExercise().size(); j++) {
                                    if (!exList2.get(i).getExercise().
                                            get(j).getSets().get(j).getVolume().isEmpty()){
                                        tot2 = tot2 + Double.parseDouble(exList2.get(i).getExercise().
                                                get(j).getSets().get(j).getVolume());
                                    //for getting rm1
                                    rm1tot2 = rm1tot2 + Double.parseDouble(exList2.get(i).getExercise().
                                            get(j).getSets().get(j).getRM1());
                                    //for getting weight
                                    weighttot2 = weighttot2 + Double.parseDouble(exList2.get(i).getExercise().
                                            get(j).getSets().get(j).getWeight());
                                    //for getting mupic1s
                                    mupic2.add(exList2.get(i).getExercise().
                                            get(j).getTargetedmuscle());
                                }
                            }
                        }
                        displaytargetedmucsles();
                            p2.vol = String.valueOf(tot2);
                            p2.rm1 = String.valueOf(rm1tot2);
                            p2.weight = String.valueOf(weighttot2);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    monthCountRef.child("Month 1").child("Week 3").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            exList3 = new ArrayList<>();
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
                                exList3.add(ds.getValue(Exercises.class));
                                exList3.size();
                            }
                            tot3 = 0;
                            weighttot3 = 0;
                            rm1tot3 = 0;
                            for (int i = 0; i < exList3.size(); i++) {
                                for (int j = 0; j < exList3.get(i).getExercise().size(); j++) {
                                    if (!exList3.get(i).getExercise().
                                            get(j).getSets().get(j).getVolume().isEmpty()){
                                        tot3 = tot3 + Double.parseDouble(exList3.get(i).getExercise().
                                                get(j).getSets().get(j).getVolume());
                                    //for getting rm1
                                    rm1tot3 = rm1tot3 + Double.parseDouble(exList3.get(i).getExercise().
                                            get(j).getSets().get(j).getRM1());
                                    //for getting weight
                                    weighttot3 = weighttot3 + Double.parseDouble(exList3.get(i).getExercise().
                                            get(j).getSets().get(j).getWeight());
                                        //for getting mupic1s
                                        mupic3.add(exList3.get(i).getExercise().
                                                get(j).getTargetedmuscle());
                                    }
                                }
                            }
                            displaytargetedmucsles();
                            p3.vol = String.valueOf(tot3);
                            p3.rm1 = String.valueOf(rm1tot3);
                            p3.weight = String.valueOf(weighttot3);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    monthCountRef.child("Month 1").child("Week 4").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                exList4 = new ArrayList<>();
//                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
                                exList4.add(ds.getValue(Exercises.class));
                                exList4.size();
                            }
                            tot4 = 0;
                            weighttot4 = 0;
                            rm1tot4 = 0;
                            for (int i = 0; i < exList4.size(); i++) {
                                for (int j = 0; j < exList4.get(i).getExercise().size(); j++) {
                                    if (!exList4.get(i).getExercise().
                                            get(j).getSets().get(j).getVolume().isEmpty()){
                                        tot4 = tot4 + Double.parseDouble(exList4.get(i).getExercise().
                                                get(j).getSets().get(j).getVolume());
                                    //for getting rm1
                                    rm1tot4 = rm1tot4 + Double.parseDouble(exList4.get(i).getExercise().
                                            get(j).getSets().get(j).getRM1());
                                    //for getting weight
                                    weighttot4 = weighttot4 + Double.parseDouble(exList4.get(i).getExercise().
                                            get(j).getSets().get(j).getWeight());
                                        //for getting mupic1s
                                        mupic4.add(exList4.get(i).getExercise().
                                                get(j).getTargetedmuscle());
                                    }
                                }
                            }
                            displaytargetedmucsles();
                            p4.vol = String.valueOf(tot4);
                            p4.rm1 = String.valueOf(rm1tot4);
                            p4.weight = String.valueOf(weighttot4);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                //===========================================================
                if (position == 5) {
                        artists = new ArrayList<>();
                        final ArrayList<Exercises> exList = new ArrayList<>();

                        monthCountRef.child("Month 2").child("Week 1").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                exList1 = new ArrayList<>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
                                    exList1.add(ds.getValue(Exercises.class));
                                    exList.size();
                                }
                                tot1 = 0;
                                weighttot1 = 0;
                                rm1tot1 = 0;
                                for (int i = 0; i < exList1.size(); i++) {
                                    for (int j = 0; j < exList1.get(i).getExercise().size(); j++) {
                                        if (!exList1.get(i).getExercise().
                                                get(j).getSets().get(j).getVolume().isEmpty()){
                                            //for getting vol
                                            tot1 = tot1 + Double.parseDouble(exList1.get(i).getExercise().
                                                    get(j).getSets().get(j).getVolume());
                                        //for getting rm1
                                        rm1tot1 = rm1tot1 + Double.parseDouble(exList1.get(i).getExercise().
                                                get(j).getSets().get(j).getRM1());
                                        //for getting weight
                                        weighttot1 = weighttot1 + Double.parseDouble(exList1.get(i).getExercise().
                                                get(j).getSets().get(j).getWeight());
                                        //for getting mupic1s
                                        mupic1.add(exList1.get(i).getExercise().
                                                get(j).getTargetedmuscle());
                                    }
                                }
                            }
                            displaytargetedmucsles();
                                p1.vol = String.valueOf(tot1);
                                p1.rm1 = String.valueOf(rm1tot1);
                                p1.weight = String.valueOf(weighttot1);
                                adapter.notifyDataSetChanged();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        monthCountRef.child("Month 2").child("Week 2").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                exList2 = new ArrayList<>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
                                    exList2.add(ds.getValue(Exercises.class));
                                }
                                tot2 = 0;
                                weighttot2 = 0;
                                rm1tot2 = 0;
                                for (int i = 0; i < exList2.size(); i++) {
                                    for (int j = 0; j < exList2.get(i).getExercise().size(); j++) {
                                        if (!exList2.get(i).getExercise().
                                                get(j).getSets().get(j).getVolume().isEmpty()){
                                            //for getting vol
                                            tot2 = tot2 + Double.parseDouble(exList2.get(i).getExercise().
                                                    get(j).getSets().get(j).getVolume());
                                        //for getting rm1
                                        rm1tot2 = rm1tot2 + Double.parseDouble(exList2.get(i).getExercise().
                                                get(j).getSets().get(j).getRM1());
                                        //for getting weight
                                        weighttot2 = weighttot2 + Double.parseDouble(exList2.get(i).getExercise().
                                                get(j).getSets().get(j).getWeight());
                                            //for getting mupic1s
                                            mupic2.add(exList2.get(i).getExercise().
                                                    get(j).getTargetedmuscle());
                                        }
                                    }
                                }
                                displaytargetedmucsles();
                                p2.vol = String.valueOf(tot2);
                                p2.rm1 = String.valueOf(rm1tot2);
                                p2.weight = String.valueOf(weighttot2);
                                adapter.notifyDataSetChanged();

                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        monthCountRef.child("Month 2").child("Week 3").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                exList3 = new ArrayList<>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
                                    exList3.add(ds.getValue(Exercises.class));
                                }
                                tot3 = 0;
                                weighttot3 = 0;
                                rm1tot3 = 0;
                                for (int i = 0; i < exList3.size(); i++) {
                                    for (int j = 0; j < exList3.get(i).getExercise().size(); j++) {
                                        if (!exList3.get(i).getExercise().
                                                get(j).getSets().get(j).getVolume().isEmpty()){
                                            //for getting vol
                                            tot3 = tot3 + Double.parseDouble(exList3.get(i).getExercise().
                                                    get(j).getSets().get(j).getVolume());
                                        //for getting rm1
                                        rm1tot3 = rm1tot3 + Double.parseDouble(exList3.get(i).getExercise().
                                                get(j).getSets().get(j).getRM1());
                                        //for getting weight
                                        weighttot3 = weighttot3 + Double.parseDouble(exList3.get(i).getExercise().
                                                get(j).getSets().get(j).getWeight());
                                            //for getting mupic1s
                                            mupic3.add(exList3.get(i).getExercise().
                                                    get(j).getTargetedmuscle());
                                        }
                                    }
                                }
                                displaytargetedmucsles();
                                p3.vol = String.valueOf(tot3);
                                p3.rm1 = String.valueOf(rm1tot3);
                                p3.weight = String.valueOf(weighttot3);
                                adapter.notifyDataSetChanged();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        monthCountRef.child("Month 2").child("Week 4").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                exList4 = new ArrayList<>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
                                    exList4.add(ds.getValue(Exercises.class));
                                }
                                tot4 = 0;
                                weighttot4 = 0;
                                rm1tot4 = 0;
                                for (int i = 0; i < exList4.size(); i++) {
                                    for (int j = 0; j < exList4.get(i).getExercise().size(); j++) {
                                        if (!exList4.get(i).getExercise().
                                                get(j).getSets().get(j).getVolume().isEmpty()){
                                            //for getting vol
                                            tot4 = tot4 + Double.parseDouble(exList4.get(i).getExercise().
                                                    get(j).getSets().get(j).getVolume());
                                        //for getting rm1
                                        rm1tot4 = rm1tot4 + Double.parseDouble(exList4.get(i).getExercise().
                                                get(j).getSets().get(j).getRM1());
                                        //for getting weight
                                        weighttot4 = weighttot4 + Double.parseDouble(exList4.get(i).getExercise().
                                                get(j).getSets().get(j).getWeight());
                                            //for getting mupic1s
                                            mupic4.add(exList4.get(i).getExercise().
                                                    get(j).getTargetedmuscle());
                                        }
                                    }
                                }
                                displaytargetedmucsles();
                                p4.vol = String.valueOf(tot4);
                                p4.rm1 = String.valueOf(rm1tot4);
                                p4.weight = String.valueOf(weighttot4);
                                adapter.notifyDataSetChanged();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                } else {
                    if (position == 1) {
                        artists = new ArrayList<>();
                        final ArrayList<Exercises> exList = new ArrayList<>();

                        monthCountRef.child("Month 2").child("Week 1").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                exList1 = new ArrayList<>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
                                    exList1.add(ds.getValue(Exercises.class));
                                    exList.size();
                                }
                                tot1 = 0;
                                weighttot1 = 0;
                                rm1tot1 = 0;
                                for (int i = 0; i < exList1.size(); i++) {
                                    for (int j = 0; j < exList1.get(i).getExercise().size(); j++) {
                                        if (!exList1.get(i).getExercise().
                                                get(j).getSets().get(j).getVolume().isEmpty()){
                                            //for getting vol
                                            tot1 = tot1 + Double.parseDouble(exList1.get(i).getExercise().
                                                    get(j).getSets().get(j).getVolume());
                                        //for getting rm1
                                        rm1tot1 = rm1tot1 + Double.parseDouble(exList1.get(i).getExercise().
                                                get(j).getSets().get(j).getRM1());
                                        //for getting weight
                                        weighttot1 = weighttot1 + Double.parseDouble(exList1.get(i).getExercise().
                                                get(j).getSets().get(j).getWeight());
                                            //for getting mupic1s
                                            mupic1.add(exList1.get(i).getExercise().
                                                    get(j).getTargetedmuscle());
                                        }
                                    }
                                }
                                displaytargetedmucsles();
                                p1.vol = String.valueOf(tot1);
                                p1.rm1 = String.valueOf(rm1tot1);
                                p1.weight = String.valueOf(weighttot1);
                                adapter.notifyDataSetChanged();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        monthCountRef.child("Month 2").child("Week 2").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                exList2 = new ArrayList<>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
                                    exList2.add(ds.getValue(Exercises.class));
                                }
                                tot2 = 0;
                                weighttot2 = 0;
                                rm1tot2 = 0;
                                for (int i = 0; i < exList2.size(); i++) {
                                    for (int j = 0; j < exList2.get(i).getExercise().size(); j++) {
                                        if (!exList2.get(i).getExercise().
                                                get(j).getSets().get(j).getVolume().isEmpty()) {
                                            //for getting vol
                                            tot2 = tot2 + Double.parseDouble(exList2.get(i).getExercise().
                                                    get(j).getSets().get(j).getVolume());
                                            //for getting rm1
                                            rm1tot2 = rm1tot2 + Double.parseDouble(exList2.get(i).getExercise().
                                                    get(j).getSets().get(j).getRM1());
                                            //for getting weight
                                            weighttot2 = weighttot2 + Double.parseDouble(exList2.get(i).getExercise().
                                                    get(j).getSets().get(j).getWeight());
                                            //for getting mupic1s
                                            mupic2.add(exList2.get(i).getExercise().
                                                    get(j).getTargetedmuscle());
                                        }
                                    }
                                }
                                displaytargetedmucsles();
                                p2.vol = String.valueOf(tot2);
                                p2.rm1 = String.valueOf(rm1tot2);
                                p2.weight = String.valueOf(weighttot2);
                                adapter.notifyDataSetChanged();

                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        monthCountRef.child("Month 2").child("Week 3").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                exList3 = new ArrayList<>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
                                    exList3.add(ds.getValue(Exercises.class));
                                }
                                tot3 = 0;
                                weighttot3 = 0;
                                rm1tot3 = 0;
                                for (int i = 0; i < exList3.size(); i++) {
                                    for (int j = 0; j < exList3.get(i).getExercise().size(); j++) {
                                        if (!exList3.get(i).getExercise().
                                                get(j).getSets().get(j).getVolume().isEmpty()) {
                                            //for getting vol
                                            tot3 = tot3 + Double.parseDouble(exList3.get(i).getExercise().
                                                    get(j).getSets().get(j).getVolume());
                                            //for getting rm1
                                            rm1tot3 = rm1tot3 + Double.parseDouble(exList3.get(i).getExercise().
                                                    get(j).getSets().get(j).getRM1());
                                            //for getting weight
                                            weighttot3 = weighttot3 + Double.parseDouble(exList3.get(i).getExercise().
                                                    get(j).getSets().get(j).getWeight());
                                            //for getting mupic1s
                                            mupic3.add(exList3.get(i).getExercise().
                                                    get(j).getTargetedmuscle());
                                        }
                                    }
                                }
                                displaytargetedmucsles();
                                p3.vol = String.valueOf(tot3);
                                p3.rm1 = String.valueOf(rm1tot3);
                                p3.weight = String.valueOf(weighttot3);
                                adapter.notifyDataSetChanged();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        monthCountRef.child("Month 2").child("Week 4").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                exList4 = new ArrayList<>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
                                    exList4.add(ds.getValue(Exercises.class));
                                }
                                tot4 = 0;
                                weighttot4 = 0;
                                rm1tot4 = 0;
                                for (int i = 0; i < exList4.size(); i++) {
                                    for (int j = 0; j < exList4.get(i).getExercise().size(); j++) {
                                        if (!exList4.get(i).getExercise().
                                                get(j).getSets().get(j).getVolume().isEmpty()){
                                            //for getting vol
                                            tot4 = tot4 + Double.parseDouble(exList4.get(i).getExercise().
                                                    get(j).getSets().get(j).getVolume());
                                        //for getting rm1
                                        rm1tot4 = rm1tot4 + Double.parseDouble(exList4.get(i).getExercise().
                                                get(j).getSets().get(j).getRM1());
                                        //for getting weight
                                        weighttot4 = weighttot4 + Double.parseDouble(exList4.get(i).getExercise().
                                                get(j).getSets().get(j).getWeight());
                                            //for getting mupic1s
                                            mupic4.add(exList4.get(i).getExercise().
                                                    get(j).getTargetedmuscle());
                                        }
                                    }
                                }
                                displaytargetedmucsles();
                                p4.vol = String.valueOf(tot4);
                                p4.rm1 = String.valueOf(rm1tot4);
                                p4.weight = String.valueOf(weighttot4);
                                adapter.notifyDataSetChanged();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
                    if (position == 6) {
                        monthCountRef.child("Month 3").child("Week 1").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                exList1 = new ArrayList<>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
                                    exList1.add(ds.getValue(Exercises.class));
                                }
                                tot1 = 0;
                                weighttot1 = 0;
                                rm1tot1 = 0;
                                for (int i = 0; i < exList1.size(); i++) {
                                    for (int j = 0; j < exList1.get(i).getExercise().size(); j++) {
                                        if (!exList1.get(i).getExercise().
                                                get(j).getSets().get(j).getVolume().isEmpty()) {
                                            //for getting vol
                                            tot1 = tot1 + Double.parseDouble(exList1.get(i).getExercise().
                                                    get(j).getSets().get(j).getVolume());
                                            //for getting rm1
                                            rm1tot1 = rm1tot1 + Double.parseDouble(exList1.get(i).getExercise().
                                                    get(j).getSets().get(j).getRM1());
                                            //for getting weight
                                            weighttot1 = weighttot1 + Double.parseDouble(exList1.get(i).getExercise().
                                                    get(j).getSets().get(j).getWeight());
                                            //for getting mupic1s
                                            mupic1.add(exList1.get(i).getExercise().
                                                    get(j).getTargetedmuscle());
                                        }
                                    }
                                }
                                displaytargetedmucsles();
                                p1.vol = String.valueOf(tot1);
                                p1.rm1 = String.valueOf(rm1tot1);
                                p1.weight = String.valueOf(weighttot1);
                                adapter.notifyDataSetChanged();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        monthCountRef.child("Month 3").child("Week 2").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                exList2 = new ArrayList<>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
                                    exList2.add(ds.getValue(Exercises.class));
                                }
                                tot2 = 0;
                                weighttot2 = 0;
                                rm1tot2 = 0;
                                for (int i = 0; i < exList2.size(); i++) {
                                    for (int j = 0; j < exList2.get(i).getExercise().size(); j++) {
                                        if (!exList2.get(i).getExercise().
                                                get(j).getSets().get(j).getVolume().isEmpty()) {
                                            //for getting vol
                                            tot2 = tot2 + Double.parseDouble(exList2.get(i).getExercise().
                                                    get(j).getSets().get(j).getVolume());
                                            //for getting rm1
                                            rm1tot2 = rm1tot2 + Double.parseDouble(exList2.get(i).getExercise().
                                                    get(j).getSets().get(j).getRM1());
                                            //for getting weight
                                            weighttot2 = weighttot2 + Double.parseDouble(exList2.get(i).getExercise().
                                                    get(j).getSets().get(j).getWeight());
                                            //for getting mupic1s
                                            mupic2.add(exList2.get(i).getExercise().
                                                    get(j).getTargetedmuscle());
                                        }
                                    }
                                }
                                displaytargetedmucsles();
                                p2.vol = String.valueOf(tot2);
                                p2.rm1 = String.valueOf(rm1tot2);
                                p2.weight = String.valueOf(weighttot2);
                                adapter.notifyDataSetChanged();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        monthCountRef.child("Month 3").child("Week 3").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                exList3 = new ArrayList<>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
                                    exList3.add(ds.getValue(Exercises.class));
                                }
                                tot3 = 0;
                                weighttot3 = 0;
                                rm1tot3 = 0;
                                for (int i = 0; i < exList3.size(); i++) {
                                    for (int j = 0; j < exList3.get(i).getExercise().size(); j++) {
                                        if (!exList3.get(i).getExercise().
                                                get(j).getSets().get(j).getVolume().isEmpty()) {
                                            //for getting vol
                                            tot3 = tot3 + Double.parseDouble(exList3.get(i).getExercise().
                                                    get(j).getSets().get(j).getVolume());
                                            //for getting rm1
                                            rm1tot3 = rm1tot3 + Double.parseDouble(exList3.get(i).getExercise().
                                                    get(j).getSets().get(j).getRM1());
                                            //for getting weight
                                            weighttot3 = weighttot3 + Double.parseDouble(exList3.get(i).getExercise().
                                                    get(j).getSets().get(j).getWeight());
                                            //for getting mupic1s
                                            mupic3.add(exList3.get(i).getExercise().
                                                    get(j).getTargetedmuscle());
                                        }
                                    }
                                }
                                displaytargetedmucsles();
                                p3.vol = String.valueOf(tot3);
                                p3.rm1 = String.valueOf(rm1tot3);
                                p3.weight = String.valueOf(weighttot3);
                                adapter.notifyDataSetChanged();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        monthCountRef.child("Month 3").child("Week 4").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                exList4 = new ArrayList<>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
                                    exList4.add(ds.getValue(Exercises.class));
                                }
                                tot4 = 0;
                                weighttot4 = 0;
                                rm1tot4 = 0;
                                for (int i = 0; i < exList4.size(); i++) {
                                    for (int j = 0; j < exList4.get(i).getExercise().size(); j++) {
                                        if (!exList4.get(i).getExercise().
                                                get(j).getSets().get(j).getVolume().isEmpty()) {
                                            //for getting vol
                                            tot4 = tot4 + Double.parseDouble(exList4.get(i).getExercise().
                                                    get(j).getSets().get(j).getVolume());
                                            //for getting rm1
                                            rm1tot4 = rm1tot4 + Double.parseDouble(exList4.get(i).getExercise().
                                                    get(j).getSets().get(j).getRM1());
                                            //for getting weight
                                            weighttot4 = weighttot4 + Double.parseDouble(exList4.get(i).getExercise().
                                                    get(j).getSets().get(j).getWeight());
                                            //for getting mupic1s
                                            mupic4.add(exList4.get(i).getExercise().
                                                    get(j).getTargetedmuscle());
                                        }
                                    }
                                }
                                displaytargetedmucsles();
                                p4.vol = String.valueOf(tot4);
                                p4.rm1 = String.valueOf(rm1tot4);
                                p4.weight = String.valueOf(weighttot4);
                                adapter.notifyDataSetChanged();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                }else {
                    if (position == 2) {
                        monthCountRef.child("Month 3").child("Week 1").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                exList1 = new ArrayList<>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
                                    exList1.add(ds.getValue(Exercises.class));
                                }
                                tot1 = 0;
                                weighttot1 = 0;
                                rm1tot1 = 0;
                                for (int i = 0; i < exList1.size(); i++) {
                                    for (int j = 0; j < exList1.get(i).getExercise().size(); j++) {
                                        if (!exList1.get(i).getExercise().
                                                get(j).getSets().get(j).getVolume().isEmpty()) {
                                            //for getting vol
                                            tot1 = tot1 + Double.parseDouble(exList1.get(i).getExercise().
                                                    get(j).getSets().get(j).getVolume());
                                            //for getting rm1
                                            rm1tot1 = rm1tot1 + Double.parseDouble(exList1.get(i).getExercise().
                                                    get(j).getSets().get(j).getRM1());
                                            //for getting weight
                                            weighttot1 = weighttot1 + Double.parseDouble(exList1.get(i).getExercise().
                                                    get(j).getSets().get(j).getWeight());
                                            //for getting mupic1s
                                            mupic1.add(exList1.get(i).getExercise().
                                                    get(j).getTargetedmuscle());
                                        }
                                    }
                                }
                                displaytargetedmucsles();
                                p1.vol = String.valueOf(tot1);
                                p1.rm1 = String.valueOf(rm1tot1);
                                p1.weight = String.valueOf(weighttot1);
                                adapter.notifyDataSetChanged();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        monthCountRef.child("Month 3").child("Week 2").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                exList2 = new ArrayList<>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
                                    exList2.add(ds.getValue(Exercises.class));
                                }
                                tot2 = 0;
                                weighttot2 = 0;
                                rm1tot2 = 0;
                                for (int i = 0; i < exList2.size(); i++) {
                                    for (int j = 0; j < exList2.get(i).getExercise().size(); j++) {
                                        if (!exList2.get(i).getExercise().
                                                get(j).getSets().get(j).getVolume().isEmpty()) {
                                            //for getting vol
                                            tot2 = tot2 + Double.parseDouble(exList2.get(i).getExercise().
                                                    get(j).getSets().get(j).getVolume());
                                            //for getting rm1
                                            rm1tot2 = rm1tot2 + Double.parseDouble(exList2.get(i).getExercise().
                                                    get(j).getSets().get(j).getRM1());
                                            //for getting weight
                                            weighttot2 = weighttot2 + Double.parseDouble(exList2.get(i).getExercise().
                                                    get(j).getSets().get(j).getWeight());
                                            //for getting mupic1s
                                            mupic2.add(exList2.get(i).getExercise().
                                                    get(j).getTargetedmuscle());
                                        }
                                    }
                                }
                                displaytargetedmucsles();
                                p2.vol = String.valueOf(tot2);
                                p2.rm1 = String.valueOf(rm1tot2);
                                p2.weight = String.valueOf(weighttot2);
                                adapter.notifyDataSetChanged();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        monthCountRef.child("Month 3").child("Week 3").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                exList3 = new ArrayList<>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
                                    exList3.add(ds.getValue(Exercises.class));
                                }
                                tot3 = 0;
                                weighttot3 = 0;
                                rm1tot3 = 0;
                                for (int i = 0; i < exList3.size(); i++) {
                                    for (int j = 0; j < exList3.get(i).getExercise().size(); j++) {
                                        if (!exList3.get(i).getExercise().
                                                get(j).getSets().get(j).getVolume().isEmpty()) {
                                            //for getting vol
                                            tot3 = tot3 + Double.parseDouble(exList3.get(i).getExercise().
                                                    get(j).getSets().get(j).getVolume());
                                            //for getting rm1
                                            rm1tot3 = rm1tot3 + Double.parseDouble(exList3.get(i).getExercise().
                                                    get(j).getSets().get(j).getRM1());
                                            //for getting weight
                                            weighttot3 = weighttot3 + Double.parseDouble(exList3.get(i).getExercise().
                                                    get(j).getSets().get(j).getWeight());
                                            //for getting mupic1s
                                            mupic3.add(exList3.get(i).getExercise().
                                                    get(j).getTargetedmuscle());
                                        }
                                    }
                                }
                                displaytargetedmucsles();
                                p3.vol = String.valueOf(tot3);
                                p3.rm1 = String.valueOf(rm1tot3);
                                p3.weight = String.valueOf(weighttot3);
                                adapter.notifyDataSetChanged();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        monthCountRef.child("Month 3").child("Week 4").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                exList4 = new ArrayList<>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
                                    exList4.add(ds.getValue(Exercises.class));
                                }
                                tot4 = 0;
                                weighttot4 = 0;
                                rm1tot4 = 0;
                                for (int i = 0; i < exList4.size(); i++) {
                                    for (int j = 0; j < exList4.get(i).getExercise().size(); j++) {
                                        if (!exList4.get(i).getExercise().
                                                get(j).getSets().get(j).getVolume().isEmpty()) {
                                            //for getting vol
                                            tot4 = tot4 + Double.parseDouble(exList4.get(i).getExercise().
                                                    get(j).getSets().get(j).getVolume());
                                            //for getting rm1
                                            rm1tot4 = rm1tot4 + Double.parseDouble(exList4.get(i).getExercise().
                                                    get(j).getSets().get(j).getRM1());
                                            //for getting weight
                                            weighttot4 = weighttot4 + Double.parseDouble(exList4.get(i).getExercise().
                                                    get(j).getSets().get(j).getWeight());
                                            //for getting mupic1s
                                            mupic1.add(exList1.get(i).getExercise().
                                                    get(j).getTargetedmuscle());
                                        }
                                    }
                                }
                                displaytargetedmucsles();
                                p4.vol = String.valueOf(tot4);
                                p4.rm1 = String.valueOf(rm1tot4);
                                p4.weight = String.valueOf(weighttot4);
                                adapter.notifyDataSetChanged();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//            if (position == 7) {
//                monthCountRef.child("Month 4").child("Week 1").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList1 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList1.add(ds.getValue(Exercises.class));
//                        }
//                        tot1 = 0;
//                        weighttot1 = 0;
//                        rm1tot1 = 0;
//                        for (int i = 0; i < exList1.size(); i++) {
//                            for (int j = 0; j < exList1.get(i).getExercise().size(); j++) {
//                                if (!exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot1 = tot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot1 = rm1tot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot1 = weighttot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p1.vol = String.valueOf(tot1);
//                        p1.rm1 = String.valueOf(rm1tot1);
//                        p1.weight = String.valueOf(weighttot1);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 4").child("Week 2").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList2 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList2.add(ds.getValue(Exercises.class));
//                        }
//                        tot2 = 0;
//                        weighttot2 = 0;
//                        rm1tot2 = 0;
//                        for (int i = 0; i < exList2.size(); i++) {
//                            for (int j = 0; j < exList2.get(i).getExercise().size(); j++) {
//                                if (!exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot2 = tot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot2 = rm1tot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot2 = weighttot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p2.vol = String.valueOf(tot2);
//                        p2.rm1 = String.valueOf(rm1tot2);
//                        p2.weight = String.valueOf(weighttot2);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 4").child("Week 3").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList3 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList3.add(ds.getValue(Exercises.class));
//                        }
//                        tot3 = 0;
//                        weighttot3 = 0;
//                        rm1tot3 = 0;
//                        for (int i = 0; i < exList3.size(); i++) {
//                            for (int j = 0; j < exList3.get(i).getExercise().size(); j++) {
//                                if (!exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot3 = tot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot3 = rm1tot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot3 = weighttot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p3.vol = String.valueOf(tot3);
//                        p3.rm1 = String.valueOf(rm1tot3);
//                        p3.weight = String.valueOf(weighttot3);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 4").child("Week 4").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList4 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList4.add(ds.getValue(Exercises.class));
//                        }
//                        tot4 = 0;
//                        weighttot4 = 0;
//                        rm1tot4 = 0;
//                        for (int i = 0; i < exList4.size(); i++) {
//                            for (int j = 0; j < exList4.get(i).getExercise().size(); j++) {
//                                if (!exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot4 = tot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot4 = rm1tot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot4 = weighttot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p4.vol = String.valueOf(tot4);
//                        p4.rm1 = String.valueOf(rm1tot4);
//                        p4.weight = String.valueOf(weighttot4);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//        }else {
//            if (position == 3) {
//                monthCountRef.child("Month 4").child("Week 1").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList1 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList1.add(ds.getValue(Exercises.class));
//                        }
//                        tot1 = 0;
//                        weighttot1 = 0;
//                        rm1tot1 = 0;
//                        for (int i = 0; i < exList1.size(); i++) {
//                            for (int j = 0; j < exList1.get(i).getExercise().size(); j++) {
//                                if (!exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot1 = tot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot1 = rm1tot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot1 = weighttot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p1.vol = String.valueOf(tot1);
//                        p1.rm1 = String.valueOf(rm1tot1);
//                        p1.weight = String.valueOf(weighttot1);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 4").child("Week 2").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList2 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList2.add(ds.getValue(Exercises.class));
//                        }
//                        tot2 = 0;
//                        weighttot2 = 0;
//                        rm1tot2 = 0;
//                        for (int i = 0; i < exList2.size(); i++) {
//                            for (int j = 0; j < exList2.get(i).getExercise().size(); j++) {
//                                if (!exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot2 = tot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot2 = rm1tot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot2 = weighttot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p2.vol = String.valueOf(tot2);
//                        p2.rm1 = String.valueOf(rm1tot2);
//                        p2.weight = String.valueOf(weighttot2);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 4").child("Week 3").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList3 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList3.add(ds.getValue(Exercises.class));
//                        }
//                        tot3 = 0;
//                        weighttot3 = 0;
//                        rm1tot3 = 0;
//                        for (int i = 0; i < exList3.size(); i++) {
//                            for (int j = 0; j < exList3.get(i).getExercise().size(); j++) {
//                                if (!exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot3 = tot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot3 = rm1tot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot3 = weighttot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p3.vol = String.valueOf(tot3);
//                        p3.rm1 = String.valueOf(rm1tot3);
//                        p3.weight = String.valueOf(weighttot3);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 4").child("Week 4").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList4 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList4.add(ds.getValue(Exercises.class));
//                        }
//                        tot4 = 0;
//                        weighttot4 = 0;
//                        rm1tot4 = 0;
//                        for (int i = 0; i < exList4.size(); i++) {
//                            for (int j = 0; j < exList4.get(i).getExercise().size(); j++) {
//                                if (!exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot4 = tot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot4 = rm1tot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot4 = weighttot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p4.vol = String.valueOf(tot4);
//                        p4.rm1 = String.valueOf(rm1tot4);
//                        p4.weight = String.valueOf(weighttot4);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//            }
//        }
//            if (position == 8) {
//                monthCountRef.child("Month 5").child("Week 1").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList1 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList1.add(ds.getValue(Exercises.class));
//                        }
//                        tot1 = 0;
//                        weighttot1 = 0;
//                        rm1tot1 = 0;
//                        for (int i = 0; i < exList1.size(); i++) {
//                            for (int j = 0; j < exList1.get(i).getExercise().size(); j++) {
//                                if (!exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot1 = tot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot1 = rm1tot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot1 = weighttot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p1.vol = String.valueOf(tot1);
//                        p1.rm1 = String.valueOf(rm1tot1);
//                        p1.weight = String.valueOf(weighttot1);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 5").child("Week 2").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList2 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList2.add(ds.getValue(Exercises.class));
//                        }
//                        tot2 = 0;
//                        weighttot2 = 0;
//                        rm1tot2 = 0;
//                        for (int i = 0; i < exList2.size(); i++) {
//                            for (int j = 0; j < exList2.get(i).getExercise().size(); j++) {
//                                if (!exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot2 = tot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot2 = rm1tot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot2 = weighttot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p2.vol = String.valueOf(tot2);
//                        p2.rm1 = String.valueOf(rm1tot2);
//                        p2.weight = String.valueOf(weighttot2);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 5").child("Week 3").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList3 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList3.add(ds.getValue(Exercises.class));
//                        }
//                        tot3 = 0;
//                        weighttot3 = 0;
//                        rm1tot3 = 0;
//                        for (int i = 0; i < exList3.size(); i++) {
//                            for (int j = 0; j < exList3.get(i).getExercise().size(); j++) {
//                                if (!exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot3 = tot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot3 = rm1tot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot3 = weighttot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p3.vol = String.valueOf(tot3);
//                        p3.rm1 = String.valueOf(rm1tot3);
//                        p3.weight = String.valueOf(weighttot3);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 5").child("Week 4").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList4 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList4.add(ds.getValue(Exercises.class));
//                        }
//                        tot4 = 0;
//                        weighttot4 = 0;
//                        rm1tot4 = 0;
//                        for (int i = 0; i < exList4.size(); i++) {
//                            for (int j = 0; j < exList4.get(i).getExercise().size(); j++) {
//                                if (!exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot4 = tot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot4 = rm1tot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot4 = weighttot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p4.vol = String.valueOf(tot4);
//                        p4.rm1 = String.valueOf(rm1tot4);
//                        p4.weight = String.valueOf(weighttot4);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//        }else {
//            if (position == 4) {
//                monthCountRef.child("Month 5").child("Week 1").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList1 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList1.add(ds.getValue(Exercises.class));
//                        }
//                        tot1 = 0;
//                        weighttot1 = 0;
//                        rm1tot1 = 0;
//                        for (int i = 0; i < exList1.size(); i++) {
//                            for (int j = 0; j < exList1.get(i).getExercise().size(); j++) {
//                                if (!exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot1 = tot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot1 = rm1tot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot1 = weighttot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p1.vol = String.valueOf(tot1);
//                        p1.rm1 = String.valueOf(rm1tot1);
//                        p1.weight = String.valueOf(weighttot1);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 5").child("Week 2").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList2 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList2.add(ds.getValue(Exercises.class));
//                        }
//                        tot2 = 0;
//                        weighttot2 = 0;
//                        rm1tot2 = 0;
//                        for (int i = 0; i < exList2.size(); i++) {
//                            for (int j = 0; j < exList2.get(i).getExercise().size(); j++) {
//                                if (!exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot2 = tot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot2 = rm1tot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot2 = weighttot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p2.vol = String.valueOf(tot2);
//                        p2.rm1 = String.valueOf(rm1tot2);
//                        p2.weight = String.valueOf(weighttot2);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 5").child("Week 3").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList3 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList3.add(ds.getValue(Exercises.class));
//                        }
//                        tot3 = 0;
//                        weighttot3 = 0;
//                        rm1tot3 = 0;
//                        for (int i = 0; i < exList3.size(); i++) {
//                            for (int j = 0; j < exList3.get(i).getExercise().size(); j++) {
//                                if (!exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot3 = tot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot3 = rm1tot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot3 = weighttot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p3.vol = String.valueOf(tot3);
//                        p3.rm1 = String.valueOf(rm1tot3);
//                        p3.weight = String.valueOf(weighttot3);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 5").child("Week 4").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList4 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList4.add(ds.getValue(Exercises.class));
//                        }
//                        tot4 = 0;
//                        weighttot4 = 0;
//                        rm1tot4 = 0;
//                        for (int i = 0; i < exList4.size(); i++) {
//                            for (int j = 0; j < exList4.get(i).getExercise().size(); j++) {
//                                if (!exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot4 = tot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot4 = rm1tot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot4 = weighttot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p4.vol = String.valueOf(tot4);
//                        p4.rm1 = String.valueOf(rm1tot4);
//                        p4.weight = String.valueOf(weighttot4);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//            }
//        }
//            if (position == 9) {
//                monthCountRef.child("Month 6").child("Week 1").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList1 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList1.add(ds.getValue(Exercises.class));
//                        }
//                        tot1 = 0;
//                        weighttot1 = 0;
//                        rm1tot1 = 0;
//                        for (int i = 0; i < exList1.size(); i++) {
//                            for (int j = 0; j < exList1.get(i).getExercise().size(); j++) {
//                                if (!exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot1 = tot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot1 = rm1tot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot1 = weighttot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p1.vol = String.valueOf(tot1);
//                        p1.rm1 = String.valueOf(rm1tot1);
//                        p1.weight = String.valueOf(weighttot1);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 6").child("Week 2").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList2 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList2.add(ds.getValue(Exercises.class));
//                        }
//                        tot2 = 0;
//                        weighttot2 = 0;
//                        rm1tot2 = 0;
//                        for (int i = 0; i < exList2.size(); i++) {
//                            for (int j = 0; j < exList2.get(i).getExercise().size(); j++) {
//                                if (!exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot2 = tot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot2 = rm1tot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot2 = weighttot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p2.vol = String.valueOf(tot2);
//                        p2.rm1 = String.valueOf(rm1tot2);
//                        p2.weight = String.valueOf(weighttot2);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 6").child("Week 3").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList3 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList3.add(ds.getValue(Exercises.class));
//                        }
//                        tot3 = 0;
//                        weighttot3 = 0;
//                        rm1tot3 = 0;
//                        for (int i = 0; i < exList3.size(); i++) {
//                            for (int j = 0; j < exList3.get(i).getExercise().size(); j++) {
//                                if (!exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot3 = tot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot3 = rm1tot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot3 = weighttot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p3.vol = String.valueOf(tot3);
//                        p3.rm1 = String.valueOf(rm1tot3);
//                        p3.weight = String.valueOf(weighttot3);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 6").child("Week 4").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList4 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList4.add(ds.getValue(Exercises.class));
//                        }
//                        tot4 = 0;
//                        weighttot4 = 0;
//                        rm1tot4 = 0;
//                        for (int i = 0; i < exList4.size(); i++) {
//                            for (int j = 0; j < exList4.get(i).getExercise().size(); j++) {
//                                if (!exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot4 = tot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot4 = rm1tot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot4 = weighttot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p4.vol = String.valueOf(tot4);
//                        p4.rm1 = String.valueOf(rm1tot4);
//                        p4.weight = String.valueOf(weighttot4);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//        }else {
//            if (position == 5) {
//                monthCountRef.child("Month 6").child("Week 1").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList1 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList1.add(ds.getValue(Exercises.class));
//                        }
//                        tot1 = 0;
//                        weighttot1 = 0;
//                        rm1tot1 = 0;
//                        for (int i = 0; i < exList1.size(); i++) {
//                            for (int j = 0; j < exList1.get(i).getExercise().size(); j++) {
//                                if (!exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot1 = tot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot1 = rm1tot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot1 = weighttot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p1.vol = String.valueOf(tot1);
//                        p1.rm1 = String.valueOf(rm1tot1);
//                        p1.weight = String.valueOf(weighttot1);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 6").child("Week 2").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList2 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList2.add(ds.getValue(Exercises.class));
//                        }
//                        tot2 = 0;
//                        weighttot2 = 0;
//                        rm1tot2 = 0;
//                        for (int i = 0; i < exList2.size(); i++) {
//                            for (int j = 0; j < exList2.get(i).getExercise().size(); j++) {
//                                if (!exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot2 = tot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot2 = rm1tot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot2 = weighttot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p2.vol = String.valueOf(tot2);
//                        p2.rm1 = String.valueOf(rm1tot2);
//                        p2.weight = String.valueOf(weighttot2);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 6").child("Week 3").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList3 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList3.add(ds.getValue(Exercises.class));
//                        }
//                        tot3 = 0;
//                        weighttot3 = 0;
//                        rm1tot3 = 0;
//                        for (int i = 0; i < exList3.size(); i++) {
//                            for (int j = 0; j < exList3.get(i).getExercise().size(); j++) {
//                                if (!exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot3 = tot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot3 = rm1tot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot3 = weighttot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p3.vol = String.valueOf(tot3);
//                        p3.rm1 = String.valueOf(rm1tot3);
//                        p3.weight = String.valueOf(weighttot3);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 6").child("Week 4").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList4 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList4.add(ds.getValue(Exercises.class));
//                        }
//                        tot4 = 0;
//                        weighttot4 = 0;
//                        rm1tot4 = 0;
//                        for (int i = 0; i < exList4.size(); i++) {
//                            for (int j = 0; j < exList4.get(i).getExercise().size(); j++) {
//                                if (!exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot4 = tot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot4 = rm1tot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot4 = weighttot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p4.vol = String.valueOf(tot4);
//                        p4.rm1 = String.valueOf(rm1tot4);
//                        p4.weight = String.valueOf(weighttot4);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//            }
//        }
//            if (position == 10) {
//                monthCountRef.child("Month 7").child("Week 1").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList1 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList1.add(ds.getValue(Exercises.class));
//                        }
//                        tot1 = 0;
//                        weighttot1 = 0;
//                        rm1tot1 = 0;
//                        for (int i = 0; i < exList1.size(); i++) {
//                            for (int j = 0; j < exList1.get(i).getExercise().size(); j++) {
//                                if (!exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot1 = tot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot1 = rm1tot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot1 = weighttot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p1.vol = String.valueOf(tot1);
//                        p1.rm1 = String.valueOf(rm1tot1);
//                        p1.weight = String.valueOf(weighttot1);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 7").child("Week 2").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList2 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList2.add(ds.getValue(Exercises.class));
//                        }
//                        tot2 = 0;
//                        weighttot2 = 0;
//                        rm1tot2 = 0;
//                        for (int i = 0; i < exList2.size(); i++) {
//                            for (int j = 0; j < exList2.get(i).getExercise().size(); j++) {
//                                if (!exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot2 = tot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot2 = rm1tot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot2 = weighttot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p2.vol = String.valueOf(tot2);
//                        p2.rm1 = String.valueOf(rm1tot2);
//                        p2.weight = String.valueOf(weighttot2);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 7").child("Week 3").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList3 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList3.add(ds.getValue(Exercises.class));
//                        }
//                        tot3 = 0;
//                        weighttot3 = 0;
//                        rm1tot3 = 0;
//                        for (int i = 0; i < exList3.size(); i++) {
//                            for (int j = 0; j < exList3.get(i).getExercise().size(); j++) {
//                                if (!exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot3 = tot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot3 = rm1tot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot3 = weighttot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p3.vol = String.valueOf(tot3);
//                        p3.rm1 = String.valueOf(rm1tot3);
//                        p3.weight = String.valueOf(weighttot3);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 7").child("Week 4").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList4 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList4.add(ds.getValue(Exercises.class));
//                        }
//                        tot4 = 0;
//                        weighttot4 = 0;
//                        rm1tot4 = 0;
//                        for (int i = 0; i < exList4.size(); i++) {
//                            for (int j = 0; j < exList4.get(i).getExercise().size(); j++) {
//                                if (!exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot4 = tot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot4 = rm1tot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot4 = weighttot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p4.vol = String.valueOf(tot4);
//                        p4.rm1 = String.valueOf(rm1tot4);
//                        p4.weight = String.valueOf(weighttot4);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//        }else {
//            if (position == 6) {
//                monthCountRef.child("Month 7").child("Week 1").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList1 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList1.add(ds.getValue(Exercises.class));
//                        }
//                        tot1 = 0;
//                        weighttot1 = 0;
//                        rm1tot1 = 0;
//                        for (int i = 0; i < exList1.size(); i++) {
//                            for (int j = 0; j < exList1.get(i).getExercise().size(); j++) {
//                                if (!exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot1 = tot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot1 = rm1tot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot1 = weighttot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p1.vol = String.valueOf(tot1);
//                        p1.rm1 = String.valueOf(rm1tot1);
//                        p1.weight = String.valueOf(weighttot1);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 7").child("Week 2").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList2 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList2.add(ds.getValue(Exercises.class));
//                        }
//                        tot2 = 0;
//                        weighttot2 = 0;
//                        rm1tot2 = 0;
//                        for (int i = 0; i < exList2.size(); i++) {
//                            for (int j = 0; j < exList2.get(i).getExercise().size(); j++) {
//                                if (!exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot2 = tot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot2 = rm1tot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot2 = weighttot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p2.vol = String.valueOf(tot2);
//                        p2.rm1 = String.valueOf(rm1tot2);
//                        p2.weight = String.valueOf(weighttot2);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 7").child("Week 3").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList3 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList3.add(ds.getValue(Exercises.class));
//                        }
//                        tot3 = 0;
//                        weighttot3 = 0;
//                        rm1tot3 = 0;
//                        for (int i = 0; i < exList3.size(); i++) {
//                            for (int j = 0; j < exList3.get(i).getExercise().size(); j++) {
//                                if (!exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot3 = tot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot3 = rm1tot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot3 = weighttot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p3.vol = String.valueOf(tot3);
//                        p3.rm1 = String.valueOf(rm1tot3);
//                        p3.weight = String.valueOf(weighttot3);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 7").child("Week 4").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList4 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList4.add(ds.getValue(Exercises.class));
//                        }
//                        tot4 = 0;
//                        weighttot4 = 0;
//                        rm1tot4 = 0;
//                        for (int i = 0; i < exList4.size(); i++) {
//                            for (int j = 0; j < exList4.get(i).getExercise().size(); j++) {
//                                if (!exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot4 = tot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot4 = rm1tot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot4 = weighttot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p4.vol = String.valueOf(tot4);
//                        p4.rm1 = String.valueOf(rm1tot4);
//                        p4.weight = String.valueOf(weighttot4);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//            }
//        }
//            if (position == 11) {
//                monthCountRef.child("Month 8").child("Week 1").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList1 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList1.add(ds.getValue(Exercises.class));
//                        }
//                        tot1 = 0;
//                        weighttot1 = 0;
//                        rm1tot1 = 0;
//                        for (int i = 0; i < exList1.size(); i++) {
//                            for (int j = 0; j < exList1.get(i).getExercise().size(); j++) {
//                                if (!exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot1 = tot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot1 = rm1tot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot1 = weighttot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p1.vol = String.valueOf(tot1);
//                        p1.rm1 = String.valueOf(rm1tot1);
//                        p1.weight = String.valueOf(weighttot1);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 8").child("Week 2").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList2 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList2.add(ds.getValue(Exercises.class));
//                        }
//                        tot2 = 0;
//                        weighttot2 = 0;
//                        rm1tot2 = 0;
//                        for (int i = 0; i < exList2.size(); i++) {
//                            for (int j = 0; j < exList2.get(i).getExercise().size(); j++) {
//                                if (!exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot2 = tot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot2 = rm1tot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot2 = weighttot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p2.vol = String.valueOf(tot2);
//                        p2.rm1 = String.valueOf(rm1tot2);
//                        p2.weight = String.valueOf(weighttot2);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 8").child("Week 3").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList3 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList3.add(ds.getValue(Exercises.class));
//                        }
//                        tot3 = 0;
//                        weighttot3 = 0;
//                        rm1tot3 = 0;
//                        for (int i = 0; i < exList3.size(); i++) {
//                            for (int j = 0; j < exList3.get(i).getExercise().size(); j++) {
//                                if (!exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot3 = tot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot3 = rm1tot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot3 = weighttot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p3.vol = String.valueOf(tot3);
//                        p3.rm1 = String.valueOf(rm1tot3);
//                        p3.weight = String.valueOf(weighttot3);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 8").child("Week 4").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList4 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList4.add(ds.getValue(Exercises.class));
//                        }
//                        tot4 = 0;
//                        weighttot4 = 0;
//                        rm1tot4 = 0;
//                        for (int i = 0; i < exList4.size(); i++) {
//                            for (int j = 0; j < exList4.get(i).getExercise().size(); j++) {
//                                if (!exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot4 = tot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot4 = rm1tot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot4 = weighttot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p4.vol = String.valueOf(tot4);
//                        p4.rm1 = String.valueOf(rm1tot4);
//                        p4.weight = String.valueOf(weighttot4);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//        }else {
//            if (position == 7) {
//                monthCountRef.child("Month 8").child("Week 1").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList1 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList1.add(ds.getValue(Exercises.class));
//                        }
//                        tot1 = 0;
//                        weighttot1 = 0;
//                        rm1tot1 = 0;
//                        for (int i = 0; i < exList1.size(); i++) {
//                            for (int j = 0; j < exList1.get(i).getExercise().size(); j++) {
//                                if (!exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot1 = tot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot1 = rm1tot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot1 = weighttot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p1.vol = String.valueOf(tot1);
//                        p1.rm1 = String.valueOf(rm1tot1);
//                        p1.weight = String.valueOf(weighttot1);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 8").child("Week 2").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList2 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList2.add(ds.getValue(Exercises.class));
//                        }
//                        tot2 = 0;
//                        weighttot2 = 0;
//                        rm1tot2 = 0;
//                        for (int i = 0; i < exList2.size(); i++) {
//                            for (int j = 0; j < exList2.get(i).getExercise().size(); j++) {
//                                if (!exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot2 = tot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot2 = rm1tot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot2 = weighttot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p2.vol = String.valueOf(tot2);
//                        p2.rm1 = String.valueOf(rm1tot2);
//                        p2.weight = String.valueOf(weighttot2);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 8").child("Week 3").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList3 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList3.add(ds.getValue(Exercises.class));
//                        }
//                        tot3 = 0;
//                        weighttot3 = 0;
//                        rm1tot3 = 0;
//                        for (int i = 0; i < exList3.size(); i++) {
//                            for (int j = 0; j < exList3.get(i).getExercise().size(); j++) {
//                                if (!exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot3 = tot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot3 = rm1tot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot3 = weighttot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p3.vol = String.valueOf(tot3);
//                        p3.rm1 = String.valueOf(rm1tot3);
//                        p3.weight = String.valueOf(weighttot3);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 8").child("Week 4").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList4 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList4.add(ds.getValue(Exercises.class));
//                        }
//                        tot4 = 0;
//                        weighttot4 = 0;
//                        rm1tot4 = 0;
//                        for (int i = 0; i < exList4.size(); i++) {
//                            for (int j = 0; j < exList4.get(i).getExercise().size(); j++) {
//                                if (!exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot4 = tot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot4 = rm1tot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot4 = weighttot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p4.vol = String.valueOf(tot4);
//                        p4.rm1 = String.valueOf(rm1tot4);
//                        p4.weight = String.valueOf(weighttot4);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//            }
//        }
//            if (position == 11) {
//                monthCountRef.child("Month 8").child("Week 1").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList1 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList1.add(ds.getValue(Exercises.class));
//                        }
//                        tot1 = 0;
//                        weighttot1 = 0;
//                        rm1tot1 = 0;
//                        for (int i = 0; i < exList1.size(); i++) {
//                            for (int j = 0; j < exList1.get(i).getExercise().size(); j++) {
//                                if (!exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot1 = tot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot1 = rm1tot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot1 = weighttot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p1.vol = String.valueOf(tot1);
//                        p1.rm1 = String.valueOf(rm1tot1);
//                        p1.weight = String.valueOf(weighttot1);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 8").child("Week 2").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList2 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList2.add(ds.getValue(Exercises.class));
//                        }
//                        tot2 = 0;
//                        weighttot2 = 0;
//                        rm1tot2 = 0;
//                        for (int i = 0; i < exList2.size(); i++) {
//                            for (int j = 0; j < exList2.get(i).getExercise().size(); j++) {
//                                if (!exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot2 = tot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot2 = rm1tot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot2 = weighttot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p2.vol = String.valueOf(tot2);
//                        p2.rm1 = String.valueOf(rm1tot2);
//                        p2.weight = String.valueOf(weighttot2);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 8").child("Week 3").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList3 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList3.add(ds.getValue(Exercises.class));
//                        }
//                        tot3 = 0;
//                        weighttot3 = 0;
//                        rm1tot3 = 0;
//                        for (int i = 0; i < exList3.size(); i++) {
//                            for (int j = 0; j < exList3.get(i).getExercise().size(); j++) {
//                                if (!exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot3 = tot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot3 = rm1tot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot3 = weighttot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p3.vol = String.valueOf(tot3);
//                        p3.rm1 = String.valueOf(rm1tot3);
//                        p3.weight = String.valueOf(weighttot3);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 8").child("Week 4").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList4 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList4.add(ds.getValue(Exercises.class));
//                        }
//                        tot4 = 0;
//                        weighttot4 = 0;
//                        rm1tot4 = 0;
//                        for (int i = 0; i < exList4.size(); i++) {
//                            for (int j = 0; j < exList4.get(i).getExercise().size(); j++) {
//                                if (!exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot4 = tot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot4 = rm1tot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot4 = weighttot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p4.vol = String.valueOf(tot4);
//                        p4.rm1 = String.valueOf(rm1tot4);
//                        p4.weight = String.valueOf(weighttot4);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//        }else {
//            if (position == 7) {
//                monthCountRef.child("Month 8").child("Week 1").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList1 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList1.add(ds.getValue(Exercises.class));
//                        }
//                        tot1 = 0;
//                        weighttot1 = 0;
//                        rm1tot1 = 0;
//                        for (int i = 0; i < exList1.size(); i++) {
//                            for (int j = 0; j < exList1.get(i).getExercise().size(); j++) {
//                                if (!exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot1 = tot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot1 = rm1tot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot1 = weighttot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p1.vol = String.valueOf(tot1);
//                        p1.rm1 = String.valueOf(rm1tot1);
//                        p1.weight = String.valueOf(weighttot1);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 8").child("Week 2").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList2 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList2.add(ds.getValue(Exercises.class));
//                        }
//                        tot2 = 0;
//                        weighttot2 = 0;
//                        rm1tot2 = 0;
//                        for (int i = 0; i < exList2.size(); i++) {
//                            for (int j = 0; j < exList2.get(i).getExercise().size(); j++) {
//                                if (!exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot2 = tot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot2 = rm1tot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot2 = weighttot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p2.vol = String.valueOf(tot2);
//                        p2.rm1 = String.valueOf(rm1tot2);
//                        p2.weight = String.valueOf(weighttot2);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 8").child("Week 3").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList3 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList3.add(ds.getValue(Exercises.class));
//                        }
//                        tot3 = 0;
//                        weighttot3 = 0;
//                        rm1tot3 = 0;
//                        for (int i = 0; i < exList3.size(); i++) {
//                            for (int j = 0; j < exList3.get(i).getExercise().size(); j++) {
//                                if (!exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot3 = tot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot3 = rm1tot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot3 = weighttot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p3.vol = String.valueOf(tot3);
//                        p3.rm1 = String.valueOf(rm1tot3);
//                        p3.weight = String.valueOf(weighttot3);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 8").child("Week 4").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList4 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList4.add(ds.getValue(Exercises.class));
//                        }
//                        tot4 = 0;
//                        weighttot4 = 0;
//                        rm1tot4 = 0;
//                        for (int i = 0; i < exList4.size(); i++) {
//                            for (int j = 0; j < exList4.get(i).getExercise().size(); j++) {
//                                if (!exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot4 = tot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot4 = rm1tot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot4 = weighttot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p4.vol = String.valueOf(tot4);
//                        p4.rm1 = String.valueOf(rm1tot4);
//                        p4.weight = String.valueOf(weighttot4);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//            }
//        }
//            if (position == 12) {
//                monthCountRef.child("Month 9").child("Week 1").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList1 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList1.add(ds.getValue(Exercises.class));
//                        }
//                        tot1 = 0;
//                        weighttot1 = 0;
//                        rm1tot1 = 0;
//                        for (int i = 0; i < exList1.size(); i++) {
//                            for (int j = 0; j < exList1.get(i).getExercise().size(); j++) {
//                                if (!exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot1 = tot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot1 = rm1tot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot1 = weighttot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p1.vol = String.valueOf(tot1);
//                        p1.rm1 = String.valueOf(rm1tot1);
//                        p1.weight = String.valueOf(weighttot1);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 9").child("Week 2").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList2 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList2.add(ds.getValue(Exercises.class));
//                        }
//                        tot2 = 0;
//                        weighttot2 = 0;
//                        rm1tot2 = 0;
//                        for (int i = 0; i < exList2.size(); i++) {
//                            for (int j = 0; j < exList2.get(i).getExercise().size(); j++) {
//                                if (!exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot2 = tot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot2 = rm1tot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot2 = weighttot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p2.vol = String.valueOf(tot2);
//                        p2.rm1 = String.valueOf(rm1tot2);
//                        p2.weight = String.valueOf(weighttot2);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 9").child("Week 3").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList3 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList3.add(ds.getValue(Exercises.class));
//                        }
//                        tot3 = 0;
//                        weighttot3 = 0;
//                        rm1tot3 = 0;
//                        for (int i = 0; i < exList3.size(); i++) {
//                            for (int j = 0; j < exList3.get(i).getExercise().size(); j++) {
//                                if (!exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot3 = tot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot3 = rm1tot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot3 = weighttot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p3.vol = String.valueOf(tot3);
//                        p3.rm1 = String.valueOf(rm1tot3);
//                        p3.weight = String.valueOf(weighttot3);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 9").child("Week 4").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList4 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList4.add(ds.getValue(Exercises.class));
//                        }
//                        tot4 = 0;
//                        weighttot4 = 0;
//                        rm1tot4 = 0;
//                        for (int i = 0; i < exList4.size(); i++) {
//                            for (int j = 0; j < exList4.get(i).getExercise().size(); j++) {
//                                if (!exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot4 = tot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot4 = rm1tot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot4 = weighttot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p4.vol = String.valueOf(tot4);
//                        p4.rm1 = String.valueOf(rm1tot4);
//                        p4.weight = String.valueOf(weighttot4);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//        }else {
//            if (position == 8) {
//                monthCountRef.child("Month 9").child("Week 1").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList1 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList1.add(ds.getValue(Exercises.class));
//                        }
//                        tot1 = 0;
//                        weighttot1 = 0;
//                        rm1tot1 = 0;
//                        for (int i = 0; i < exList1.size(); i++) {
//                            for (int j = 0; j < exList1.get(i).getExercise().size(); j++) {
//                                if (!exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot1 = tot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot1 = rm1tot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot1 = weighttot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p1.vol = String.valueOf(tot1);
//                        p1.rm1 = String.valueOf(rm1tot1);
//                        p1.weight = String.valueOf(weighttot1);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 9").child("Week 2").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList2 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList2.add(ds.getValue(Exercises.class));
//                        }
//                        tot2 = 0;
//                        weighttot2 = 0;
//                        rm1tot2 = 0;
//                        for (int i = 0; i < exList2.size(); i++) {
//                            for (int j = 0; j < exList2.get(i).getExercise().size(); j++) {
//                                if (!exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot2 = tot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot2 = rm1tot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot2 = weighttot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p2.vol = String.valueOf(tot2);
//                        p2.rm1 = String.valueOf(rm1tot2);
//                        p2.weight = String.valueOf(weighttot2);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 9").child("Week 3").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList3 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList3.add(ds.getValue(Exercises.class));
//                        }
//                        tot3 = 0;
//                        weighttot3 = 0;
//                        rm1tot3 = 0;
//                        for (int i = 0; i < exList3.size(); i++) {
//                            for (int j = 0; j < exList3.get(i).getExercise().size(); j++) {
//                                if (!exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot3 = tot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot3 = rm1tot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot3 = weighttot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p3.vol = String.valueOf(tot3);
//                        p3.rm1 = String.valueOf(rm1tot3);
//                        p3.weight = String.valueOf(weighttot3);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 9").child("Week 4").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList4 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList4.add(ds.getValue(Exercises.class));
//                        }
//                        tot4 = 0;
//                        weighttot4 = 0;
//                        rm1tot4 = 0;
//                        for (int i = 0; i < exList4.size(); i++) {
//                            for (int j = 0; j < exList4.get(i).getExercise().size(); j++) {
//                                if (!exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot4 = tot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot4 = rm1tot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot4 = weighttot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p4.vol = String.valueOf(tot4);
//                        p4.rm1 = String.valueOf(rm1tot4);
//                        p4.weight = String.valueOf(weighttot4);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//            }
//        }
//            if (position == 13) {
//                monthCountRef.child("Month 10").child("Week 1").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList1 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList1.add(ds.getValue(Exercises.class));
//                        }
//                        tot1 = 0;
//                        weighttot1 = 0;
//                        rm1tot1 = 0;
//                        for (int i = 0; i < exList1.size(); i++) {
//                            for (int j = 0; j < exList1.get(i).getExercise().size(); j++) {
//                                if (!exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot1 = tot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot1 = rm1tot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot1 = weighttot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p1.vol = String.valueOf(tot1);
//                        p1.rm1 = String.valueOf(rm1tot1);
//                        p1.weight = String.valueOf(weighttot1);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 10").child("Week 2").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList2 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList2.add(ds.getValue(Exercises.class));
//                        }
//                        tot2 = 0;
//                        weighttot2 = 0;
//                        rm1tot2 = 0;
//                        for (int i = 0; i < exList2.size(); i++) {
//                            for (int j = 0; j < exList2.get(i).getExercise().size(); j++) {
//                                if (!exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot2 = tot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot2 = rm1tot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot2 = weighttot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p2.vol = String.valueOf(tot2);
//                        p2.rm1 = String.valueOf(rm1tot2);
//                        p2.weight = String.valueOf(weighttot2);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 10").child("Week 3").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList3 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList3.add(ds.getValue(Exercises.class));
//                        }
//                        tot3 = 0;
//                        weighttot3 = 0;
//                        rm1tot3 = 0;
//                        for (int i = 0; i < exList3.size(); i++) {
//                            for (int j = 0; j < exList3.get(i).getExercise().size(); j++) {
//                                if (!exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot3 = tot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot3 = rm1tot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot3 = weighttot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p3.vol = String.valueOf(tot3);
//                        p3.rm1 = String.valueOf(rm1tot3);
//                        p3.weight = String.valueOf(weighttot3);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 10").child("Week 4").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList4 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList4.add(ds.getValue(Exercises.class));
//                        }
//                        tot4 = 0;
//                        weighttot4 = 0;
//                        rm1tot4 = 0;
//                        for (int i = 0; i < exList4.size(); i++) {
//                            for (int j = 0; j < exList4.get(i).getExercise().size(); j++) {
//                                if (!exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot4 = tot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot4 = rm1tot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot4 = weighttot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p4.vol = String.valueOf(tot4);
//                        p4.rm1 = String.valueOf(rm1tot4);
//                        p4.weight = String.valueOf(weighttot4);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//        }else {
//            if (position == 9) {
//                monthCountRef.child("Month 10").child("Week 1").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList1 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList1.add(ds.getValue(Exercises.class));
//                        }
//                        tot1 = 0;
//                        weighttot1 = 0;
//                        rm1tot1 = 0;
//                        for (int i = 0; i < exList1.size(); i++) {
//                            for (int j = 0; j < exList1.get(i).getExercise().size(); j++) {
//                                if (!exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot1 = tot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot1 = rm1tot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot1 = weighttot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p1.vol = String.valueOf(tot1);
//                        p1.rm1 = String.valueOf(rm1tot1);
//                        p1.weight = String.valueOf(weighttot1);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 10").child("Week 2").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList2 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList2.add(ds.getValue(Exercises.class));
//                        }
//                        tot2 = 0;
//                        weighttot2 = 0;
//                        rm1tot2 = 0;
//                        for (int i = 0; i < exList2.size(); i++) {
//                            for (int j = 0; j < exList2.get(i).getExercise().size(); j++) {
//                                if (!exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot2 = tot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot2 = rm1tot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot2 = weighttot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p2.vol = String.valueOf(tot2);
//                        p2.rm1 = String.valueOf(rm1tot2);
//                        p2.weight = String.valueOf(weighttot2);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 10").child("Week 3").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList3 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList3.add(ds.getValue(Exercises.class));
//                        }
//                        tot3 = 0;
//                        weighttot3 = 0;
//                        rm1tot3 = 0;
//                        for (int i = 0; i < exList3.size(); i++) {
//                            for (int j = 0; j < exList3.get(i).getExercise().size(); j++) {
//                                if (!exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot3 = tot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot3 = rm1tot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot3 = weighttot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p3.vol = String.valueOf(tot3);
//                        p3.rm1 = String.valueOf(rm1tot3);
//                        p3.weight = String.valueOf(weighttot3);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 10").child("Week 4").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList4 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList4.add(ds.getValue(Exercises.class));
//                        }
//                        tot4 = 0;
//                        weighttot4 = 0;
//                        rm1tot4 = 0;
//                        for (int i = 0; i < exList4.size(); i++) {
//                            for (int j = 0; j < exList4.get(i).getExercise().size(); j++) {
//                                if (!exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot4 = tot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot4 = rm1tot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot4 = weighttot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p4.vol = String.valueOf(tot4);
//                        p4.rm1 = String.valueOf(rm1tot4);
//                        p4.weight = String.valueOf(weighttot4);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//            }
//        }
//            if (position == 14) {
//                monthCountRef.child("Month 11").child("Week 1").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList1 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList1.add(ds.getValue(Exercises.class));
//                        }
//                        tot1 = 0;
//                        weighttot1 = 0;
//                        rm1tot1 = 0;
//                        for (int i = 0; i < exList1.size(); i++) {
//                            for (int j = 0; j < exList1.get(i).getExercise().size(); j++) {
//                                if (!exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot1 = tot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot1 = rm1tot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot1 = weighttot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p1.vol = String.valueOf(tot1);
//                        p1.rm1 = String.valueOf(rm1tot1);
//                        p1.weight = String.valueOf(weighttot1);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 11").child("Week 2").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList2 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList2.add(ds.getValue(Exercises.class));
//                        }
//                        tot2 = 0;
//                        weighttot2 = 0;
//                        rm1tot2 = 0;
//                        for (int i = 0; i < exList2.size(); i++) {
//                            for (int j = 0; j < exList2.get(i).getExercise().size(); j++) {
//                                if (!exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot2 = tot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot2 = rm1tot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot2 = weighttot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p2.vol = String.valueOf(tot2);
//                        p2.rm1 = String.valueOf(rm1tot2);
//                        p2.weight = String.valueOf(weighttot2);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 11").child("Week 3").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList3 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList3.add(ds.getValue(Exercises.class));
//                        }
//                        tot3 = 0;
//                        weighttot3 = 0;
//                        rm1tot3 = 0;
//                        for (int i = 0; i < exList3.size(); i++) {
//                            for (int j = 0; j < exList3.get(i).getExercise().size(); j++) {
//                                if (!exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot3 = tot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot3 = rm1tot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot3 = weighttot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p3.vol = String.valueOf(tot3);
//                        p3.rm1 = String.valueOf(rm1tot3);
//                        p3.weight = String.valueOf(weighttot3);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 11").child("Week 4").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList4 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList4.add(ds.getValue(Exercises.class));
//                        }
//                        tot4 = 0;
//                        weighttot4 = 0;
//                        rm1tot4 = 0;
//                        for (int i = 0; i < exList4.size(); i++) {
//                            for (int j = 0; j < exList4.get(i).getExercise().size(); j++) {
//                                if (!exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot4 = tot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot4 = rm1tot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot4 = weighttot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p4.vol = String.valueOf(tot4);
//                        p4.rm1 = String.valueOf(rm1tot4);
//                        p4.weight = String.valueOf(weighttot4);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//        }else {
//            if (position == 10) {
//                monthCountRef.child("Month 11").child("Week 1").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList1 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList1.add(ds.getValue(Exercises.class));
//                        }
//                        tot1 = 0;
//                        weighttot1 = 0;
//                        rm1tot1 = 0;
//                        for (int i = 0; i < exList1.size(); i++) {
//                            for (int j = 0; j < exList1.get(i).getExercise().size(); j++) {
//                                if (!exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot1 = tot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot1 = rm1tot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot1 = weighttot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p1.vol = String.valueOf(tot1);
//                        p1.rm1 = String.valueOf(rm1tot1);
//                        p1.weight = String.valueOf(weighttot1);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 11").child("Week 2").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList2 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList2.add(ds.getValue(Exercises.class));
//                        }
//                        tot2 = 0;
//                        weighttot2 = 0;
//                        rm1tot2 = 0;
//                        for (int i = 0; i < exList2.size(); i++) {
//                            for (int j = 0; j < exList2.get(i).getExercise().size(); j++) {
//                                if (!exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot2 = tot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot2 = rm1tot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot2 = weighttot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p2.vol = String.valueOf(tot2);
//                        p2.rm1 = String.valueOf(rm1tot2);
//                        p2.weight = String.valueOf(weighttot2);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 11").child("Week 3").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList3 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList3.add(ds.getValue(Exercises.class));
//                        }
//                        tot3 = 0;
//                        weighttot3 = 0;
//                        rm1tot3 = 0;
//                        for (int i = 0; i < exList3.size(); i++) {
//                            for (int j = 0; j < exList3.get(i).getExercise().size(); j++) {
//                                if (!exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot3 = tot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot3 = rm1tot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot3 = weighttot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p3.vol = String.valueOf(tot3);
//                        p3.rm1 = String.valueOf(rm1tot3);
//                        p3.weight = String.valueOf(weighttot3);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 11").child("Week 4").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList4 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList4.add(ds.getValue(Exercises.class));
//                        }
//                        tot4 = 0;
//                        weighttot4 = 0;
//                        rm1tot4 = 0;
//                        for (int i = 0; i < exList4.size(); i++) {
//                            for (int j = 0; j < exList4.get(i).getExercise().size(); j++) {
//                                if (!exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot4 = tot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot4 = rm1tot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot4 = weighttot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p4.vol = String.valueOf(tot4);
//                        p4.rm1 = String.valueOf(rm1tot4);
//                        p4.weight = String.valueOf(weighttot4);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//            }
//        }
//            if (position == 16) {
//                monthCountRef.child("Month 12").child("Week 1").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList1 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList1.add(ds.getValue(Exercises.class));
//                        }
//                        tot1 = 0;
//                        weighttot1 = 0;
//                        rm1tot1 = 0;
//                        for (int i = 0; i < exList1.size(); i++) {
//                            for (int j = 0; j < exList1.get(i).getExercise().size(); j++) {
//                                if (!exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot1 = tot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot1 = rm1tot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot1 = weighttot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p1.vol = String.valueOf(tot1);
//                        p1.rm1 = String.valueOf(rm1tot1);
//                        p1.weight = String.valueOf(weighttot1);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 12").child("Week 2").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList2 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList2.add(ds.getValue(Exercises.class));
//                        }
//                        tot2 = 0;
//                        weighttot2 = 0;
//                        rm1tot2 = 0;
//                        for (int i = 0; i < exList2.size(); i++) {
//                            for (int j = 0; j < exList2.get(i).getExercise().size(); j++) {
//                                if (!exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot2 = tot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot2 = rm1tot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot2 = weighttot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p2.vol = String.valueOf(tot2);
//                        p2.rm1 = String.valueOf(rm1tot2);
//                        p2.weight = String.valueOf(weighttot2);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 12").child("Week 3").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList3 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList3.add(ds.getValue(Exercises.class));
//                        }
//                        tot3 = 0;
//                        weighttot3 = 0;
//                        rm1tot3 = 0;
//                        for (int i = 0; i < exList3.size(); i++) {
//                            for (int j = 0; j < exList3.get(i).getExercise().size(); j++) {
//                                if (!exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot3 = tot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot3 = rm1tot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot3 = weighttot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p3.vol = String.valueOf(tot3);
//                        p3.rm1 = String.valueOf(rm1tot3);
//                        p3.weight = String.valueOf(weighttot3);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 12").child("Week 4").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList4 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList4.add(ds.getValue(Exercises.class));
//                        }
//                        tot4 = 0;
//                        weighttot4 = 0;
//                        rm1tot4 = 0;
//                        for (int i = 0; i < exList4.size(); i++) {
//                            for (int j = 0; j < exList4.get(i).getExercise().size(); j++) {
//                                if (!exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot4 = tot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot4 = rm1tot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot4 = weighttot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p4.vol = String.valueOf(tot4);
//                        p4.rm1 = String.valueOf(rm1tot4);
//                        p4.weight = String.valueOf(weighttot4);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//        }else {
//            if (position == 11) {
//                monthCountRef.child("Month 12").child("Week 1").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList1 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList1.add(ds.getValue(Exercises.class));
//                        }
//                        tot1 = 0;
//                        weighttot1 = 0;
//                        rm1tot1 = 0;
//                        for (int i = 0; i < exList1.size(); i++) {
//                            for (int j = 0; j < exList1.get(i).getExercise().size(); j++) {
//                                if (!exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot1 = tot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot1 = rm1tot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot1 = weighttot1 + Double.parseDouble(exList1.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p1.vol = String.valueOf(tot1);
//                        p1.rm1 = String.valueOf(rm1tot1);
//                        p1.weight = String.valueOf(weighttot1);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 12").child("Week 2").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList2 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList2.add(ds.getValue(Exercises.class));
//                        }
//                        tot2 = 0;
//                        weighttot2 = 0;
//                        rm1tot2 = 0;
//                        for (int i = 0; i < exList2.size(); i++) {
//                            for (int j = 0; j < exList2.get(i).getExercise().size(); j++) {
//                                if (!exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot2 = tot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot2 = rm1tot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot2 = weighttot2 + Double.parseDouble(exList2.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p2.vol = String.valueOf(tot2);
//                        p2.rm1 = String.valueOf(rm1tot2);
//                        p2.weight = String.valueOf(weighttot2);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 12").child("Week 3").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList3 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList3.add(ds.getValue(Exercises.class));
//                        }
//                        tot3 = 0;
//                        weighttot3 = 0;
//                        rm1tot3 = 0;
//                        for (int i = 0; i < exList3.size(); i++) {
//                            for (int j = 0; j < exList3.get(i).getExercise().size(); j++) {
//                                if (!exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot3 = tot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot3 = rm1tot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot3 = weighttot3 + Double.parseDouble(exList3.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p3.vol = String.valueOf(tot3);
//                        p3.rm1 = String.valueOf(rm1tot3);
//                        p3.weight = String.valueOf(weighttot3);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                monthCountRef.child("Month 12").child("Week 4").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        exList4 = new ArrayList<>();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                                    MonthExercise t = dataSnapshot.getValue(MonthExercise.class);
//                            exList4.add(ds.getValue(Exercises.class));
//                        }
//                        tot4 = 0;
//                        weighttot4 = 0;
//                        rm1tot4 = 0;
//                        for (int i = 0; i < exList4.size(); i++) {
//                            for (int j = 0; j < exList4.get(i).getExercise().size(); j++) {
//                                if (!exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getVolume().isEmpty()){
//                                    //for getting vol
//                                    tot4 = tot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                            get(j).getSets().get(j).getVolume());
//                                //for getting rm1
//                                rm1tot4 = rm1tot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getRM1());
//                                //for getting weight
//                                weighttot4 = weighttot4 + Double.parseDouble(exList4.get(i).getExercise().
//                                        get(j).getSets().get(j).getWeight());
//                                    //for getting mupic1s
//                                    mupic1.add(exList1.get(i).getExercise().
//                                            get(j).getTargetedmuscle());
//                                }
//                            }
//                        }
//                        displaytargetedmucsles();
//                        p4.vol = String.valueOf(tot4);
//                        p4.rm1 = String.valueOf(rm1tot4);
//                        p4.weight = String.valueOf(weighttot4);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//            }
//        }
    }
    public void displaytargetedmucsles(){

        adapter.setOnItemClickListenerchild(new PeopleAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position, View v) {
                //create timer
                        for (String line : mupic1) {
                            toolTip = new ToolTip();
                            String to = mupic1.get(0)+" - ";
                            toolTip
                                    .withText(to+=line)
                                    .withColor(Color.GRAY) //or whatever you want
                                    .withTextColor(Color.WHITE)
                                    .withAnimationType(ToolTip.AnimationType.FROM_MASTER_VIEW)
                                    .withShadow();
                        }
                        tooltips.showToolTip(toolTip, v);
        }
    });
//    MonthExercise t = new MonthExercise();
//    ArrayList<String> tes = new ArrayList<>();
//                                t.setWeekNum(tes);
//                        tes.add("week 1");

}
}
