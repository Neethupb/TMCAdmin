package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AreportActivity extends AppCompatActivity {
  //  public class AreportActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
   /* EditText from,to;
    private int mYear, mMonth, mDay;
    String frmdt,todt,shopid,dlr,prdt;
    String URL="http://www.truemobilecare.com/adminplus/shoplist.php";
    ArrayList<String> CountryName;
    // Creating Volley RequestQueue.
    RequestQueue requestQueue;
    // Creating Progress dialog.
    ProgressDialog progressDialog;
    String HttpUrl = "http://www.truemobilecare.com/adminplus/product.php";
    String[] country = { "--select product--", "Knightshield Bronze ", "Knightshield BronzePlus", "Knightshield Silver", "Knightshield SilverPlus","Knightshield Gold","Knightshield GoldPlus","Knightshield Platinum","Knightshield PlatinumPlus","Knightshield Diamond"};
    Spinner dealer;
    Button download,views,back;
    LinearLayout linear;
    TextView product,quantity,price;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_areport);



        WebView webView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("http://www.truemobilecare.com/admin/report.php");

       /* getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        from=(EditText)findViewById(R.id.editText25);
        to=(EditText)findViewById(R.id.editText26);
        from.setOnClickListener(this);
        to.setOnClickListener(this);
        download=(Button)findViewById(R.id.button22);
        views=(Button)findViewById(R.id.button4);
        back=(Button)findViewById(R.id.button5);

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(AreportActivity.this);
        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(AreportActivity.this);



        linear=(LinearLayout)findViewById(R.id.linear);
        linear.setVisibility(View.GONE);

        product=(TextView)findViewById(R.id.product);
        quantity=(TextView)findViewById(R.id.quantity);
        price=(TextView)findViewById(R.id.price);

      *//*  //Getting the instance of Spinner and applying OnItemSelectedListener on it
        final Spinner spin = (Spinner) findViewById(R.id.spinner5);
        spin.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the country list
        final ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);*//*

        CountryName=new ArrayList<>();
        dealer=(Spinner)findViewById(R.id.spinner4);
        loadSpinnerData(URL);
        dealer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String country=   dealer.getItemAtPosition(dealer.getSelectedItemPosition()).toString();
               // Toast.makeText(getApplicationContext(),country,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(AreportActivity.this,AloginActivity.class);
                startActivity(in);
                finish();
            }
        });
        views.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frmdt=from.getText().toString().trim();
                todt=to.getText().toString().trim();
                dlr = dealer.getSelectedItem().toString();
              //  prdt = spin.getSelectedItem().toString();

               *//* if (frmdt.equalsIgnoreCase("") || todt.equalsIgnoreCase("")||dlr.equalsIgnoreCase("")||prdt.equalsIgnoreCase("")){
                    from.setError("Select From Date");
                    to.setError("Select To Date");
                }
                else {*//*
                    linear.setVisibility(View.VISIBLE);
                    product.setText("");
                    quantity.setText("");
                    price.setText("");
                    ViewReport();

               // }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(AreportActivity.this,AloginActivity.class);
                startActivity(in);
                finish();
            }
        });

    }

    private void ViewReport() {
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
                        ArrayList<PlayerModelReport> playersModelArrayList = new ArrayList<>();

                        try {

                            JSONArray array=new JSONArray(ServerResponse);
                            for (int i=0; i < array.length(); i++) {
                                PlayerModelReport playerModelReport = new PlayerModelReport();
                                JSONObject product = array.getJSONObject(i);
                                playerModelReport.setProduct(product.getString("product"));
                                playerModelReport.setQuantity(product.getString("quantity"));
                                playerModelReport.setPrice(product.getString("price"));
                                playersModelArrayList.add(playerModelReport);
                            }
                            for (int j = 0; j < playersModelArrayList.size(); j++){
                                product.setText(product.getText()+ playersModelArrayList.get(j).getProduct()+"\n");
                                quantity.setText(quantity.getText()+ playersModelArrayList.get(j).getQuantity()+"\n");
                                price.setText(price.getText()+ playersModelArrayList.get(j).getPrice()+"\n");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        // Showing error message if something goes wrong.
                        Toast.makeText(AreportActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("fromdate", frmdt);
                params.put("todate", todt);
                params.put("dealer", dlr);
               // params.put("product", prdt);
                return params;
            }
        };
        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(AreportActivity.this);
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);*/
    }


  /*  private void loadSpinnerData(String url) {

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
                        String id=jsonObject1.getString("shopreg_id");
                       // Toast.makeText(AreportActivity.this, id, Toast.LENGTH_SHORT).show();
                        CountryName.add(country);
                    }

                    dealer.setAdapter(new ArrayAdapter<String>(AreportActivity.this, android.R.layout.simple_spinner_dropdown_item, CountryName));
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
        Intent in=new Intent(AreportActivity.this,AloginActivity.class);
        startActivity(in);
        finish();


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if (v == from) {
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            from.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        else if (v == to){
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            to.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }*/
  @Override
  public void onBackPressed()
  {
      Intent in=new Intent(AreportActivity.this,AloginActivity.class);
      startActivity(in);
      finish();
  }
}
