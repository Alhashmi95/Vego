package com.vego.vego.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.spyhunter99.supertooltips.ToolTip;
import com.spyhunter99.supertooltips.ToolTipManager;
import com.vego.vego.R;

import java.util.ArrayList;
import java.util.List;

public class PeopleAdapter extends ExpandableRecyclerAdapter<PeopleAdapter.PeopleListItem> {
    public static final int TYPE_PERSON = 1001;

    //-----add
    public interface OnItemClickListener{
        void OnItemClick(int position, View view);
    }
    public PeopleAdapter.OnItemClickListener onItemClickListener;
    public void setOnItemClickListenerchild(PeopleAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
//------



    public PeopleAdapter(Context context) {
        super(context);

    }

    public static class PeopleListItem extends ExpandableRecyclerAdapter.ListItem {
        public String Text;
        public String vol;
        public String rm1;
        public String weight;
        public String muPic;
        public String week;
        public ArrayList<String> imgList;

        public PeopleListItem(String group) {
            super(TYPE_HEADER);

            Text = group;
        }

        public PeopleListItem(String vol,String d, String rm1,String weight,String muPic,ArrayList<String> imgList) {
            super(TYPE_PERSON);

            this.vol = vol;
            this.week = d;
            this.rm1 = rm1;
            this.weight = weight;
            this.muPic = muPic;
            this.imgList = imgList;
        }
    }

    public class HeaderViewHolder extends ExpandableRecyclerAdapter.HeaderViewHolder {
        TextView name;

        public HeaderViewHolder(View view) {
            super(view, (ImageView) view.findViewById(R.id.img_arrow),view.findViewById(R.id.relativeExpandable));

            name = (TextView) view.findViewById(R.id.txt_header_name);
        }

        public void bind(int position) {
            super.bind(position);

            name.setText(visibleItems.get(position).Text);


        }
    }

    public class PersonViewHolder extends ExpandableRecyclerAdapter.ViewHolder {
        TextView name, vol;

        ImageView img;

        ToolTipManager tooltips;

        RelativeLayout relativeLayout;


        public PersonViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.option1);
            vol = view.findViewById(R.id.option2);
            img = view.findViewById(R.id.iv_muList);
            relativeLayout = view.findViewById(R.id.rv_tooltip);

            tooltips = new ToolTipManager((Activity) view.getContext());
        }

        @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
        public void bind(int position) {
            vol.setText(visibleItems.get(position).vol + " / " + visibleItems.get(position).rm1 +
                    " / " + visibleItems.get(position).weight);
            name.setText(visibleItems.get(position).week);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //-----add

                    if(onItemClickListener!=null){
                        onItemClickListener.OnItemClick(position, v);
                    }
                    //------
                }

//            img.setImageResource(visibleItems.get(position).imgList.get(0));

            });

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEADER:
                return new HeaderViewHolder(inflate(R.layout.item_header, parent));
            case TYPE_PERSON:
            default:
                return new PersonViewHolder(inflate(R.layout.item_content, parent));
        }
    }

    @Override
    public void onBindViewHolder(ExpandableRecyclerAdapter.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                ((HeaderViewHolder) holder).bind(position);
                break;
            case TYPE_PERSON:
            default:
                ((PersonViewHolder) holder).bind(position);
                break;
        }
    }
}