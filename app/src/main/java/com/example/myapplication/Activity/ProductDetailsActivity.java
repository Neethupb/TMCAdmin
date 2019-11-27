package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.myapplication.R;

public class ProductDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_product_details);
    }
    @Override
    public void onBackPressed()
    {
        Intent in=new Intent(ProductDetailsActivity.this, AshopActivity.class);
        startActivity(in);
        finish();
    }
}
