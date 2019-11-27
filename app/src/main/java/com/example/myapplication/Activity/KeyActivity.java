package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
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

import java.util.HashMap;
import java.util.Map;

public class KeyActivity extends AppCompatActivity {
    EditText key;
    // Creating Volley RequestQueue.
    RequestQueue requestQueue;
    // Creating Progress dialog.
    ProgressDialog progressDialog;
    // Storing server url into String variable.
    SharedPreferences sharedpreferences;
    public static final String DEFAULT = "N/A";
    Intent intent;
    String mobf,mobb,shopid,actkey,name,email,phone,proof,id,front,imei,mob,typ,price,invoice_date,gadget,shop,mobshop,back,bill;
    Button activate,backbtn;
    TextView txtname,txtemail,txtphone,txtid,txtcardnumber,txtimei,txtmob,txttyp,txtprice,txtdate,txtgadget,txtshop,txtmobshop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_key);
        key=(EditText)findViewById(R.id.editText14);
        activate=(Button)findViewById(R.id.button2);
        backbtn=(Button)findViewById(R.id.button16);
        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(KeyActivity.this);
        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(KeyActivity.this);
        sharedpreferences = getSharedPreferences(Constants.MyPREFERENCES, Context.MODE_PRIVATE);
        name = sharedpreferences.getString("customer",DEFAULT);
        email = sharedpreferences.getString("email", DEFAULT);
        phone = sharedpreferences.getString("phone", DEFAULT);
        proof = sharedpreferences.getString("proof", DEFAULT);
        id = sharedpreferences.getString("id", DEFAULT);
        front = sharedpreferences.getString("front", DEFAULT);
        back=sharedpreferences.getString("back",DEFAULT);
        bill=sharedpreferences.getString("bill",DEFAULT);
        mobf = sharedpreferences.getString("mobf", " ");
        mobb = sharedpreferences.getString("mobb", " ");
        imei = sharedpreferences.getString("imei", DEFAULT);
        mob = sharedpreferences.getString("mob", DEFAULT);
        typ = sharedpreferences.getString("typ", DEFAULT);
        price = sharedpreferences.getString("price", DEFAULT);
        invoice_date = sharedpreferences.getString("invoice_date", DEFAULT);
        gadget = sharedpreferences.getString("gadget", DEFAULT);
        shop = sharedpreferences.getString("shopreg_name", DEFAULT);
        mobshop = sharedpreferences.getString("mobshop", DEFAULT);
        shopid=sharedpreferences.getString("shopreg_id",DEFAULT);
        Toast.makeText(this, shopid, Toast.LENGTH_SHORT).show();
        Toast.makeText(KeyActivity.this, ""+name+""+email+""+phone
                +""+proof+""+id+""+front+""+back+""+imei+""+mob+""+typ+""+price
                +""+invoice_date+""+gadget+""+shop+""+mobshop+""+shopid+""+bill, Toast.LENGTH_SHORT).show();
        txtname=(TextView)findViewById(R.id.name);
        txtname.setText(name);
        txtemail=(TextView)findViewById(R.id.email);
        txtemail.setText(email);
        txtphone=(TextView)findViewById(R.id.contact);
        txtphone.setText(phone);
        txtid=(TextView)findViewById(R.id.id);
        txtid.setText(proof);
        txtcardnumber=(TextView)findViewById(R.id.cardnumber);
        txtcardnumber.setText(id);
        txtimei=(TextView)findViewById(R.id.imei);
        txtimei.setText(imei);
        txtmob=(TextView)findViewById(R.id.mob);
        txtmob.setText(mob);
        txttyp=(TextView)findViewById(R.id.type);
        txttyp.setText(typ);
        txtprice=(TextView)findViewById(R.id.price);
        txtprice.setText(price);
        txtdate=(TextView)findViewById(R.id.date);
        txtdate.setText(invoice_date);
        txtgadget=(TextView)findViewById(R.id.gadget);
        txtgadget.setText(gadget);
        txtshop=(TextView)findViewById(R.id.knightshop);
        txtshop.setText(shop);
        txtmobshop=(TextView)findViewById(R.id.mobshop);
        txtmobshop.setText(mobshop);

backbtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
onBackPressed();
    }
});

        activate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actkey=key.getText().toString();
                if (key.getText()== null || key.getText().toString().equalsIgnoreCase("")){
                    key.setText("");
                    key.setError("Please Enter Key");
                }else if(mobf.equalsIgnoreCase("") && mobb.equalsIgnoreCase("")){
                    key.setError("");
                    key.setError("Upload Mobile Front & Back Must");
                }
                else {
                   // Toast.makeText(KeyActivity.this, shopid, Toast.LENGTH_SHORT).show();
                   KeyActivation();
                }
            }
        });
    }
    private void KeyActivation() {
        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.HttpUrlregistration,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        // Matching server responce message to our text.
                        if (ServerResponse.contains("success")) {
                            final AlertDialog alertDialog = new AlertDialog.Builder(KeyActivity.this).create();
                            // Setting Dialog Title
                            alertDialog.setTitle("Hello " + name);
                            // Setting Dialog Message
                            alertDialog.setMessage("Congatulations! Your " + typ + " Security Activated Successfully on Your " + gadget);
                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    Sendmail();
                                    // SendMessage();
                                }
                            });
                            alertDialog.setOnKeyListener(new Dialog.OnKeyListener() {

                                @Override
                                public boolean onKey(DialogInterface arg0, int keyCode,
                                                     KeyEvent event) {
                                    // TODO Auto-generated method stub
                                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                                        // onBackPressed();
                                        SharedPreferences.Editor editor = sharedpreferences.edit();
                                        editor.clear();
                                        editor.putString("shopreg_name", shop);
                                        editor.putString("shopreg_id", shopid);
                                        editor.apply();
                                        Intent intent = new Intent(KeyActivity.this, ActivationActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    return true;
                                }
                            });
                            // Showing Alert Message
                            alertDialog.show();
                        } else if (ServerResponse.contains("Key already used")) {
                            AlertDialog alertDialog = new AlertDialog.Builder(KeyActivity.this).create();
                            // Setting Dialog Title
                            alertDialog.setTitle("Error");
                            // Setting Dialog Message
                            alertDialog.setMessage("Key Already Used");
                            // Setting Icon to Dialog
                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(KeyActivity.this, KeyActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                            // Showing Alert Message
                            alertDialog.show();
                            // Showing Echo Response Message Coming From Server.
                            //   Toast.makeText(KeyActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                        } else if (ServerResponse.contains("IMEI Number already registered")) {
                            AlertDialog alertDialog = new AlertDialog.Builder(KeyActivity.this).create();
                            // Setting Dialog Title
                            alertDialog.setTitle("Error");
                            // Setting Dialog Message
                            alertDialog.setMessage("IMEI Number already registered");
                            // Setting Icon to Dialog
                            // alertDialog.setIcon(R.drawable.tick);
                            // Setting OK Button
                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.clear();
                                    editor.putString("shopreg_name", shop);
                                    editor.putString("shopreg_id", shopid);
                                    editor.apply();
                                    Intent intent = new Intent(KeyActivity.this, AloginActivity.class);
                                    startActivity(intent);
                                    finish();
                                    // Sendmail();
                                }
                            });
                            // Showing Alert Message
                            alertDialog.show();
                        } else if (ServerResponse.contains("key error")) {
                            AlertDialog alertDialog = new AlertDialog.Builder(KeyActivity.this).create();
                            // Setting Dialog Title
                            alertDialog.setTitle("Error");
                            // Setting Dialog Message
                            alertDialog.setMessage("key error");
                            // Setting Icon to Dialog
                            // alertDialog.setIcon(R.drawable.tick);
                            // Setting OK Button
                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(KeyActivity.this, KeyActivity.class);
                                    startActivity(intent);
                                    finish();
                                    // Sendmail();
                                }
                            });
                            // Showing Alert Message
                            alertDialog.show();
                        } else {
                            AlertDialog alertDialog = new AlertDialog.Builder(KeyActivity.this).create();
                            // Setting Dialog Title
                            alertDialog.setTitle("Error");
                            // Setting Dialog Message
                            alertDialog.setMessage("Failed to Upload");
                            // Setting Icon to Dialog
                            // alertDialog.setIcon(R.drawable.tick);
                            // Setting OK Button
                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(KeyActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                    // Sendmail();
                                }
                            });
                            // Showing Alert Message
                            alertDialog.show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        // Showing error message if something goes wrong.
                        Toast.makeText(KeyActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("activation_name", name);
                params.put("activation_mailid", email);
                params.put("activation_mobile", phone);
                params.put("activation_proof", proof);
                params.put("activation_idnumber", id);
                params.put("activation_idfront", front);
                params.put("activation_idback", back);
                params.put("activation_bill", bill);
                params.put("activation_productimage1", mobf);
                params.put("activation_productimage2", mobb);
                params.put("activation_imei", imei);
                params.put("activation_mobileprice", mob);
                params.put("activation_knightshieldtype", typ);
                params.put("activation_knightshieldprice", price);
                params.put("activation_invoicedate", invoice_date);
                params.put("activation_gadgetname", gadget);
                params.put("activation_shopid", shopid);
                params.put("activation_knightshieldpurchaseshop", shop);
                params.put("activation_mobileshop", mobshop);
                params.put("activation_key", actkey);
                // params.put("image_idfront", front);
                return params;
            }
        };
        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(KeyActivity.this);
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }
    private void SendMessage() {
        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.HttpUrlmessage,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        // Matching server responce message to our text.
                        if (ServerResponse.contains("success")) {
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.clear();
                            editor.putString("shopreg_name", shop);
                            editor.putString("shopreg_id", shopid);
                            editor.apply();
                            Intent intent = new Intent(KeyActivity.this, ActivationActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // Showing Echo Response Message Coming From Server.
                            Toast.makeText(KeyActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        // Showing error message if something goes wrong.
                        Toast.makeText(KeyActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                params.put("activation_name", name);
                params.put("activation_mobile", phone);
                params.put("activation_imei", imei);
                params.put("activation_gadgetname", gadget);
                return params;
            }
        };
        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(KeyActivity.this);
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }
    private void Sendmail() {
        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.HttpUrlMail,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        // Matching server responce message to our text.
                        if (ServerResponse.contains("success")) {
                           /* SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.clear();
                            editor.putString("shopreg_name", shop);
                            editor.putString("shopreg_id", shopid);
                            editor.apply();
                            Intent intent = new Intent(KeyActivity.this, ActivationActivity.class);
                            startActivity(intent);
                            finish();*/
                            SendMessage();
                        } else {
                            // Showing Echo Response Message Coming From Server.
                            Toast.makeText(KeyActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        // Showing error message if something goes wrong.
                        Toast.makeText(KeyActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                params.put("activation_name", name);
                params.put("activation_mailid", email);
                params.put("activation_imei", imei);
                params.put("activation_gadgetname", gadget);
                return params;
            }
        };
        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(KeyActivity.this);
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }
    @Override
    public void onBackPressed() {
        Intent in = new Intent(KeyActivity.this, ActivationActivity.class);
        startActivity(in);
        finish();
    }
}
