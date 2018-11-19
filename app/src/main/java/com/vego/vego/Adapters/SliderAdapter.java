package com.vego.vego.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;
import com.vego.vego.R;

public class SliderAdapter extends PagerAdapter {
    Context mContext;
    LayoutInflater layoutInflater;
    String isAdmin;
    ProgressBar progressBar;

    public SliderAdapter(Context mContext, String isAdmin) {
        this.mContext = mContext;
        this.isAdmin = isAdmin;
    }

    public String[] slide_images =

            {
                    "https://firebasestorage.googleapis.com/v0/b/vego-6.appspot.com/o/gif%2Ftrack_user.gif?alt=media&token=563f72a5-efb9-481b-aeac-edea08a6891d",
                    "https://firebasestorage.googleapis.com/v0/b/vego-6.appspot.com/o/gif%2Fmeals_user.gif?alt=media&token=c83e63dc-f361-4ee5-8bfa-289624517a1c",
                    "https://firebasestorage.googleapis.com/v0/b/vego-6.appspot.com/o/gif%2Fex_user.gif?alt=media&token=83153d25-8a5e-4659-b418-7e8a57976540",
                    "https://firebasestorage.googleapis.com/v0/b/vego-6.appspot.com/o/gif%2Ffree_ex_user.gif?alt=media&token=35ca9e1d-122f-41c0-850e-bff4270ae277",
                    "https://firebasestorage.googleapis.com/v0/b/vego-6.appspot.com/o/gif%2Fchat_user.gif?alt=media&token=1aad8dbb-8f49-48d9-84c2-2b0a60531764"
            };
    public String[] slide_headings =

            {

                    "جدول المتابعة",
                    "الجدول الغذائي",
                    "جدول التمارين",
                    "جدول التمارين العامة",
                    "الدعم"
            };
    public String[] slide_desc =

            {
                    "في هذه الواجهة تستطيع ان تحدث وزنك الحالي في حال حصلت زيادة او نقصان على الوزن المسجل عند واجهة تسجيل البيانات وايضاً يمكن رؤية نبذة عن البرنامج الغذائي/التمارين الخاص بك \n\n ملاحظة: قد يستغرق وضع البرنامج الخاص بك عدة ايام",
                    "في هذه الواجهة تستطيع رؤية الجدول الغذائي الخاص بك وتفاصيل كل وجبة من مكونات غذائية بكمياتها الى عدد السعرات الموجودة بها",
                    "على غرار الواجهة السابقة تستطيع في هذه الواجهة ان تطّلع على التمارين المعطاة لك من قبل المدرب وايضا يمكنك حساب الVolume و 1RM لكل تمرين \n\n ملاحظة: اضغط على صورة التمرين للدخول لواجهة تفاصيل التمرين",
                    "في هذه الواجهة يمكنك عمل تمارين بالقدر الذي تريده على عكس الواجهة السابقة حيث يضع المدرب الخاص بك كل جلسة بعدد مرات التكرار",
                    "هذه الواجهة تتيح لك التواصل مع المدربين حيث يمكنك ارسال استفسارك وسيتم الرد عليه من قبل المدربين في اسرع وقت ممكن"
            };
    public String[] slide_imagesAdmin =

            {
                    "https://firebasestorage.googleapis.com/v0/b/vego-6.appspot.com/o/gif%2Ftrack_admin.gif?alt=media&token=d89cc0aa-be75-45d3-b4ee-7f8b4a3d16eb",
                    "https://firebasestorage.googleapis.com/v0/b/vego-6.appspot.com/o/gif%2Fmeals_admin.gif?alt=media&token=8e8e17a8-c4ae-4a73-83cb-bcd45846e7d2",
                    "https://firebasestorage.googleapis.com/v0/b/vego-6.appspot.com/o/gif%2Fex_admin.gif?alt=media&token=9c629590-2ec3-4468-a9f1-fd0ae2639767",
                    "https://firebasestorage.googleapis.com/v0/b/vego-6.appspot.com/o/gif%2Fadd_meal_ex_admin.gif?alt=media&token=1c24c8f0-25e7-4f28-901a-273c21a50709",
                    "https://firebasestorage.googleapis.com/v0/b/vego-6.appspot.com/o/gif%2Fchat_admin.gif?alt=media&token=073b41dd-9c18-4595-89fa-7f3674dbddb7"
            };
    public String[] slide_headingsAdmin =

