package com.vego.vego.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.builder.AnimateGifMode;
import com.squareup.picasso.Picasso;
import com.vego.vego.R;

public class SliderAdapter extends PagerAdapter {
    Context mContext;
    LayoutInflater layoutInflater;
    String isAdmin;

    public SliderAdapter(Context mContext, String isAdmin) {
        this.mContext = mContext;
        this.isAdmin = isAdmin;
    }

    public int[] slide_images =

            {
                    R.drawable.track_user,
                    R.drawable.meals_user,
                    R.drawable.ex_user,
                    R.drawable.free_ex_user,
                    R.drawable.chat_user
            };
    public String[] slide_headings =

            {

                    "slide 1",
                    "slide 2",
                    "slide 3",
                    "slide 4",
                    "slide 5"
            };
    public String[] slide_desc =

            {
                    "kfjdifsjildjiodfjgiogjuoierld",
                    "fkldsjfijdiogjodifgjodiorldfk fkjdsifk",
                    "kfjdsikfijids kjvhdfsikfhidsk kjfhsiufhusi kufhisku",
                    "klgjvdfiogjofdjgoidjg kjfdnkljgmiodfing.kljdfiokjgoid",
                    "vkljdfilgjiodfgio kndfkjdfk kijvdfifidufh idhfoisfjos hfiod"
            };
    public int[] slide_imagesAdmin =

            {
                    R.drawable.track_admin,
                    R.drawable.meals_admin,
                    R.drawable.ex_admin,
                    R.drawable.add_meal_ex_admin,
                    R.drawable.chat_admin
            };
    public String[] slide_headingsAdmin =

            {

                    "Admin 1",
                    "Admin 2",
                    "Admin 3",
                    "Admin 4",
                    "Admin 5"
            };
    public String[] slide_descAdmin =

            {
                    "kfjdifsjildjiodfjgiogjuoierld",
                    "fkldsjfijdiogjodifgjodiorldfk fkjdsifk",
                    "kfjdsikfijids kjvhdfsikfhidsk kjfhsiufhusi kufhisku",
                    "lkgbfdlkjgoifgkdjfg kgjikdsfjigkd kjgioejgiof kjdfgikdfiogjior0",
            };

    @Override
    public int getCount() {
        return slide_imagesAdmin.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        View view = layoutInflater.inflate(R.layout.slider_view_holder, container, false);


        ImageView imageView = view.findViewById(R.id.iv_slider);
        TextView textViewHeading = view.findViewById(R.id.tv_sliderHeader);
        TextView textViewDesc = view.findViewById(R.id.tv_slider_desc);


        if (isAdmin.equals("false")) {
            Glide.with(mContext)
                    .load(slide_images[position])
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .into(imageView);
            //imageView.setImageResource(slide_images[position]);
            textViewHeading.setText(slide_headings[position]);
            textViewDesc.setText(slide_desc[position]);
        } else {
            Glide.with(mContext)
                    .load(slide_imagesAdmin[position])
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .into(imageView);

            //imageView.setImageResource(slide_imagesAdmin[position]);
            textViewHeading.setText(slide_headingsAdmin[position]);
            textViewDesc.setText(slide_descAdmin[position]);
        }

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}
