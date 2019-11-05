package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OtpActivity extends AppCompatActivity {
    Button submit,resend;
    String URL="http://www.truemobilecare.com/adminplus/shoplist.php";
    String HttpUrl = "http://www.truemobilecare.com/adminplus/otp.php";
    String HttpUrlResend = "http://www.truemobilecare.com/adminplus/resendotp.php";
    String HttpUrlSubmit = "http://www.truemobilecare.com/adminplus/submit.php";

    // Creating Volley RequestQueue.
    RequestQueue requestQueue;
    // Creating Progress dialog.
    ProgressDialog progressDialog;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    public static final String DEFAULT = "N/A";
    TextView send;
    String selectedItem,pname,pcontact,pmail,ptarget,psalary,ptiming,pasm,pptype,pdate,pimage,pid,presume,pbank;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(OtpActivity.this);
        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(OtpActivity.this);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        selectedItem=sharedpreferences.getString("shop",DEFAULT);
        pbank=sharedpreferences.getString("message",DEFAULT);
        pname=sharedpreferences.getString("pname",DEFAULT);
        pcontact=sharedpreferences.getString("contact",DEFAULT);
        pmail=sharedpreferences.getString("mail",DEFAULT);
        ptarget=sharedpreferences.getString("target",DEFAULT);
        psalary=sharedpreferences.getString("salary",DEFAULT);
        ptiming=sharedpreferences.getString("timing",DEFAULT);
        pasm=sharedpreferences.getString("name",DEFAULT);
        pptype=sharedpreferences.getString("ptype",DEFAULT);
        pdate=sharedpreferences.getString("date",DEFAULT);
        pimage=sharedpreferences.getString("messagephoto",DEFAULT);
        pid=sharedpreferences.getString("messageid",DEFAULT);
        presume=sharedpreferences.getString("pdf",DEFAULT);
        Toast.makeText(this, ""+selectedItem+""+pbank+""+pname+""+pcontact+""+pmail+""+ptarget+""+psalary+""+ptiming+""+pasm+""+pptype+""+pdate+""+pimage+""+pid+""+presume, Toast.LENGTH_SHORT).show();
        resend=(Button)findViewById(R.id.resend);
        resend.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        ReSend();
    }
});
        submit=(Button)findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Save();
    }
});
        send=(TextView)findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendOtp();
            }
        });
    }

    private void Save() {
        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrlSubmit,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        // Matching server responce message to our text.
                        if(ServerResponse.contains("success")) {
                           /* SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString("shopreg_name", shop);
                            editor.commit();
                            Intent intent=new Intent(KeyActivity.this,HomeActivity.class);
                            startActivity(intent);
                            finish();*/
                        }
                        else {
                            // Showing Echo Response Message Coming From Server.
                            Toast.makeText(OtpActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        // Showing error message if something goes wrong.
                        Toast.makeText(OtpActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
               /* params.put("activation_name", name);
                params.put("activation_mailid", email);
                params.put("activation_mobile", phone);
                params.put("activation_proof", proof);
                params.put("activation_idnumber", id);
                params.put("activation_idfront", front);
                params.put("activation_imei", imei);
                params.put("activation_mobileprice", mob);
                params.put("activation_knightshieldtype", typ);
                params.put("activation_knightshieldprice", price);
                params.put("activation_invoicedate", invoice_date);
                params.put("activation_gadgetname", gadget);
                params.put("activation_shopid",shopid);
                params.put("activation_knightshieldpurchaseshop", shop);
                params.put("activation_mobileshop", mobshop);
                params.put("activation_key", actkey);*/
                // params.put("image_idfront", front);
                return params;
            }
        };
        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(OtpActivity.this);
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }

    private void ReSend() {
        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        // Matching server responce message to our text.
                        if(ServerResponse.contains("success")) {
                           /* SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString("shopreg_name", shop);
                            editor.commit();
                            Intent intent=new Intent(KeyActivity.this,HomeActivity.class);
                            startActivity(intent);
                            finish();*/
                        }
                        else {
                            // Showing Echo Response Message Coming From Server.
                            Toast.makeText(OtpActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        // Showing error message if something goes wrong.
                        Toast.makeText(OtpActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("pro_name", pname);
                params.put("pro_shop", selectedItem);
                params.put("pro_type", pptype);
                params.put("pro_asm", pasm);
                params.put("pro_mob", pcontact);
                params.put("pro_mailid", pmail);
                params.put("pro_target", ptarget);
                params.put("pro_salary", psalary);
                params.put("pro_joindate", pdate);
                params.put("pro_worktime", ptiming);
                params.put("pro_image", pimage);
                params.put("pro_idproof", pid);
                params.put("pro_resume", presume);
                params.put("pro_bank", pbank);
                // params.put("image_idfront", front);
                return params;
            }
        };
        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(OtpActivity.this);
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }

    private void SendOtp() {
        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        // Matching server responce message to our text.
                        if(ServerResponse.contains("success")) {
                           /* SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString("shopreg_name", shop);
                            editor.commit();
                            Intent intent=new Intent(KeyActivity.this,HomeActivity.class);
                            startActivity(intent);
                            finish();*/
                        }
                        else {
                            // Showing Echo Response Message Coming From Server.
                            Toast.makeText(OtpActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        // Showing error message if something goes wrong.
                        Toast.makeText(OtpActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("pro_name", pname);
                params.put("pro_shop", selectedItem);
                params.put("pro_type", pptype);
                params.put("pro_asm", pasm);
                params.put("pro_mob", pcontact);
                params.put("pro_mailid", pmail);
                params.put("pro_target", ptarget);
                params.put("pro_salary", psalary);
                params.put("pro_joindate", pdate);
                params.put("pro_worktime", ptiming);
                params.put("pro_image", pimage);
                params.put("pro_idproof", pid);
                params.put("pro_resume", presume);
                params.put("pro_bank", pbank);
                // params.put("image_idfront", front);
                return params;
            }
        };
        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(OtpActivity.this);
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }


    @Override
    public void onBackPressed()
    {
        Intent in=new Intent(OtpActivity.this,AddpromoterActivity.class);
        startActivity(in);
        finish();
    }
}
