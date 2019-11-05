package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class ClaimIntimationActivity extends AppCompatActivity {
    EditText imei;
    TextView toll;
    Button next,search;
    // Creating Progress dialog.
    ProgressDialog progressDialog;
    // Storing server url into String variable.
    String HttpUrl = "http://www.truemobilecare.com/android/details.php";
    // Creating Volley RequestQueue.
    RequestQueue requestQueue;
    TextView name,email,phone,idpf,idnmbr,imeinmbr,mobprce,date,gadget,knightshop,mobshop;
    String ime;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim_intimation);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(ClaimIntimationActivity.this);
        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(ClaimIntimationActivity.this);

        name=(TextView)findViewById(R.id.textView36);
        email=(TextView)findViewById(R.id.textView35);
        phone=(TextView)findViewById(R.id.textView34);
        idpf=(TextView)findViewById(R.id.textView33);
        idnmbr=(TextView)findViewById(R.id.textView32);
        imeinmbr=(TextView)findViewById(R.id.textView31);
        mobprce=(TextView)findViewById(R.id.textView30);
        date=(TextView)findViewById(R.id.textView28);
        gadget=(TextView)findViewById(R.id.textView29);
        knightshop=(TextView)findViewById(R.id.textView27);
        mobshop=(TextView)findViewById(R.id.textView25);

        imei=(EditText)findViewById(R.id.editText15);
        toll=(TextView)findViewById(R.id.textView2);
        search=(Button)findViewById(R.id.button3);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ime=imei.getText().toString().trim();
                if (imei.getText() == null || imei.getText().toString().equalsIgnoreCase("")) {
                    imei.setError("Enter imei Number");
                    imei.setText("");
                } else if ((imei.getText().toString()).length() != 15) {
                    imei.setError("Invalid IMEI Number");
                }
                else {
                    next.setVisibility(View.VISIBLE);
                    Checkdetails();
                }
            }
        });
        next=(Button)findViewById(R.id.button12);
        next.setVisibility(View.GONE);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ime=imei.getText().toString().trim();
                if (imei.getText() == null || imei.getText().toString().equalsIgnoreCase("")) {
                    imei.setError("Enter imei Number");
                    imei.setText("");
                } else if ((imei.getText().toString()).length() != 15) {
                    imei.setError("Invalid IMEI Number");
                }
                else {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("imei", ime);
                   // editor.putString("shopreg_name", String.valueOf(name));
                    editor.commit();
                        Intent intent=new Intent(ClaimIntimationActivity.this,IntimationActivity.class);
                        startActivity(intent);
                }
            }
        });
        toll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = "1800123366666";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));


                if (ActivityCompat.checkSelfPermission(ClaimIntimationActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(intent);

            }
        });
    }

    private void Checkdetails() {
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
                        if (!ServerResponse.equalsIgnoreCase("error")) {
                            try {
                                //getting the whole json object from the response
                                JSONObject obj = new JSONObject(ServerResponse);
                                name.setText(obj.getString("activation_name"));
                                email.setText(obj.getString("activation_mailid"));
                                phone.setText(obj.getString("activation_mobile"));
                                idpf.setText(obj.getString("activation_proof"));
                                idnmbr.setText(obj.getString("activation_idnumber"));
                                imeinmbr.setText(obj.getString("activation_imei"));
                                mobprce.setText(obj.getString("activation_mobileprice"));
                                date.setText(obj.getString("activation_invoicedate"));
                                gadget.setText(obj.getString("activation_gadgetname"));
                                knightshop.setText(obj.getString("activation_knightshieldpurchaseshop"));
                                mobshop.setText(obj.getString("activation_mobileshop"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            // Showing Echo Response Message Coming From Server.
                            Toast.makeText(ClaimIntimationActivity.this, "IMEI number not found! try again", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(ClaimIntimationActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("activation_imei",ime);
                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(ClaimIntimationActivity.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }

    @Override
    public void onBackPressed()
    {
        Intent in=new Intent(ClaimIntimationActivity.this,HomeActivity.class);
        startActivity(in);
        finish();
    }
}
