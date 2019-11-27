package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Constants;
import com.example.myapplication.R;

import java.util.HashMap;
import java.util.Map;

public class AShopregActivity extends AppCompatActivity {
EditText shopname,address,owner,mobile,mail,pass,location,gst,target;
String shop,adres,ownr,mobl,email,passs,lctn,gstin,asm,tar;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    // Creating Volley RequestQueue.
    RequestQueue requestQueue;
    // Creating Progress dialog.
    ProgressDialog progressDialog;
    // Storing server url into String variable.
    Button reg,back;
    SharedPreferences sharedpreferences;
    public static final String DEFAULT = "N/A";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_ashopreg);
        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(AShopregActivity.this);
        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(AShopregActivity.this);
        sharedpreferences = getSharedPreferences(Constants.MyPREFERENCES, Context.MODE_PRIVATE);
        asm=sharedpreferences.getString("name",DEFAULT);
       // Toast.makeText(this, asm, Toast.LENGTH_SHORT).show();
        shopname=(EditText)findViewById(R.id.editText27);
        address=(EditText)findViewById(R.id.editText28);
        owner=(EditText)findViewById(R.id.editText29);
        mobile=(EditText)findViewById(R.id.editText30);
        mail=(EditText)findViewById(R.id.editText31);
        pass=(EditText)findViewById(R.id.editText32);
        location=(EditText)findViewById(R.id.editText33);
        gst=(EditText)findViewById(R.id.editText34);
        reg=(Button)findViewById(R.id.button24);
        target=findViewById(R.id.target);
        back=(Button)findViewById(R.id.button23);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent in=new Intent(AShopregActivity.this,AloginActivity.class);
                    startActivity(in);
                    finish();

            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shop = shopname.getText().toString();
                adres = address.getText().toString();
                ownr = owner.getText().toString();
                mobl = mobile.getText().toString();
                email = mail.getText().toString();
                passs = pass.getText().toString();
                lctn = location.getText().toString();
                gstin = gst.getText().toString();
                tar=target.getText().toString();
                validUserDetailsData();
            }
        });
    }
    private void validUserDetailsData() {
        if (shopname.getText() == null || shopname.getText().toString().equalsIgnoreCase("")) {
            shopname.setError("Enter Shop Name");
            shopname.setText("");
        }
        else if (address.getText() == null || address.getText().toString().equalsIgnoreCase("")) {
            address.setError("Enter Address");
            address.setText("");
        }
        else if (owner.getText() == null || owner.getText().toString().equalsIgnoreCase("")) {
            owner.setError("Enter Owner ");
            owner.setText("");
        }
        else if (mobile.getText() == null || mobile.getText().toString().equalsIgnoreCase("")) {
            mobile.setError("Enter Mobile Number");
            mobile.setText("");
        }
        else if (mail.getText() == null || mail.getText().toString().equalsIgnoreCase("")) {
            mail.setError("Enter Mailid");
            mail.setText("");
        }
        else if (!(mail.getText().toString().matches(emailPattern) && mail.getText().toString().length() > 0)) {
            mail.setError("Invalid Email Id");
        }
        else if (pass.getText() == null || pass.getText().toString().equalsIgnoreCase("")) {
            pass.setError("Enter Password");
            pass.setText("");
        }
        else if (location.getText() == null || location.getText().toString().equalsIgnoreCase("")) {
            location.setError("Enter Location");
            location.setText("");
        }
        else if (gst.getText() == null || gst.getText().toString().equalsIgnoreCase("")) {
            gst.setError("Enter GSTIN");
            gst.setText("");
        }
        else if (target.getText() == null || target.getText().toString().equalsIgnoreCase("")) {
            target.setError("Enter GSTIN");
            target.setText("");
        }
        else {
            ShopRegister();
        }
    }
    private void ShopRegister() {
        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.HttpUrlshopreg,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        // Matching server responce message to our text.
                        if(ServerResponse.contains("success")) {
                            AlertDialog alertDialog = new AlertDialog.Builder(AShopregActivity.this).create();
                            // Setting Dialog Title
                            alertDialog.setTitle("Hello");
                            // Setting Dialog Message
                            alertDialog.setMessage("Your Shop registration completed successfully");
                            // Setting Icon to Dialog
                            // alertDialog.setIcon(R.drawable.tick);
                            // Setting OK Button
                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                        Intent intent=new Intent(AShopregActivity.this,AshopActivity.class);
                                        startActivity(intent);
                                        finish();
                                    //Sendmail();
                                }
                            });
                            // Showing Alert Message
                            alertDialog.show();
                        }
                        else {
                            Toast.makeText(AShopregActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        // Showing error message if something goes wrong.
                        Toast.makeText(AShopregActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("shopreg_name", shop);
                params.put("shopreg_address", adres);
                params.put("shopreg_owner", ownr);
                params.put("shopreg_mobile", mobl);
                params.put("shopreg_mailid", email);
                params.put("shopreg_password", passs);
                params.put("shopreg_location", lctn);
                params.put("shopreg_gst", gstin);
                params.put("shopreg_asm", asm);
                params.put("shopreg_target", tar);
                return params;
            }
        };
        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(AShopregActivity.this);
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }
    @Override
    public void onBackPressed()
    {
        Intent in=new Intent(AShopregActivity.this,AshopActivity.class);
        startActivity(in);
        finish();


    }
}
