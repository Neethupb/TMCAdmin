package com.example.myapplication.Activity;

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
import android.widget.EditText;
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
import com.example.myapplication.Constants;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShopeditActivity extends AppCompatActivity {


    ArrayList<String> CountryName;
    // Creating Volley RequestQueue.
    RequestQueue requestQueue;
    // Creating Progress dialog.
    ProgressDialog progressDialog;
    SharedPreferences sharedpreferences;
    String selectedItem,id;
    Spinner spinner;
    LinearLayout linear;
    Button search,save,back;
    String sname,saddress,sowner,smobile,smail,slocation,starget,sgst,sasm;
    EditText name,address,owner,mobile,mail,location,targetr,gst,asm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_shopedit);
        sharedpreferences = getSharedPreferences(Constants.MyPREFERENCES, Context.MODE_PRIVATE);
        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(ShopeditActivity.this);
        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(ShopeditActivity.this);

        linear=(LinearLayout)findViewById(R.id.linear);
        linear.setVisibility(View.GONE);
        name=(EditText)findViewById(R.id.name);
        address=(EditText)findViewById(R.id.adrs);
        owner=(EditText)findViewById(R.id.owner);
        mobile=(EditText)findViewById(R.id.mobile);
        mail=(EditText)findViewById(R.id.mail);
        location=(EditText)findViewById(R.id.location);
        targetr=findViewById(R.id.target);
        asm=findViewById(R.id.asm);
        gst=findViewById(R.id.gst);

        search=(Button)findViewById(R.id.search);
        save=(Button)findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sname=name.getText().toString();
                saddress=address.getText().toString();
                sowner=owner.getText().toString();
                smobile=mobile.getText().toString();
                smail=mail.getText().toString();
                slocation=location.getText().toString();
                starget=targetr.getText().toString();
                sasm=asm.getText().toString();
                sgst=gst.getText().toString();
                Updateshop();
            }
        });
        back=(Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(ShopeditActivity.this, AshopActivity.class);
                startActivity(in);
                finish();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedItem = spinner.getSelectedItem().toString();
                Search();
            }
        });

        CountryName=new ArrayList<>();
        spinner=(Spinner)findViewById(R.id.spinner4);
        spinner.setPrompt("Select Shop!");
        loadSpinnerData(Constants.URLshoplist);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String country=   spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
                //  Toast.makeText(getApplicationContext(),country,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
    }

    private void Updateshop() {
        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.HttpUrlshopupdate,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        if (!ServerResponse.equalsIgnoreCase("error")) {
                            Intent in=new Intent(ShopeditActivity.this, AshopActivity.class);
                            startActivity(in);
                            finish();
                        }
                        else {
                            // Showing Echo Response Message Coming From Server.
                            Toast.makeText(ShopeditActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(ShopeditActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("shopreg_name",sname);
                params.put("shopreg_address",saddress);
                params.put("shopreg_owner",sowner);
                params.put("shopreg_mobile",smobile);
                params.put("shopreg_mailid",smail);
                params.put("shopreg_location",slocation);
                params.put("shopreg_target",starget);
                params.put("shopreg_gst",sgst);
                params.put("shopreg_asm",sasm);
                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(ShopeditActivity.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }

    private void Search() {
        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.HttpUrlshopdetails,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        if (!ServerResponse.equalsIgnoreCase("error")) {
                            try {
                                linear.setVisibility(View.VISIBLE);
                                //getting the whole json object from the response
                                JSONObject obj = new JSONObject(ServerResponse);
                                name.setText(obj.getString("shopreg_name"));
                                address.setText(obj.getString("shopreg_address"));
                                owner.setText(obj.getString("shopreg_owner"));
                                mobile.setText(obj.getString("shopreg_mobile"));
                                mail.setText(obj.getString("shopreg_mailid"));
                                location.setText(obj.getString("shopreg_location"));
                                gst.setText(obj.getString("shopreg_gst"));
                                asm.setText(obj.getString("shopreg_asm"));
                                targetr.setText(obj.getString("shopreg_target"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            // Showing Echo Response Message Coming From Server.
                            Toast.makeText(ShopeditActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(ShopeditActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("shopreg_name",selectedItem);
                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(ShopeditActivity.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }

    private void loadSpinnerData(String url) {
        {
            RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try{
                        //getting the whole json object from the response
                        JSONArray array=new JSONArray(response);
                        for (int i=0; i < array.length(); i++) {
                            JSONObject jsonObject1=array.getJSONObject(i);
                            String country=jsonObject1.getString("shopreg_name");
                            CountryName.add(country);
                        }

                        spinner.setAdapter(new ArrayAdapter<String>(ShopeditActivity.this, android.R.layout.simple_spinner_dropdown_item, CountryName));
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
    }

    @Override
    public void onBackPressed()
    {
        Intent in=new Intent(ShopeditActivity.this,AshopActivity.class);
        startActivity(in);
        finish();


    }
}
