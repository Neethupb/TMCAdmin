package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;


public class HomeActivity extends AppCompatActivity {
    TextView user,items;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    String id,message,name;

    public static final String DEFAULT = "N/A";
    ImageView act,prod,check,report,claim,stock,clstatus,tc,logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        user=(TextView)findViewById(R.id.textView26);






        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        id=sharedpreferences.getString("shopreg_id",DEFAULT);
        name=sharedpreferences.getString("shopreg_name",DEFAULT);
        user.setText(name);
        //user.setText(id);
        message=user.getText().toString();

        act=(ImageView)findViewById(R.id.imageView3);
        act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("shopreg_name", message);
                editor.putString("shopreg_id",id);
              //  Toast.makeText(HomeActivity.this, id, Toast.LENGTH_SHORT).show();
                editor.commit();
                Intent intent=new Intent(HomeActivity.this, ActivationActivity.class);
                startActivity(intent);
                finish();

            }
        });
        prod=(ImageView)findViewById(R.id.imageView2);
        prod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(HomeActivity.this, ProductDetailsActivity.class);
                startActivity(in);
                finish();
            }
        });
        check=(ImageView)findViewById(R.id.imageView);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(HomeActivity.this, CheckDetailsActivity.class);
                startActivity(in);
                finish();
            }
        });
        report=(ImageView)findViewById(R.id.imageView6);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(HomeActivity.this, ReportActivity.class);
                startActivity(in);
                finish();
            }
        });
        claim=(ImageView)findViewById(R.id.imageView5);
        claim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(HomeActivity.this, ClaimIntimationActivity.class);
                startActivity(in);
                finish();
            }
        });
        stock=(ImageView)findViewById(R.id.imageView4);
        stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(HomeActivity.this, StockDetailsActivity.class);
                startActivity(in);
                finish();
            }
        });
        clstatus=(ImageView)findViewById(R.id.imageView9);
        clstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(HomeActivity.this, ClaimStatusActivity.class);
                startActivity(in);
                finish();
            }
        });
        tc=(ImageView)findViewById(R.id.imageView8);
        tc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(HomeActivity.this, TCActivity.class);
                startActivity(in);
                finish();
            }
        });
        logout=(ImageView)findViewById(R.id.imageView7);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setTitle(R.string.app_name);
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setMessage("Do you want to exit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();*/
                Intent in=new Intent(HomeActivity.this, AloginActivity.class);
                startActivity(in);
                finish();
            }
        });
    }
     @Override
    public void onBackPressed()
    {
        Intent in=new Intent(HomeActivity.this,AloginActivity.class);
        //Intent in=new Intent(MapsActivity.this,HomeActivity.class);
        startActivity(in);
        finish();
       /* AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("Do you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();*/
    }
}
