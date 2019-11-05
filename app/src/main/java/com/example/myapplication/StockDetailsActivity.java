package com.example.myapplication;

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
import java.util.HashMap;
import java.util.Map;

public class StockDetailsActivity extends AppCompatActivity {
    String URL="http://www.truemobilecare.com/adminplus/shoplist.php";
    String HttpUrl = "http://www.truemobilecare.com/adminplus/stock.php";
    String HttpUrlId = "http://www.truemobilecare.com/adminplus/shoplogin.php";

    ArrayList<String> CountryName;
    ArrayList<String> CountryId;

    // Creating Volley RequestQueue.
    RequestQueue requestQueue;
    // Creating Progress dialog.
    ProgressDialog progressDialog;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    EditText user;
    Button download,share;
    public static final String DEFAULT = "N/A";
    String id,message,name,shop,shopid,country,shopname;
    Spinner spinner;
    TextView brnz,brnzplus,silvr,silvrpls,gold,gldpls,pltnm,pltnmpls,dimnd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_details);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
      //  sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(StockDetailsActivity.this);
        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(StockDetailsActivity.this);

       // user=(EditText)findViewById(R.id.editText16);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
      //  shopid=sharedpreferences.getString("shopreg_id",DEFAULT);
       // Toast.makeText(this, shopid, Toast.LENGTH_SHORT).show();
       /* user.setText(name);
        user.setEnabled(false);*/

        brnz=(TextView)findViewById(R.id.textView5);
        brnzplus=(TextView)findViewById(R.id.textView7);
        silvr=(TextView)findViewById(R.id.textView9);
        silvrpls=(TextView)findViewById(R.id.textView12);
        gold=(TextView)findViewById(R.id.textView13);
        gldpls=(TextView)findViewById(R.id.textView15);
        pltnm=(TextView)findViewById(R.id.textView17);
        pltnmpls=(TextView)findViewById(R.id.textView19);
        dimnd=(TextView)findViewById(R.id.textView21);
        download=(Button)findViewById(R.id.button9);
download.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

    }
});
share=(Button)findViewById(R.id.button14);
share.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

    }
});

        CountryName=new ArrayList<>();
        spinner=(Spinner)findViewById(R.id.spinner7);
        spinner.setPrompt("Select Shop!");
        loadSpinnerData(URL);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                shop = spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();

                    ShopId();

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
    }

    private void ShopId() {
        // Showing progress dialog at user registration time.
       /* progressDialog.setMessage("Please Wait");
        progressDialog.show();*/
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrlId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        // Hiding the progress dialog after all task complete.
                        //  progressDialog.dismiss();
                        // Matching server responce message to our text.
                        if(!ServerResponse.equalsIgnoreCase("error")) {
                            try {
                                //getting the whole json object from the response
                                JSONObject obj = new JSONObject(ServerResponse);
                                id = obj.getString("shopreg_id");
                                shopname = obj.getString("shopreg_name");
                                Login();
                                // shopid=Integer.toString(id);
                              /*  Toast.makeText(StockDetailsActivity.this, shopname, Toast.LENGTH_SHORT).show();
                                Toast.makeText(StockDetailsActivity.this, id, Toast.LENGTH_SHORT).show();
                          */  } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            // If response matched then show the toast.
                            //  Toast.makeText(ShopLoginActivity.this, "Logged In Successfully", Toast.LENGTH_LONG).show();
                            // Finish the current Login activity.
                            //  finish();

                       /*     SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString("shopreg_id", id);
                            editor.putString("shopreg_name",shopname);
                            editor.apply();*/
                            //  editor.clear();
                            // Opening the user profile activity using intent.
                           /* Intent intent = new Intent(ActivationActivity.this, ActivationActivity.class);
                            // Sending User Email to another activitFiny using intent.
                            //  intent.putExtra("shopreg_name",name);
                            //  Toast.makeText(MainActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                            startActivity(intent);
                            finish();*/
                        }
                        else {
                            // Showing Echo Response Message Coming From Server.
                            Toast.makeText(StockDetailsActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Hiding the progress dialog after all task complete.
                        //  progressDialog.dismiss();
                        // Showing error message if something goes wrong.
                        Toast.makeText(StockDetailsActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("shopreg_name", shop);
                // params.put("shopreg_password", password);
                return params;
            }
        };
        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(StockDetailsActivity.this);
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }

    private void Login() {
        // Showing progress dialog at user registration time.
       /* progressDialog.setMessage("Please Wait");
        progressDialog.show();*/
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        // Hiding the progress dialog after all task complete.
                        //  progressDialog.dismiss();
                        // Matching server responce message to our text.
                        if(!ServerResponse.equalsIgnoreCase("error")) {
                            ArrayList<PlayerModelStock> playersModelArrayList = new ArrayList<>();

                            try {

                                JSONArray array=new JSONArray(ServerResponse);
                                for (int i=0; i < array.length(); i++) {
                                    PlayerModelStock playerModel = new PlayerModelStock();
                                    JSONObject product = array.getJSONObject(i);
                                    playerModel.setBronze(product.getString("Knightshield Bronze"));
                                    playerModel.setBronzeplus(product.getString("Knightshield BronzePlus"));
                                    playerModel.setSilver(product.getString("Knightshield Diamond"));
                                    playerModel.setSilverplus(product.getString("knightshield gold"));
                                    playerModel.setGold(product.getString("Knightshield GoldPlus"));
                                    playerModel.setGoldplus(product.getString("Knightshield Platinum"));
                                    playerModel.setPlatinum(product.getString("Knightshield PlatinumPlus"));
                                    playerModel.setPlatinumplus(product.getString("Knightshield Silver"));
                                    playerModel.setDiamond(product.getString("Knightshield SilverPlus"));
                                    playersModelArrayList.add(playerModel);
                                }

                                for (int j = 0; j < playersModelArrayList.size(); j++){
                                    // climid.setText(climid.getText()+ playersModelArrayList.get(j).getClaimid()+"\n");
                                 brnz.setText(brnz.getText()+ playersModelArrayList.get(j).getBronze()+"\n");
                                    brnzplus.setText(brnzplus.getText()+ playersModelArrayList.get(j).getBronzeplus()+"\n");
                                    dimnd.setText(dimnd.getText()+ playersModelArrayList.get(j).getDiamond()+"\n");
                                  /*  String knfbr=playersModelArrayList.get(j).getBronze();
                                    Toast.makeText(StockDetailsActivity.this, knfbr, Toast.LENGTH_SHORT).show();
                              */  }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            // Showing Echo Response Message Coming From Server.
                            Toast.makeText(StockDetailsActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Hiding the progress dialog after all task complete.
                        //  progressDialog.dismiss();
                        // Showing error message if something goes wrong.
                        Toast.makeText(StockDetailsActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("shop", id);
                // params.put("shopreg_password", password);
                return params;
            }
        };
        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(StockDetailsActivity.this);
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }

    private void loadSpinnerData(String url) {

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    //getting the whole json object from the response
                    JSONArray array=new JSONArray(response);
                    for (int i=0; i < array.length(); i++) {
                        JSONObject jsonObject1=array.getJSONObject(i);
                        country=jsonObject1.getString("shopreg_name");
                       //  shopid=jsonObject1.getString("shopreg_id");
                        CountryName.add(country);
                       // CountryName.add(shopid);
                    }

                    spinner.setAdapter(new ArrayAdapter<String>(StockDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, CountryName));
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
        Intent in=new Intent(StockDetailsActivity.this,AloginActivity.class);
        startActivity(in);
        finish();
    }
}
