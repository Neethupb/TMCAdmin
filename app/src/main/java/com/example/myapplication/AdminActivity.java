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
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AdminActivity extends AppCompatActivity {
EditText user,pass;
    String username,password;
    // Creating Volley RequestQueue.
    RequestQueue requestQueue;
    // Creating Progress dialog.
    ProgressDialog progressDialog;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    // Storing server url into String variable.
    String HttpUrl = "http://www.truemobilecare.com/adminplus/login.php";
    Boolean CheckEditText;
    String name,shopid;
    int id;
Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        user=(EditText)findViewById(R.id.editText);
        pass=(EditText)findViewById(R.id.editText2);
        login=(Button)findViewById(R.id.button);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(AdminActivity.this);
        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(AdminActivity.this);
        // Adding click listener to button.

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Getting values from EditText.
                username = user.getText().toString().trim();
                password = pass.getText().toString().trim();

                if (user.getText().toString().equalsIgnoreCase("") || pass.getText().toString().equalsIgnoreCase("")){
                    user.setError("Enter Username");
                    pass.setError("Enter Password");
                }
                else {
                    AdminLogin();
                }
               /* Intent intent = new Intent(AdminActivity.this, AloginActivity.class);
                startActivity(intent);
                finish();*/
            }
        });
    }

    private void AdminLogin() {
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
                        if(!ServerResponse.equalsIgnoreCase("error")) {
                            try {
                                //getting the whole json object from the response
                                JSONObject obj = new JSONObject(ServerResponse);
                              //  id = obj.getInt("shopreg_id");
                                name = obj.getString("name");
                               // shopid=Integer.toString(id);
                               //  Toast.makeText(AdminActivity.this, name, Toast.LENGTH_SHORT).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            // If response matched then show the toast.
                            Toast.makeText(AdminActivity.this, "Logged In Successfully", Toast.LENGTH_LONG).show();
                            // Finish the current Login activity.
                            finish();

                            SharedPreferences.Editor editor = sharedpreferences.edit();
                           // editor.putString("shopreg_id", shopid);
                            editor.putString("name",name);
                            editor.commit();
                            // Opening the user profile activity using intent.
                            Intent intent = new Intent(AdminActivity.this, AloginActivity.class);
                            // Sending User Email to another activitFiny using intent.
                            //  intent.putExtra("shopreg_name",name);
                            //  Toast.makeText(MainActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                            startActivity(intent);
                            finish();
                        }
                        else {
                            // Showing Echo Response Message Coming From Server.
                            Toast.makeText(AdminActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        // Showing error message if something goes wrong.
                        Toast.makeText(AdminActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("admin_user", username);
                params.put("admin_pass", password);
                return params;
            }
        };
        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(AdminActivity.this);
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }
}