            {

                    "متابعة المتدرب",
                    "اضافة جدول غذائي",
                    "اضافة جدول تمارين",
                    "اضافة وجبة/تمرين",
                    "الدعم"
            };
    public String[] slide_descAdmin =

            {
                    "في هذه الواجهة ستتمكن من متابعة وزن المتدرب الحالي/السابق ووضع نبذة عن البرنامج الغذائي وبرنامج التمارين في الخانة المخصصة الموضحة في الصورة اعلاه",
                    "في هذه الواجهة ستتمكن من اضافة جدول غذائي لكل مستخدم على حدة وذلك باختيار الوجبات من القائمة المنسدلة والضغط على زر (حفظ الوجبة)\n\n ملاحظة: الرجاء تحديد الشهر والاسبوع من اعلى الصفحة",
                    "على غرار الواجهة السابقة ستتمكن في هذه الواجهة من اضافة وتعديل تمارين لكل مستخدم وذلك باختيار التمرين والعضلة المستهدفة وبعد ذلك اضغط على زر (حفظ التمرين)",
                    "في هاتين الواجهتين ستتمكن من اضافة وجبة/تمرين جديد/ة وستظهر في القائمة المنسدلة الخاصة بالوجبات والتمارين التي سبق ذكرها. \n\nملاحظة: عند الضغط على زر حفظ تمرين عام سيذهب هذا التمارين مباشرة الى واجهة التمارين العامة الخاصة بالمتدرب ولن يظهر في القائمة المنسدلة الخاصة بالتمارين",
                    "هذه الواجهة تسمح للمدربين بالرد على استفسارات العملاء والتواصل معهم بشكل مباشر"
            };

    @Override
    public int getCount() {
        return slide_imagesAdmin.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        View view = layoutInflater.inflate(R.layout.slider_view_holder, container, false);


        ImageView imageView = view.findViewById(R.id.iv_slider);
        TextView textViewHeading = view.findViewById(R.id.tv_sliderHeader);
        TextView textViewDesc = view.findViewById(R.id.tv_slider_desc);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setProgress(0);
        progressBar.setVisibility(View.VISIBLE);




        if (isAdmin.equals("false")) {
            Ion.with(mContext)
                    .load(slide_images[position])
                    .progress(new ProgressCallback() {
                        @Override
                        public void onProgress(long downloaded, long total) {
                            int mProgress = (int) (downloaded * 100 / total);
                            progressBar.setProgress(mProgress);
                        }
                    })
                    .progressBar(progressBar)
                    .withBitmap()
                    // .placeholder(R.drawable.ic_loading)
                    .intoImageView(imageView).setCallback(new FutureCallback<ImageView>() {
                @Override
                public void onCompleted(Exception e, ImageView result) {
                progressBar.setVisibility(View.INVISIBLE);
                }
            });
            //imageView.setImageResource(slide_images[position]);
            textViewHeading.setText(slide_headings[position]);
            textViewDesc.setText(slide_desc[position]);
        } else {
            Ion.with(mContext)
                    .load(slide_imagesAdmin[position])
                    .progressHandler(new ProgressCallback() {
                @Override
                public void onProgress(long downloaded, long total) {
                    int mProgress = (int) (downloaded * 100 / total);
                    progressBar.setProgress(mProgress);

                }
            })
                    .progressBar(progressBar)
                    .withBitmap()
                    .intoImageView(imageView).setCallback(new FutureCallback<ImageView>() {
                @Override
                public void onCompleted(Exception e, ImageView result) {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });
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
