package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
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


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Constants;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AloginActivity extends AppCompatActivity {
LinearLayout report,shop,attendance,logout,activation,promoter;
String asm,selectshop,ids,shopname;
    SharedPreferences sharedpreferences;
    ArrayList<String> CountryName;
    Spinner shoplist;
    public static final String DEFAULT = "N/A";
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_alogin);
        sharedpreferences = getSharedPreferences(Constants.MyPREFERENCES, Context.MODE_PRIVATE);
        asm = sharedpreferences.getString("name",DEFAULT);
        report=(LinearLayout) findViewById(R.id.report);
        shop=(LinearLayout)findViewById(R.id.shop);
        attendance=(LinearLayout)findViewById(R.id.attendance);
        logout=(LinearLayout)findViewById(R.id.logout);
        activation=(LinearLayout)findViewById(R.id.activation);
        promoter=(LinearLayout)findViewById(R.id.promoter);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AloginActivity.this, AreportActivity.class);
                startActivity(intent);
                finish();
            }
        });
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AloginActivity.this, AshopActivity.class);
                startActivity(intent);
                finish();
            }
        });
        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("name", asm);
                editor.apply();
                Intent intent = new Intent(AloginActivity.this, MapsActivity.class);
                startActivity(intent);
                finish();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AloginActivity.this);
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
                alert.show();
            }
        });
        activation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    LayoutInflater layoutInflater =
                            (LayoutInflater) getBaseContext()
                                    .getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = layoutInflater.inflate(R.layout.popup, null);
                    final PopupWindow popupWindow = new PopupWindow(
                            popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                     Button btnsubmit = popupView.findViewById(R.id.dismiss);
                     Button btndismiss=popupView.findViewById(R.id.dissmiss);
                     btndismiss.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                             popupWindow.dismiss();
                         }
                     });
                     btnsubmit.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                             Login();
                         }
                     });
                    CountryName = new ArrayList<>();
                    shoplist = (Spinner)popupView.findViewById(R.id.popupspinner);
                    loadSpinnerData(Constants.URLshoplist);
                    shoplist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            selectshop = shoplist.getItemAtPosition(shoplist.getSelectedItemPosition()).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                popupWindow.showAsDropDown(activation, 50, -30);

            }
        });
        promoter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AloginActivity.this, PromoterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void Login() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.HttpUrlshoplogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        if(!ServerResponse.equalsIgnoreCase("error")) {
                            try {
                                //getting the whole json object from the response
                                JSONObject obj = new JSONObject(ServerResponse);
                                ids = obj.getString("shopreg_id");
                                shopname = obj.getString("shopreg_name");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.clear();
                            editor.putString("name", asm);
                            editor.putString("shopreg_id", ids);
                            editor.putString("shopreg_name",shopname);
                            editor.apply();
                            Intent intent = new Intent(AloginActivity.this, ActivationActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            // Showing Echo Response Message Coming From Server.
                            Toast.makeText(AloginActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Hiding the progress dialog after all task complete.
                        //  progressDialog.dismiss();
                        // Showing error message if something goes wrong.
                        Toast.makeText(AloginActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("shopreg_name", selectshop);
                // params.put("shopreg_password", password);
                return params;
            }
        };
        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(AloginActivity.this);
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }

    private void loadSpinnerData(String urLshoplist) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, urLshoplist, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    //getting the whole json object from the response
                    JSONArray array=new JSONArray(response);
                    for (int i=0; i < array.length(); i++) {
                        JSONObject jsonObject1=array.getJSONObject(i);
                        String country=jsonObject1.getString("shopreg_name");
                        CountryName.add(country);
                        //  Toast.makeText(ActivationActivity.this, country, Toast.LENGTH_SHORT).show();
                    }

                    shoplist.setAdapter(new ArrayAdapter<String>(AloginActivity.this, android.R.layout.simple_spinner_dropdown_item, CountryName));
                }catch (JSONException e){e.printStackTrace();}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(AloginActivity.this);
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
        alert.show();
    }
}
