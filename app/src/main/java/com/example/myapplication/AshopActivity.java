package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AshopActivity extends AppCompatActivity {

LinearLayout shop,edit,stock,purchase,product,home,collection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ashop);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


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
                Intent in=new Intent(AshopActivity.this,ShopeditActivity.class);
                startActivity(in);
                finish();
            }
        });

        stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(AshopActivity.this,StockDetailsActivity.class);
                startActivity(in);
                finish();
            }
        });

        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(AshopActivity.this,PurchaseOrderActivity.class);
                startActivity(in);
                finish();
            }
        });

        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(AshopActivity.this,ProductDetailsActivity.class);
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
