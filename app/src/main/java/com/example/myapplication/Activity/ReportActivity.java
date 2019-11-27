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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Constants;
import com.example.myapplication.Models.PlayerModelReport;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
public class ReportActivity extends AppCompatActivity implements View.OnClickListener {
EditText from,to;
    private int mYear, mMonth, mDay;
    // Creating Volley RequestQueue.
    RequestQueue requestQueue;
    // Creating Progress dialog.
    ProgressDialog progressDialog;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    // Storing server url into String variable.
    Button view;
    String frmdt,todt,shopid;
    public static final String DEFAULT = "N/A";
    LinearLayout linear,total;
    TextView product,quantity,price,totalquty,totalprc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        from=(EditText)findViewById(R.id.editText18);
        to=(EditText)findViewById(R.id.editText17);
        view=(Button)findViewById(R.id.button8);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        shopid=sharedpreferences.getString("shopreg_id",DEFAULT);
      //  Toast.makeText(this, shopid, Toast.LENGTH_SHORT).show();
        // Creating Volley newRequestQueue
        requestQueue = Volley.newRequestQueue(ReportActivity.this);
        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(ReportActivity.this);


linear=(LinearLayout)findViewById(R.id.linear);
linear.setVisibility(View.GONE);

/*total=(LinearLayout)findViewById(R.id.total);
total.setVisibility(View.GONE);*/

        product=(TextView)findViewById(R.id.product);
        quantity=(TextView)findViewById(R.id.quantity);
        price=(TextView)findViewById(R.id.price);

       /* totalquty=(TextView)findViewById(R.id.totalqt);
        totalprc=(TextView)findViewById(R.id.totalprc);*/

        from.setOnClickListener(this);
        to.setOnClickListener(this);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                frmdt=from.getText().toString().trim();
                todt=to.getText().toString().trim();
                if(from.getText().toString().equalsIgnoreCase("")|| from.getText().toString()==null){
                    from.setText("Select From Date");
                }
                else if (to.getText().toString().equalsIgnoreCase("")|| to.getText().toString()== null){
                    to.setText("Select To date");
                }
                else {
                    linear.setVisibility(View.VISIBLE);
                   // total.setVisibility(View.VISIBLE);
                    Report();
                }
            }
        });
    }
    private void Report(){
        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.HttpUrlreport,
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
                        Toast.makeText(ReportActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("activation_shopid", shopid);
                params.put("fromdate", frmdt);
                params.put("todate", todt);
                return params;
            }
        };
        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(ReportActivity.this);
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }
    @Override
    public void onBackPressed()
    {
        Intent in=new Intent(ReportActivity.this, HomeActivity.class);
        startActivity(in);
        finish();
    }
    @Override
    public void onClick(View view) {
        if (view == from) {
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
        else if (view == to){
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
    }

}
