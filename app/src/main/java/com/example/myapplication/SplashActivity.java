package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class SplashActivity extends AppCompatActivity {
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               // Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                Intent intent=new Intent(SplashActivity.this,AdminActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }
}
