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

public class AddpromoterActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
EditText name,contact,mail,target,salary,timing,asm,date;
Spinner shop,ptype;
Button doc,aproval,back;
    String[] country = {"--Choose a type--", "promoter", "Non Promoter"};
    ArrayList<String> CountryName;
    // Creating Volley RequestQueue.
    RequestQueue requestQueue;
    // Creating Progress dialog.
    ProgressDialog progressDialog;
    SharedPreferences sharedpreferences;
    public static final String DEFAULT = "N/A";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private int mYear, mMonth, mDay;
    String mode;
    String selectedItem,asmname,pname,pcontact,pmail,ptarget,psalary,ptiming,pasm,pptype,pdate,pimage,pid,presume,pbank;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_addpromoter);

        sharedpreferences = getSharedPreferences(Constants.MyPREFERENCES, Context.MODE_PRIVATE);
        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(AddpromoterActivity.this);
        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(AddpromoterActivity.this);
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        final Spinner ptype = (Spinner) findViewById(R.id.ptype);
        ptype.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the country list
        final ArrayAdapter<String> aa = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        ptype.setAdapter(aa);
        asmname = sharedpreferences.getString("name",DEFAULT);
        pimage = sharedpreferences.getString("messagephoto",DEFAULT);
        pid = sharedpreferences.getString("messageid",DEFAULT);
        pbank = sharedpreferences.getString("message",DEFAULT);
        date=(EditText)findViewById(R.id.date);
        date.setOnClickListener(this);
        name=(EditText)findViewById(R.id.name);
        contact=(EditText)findViewById(R.id.phone);
        mail=(EditText)findViewById(R.id.email);
        target=(EditText)findViewById(R.id.target);
        salary=(EditText)findViewById(R.id.salary);
        timing=(EditText)findViewById(R.id.timing);
        asm=(EditText) findViewById(R.id.asm);
        asm.setText(asmname);
        asm.setEnabled(false);
        doc=(Button)findViewById(R.id.documents);
        doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveToPrefer();
                Intent in=new Intent(AddpromoterActivity.this, UploadActivity.class);
                startActivity(in);
                finish();
            }
        });
        aproval=(Button)findViewById(R.id.approval);
        aproval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pname=name.getText().toString().trim();
                pcontact=contact.getText().toString().trim();
                pmail=mail.getText().toString().trim();
                ptarget=target.getText().toString().trim();
                pdate=date.getText().toString().trim();
                psalary=salary.getText().toString().trim();
                ptiming=timing.getText().toString().trim();
                pasm=asm.getText().toString().trim();
                selectedItem=shop.getSelectedItem().toString();
                pptype=ptype.getSelectedItem().toString();
                Validation();
            }
        });
        back=(Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(AddpromoterActivity.this,AloginActivity.class);
                startActivity(in);
                finish();
            }
        });
        CountryName=new ArrayList<>();
        shop=(Spinner)findViewById(R.id.shop);
        shop.setPrompt("Select Shop!");
        loadSpinnerData(Constants.URLshoplist);
        shop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String country=   shop.getItemAtPosition(shop.getSelectedItemPosition()).toString();
                //  Toast.makeText(getApplicationContext(),country,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
    }
    @Override
    protected void onResume() {
        PopulateFromPrefer();
        super.onResume();
    }
    private void PopulateFromPrefer() {
        name.setText(sharedpreferences.getString("proname", ""));
        mail.setText(sharedpreferences.getString("promail", ""));
        contact.setText(sharedpreferences.getString("procontact", ""));
        asm.setText(sharedpreferences.getString("proasm", asmname));
        target.setText(sharedpreferences.getString("protarget", ""));
        salary.setText(sharedpreferences.getString("prosalary", ""));
        date.setText(sharedpreferences.getString("prodate", ""));
        timing.setText(sharedpreferences.getString("protiming", ""));
    }
     @Override
     protected void onPause() {
         SaveToPrefer();
         super.onPause();
     }
    private void SaveToPrefer() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("proname", name.getText().toString());
        editor.putString("proasm", asm.getText().toString());
        editor.putString("procontact", contact.getText().toString());
        editor.putString("promail", mail.getText().toString());
        editor.putString("protarget", target.getText().toString());
        editor.putString("prosalary", salary.getText().toString());
        editor.putString("prodate", date.getText().toString());
        editor.putString("protiming", timing.getText().toString());
        editor.apply();
    }
    private void Validation() {
        if (name.getText() == null || name.getText().toString().equalsIgnoreCase("")) {
            name.setError("Enter Promoter Name");
        }
        else if (pptype.equals ("--Choose a type--")){
            Toast.makeText(this, "Select Type", Toast.LENGTH_SHORT).show();
        }
        else if (mail.getText() == null || mail.getText().toString().equalsIgnoreCase("")) {
            mail.setError("Enter Email Id");
        }
        else if (!(mail.getText().toString().matches(emailPattern) && mail.getText().toString().length() > 0)) {
            mail.setError("Invalid Email Id");
        }
        else if (contact.getText() == null || contact.getText().toString().equalsIgnoreCase("")) {
            contact.setError("Enter Phone Number");
        }else if ((contact.getText().toString()).length() > 10) {
            contact.setError("Invalid phone Number");
        }
        else if (target.getText() == null || target.getText().toString().equalsIgnoreCase("")) {
            target.setError("Enter Target Amount");
        }
        else if (salary.getText() == null || salary.getText().toString().equalsIgnoreCase("")) {
            salary.setError("Enter Salary Amount");
        }
        else if (timing.getText() == null || timing.getText().toString().equalsIgnoreCase("")) {
            timing.setError("Enter Timing ");
        } else if (date.getText() == null || date.getText().toString().equalsIgnoreCase("")) {
            date.setError("Select Date ");
        }
        else {
        PromoterLogin();
        }
    }

    private void PromoterLogin() {
        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.HttpUrlpromoter,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        // Matching server responce message to our text.
                        if(!ServerResponse.equalsIgnoreCase("error")) {
                            Intent intent = new Intent(AddpromoterActivity.this, PromoterActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            // Showing Echo Response Message Coming From Server.
                            Toast.makeText(AddpromoterActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        // Showing error message if something goes wrong.
                        Toast.makeText(AddpromoterActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
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
               // params.put("pro_resume", presume);
                params.put("pro_bank", pbank);
                return params;
            }
        };
        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(AddpromoterActivity.this);
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

                        shop.setAdapter(new ArrayAdapter<String>(AddpromoterActivity.this, android.R.layout.simple_spinner_dropdown_item, CountryName));
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
    public void onClick(View view) {
        if (view == date) {
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
                            date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }
    @Override
    public void onBackPressed()
    {
         SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
        editor.putString("name",asmname);
        editor.apply();
        Intent in=new Intent(AddpromoterActivity.this,PromoterActivity.class);
        startActivity(in);
        finish();
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
