package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText user,pass;
    String username,password,emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Button login;
    // Creating Volley RequestQueue.
    RequestQueue requestQueue;
    // Creating Progress dialog.
    ProgressDialog progressDialog;
    SharedPreferences sharedpreferences;
    // Storing server url into String variable.
    Boolean CheckEditText;
    String name,shopid;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user=(EditText)findViewById(R.id.editText);
        pass=(EditText)findViewById(R.id.editText2);
        login=(Button)findViewById(R.id.button);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        sharedpreferences = getSharedPreferences(Constants.MyPREFERENCES, Context.MODE_PRIVATE);

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(MainActivity.this);
        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(MainActivity.this);
        // Adding click listener to button.
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckEditTextIsEmptyOrNot();
                if (CheckEditText) {
                   UserLogin();
                    // Opening the user profile activity using intent.
                   /* Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                     startActivity(intent);*/
                } else {
                }
            }
        });
    }
    // Creating user login function.
    public void UserLogin() {
        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.HttpUrllogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        // Matching server responce message to our text.
                        if(!ServerResponse.equalsIgnoreCase("error")) {
                            try {
                                //getting the whole json object from the response
                                JSONObject obj = new JSONObject(ServerResponse);
                                     id = obj.getInt("shopreg_id");
                                     name = obj.getString("shopreg_name");
                                     shopid=Integer.toString(id);
                              // Toast.makeText(MainActivity.this, shopid, Toast.LENGTH_SHORT).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            // If response matched then show the toast.
                            Toast.makeText(MainActivity.this, "Logged In Successfully", Toast.LENGTH_LONG).show();
                            // Finish the current Login activity.
                            finish();

                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString("shopreg_id", shopid);
                            editor.putString("shopreg_name",name);
                            editor.commit();
                            // Opening the user profile activity using intent.
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            // Sending User Email to another activitFiny using intent.
                          //  intent.putExtra("shopreg_name",name);
                            //  Toast.makeText(MainActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                            startActivity(intent);
                            finish();
                        }
                        else {
                            // Showing Echo Response Message Coming From Server.
                            Toast.makeText(MainActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        // Showing error message if something goes wrong.
                        Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("shopreg_mailid", username);
                params.put("shopreg_password", password);
                return params;
            }
        };
        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }
    public void CheckEditTextIsEmptyOrNot() {
        // Getting values from EditText.
        username = user.getText().toString().trim();
        password = pass.getText().toString().trim();
        // Checking whether EditText value is empty or not.
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            // If any of EditText is empty then set variable value as False.
            CheckEditText = false;
            if (!(user.getText().toString().matches(emailPattern) && user.getText().toString().length() > 0)){
           // user.setHint("Invalid username");
           // user.setText("");
            user.setError("Invalid username");
        }

        if (pass.getText() == null
                || pass.getText().toString().trim().equalsIgnoreCase("")) {
          //  pass.setHint("Invalid password");
          //  pass.setText("");
            pass.setError("Enter password");
        }
        } else {
            // If any of EditText is filled then set variable value as True.
            CheckEditText = true;
        }
    }
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed()
    {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}



