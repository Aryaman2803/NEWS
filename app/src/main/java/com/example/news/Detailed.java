package com.example.news;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Detailed extends AppCompatActivity {

    TextView tvTitle, tvSource, tvTime, tvDesc;
    WebView webView;
    ImageView imageView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);


//        tvTitle = findViewById(R.id.tvIdCardView);
//        tvSource = findViewById(R.id.tvSource);
//        tvTime = findViewById(R.id.tvDate);
//        tvDesc = findViewById(R.id.tvDesc);
        webView = findViewById(R.id.webView);
//        imageView = findViewById(R.id.imageCardView);
        progressBar = findViewById(R.id.webViewLoader);
        progressBar.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String source = intent.getStringExtra("source");
        String time = intent.getStringExtra("time");
        String desc = intent.getStringExtra("desc");
        String imageUrl = intent.getStringExtra("imageUrl");
        String url = intent.getStringExtra("url");

//        tvTitle.setText(title);
//        tvSource.setText(source);
//        tvTime.setText(time);
//        tvDesc.setText(desc);


//        if (TextUtils.isEmpty(imageUrl)) {
//            imageView.setImageResource(R.drawable.ic_baseline_image_24);
//        } else {
//            Picasso.get().load(imageUrl).resize(1080,720).onlyScaleDown().into(imageView);
//        }


//        webView.setWebViewClient(new WebViewClient());
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setDomStorageEnabled(true);
//        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
//        webView.getSettings().setLoadsImagesAutomatically(true);


        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setAppCacheEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);


//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
//            webView.setLayerType(View.LAYER_TYPE_HARDWARE,null);
//        }else {
//            webView.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
//        }
        webView.loadUrl(url);
        if (webView.isShown()) {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

}