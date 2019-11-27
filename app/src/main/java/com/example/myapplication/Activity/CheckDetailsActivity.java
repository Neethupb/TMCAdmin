package com.example.myapplication.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
import com.example.myapplication.Constants;
import com.example.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CheckDetailsActivity extends Activity {
    TextView name,email,phone,idpf,idnmbr,imeinmbr,mobprce,date,gadget,knightshop,mobshop;
    EditText imei;
    Button search;
    String imeinumber;
    // Creating Progress dialog.
    ProgressDialog progressDialog;
    // Storing server url into String variable.
    // Creating Volley RequestQueue.
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_check_details);
        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(CheckDetailsActivity.this);
        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(CheckDetailsActivity.this);

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
        search=(Button)findViewById(R.id.button3);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        validation();
              //  Toast.makeText(CheckDetailsActivity.this, imeinumber, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void validation() {
        imeinumber=imei.getText().toString().trim();
        if (imei.getText() == null || imei.getText().toString().equalsIgnoreCase("")) {
            imei.setError("Enter imei Number");
            imei.setText("");
        } else if ((imei.getText().toString()).length() != 15) {
            imei.setError("Invalid IMEI Number");
        }
        else {
            Checkdetails();
        }
    }
    private void Checkdetails() {
        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.HttpUrlcheckdetails,
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
    Toast.makeText(CheckDetailsActivity.this, "IMEI number not found! try again", Toast.LENGTH_LONG).show();
}
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(CheckDetailsActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("activation_imei",imeinumber);
                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(CheckDetailsActivity.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }
    @Override
    public void onBackPressed()
    {
        Intent in=new Intent(CheckDetailsActivity.this, HomeActivity.class);
        startActivity(in);
        finish();
    }
}
