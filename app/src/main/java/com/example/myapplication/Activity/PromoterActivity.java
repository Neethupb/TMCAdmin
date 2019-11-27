package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.myapplication.Constants;
import com.example.myapplication.R;

public class PromoterActivity extends AppCompatActivity {
LinearLayout addpromoter,edit,viewpromoter,home;
    SharedPreferences sharedpreferences;
    String asmname;
    public static final String DEFAULT = "N/A";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_promoter);
        sharedpreferences = getSharedPreferences(Constants.MyPREFERENCES, Context.MODE_PRIVATE);

        asmname = sharedpreferences.getString("name",DEFAULT);
        addpromoter=(LinearLayout) findViewById(R.id.promoter);
        addpromoter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.putString("name",asmname);
                editor.apply();
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
