package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

public class HomeActivity extends AppCompatActivity {
    TextView user,items;
    SliderView sliderView;
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


        sliderView = findViewById(R.id.imageSlider);
        final SliderAdapterExample adapter = new SliderAdapterExample(this);
        adapter.setCount(5);
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimations.SLIDE); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.CUBEINROTATIONTRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.startAutoCycle();
        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                sliderView.setCurrentPagePosition(position);
            }
        });



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
                Intent intent=new Intent(HomeActivity.this,ActivationActivity.class);
                startActivity(intent);
                finish();

            }
        });
        prod=(ImageView)findViewById(R.id.imageView2);
        prod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(HomeActivity.this,ProductDetailsActivity.class);
                startActivity(in);
                finish();
            }
        });
        check=(ImageView)findViewById(R.id.imageView);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(HomeActivity.this,CheckDetailsActivity.class);
                startActivity(in);
                finish();
            }
        });
        report=(ImageView)findViewById(R.id.imageView6);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(HomeActivity.this,ReportActivity.class);
                startActivity(in);
                finish();
            }
        });
        claim=(ImageView)findViewById(R.id.imageView5);
        claim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(HomeActivity.this,ClaimIntimationActivity.class);
                startActivity(in);
                finish();
            }
        });
        stock=(ImageView)findViewById(R.id.imageView4);
        stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(HomeActivity.this,StockDetailsActivity.class);
                startActivity(in);
                finish();
            }
        });
        clstatus=(ImageView)findViewById(R.id.imageView9);
        clstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(HomeActivity.this,ClaimStatusActivity.class);
                startActivity(in);
                finish();
            }
        });
        tc=(ImageView)findViewById(R.id.imageView8);
        tc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(HomeActivity.this,TCActivity.class);
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
                Intent in=new Intent(HomeActivity.this,AloginActivity.class);
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
