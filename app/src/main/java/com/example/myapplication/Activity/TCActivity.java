package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.WebView;

import com.example.myapplication.R;

public class TCActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tc);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        WebView mywebview = (WebView) findViewById(R.id.webview);
        mywebview.loadUrl("http://truemobilecare.com/assets/uploads/knightfield.pdf");
    }
    @Override
    public void onBackPressed()
    {
        Intent in=new Intent(TCActivity.this, HomeActivity.class);
        startActivity(in);
        finish();
    }
}
