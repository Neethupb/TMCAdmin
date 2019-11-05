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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PurchaseOrderActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String URL="http://www.truemobilecare.com/adminplus/shoplist.php";
    String HttpUrl = "http://www.truemobilecare.com/adminplus/stock.php";
    ArrayList<String> CountryName;
    // Creating Volley RequestQueue.
    RequestQueue requestQueue;
    // Creating Progress dialog.
    ProgressDialog progressDialog;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    Spinner spinner;
    LinearLayout linear;
    Button submit,order;
    int dp,dppc;
    String product,quantity,dpprice,sprdct,squnty,sprc,shop;
    EditText qutity;
   // int stringIdList[] = {R.string.text1, R.string.text2, R.string.text3, R.string.text4};
    int stringListCounter = 0;
    TextView pbr,qbr,dpbr;
    String[] country = { "--select product--", "Knightshield Bronze ", "Knightshield BronzePlus", "Knightshield Silver", "Knightshield SilverPlus","Knightshield Gold","Knightshield GoldPlus","Knightshield Platinum","Knightshield PlatinumPlus","Knightshield Diamond"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_order);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(PurchaseOrderActivity.this);
        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(PurchaseOrderActivity.this);

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        final Spinner spin = (Spinner) findViewById(R.id.spinner3);
        spin.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the country list
        final ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        qutity=(EditText)findViewById(R.id.quantity);
        linear=(LinearLayout)findViewById(R.id.linear);
        linear.setVisibility(View.GONE);
        pbr=(TextView)findViewById(R.id.pbr);
        qbr=(TextView)findViewById(R.id.qbr);
        dpbr=(TextView)findViewById(R.id.dpbr);
        submit=(Button)findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                linear.setVisibility(View.VISIBLE);
                product=spin.getSelectedItem().toString();
                quantity=qutity.getText().toString();
                dp=Integer.parseInt(quantity);
                switch (product) {
                    case "Knightshield Bronze ":
                        dppc = (dp * 599);
                        dpprice = Integer.toString(dppc);
                        break;
                    case "Knightshield BronzePlus":
                        dppc = (dp * 799);
                        dpprice = Integer.toString(dppc);
                        break;
                    case "Knightshield Silver":
                        dppc = (dp * 1199);
                        dpprice = Integer.toString(dppc);
                        break;
                    case "Knightshield SilverPlus":
                        dppc = (dp * 1599);
                        dpprice = Integer.toString(dppc);
                        break;
                    case "Knightshield Gold":
                        dppc = (dp * 1999);
                        dpprice = Integer.toString(dppc);
                        break;
                    case "Knightshield GoldPlus":
                        dppc = (dp * 2639);
                        dpprice = Integer.toString(dppc);
                        break;
                    case "Knightshield Platinum":
                        dppc = (dp * 3039);
                        dpprice = Integer.toString(dppc);
                        break;
                    case "Knightshield PlatinumPlus":
                        dppc = (dp * 3599);
                        dpprice = Integer.toString(dppc);
                        break;
                    case "Knightshield Diamond":
                        dppc = (dp * 4399);
                        dpprice = Integer.toString(dppc);
                        break;
                  /*  case "--select product--":
                        Toast.makeText(PurchaseOrderActivity.this, "Select Product", Toast.LENGTH_SHORT).show();
                        break;*/
                }
               /* sprdct=pbr.getText().toString();
                squnty=qbr.getText().toString();
                sprc=dpbr.getText().toString();*/
                PurchaseOrder();
                ArrayList<PlayerModelPurchaseOrder> playersModelArrayList = new ArrayList<>();

                for (int i=0; i < 1; i++) {
                    PlayerModelPurchaseOrder PlayerModelPurchaseOrder = new PlayerModelPurchaseOrder();
                    PlayerModelPurchaseOrder.setProduct(product);
                    PlayerModelPurchaseOrder.setQuantity(quantity);
                    PlayerModelPurchaseOrder.setdpPrice(dpprice);
                    playersModelArrayList.add(PlayerModelPurchaseOrder);
                }
                for (int j = 0; j < playersModelArrayList.size(); j++){
                    pbr.setText(pbr.getText()+ playersModelArrayList.get(j).getProduct()+"\n");
                    qbr.setText(qbr.getText()+ playersModelArrayList.get(j).getQuantity()+"\n");
                    dpbr.setText(dpbr.getText()+ playersModelArrayList.get(j).getdpPrice()+"\n");
                    qutity.setText("");
                }

            }
        });
        order=(Button)findViewById(R.id.order);
        order.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
       /* sprdct=pbr.getText().toString();
        squnty=qbr.getText().toString();
        sprc=dpbr.getText().toString();*/
       // PurchaseOrder();
        Intent intent = new Intent(PurchaseOrderActivity.this, AshopActivity.class);
        // Sending User Email to another activitFiny using intent.
        //  intent.putExtra("shopreg_name",name);
        //  Toast.makeText(MainActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
        startActivity(intent);
        finish();
    }
});
        CountryName=new ArrayList<>();
        spinner=(Spinner)findViewById(R.id.spinner4);
        spinner.setPrompt("Select Shop!");
        loadSpinnerData(URL);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                 shop=   spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
                //  Toast.makeText(getApplicationContext(),country,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
    }

    private void PurchaseOrder() {
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
                       /* if (!ServerResponse.equalsIgnoreCase("error")) {

                        }
                        else {
                            Toast.makeText(PurchaseOrderActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                        }*/
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        // Showing error message if something goes wrong.
                        Toast.makeText(PurchaseOrderActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("purchase_shop", shop);
                params.put("purchase_item", product);
                params.put("purchase_volume", quantity);
                params.put("purchase_dp", dpprice);
                return params;
            }
        };
        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(PurchaseOrderActivity.this);
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
                            String country=jsonObject1.getString("shopreg_name");
                            CountryName.add(country);
                        }

                        spinner.setAdapter(new ArrayAdapter<String>(PurchaseOrderActivity.this, android.R.layout.simple_spinner_dropdown_item, CountryName));
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
        Intent in=new Intent(PurchaseOrderActivity.this,AshopActivity.class);
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