package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.myapplication.R;

public class AshopActivity extends AppCompatActivity {
LinearLayout shop,edit,stock,purchase,product,home,collection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_ashop);

        shop = (LinearLayout) findViewById(R.id.shop);
        edit = (LinearLayout) findViewById(R.id.edit);
        stock = (LinearLayout) findViewById(R.id.stock);
        purchase = (LinearLayout) findViewById(R.id.purchaseorder);
        product = (LinearLayout) findViewById(R.id.product);
        home = (LinearLayout) findViewById(R.id.home);
        collection = (LinearLayout) findViewById(R.id.collection);

        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(AshopActivity.this,AShopregActivity.class);
                startActivity(in);
                finish();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(AshopActivity.this, ShopeditActivity.class);
                startActivity(in);
                finish();
            }
        });

        stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(AshopActivity.this, StockDetailsActivity.class);
                startActivity(in);
                finish();
            }
        });

        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(AshopActivity.this, PurchaseOrderActivity.class);
                startActivity(in);
                finish();
            }
        });

        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(AshopActivity.this, ProductDetailsActivity.class);
                startActivity(in);
                finish();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(AshopActivity.this,AloginActivity.class);
                startActivity(in);
                finish();
            }
        });
        collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(AshopActivity.this,CollectionActivity.class);
                startActivity(in);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed()
    {
        Intent in=new Intent(AshopActivity.this,AloginActivity.class);
        startActivity(in);
        finish();


    }

}
