package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Constants;
import com.example.myapplication.R;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class IntimationActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    EditText invcnmbr,incdntdate,incdnttym,location;
    Button intimate;
    private int mYear, mMonth, mDay;
    TimePickerDialog timePickerDialog;
    Calendar calendar;
    int currentHour;
    int currentMinute;
    String imei,phoneNo,sms;
    String[] damage = {"Theft", "Damage"};
    String amPm,invoicenumber,incidentdate,incidenttym,locatn,selectedItem;
    // Creating Volley RequestQueue.
    RequestQueue requestQueue;
    // Creating Progress dialog.
    ProgressDialog progressDialog;
    // Storing server url into String variable.
    SharedPreferences sharedpreferences;
    public static final String DEFAULT = "N/A";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intimation);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sharedpreferences = getSharedPreferences(Constants.MyPREFERENCES, Context.MODE_PRIVATE);
         imei = sharedpreferences.getString("imei",DEFAULT);
         phoneNo=sharedpreferences.getString("activation_mobile",DEFAULT);

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        final Spinner spin = (Spinner) findViewById(R.id.spinner2);
        spin.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the country list
        final ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,damage);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        invcnmbr=(EditText)findViewById(R.id.editText3);
        incdntdate=(EditText)findViewById(R.id.editText4);
        incdnttym=(EditText)findViewById(R.id.editText22);
        location=(EditText)findViewById(R.id.editText23);
        intimate=(Button)findViewById(R.id.button11);

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(IntimationActivity.this);
        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(IntimationActivity.this);

        intimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                invoicenumber=invcnmbr.getText().toString().trim();
                incidentdate=incdntdate.getText().toString().trim();
                incidenttym=incdnttym.getText().toString().trim();
                locatn=location.getText().toString().trim();
                selectedItem = spin.getSelectedItem().toString();
              //  Toast.makeText(IntimationActivity.this, incidentdate, Toast.LENGTH_SHORT).show();
                Validation();
               // Toast.makeText(IntimationActivity.this, imei, Toast.LENGTH_SHORT).show();
            }
        });
        incdntdate.setOnClickListener(this);
        incdnttym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(IntimationActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        incdnttym.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);
                    }
                }, currentHour, currentMinute, false);
                timePickerDialog.show();
            }
        });
    }
    private void Validation() {
        if (invcnmbr.getText().toString().equalsIgnoreCase("")|| invcnmbr.getText()== null){
            invcnmbr.setError("Enter Invoice Number");
        }
        else if (incdntdate.getText().toString().equalsIgnoreCase("")|| incdntdate.getText() == null){
            incdntdate.setError("Select Incident Date");
        }
        else if (incdnttym.getText().toString().equalsIgnoreCase("")|| incdnttym.getText()== null){
            incdnttym.setError("Select Incident Time");
        }
        else if (location.getText().toString().equalsIgnoreCase("")||location.getText()== null){
            location.setError("Enter Incident Location");
        }
        else {
           AddIntimation();
        }
    }
    private void AddIntimation() {
        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.HttpUrlintimation,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        // Matching server responce message to our text.
                        if(!ServerResponse.equalsIgnoreCase("error")) {
                            AlertDialog alertDialog = new AlertDialog.Builder(IntimationActivity.this).create();
                            // Setting Dialog Title
                            alertDialog.setTitle("Claim Intimated!");
                            // Setting Dialog Message
                            alertDialog.setMessage("For Reference Intimation Id: "+ServerResponse);
                            // Setting Icon to Dialog
                           // alertDialog.setIcon(R.drawable.tick);
                            // Setting OK Button
                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent=new Intent(IntimationActivity.this,HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                      }
                            });
                            // Showing Alert Message
                            alertDialog.show();
                        }
                        else {
                            // Showing Echo Response Message Coming From Server.
                            Toast.makeText(IntimationActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        // Showing error message if something goes wrong.
                        Toast.makeText(IntimationActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("intimation_imei", imei);
                params.put("intimation_invoiceno", invoicenumber);
                params.put("intimation_incidentdate", incidentdate);
                params.put("intimation_incidenttime", incidenttym);
                params.put("intimation_incidentlocation", locatn);
                params.put("intimation_damage", selectedItem);
                return params;
            }
        };
        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(IntimationActivity.this);
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }
    @Override
    public void onClick(View view) {
        if (view == incdntdate) {
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
                            incdntdate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
