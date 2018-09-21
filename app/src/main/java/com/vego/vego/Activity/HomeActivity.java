package com.vego.vego.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.vego.vego.R;



public class HomeActivity extends AppCompatActivity {



    WebView webView;

    boolean showingFirst = true;

    ProgressDialog bar;
    String path="https://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4";
    MediaController ctlr;
    VideoView videoView;


    @SuppressLint({ "NewApi", "SetJavaScriptEnabled" }) @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        videoView = findViewById(R.id.video);

        //small window video
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        android.widget.RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) videoView.getLayoutParams();
        params.width =  (int) (300*metrics.density);
        params.height = (int) (250*metrics.density);
        params.leftMargin = 200;
        params.rightMargin = 200;
        params.bottomMargin = 1500;
        params.topMargin = 100;

        videoView.setLayoutParams(params);

        videoViewEnlarge();



        //webView = findViewById(R.id.mWebView);

//        webView.setWebChromeClient(new MyChrome());
//        webView.setWebViewClient(new Browser_home());
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setAllowFileAccess(true);
//        webSettings.setAppCacheEnabled(true);
        //loadingVideoFromYoutube();

                //loadWebsite();

            String link="http://s1133.photobucket.com/albums/m590/Anniebabycupcakez/?action=view&amp; current=1376992942447_242.mp4";
//            VideoView videoView = (VideoView) findViewById(R.id.video);
//            videoView.setVideoPath("http://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4");
//            videoView.start();






    }

    private void videoViewEnlarge() {
        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(showingFirst == true){
                    //full screen video
                    DisplayMetrics metrics = new DisplayMetrics(); getWindowManager().getDefaultDisplay().getMetrics(metrics);
                    android.widget.RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) videoView.getLayoutParams();
                    params.width =  metrics.widthPixels;
                    params.height = metrics.heightPixels;
                    params.leftMargin = 0;
                    params.leftMargin = 0;
                    params.rightMargin = 0;
                    params.bottomMargin = 0;
                    params.topMargin = 0;

                    videoView.setLayoutParams(params);

                    //change to true so we do full screen again
                    showingFirst = false;

                }else{
                    //small window video
                    DisplayMetrics metrics = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(metrics);
                    android.widget.RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) videoView.getLayoutParams();
                    params.width =  (int) (300*metrics.density);
                    params.height = (int) (250*metrics.density);
                    params.leftMargin = 200;
                    params.rightMargin = 200;
                    params.bottomMargin = 1500;
                    params.topMargin = 100;

                    videoView.setLayoutParams(params);




                    //changeto false so we can toggle back fullscreen
                    showingFirst = true;

                }

            }
        });

        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        bar=new ProgressDialog(HomeActivity.this);
        bar.setTitle("Connecting server");
        bar.setMessage("Please Wait... ");
        bar.setCancelable(false);
        bar.show();
        if(bar.isShowing()) {
            Uri uri = Uri.parse(path);
            videoView.setVideoURI(uri);
            videoView.start();
            ctlr = new MediaController(this);
            ctlr.setMediaPlayer(videoView);
            videoView.setMediaController(ctlr);
            videoView.requestFocus();
        }
        bar.dismiss();
    }

    private void loadWebsite() {
        ConnectivityManager cm = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            webView.loadUrl("https://www.youtube.com/");
        } else {
            webView.setVisibility(View.GONE);
        }
    }
    class Browser_home extends WebViewClient {

        Browser_home() {
        }

        public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString)
        {
            paramWebView.loadUrl(paramString);
            return true;
        }
    }

    private class MyChrome extends WebChromeClient {

        private View mCustomView;
        private WebChromeClient.CustomViewCallback mCustomViewCallback;
        protected FrameLayout mFullscreenContainer;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;

        public MyChrome() {}

        public Bitmap getDefaultVideoPoster()
        {
            if (HomeActivity.this == null) {
                return null;
            }
            return BitmapFactory.decodeResource(HomeActivity.this.getApplicationContext().getResources(), 2130837573);
        }

        public void onHideCustomView()
        {
            ((FrameLayout)HomeActivity.this.getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            HomeActivity.this.getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            HomeActivity.this.setRequestedOrientation(this.mOriginalOrientation);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
        }

        public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback)
        {
            if (this.mCustomView != null)
            {
                onHideCustomView();
                return;
            }
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = HomeActivity.this.getWindow().getDecorView().getSystemUiVisibility();
            this.mOriginalOrientation = HomeActivity.this.getRequestedOrientation();
            this.mCustomViewCallback = paramCustomViewCallback;
            ((FrameLayout)HomeActivity.this.getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
            HomeActivity.this.getWindow().getDecorView().setSystemUiVisibility(3846);
        }
    }


        private void loadingVideoFromYoutube() {



        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON_DEMAND);

        if (Build.VERSION.SDK_INT >= 21) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
        }

        if (android.os.Build.VERSION.SDK_INT < 16) {
            webView.setBackgroundColor(0x00000000);
        } else {
            webView.setBackgroundColor(Color.argb(1, 0, 0, 0));
        }

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
                }
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView webview, String url, Bitmap favicon) {
                super.onPageStarted(webview, url, favicon);
                webview.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onPageFinished(WebView webview, String url) {

                webview.setVisibility(View.VISIBLE);
                super.onPageFinished(webview, url);

            }
        });
        //webView.setWebChromeClient(new WebChromeClient());

        //webView.getSettings().setUserAgentString("Mozilla/5.0 (Linux; U; Android 2.0; en-us; Droid Build/ESD20) AppleWebKit/530.17 (KHTML, like Gecko) Version/4.0 Mobile Safari/530.17");

        webView.loadUrl("https://www.youtube.com/watch?v=rYOSVINsQWU");
    }


    public void signinTxt(View view) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));

    }

    public void signupTxt(View view) {
        startActivity(new Intent(getApplicationContext(), SignupActivity.class));

    }


//    public void openAdmin(View view) {
//        startActivity(new Intent(this, AdminActivity.class));
//
//    }



}
