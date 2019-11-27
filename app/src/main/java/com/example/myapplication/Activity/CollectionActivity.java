package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CollectionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private int mYear, mMonth, mDay;
    ArrayList<String> CountryName;
    // Creating Volley RequestQueue.
    RequestQueue requestQueue;
    // Creating Progress dialog.
    ProgressDialog progressDialog;
    SharedPreferences sharedpreferences;
    Spinner spinner,spin;
   String mode,asm,shop,name,id,shopname,collect,collected ,sellotedit,rate,remark,chequenumber,cheqdate,ref;
   Button submit;
    public static final String DEFAULT = "N/A";
    String[] country = { "--Collection Mode--", "Cash", "Cheque","E-Payment"};
EditText chequeno,amount,sellout,date,remarks,refno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_collection);
        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(CollectionActivity.this);
        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(CollectionActivity.this);
        sharedpreferences = getSharedPreferences(Constants.MyPREFERENCES, Context.MODE_PRIVATE);
        asm=sharedpreferences.getString("name",DEFAULT);

        chequeno=(EditText)findViewById(R.id.chequeno);
        chequeno.setVisibility(View.GONE);
        date=(EditText)findViewById(R.id.date);
        date.setOnClickListener(this);
        remarks=(EditText)findViewById(R.id.remarks);
        amount=(EditText)findViewById(R.id.amount);
        sellout=(EditText)findViewById(R.id.sellout);
        submit=(Button)findViewById(R.id.submit);
        refno=findViewById(R.id.refno);
        refno.setVisibility(View.GONE);
        submit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        mode=spin.getSelectedItem().toString();
        shop=   spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
        sellotedit=sellout.getText().toString().trim();
        rate=amount.getText().toString().trim();
        chequenumber=chequeno.getText().toString().trim();
        cheqdate=date.getText().toString().trim();
        remark=remarks.getText().toString().trim();
        ref=refno.getText().toString().trim();

         if (mode.equals ("--Collection Mode--")){
            Toast.makeText(CollectionActivity.this, "Select Mode", Toast.LENGTH_SHORT).show();
         }
      else   if (amount.getText() == null || amount.getText().toString().equalsIgnoreCase("")) {
            amount.setError("Enter Amount");
            //name.setText("");
        }
        else if (remarks.getText() == null || remarks.getText().toString().equalsIgnoreCase("")) {
            remarks.setError("Enter Remarks");
            //name.setText("");
        }
        else if (sellout.getText()== null || sellout.getText().toString().equalsIgnoreCase("")) {
             sellout.setError(" Empty Field! ");
             //name.setText("");
         }
         else if (date.getText()== null || date.getText().toString().equalsIgnoreCase("")) {
             date.setError(" Empty Field! ");
             //name.setText("");
         }
         else {
             if (mode.equals("Cash")) {
                         CollectionCash();
             }
             else if (mode.equals("E-Payment")){
                 if (refno.getText() == null || refno.getText().toString().equalsIgnoreCase("")) {
                     refno.setError("Enter Reference Number");
                     //name.setText("");
                 }
                 else {
                     CollectionPayment();
                 }
             }
             else {
                if (chequeno.getText() == null || chequeno.getText().toString().equalsIgnoreCase("")) {
                     chequeno.setError("Enter Cheque Number");
                     //name.setText("");
                 }
                 else {
                     CollectionCheque();
                 }
             }
         }
    }
});
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        spin = (Spinner) findViewById(R.id.spinner5);
        spin.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the country list
        final ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);


        CountryName=new ArrayList<>();
        spinner=(Spinner)findViewById(R.id.spinner4);
        spinner.setPrompt("Select Shop!");
        loadSpinnerData(Constants.URLshoplist);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                shop=   spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
               // Toast.makeText(CollectionActivity.this, id, Toast.LENGTH_SHORT).show();
                //  Toast.makeText(getApplicationContext(),country,Toast.LENGTH_LONG).show();
               // Toast.makeText(CollectionActivity.this, shop, Toast.LENGTH_SHORT).show();
                    Login();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
    }

    private void CollectionPayment() {
        // Showing progress dialog at user registration time.
       /* progressDialog.setMessage("Please Wait");
        progressDialog.show();*/
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.HttpUrlcheque,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        // Hiding the progress dialog after all task complete.
                        //  progressDialog.dismiss();
                        // Matching server responce message to our text.
                        if(!ServerResponse.equalsIgnoreCase("error")) {
                            Intent in=new Intent(CollectionActivity.this,AshopActivity.class);
                            startActivity(in);
                            finish();
                        }
                        else {
                            // Showing Echo Response Message Coming From Server.
                            Toast.makeText(CollectionActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Hiding the progress dialog after all task complete.
                        //  progressDialog.dismiss();
                        // Showing error message if something goes wrong.
                        Toast.makeText(CollectionActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("pay_shop", shop);
                params.put("pay_outstanding", sellotedit);
                params.put("pay_type", mode);
                params.put("pay_number", ref);
                params.put("pay_date", cheqdate);
                params.put("pay_amount", rate);
                params.put("pay_remarks", remark);
                params.put("pay_user", asm);


                // params.put("shopreg_password", password);
                return params;
            }
        };
        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(CollectionActivity.this);
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }

    private void CollectionCheque() {
        // Showing progress dialog at user registration time.
       /* progressDialog.setMessage("Please Wait");
        progressDialog.show();*/
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.HttpUrlcheque,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        // Hiding the progress dialog after all task complete.
                        //  progressDialog.dismiss();
                        // Matching server responce message to our text.
                        if(!ServerResponse.equalsIgnoreCase("error")) {
                            Intent in=new Intent(CollectionActivity.this,AshopActivity.class);
                            startActivity(in);
                            finish();
                        }
                        else {
                            // Showing Echo Response Message Coming From Server.
                            Toast.makeText(CollectionActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Hiding the progress dialog after all task complete.
                        //  progressDialog.dismiss();
                        // Showing error message if something goes wrong.
                        Toast.makeText(CollectionActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("pay_shop", shop);
                params.put("pay_outstanding", sellotedit);
                params.put("pay_type", mode);
                params.put("pay_number", chequenumber);
                params.put("pay_date", cheqdate);
                params.put("pay_amount", rate);
                params.put("pay_remarks", remark);
                params.put("pay_user", asm);

                // params.put("shopreg_password", password);
                return params;
            }
        };
        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(CollectionActivity.this);
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }

    private void CollectionCash() {
        // Showing progress dialog at user registration time.
       /* progressDialog.setMessage("Please Wait");
        progressDialog.show();*/
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.HttpUrlcash,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        // Hiding the progress dialog after all task complete.
                        //  progressDialog.dismiss();
                        // Matching server responce message to our text.
                        if(!ServerResponse.equalsIgnoreCase("error")) {
                            Intent in=new Intent(CollectionActivity.this,AshopActivity.class);
                            startActivity(in);
                            finish();
                        }
                        else {
                            // Showing Echo Response Message Coming From Server.
                            Toast.makeText(CollectionActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Hiding the progress dialog after all task complete.
                        //  progressDialog.dismiss();
                        // Showing error message if something goes wrong.
                        Toast.makeText(CollectionActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("pay_shop", shop);
                params.put("pay_outstanding", sellotedit);
                params.put("pay_type", mode);
                params.put("pay_date", cheqdate);
                params.put("pay_amount", rate);
                params.put("pay_remarks", remark);
                params.put("pay_user", asm);

                // params.put("shopreg_password", password);
                return params;
            }
        };
        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(CollectionActivity.this);
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }

    private void Login() {
        // Showing progress dialog at user registration time.
       /* progressDialog.setMessage("Please Wait");
        progressDialog.show();*/
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.HttpUrloutstanding,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        // Hiding the progress dialog after all task complete.
                        //  progressDialog.dismiss();
                        // Matching server responce message to our text.
                        if(!ServerResponse.equalsIgnoreCase("error")) {
                            try {
                                //getting the whole json object from the response
                                JSONObject obj = new JSONObject(ServerResponse);
                                collect = obj.getString("amount_to_collect");
                                collected = obj.getString("amount_collected");
                                // Toast.makeText(CollectionActivity.this, collect, Toast.LENGTH_SHORT).show();
                              //  Toast.makeText(CollectionActivity.this, collected, Toast.LENGTH_SHORT).show();

                                if (!(collect.equals("")||collected.equals(""))) {
                                         int atc = Integer.parseInt(collect);
                                         int ac = Integer.parseInt(collected);
                                         // int a = (int) 0.8;
                                         int x = (atc * 80/100);
                                             int y = (x - ac);
                                          //  Toast.makeText(CollectionActivity.this, ""+y, Toast.LENGTH_SHORT).show();

                                            String sell = Integer.toString(y);
                                            sellout.setText(sell);
                                        }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            // Showing Echo Response Message Coming From Server.
                            Toast.makeText(CollectionActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Hiding the progress dialog after all task complete.
                        //  progressDialog.dismiss();
                        // Showing error message if something goes wrong.
                        Toast.makeText(CollectionActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("pay_shop", shop);
                // params.put("shopreg_password", password);
                return params;
            }
        };
        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(CollectionActivity.this);
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
                             name=jsonObject1.getString("shopreg_name");
                            // id=jsonObject1.getString("shopreg_id");
                          //  Toast.makeText(CollectionActivity.this, id, Toast.LENGTH_SHORT).show();
                            CountryName.add(name);
                        }

                        spinner.setAdapter(new ArrayAdapter<String>(CollectionActivity.this, android.R.layout.simple_spinner_dropdown_item, CountryName));
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mode=spin.getSelectedItem().toString();
      //  Toast.makeText(this, mode, Toast.LENGTH_SHORT).show();
        if (mode.equals("Cheque")){
            chequeno.setVisibility(View.VISIBLE);
            refno.setVisibility(View.GONE);
        }
        else if (mode.equals("E-Payment")){
            refno.setVisibility(View.VISIBLE);
            chequeno.setVisibility(View.GONE);
        }
        else {
            chequeno.setVisibility(View.GONE);
            refno.setVisibility(View.GONE);

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    @Override
    public void onBackPressed()
    {
        Intent in=new Intent(CollectionActivity.this,AshopActivity.class);
        startActivity(in);
        finish();
    }

    @Override
    public void onClick(View v) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}
