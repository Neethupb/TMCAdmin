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
import android.widget.ImageView;
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
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.myapplication.Constants;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewpromoterActivity extends AppCompatActivity {
    ArrayList<String> CountryName;
    Spinner promoter;
    LinearLayout linear;
    TextView shop,type,asm,contact,mail,target,salary,date,wrktym,idprf,resume,bank;
    ImageView image;
    String strpro,imageurl="http://www.truemobilecare.com/assets/promoterimage",imagel;
    Button search;
    // Creating Volley RequestQueue.
    RequestQueue requestQueue;
    // Creating Progress dialog.
    ProgressDialog progressDialog;
    SharedPreferences sharedpreferences;
    public static final String DEFAULT = "N/A";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpromoter);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        sharedpreferences = getSharedPreferences(Constants.MyPREFERENCES, Context.MODE_PRIVATE);
        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(ViewpromoterActivity.this);
        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(ViewpromoterActivity.this);

        shop=(TextView)findViewById(R.id.shop);
        type=(TextView)findViewById(R.id.type);
        asm=(TextView)findViewById(R.id.asm);
        contact=(TextView)findViewById(R.id.contact);
        mail=(TextView)findViewById(R.id.mail);
        target=(TextView)findViewById(R.id.target);
        salary=(TextView)findViewById(R.id.salary);
        date=(TextView)findViewById(R.id.date);
        wrktym=(TextView)findViewById(R.id.wrktime);
        image=findViewById(R.id.image);

       /* idprf=(TextView)findViewById(R.id.pic);
        resume=(TextView)findViewById(R.id.resume);
        bank=(TextView)findViewById(R.id.bank);*/

        linear=(LinearLayout)findViewById(R.id.linear);
        linear.setVisibility(View.GONE);
        search=(Button)findViewById(R.id.search);
      //  image=(ImageView)findViewById(R.id.image) ;
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strpro=promoter.getSelectedItem().toString();
                linear.setVisibility(View.VISIBLE);
                ViewDetails();

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
    private void ViewDetails() {
        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.HttpUrlpromoterfullview,
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

                                shop.setText(obj.getString("pro_shop"));
                                type.setText(obj.getString("pro_type"));
                                asm.setText(obj.getString("pro_asm"));
                                contact.setText(obj.getString("pro_mob"));
                                mail.setText(obj.getString("pro_mailid"));
                                target.setText(obj.getString("pro_target"));
                                salary.setText(obj.getString("pro_salary"));
                                date.setText(obj.getString("pro_joindate"));
                                wrktym.setText(obj.getString("pro_worktime"));
                                imagel=(obj.getString("pro_image"));
                                String imageurl="http://www.truemobilecare.com/assets/promoterimage/"+imagel;
                                Glide.with(getApplicationContext()).load(imageurl).into(image);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            // Showing Echo Response Message Coming From Server.
                            Toast.makeText(ViewpromoterActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        // Showing error message if something goes wrong.
                        Toast.makeText(ViewpromoterActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(ViewpromoterActivity.this);
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

                            promoter.setAdapter(new ArrayAdapter<String>(ViewpromoterActivity.this, android.R.layout.simple_spinner_dropdown_item, CountryName));
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
        Intent in=new Intent(ViewpromoterActivity.this,PromoterActivity.class);
        startActivity(in);
        finish();

    }
}
