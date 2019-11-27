package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
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
import com.example.myapplication.Constants;
import com.example.myapplication.Models.PlayerModelStock;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StockDetailsActivity extends AppCompatActivity {


    ArrayList<String> CountryName;
    ArrayList<String> CountryId;

    // Creating Volley RequestQueue.
    RequestQueue requestQueue;
    // Creating Progress dialog.
    ProgressDialog progressDialog;
    SharedPreferences sharedpreferences;
    EditText user;
    Button download,share;
    public static final String DEFAULT = "N/A";
    String id,message,name,shop,shopid,country,shopname;
    Spinner spinner;
    TextView qbr,qbrpl,qdi,qgo,qgopl,qpl,qplps,qsi,qsipl;
    String sbr,sbrpl,sdi,sgo,sgopl,spl,splps,ssi,ssipl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_stock_details);

      //  sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(StockDetailsActivity.this);
        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(StockDetailsActivity.this);

       // user=(EditText)findViewById(R.id.editText16);
        sharedpreferences = getSharedPreferences(Constants.MyPREFERENCES, Context.MODE_PRIVATE);
        qbr=findViewById(R.id.qbr);
        qbrpl=findViewById(R.id.qbrpl);
        qdi=findViewById(R.id.qdi);
        qgo=findViewById(R.id.qgo);
        qgopl=findViewById(R.id.qgopl);
        qpl=findViewById(R.id.qpl);
        qplps=findViewById(R.id.qplps);
        qsi=findViewById(R.id.qsi);
        qsipl=findViewById(R.id.qsipl);

      //  download=(Button)findViewById(R.id.button9);
 /*   download.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

    }
});*/
share=(Button)findViewById(R.id.button14);
share.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        shareIt();
    }
});

        CountryName=new ArrayList<>();
        spinner=(Spinner)findViewById(R.id.spinner7);
        spinner.setPrompt("Select Shop!");
        loadSpinnerData(Constants.URLshoplist);
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

    private void shareIt() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "StockDetails");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Bronze "+sbr+"\n"+"BronzePlus "+sbrpl+"\n"+"Silver "+
                ssi+"\n"+"SilverPlus "+ssipl+"\n"+"Gold "+sgo+"\n"+"GoldPlus " +sgopl+"\n"+
                "Platinum "+spl+"\n"+"PlatinumPlus "+splps+"\n"+"Diamond "+sdi+"\n");
        startActivity(Intent.createChooser(sharingIntent, "Share via"));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
    private void ShopId() {
        // Showing progress dialog at user registration time.
       /* progressDialog.setMessage("Please Wait");
        progressDialog.show();*/
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.HttpUrlshoplogin,
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
                                qbr.setText("");
                                qbrpl.setText("");
                                qdi.setText("");
                                qplps.setText("");
                                qpl.setText("");
                                qgopl.setText("");
                                qgo.setText("");
                                qsipl.setText("");
                                qsi.setText("");

                                Login();
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.HttpUrlstock,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        // Hiding the progress dialog after all task complete.
                        //  progressDialog.dismiss();
                        // Matching server responce message to our text.
                        if (!ServerResponse.equalsIgnoreCase("error")) {
                            ArrayList<PlayerModelStock> playersModelArrayList = new ArrayList<>();

                            try {

                                JSONArray array = new JSONArray(ServerResponse);
                                for (int i = 0; i < array.length(); i++) {
                                    PlayerModelStock playerModel = new PlayerModelStock();
                                    JSONObject product = array.getJSONObject(i);
                                    playerModel.setType(product.getString("type"));
                                    playerModel.setStock(product.getString("stock"));
                                    playersModelArrayList.add(playerModel);
                                    String type=playerModel.getType();
                                    // Toast.makeText(StockDetailsActivity.this, type, Toast.LENGTH_SHORT).show();
                                    switch (type) {
                                        case "Knightshield Bronze":
                                            qbr.setText(qbr.getText() + playersModelArrayList.get(i).getStock());
                                            sbr=qbr.getText().toString();
                                            break;
                                        case "Knightshield BronzePlus":
                                            qbrpl.setText(qbrpl.getText() + playersModelArrayList.get(i).getStock());
                                            sbrpl=qbrpl.getText().toString();
                                            break;
                                        case "Knightshield Silver":
                                            qsi.setText(qsi.getText() + playersModelArrayList.get(i).getStock());
                                            ssi=qsi.getText().toString();
                                            break;
                                        case "Knightshield SilverPlus":
                                            qsipl.setText(qsipl.getText() + playersModelArrayList.get(i).getStock());
                                            ssipl=qsipl.getText().toString();
                                            break;
                                        case "Knightshield Gold":
                                            qgo.setText(qgo.getText() + playersModelArrayList.get(i).getStock());
                                            sgo=qgo.getText().toString();
                                            break;
                                        case "Knightshield GoldPlus":
                                            qgopl.setText(qgopl.getText() + playersModelArrayList.get(i).getStock());
                                            sgopl=qgopl.getText().toString();
                                            break;
                                        case "Knightshield Platinum":
                                            qpl.setText(qpl.getText() + playersModelArrayList.get(i).getStock());
                                            spl=qpl.getText().toString();
                                            break;
                                        case "Knightshield PlatinumPlus":
                                            qplps.setText(qplps.getText() + playersModelArrayList.get(i).getStock());
                                            splps=qplps.getText().toString();
                                            break;
                                        case "Knightshield Diamond":
                                            qdi.setText(qdi.getText() + playersModelArrayList.get(i).getStock());
                                            sdi=qdi.getText().toString();
                                            break;

                                    }
                                   /* SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString("shopreg_id", shopid);
                                    editor.putString("shopreg_name", name);
                                    editor.apply();*/
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
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
        Intent in=new Intent(StockDetailsActivity.this, AshopActivity.class);
        startActivity(in);
        finish();
    }
}
