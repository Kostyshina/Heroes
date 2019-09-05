package katsapov.heroes.presentaition;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;

import katsapov.heroes.R;

public class WebViewActivity extends Activity {


    @SuppressLint("SetJavaScriptEnabled")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view);
        Uri url = getIntent().getData();
        WebView webView = findViewById(R.id.webView);
        assert url != null;
        webView.loadUrl(url.toString());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}