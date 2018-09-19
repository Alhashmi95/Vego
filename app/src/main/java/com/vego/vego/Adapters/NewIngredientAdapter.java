package com.vego.vego.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vego.vego.R;
import com.vego.vego.model.ingredients;

import java.util.ArrayList;

public class NewIngredientAdapter extends RecyclerView.Adapter<NewIngredientAdapter.MealsViewHolder> {

    private ArrayList<ingredients> ingrList;
    private Context mContext;
    private CardView cardViewMeals;
    private LayoutInflater inflater;
    String ingr, quan;
    ArrayList qtyArray,nameArray;


    public NewIngredientAdapter(ArrayList<ingredients> ingrList, Context mContext) {
        this.ingrList = ingrList;
        inflater = LayoutInflater.from(mContext);
    }


    @Override
    public NewIngredientAdapter.MealsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.meal_card_view, parent, false);
        MealsViewHolder holder = new MealsViewHolder(view);

        return holder;

    }
    public ArrayList<ingredients> getIngredientsArray() {
        return ingrList;
    }

    @Override
    public void onBindViewHolder(NewIngredientAdapter.MealsViewHolder holder, final int position) {

        holder.txtQuan.setText(ingrList.get(position).getQuantity());
        holder.txtIngr.setText(ingrList.get(position).getType());
        Log.d("print","yes");


//            holder.mealCount.setText("."+String.valueOf(position+1));

        holder.btn_delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ingrList.size()==1){
                    Toast.makeText(inflater.getContext(),"يجب ان تحتوي على عنصر واحد على الاقل",Toast.LENGTH_SHORT).show();
                }else {
                    ingrList.remove(position);
                    notifyDataSetChanged();
                }
            }
        });




    }

    @Override
    public int getItemCount() {

        return ingrList.size();
    }
    class MealsViewHolder extends RecyclerView.ViewHolder{

        EditText txtIngr, txtQuan;
        TextView mealCount;
        Button btn_delet ;




        public MealsViewHolder(final View itemView) {
            super(itemView);

            txtIngr = itemView.findViewById(R.id.txtIngr);
            txtQuan = itemView.findViewById(R.id.txtQuantity);
            btn_delet = itemView.findViewById(R.id.btn_delet);
//            mealCount = itemView.findViewById(R.id.mealCount);

            txtIngr.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    ingrList.get(getAdapterPosition()).setType(txtIngr.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            txtQuan.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    ingrList.get(getAdapterPosition()).setQuantity(txtQuan.getText().toString());
                   // Log.d("test","this is ingrList QQQQ :"+ingrList.size());


//                    //declare arraylist to store array of quantites
//                    qtyArray = new ArrayList();
//                    //for loop to add quantites cumulatively
//                    for(int ii =0; ii<ingrList.size(); ii++) {
//                        qtyArray.add(getAdapterPosition());
//                        //ingrList.add(new ingredients(qtyArray,nameArray));
//                    }
//
//
//                            Log.d("test","this is qtyArray :"+qtyArray.size());
//                            Log.d("test","this is quan :"+quan);



                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });



        }

    }

        }
