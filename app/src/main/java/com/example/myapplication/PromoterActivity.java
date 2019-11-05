package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

public class PromoterActivity extends AppCompatActivity {
LinearLayout addpromoter,edit,viewpromoter,home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promoter);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        addpromoter=(LinearLayout) findViewById(R.id.promoter);
        addpromoter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PromoterActivity.this, AddpromoterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        edit=(LinearLayout)findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PromoterActivity.this, EditActivity.class);
                startActivity(intent);
                finish();
            }
        });
        viewpromoter=(LinearLayout)findViewById(R.id.view);
        viewpromoter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PromoterActivity.this, ViewpromoterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        home=(LinearLayout)findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PromoterActivity.this, AloginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed()
    {
        Intent in=new Intent(PromoterActivity.this,AloginActivity.class);
        startActivity(in);
        finish();


    }
}
