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

public class EditActivity extends AppCompatActivity {
    Button search,save,remove,back;
    LinearLayout linear;
    ArrayList<String> CountryName;
    Spinner promoter;
    // Creating Volley RequestQueue.
    RequestQueue requestQueue;
    // Creating Progress dialog.
    ProgressDialog progressDialog;
    SharedPreferences sharedpreferences;
    public static final String DEFAULT = "N/A";
    String strpro,promotername,procontact,promail,protarget,prosalary,proattendance,proasm;
    EditText proname,contact,mail,target,salary,attendance,asm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_edit);
        sharedpreferences = getSharedPreferences(Constants.MyPREFERENCES, Context.MODE_PRIVATE);
        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(EditActivity.this);
        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(EditActivity.this);
linear=(LinearLayout)findViewById(R.id.linear);
linear.setVisibility(View.GONE);
        proname=(EditText)findViewById(R.id.name);
        contact=(EditText)findViewById(R.id.contact);
        mail=(EditText)findViewById(R.id.mail);
        target=(EditText)findViewById(R.id.target);
        salary=(EditText)findViewById(R.id.salary);
        attendance=(EditText)findViewById(R.id.attendance);
        asm=(EditText)findViewById(R.id.asm);

        search=(Button)findViewById(R.id.search);
        save=(Button)findViewById(R.id.save);
        remove=(Button)findViewById(R.id.remove);
        back=(Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strpro=promoter.getSelectedItem().toString();
                EditPromoter();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        promotername=proname.getText().toString();
        procontact=contact.getText().toString();
        promail=mail.getText().toString();
        protarget=target.getText().toString();
        prosalary=salary.getText().toString();
        proattendance=attendance.getText().toString();
        proasm=asm.getText().toString();

        Savepromoter();
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promotername=proname.getText().toString();
                procontact=contact.getText().toString();
                promail=mail.getText().toString();
                protarget=target.getText().toString();
                prosalary=salary.getText().toString();
                proattendance=attendance.getText().toString();
                proasm=asm.getText().toString();

                DeletePromoter();
            }
        });
        CountryName=new ArrayList<>();
        promoter=(Spinner)findViewById(R.id.promoter);
       // promoter.setPrompt("Select Shop!");
        loadSpinnerData(Constants.URLpromoterlist);
        promoter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String country=   promoter.getItemAtPosition(promoter.getSelectedItemPosition()).toString();
                //  Toast.makeText(getApplicationContext(),country,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
    }
    private void DeletePromoter() {
        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.HttpUrlpromoterdelete,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        // Matching server responce message to our text.
                        if(!ServerResponse.equalsIgnoreCase("error")) {

                            Intent intent = new Intent(EditActivity.this, PromoterActivity.class);
                            // Sending User Email to another activitFiny using intent.
                            //  intent.putExtra("shopreg_name",name);
                            //  Toast.makeText(MainActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                            startActivity(intent);
                            finish();
                        }
                        else {
                            // Showing Echo Response Message Coming From Server.
                            Toast.makeText(EditActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        // Showing error message if something goes wrong.
                        Toast.makeText(EditActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("pro_name", promotername);
                params.put("pro_asm", proasm);
                params.put("pro_mob", procontact);
                params.put("pro_mailid", promail);
                params.put("pro_target", protarget);
                params.put("pro_salary", prosalary);
                params.put("pro_worktime", proattendance);
                return params;
            }
        };
        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(EditActivity.this);
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }
    private void Savepromoter() {
        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.HttpUrlpromoterupdate,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        // Matching server responce message to our text.
                        if(!ServerResponse.equalsIgnoreCase("error")) {
                          /*  try {
                                //getting the whole json object from the response
                                JSONObject obj = new JSONObject(ServerResponse);
                                id = obj.getInt("shopreg_id");
                                name = obj.getString("shopreg_name");
                                shopid=Integer.toString(id);
                                // Toast.makeText(MainActivity.this, shopid, Toast.LENGTH_SHORT).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }*/
                            // If response matched then show the toast.
                           /* Toast.makeText(AddpromoterActivity.this, "Logged In Successfully", Toast.LENGTH_LONG).show();
                            // Finish the current Login activity.
                            finish();

                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString("shopreg_id", shopid);
                            editor.putString("shopreg_name",name);
                            editor.commit();*/
                            // Opening the user profile activity using intent.
                            Intent intent = new Intent(EditActivity.this, PromoterActivity.class);
                            // Sending User Email to another activitFiny using intent.
                            //  intent.putExtra("shopreg_name",name);
                            //  Toast.makeText(MainActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                            startActivity(intent);
                            finish();
                        }
                        else {
                            // Showing Echo Response Message Coming From Server.
                            Toast.makeText(EditActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        // Showing error message if something goes wrong.
                        Toast.makeText(EditActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("pro_name", promotername);
                params.put("pro_asm", proasm);
                params.put("pro_mob", procontact);
                params.put("pro_mailid", promail);
                params.put("pro_target", protarget);
                params.put("pro_salary", prosalary);
                params.put("pro_worktime", proattendance);
                return params;
            }
        };
        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(EditActivity.this);
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }
    private void EditPromoter() {
        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.HttpUrlpromoterview,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        // Matching server responce message to our text.
                        if(!ServerResponse.equalsIgnoreCase("error")) {
                            try {
                                linear.setVisibility(View.VISIBLE);
                                //getting the whole json object from the response
                                JSONObject obj = new JSONObject(ServerResponse);
                                proname.setText(obj.getString("pro_name"));
                                asm.setText(obj.getString("pro_asm"));
                                contact.setText(obj.getString("pro_mob"));
                                mail.setText(obj.getString("pro_mailid"));
                                target.setText(obj.getString("pro_target"));
                                salary.setText(obj.getString("pro_salary"));
                                attendance.setText(obj.getString("pro_worktime"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                           /* Intent intent = new Intent(EditActivity.this, PromoterActivity.class);
                            startActivity(intent);
                            finish();*/
                        }
                        else {
                            // Showing Echo Response Message Coming From Server.
                            Toast.makeText(EditActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        // Showing error message if something goes wrong.
                        Toast.makeText(EditActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("pro_name", strpro);

                return params;
            }
        };
        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(EditActivity.this);
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }
    private void loadSpinnerData(String url) {
        {
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
                                String country=jsonObject1.getString("pro_name");
                                CountryName.add(country);
                            }

                            promoter.setAdapter(new ArrayAdapter<String>(EditActivity.this, android.R.layout.simple_spinner_dropdown_item, CountryName));
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
    }
    @Override
    public void onBackPressed()
    {
        Intent in=new Intent(EditActivity.this,PromoterActivity.class);
        startActivity(in);
        finish();

    }
}
