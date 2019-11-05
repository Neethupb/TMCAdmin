package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ActivationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener {
    EditText name,emailid,contact,id,imei,mobprice,gadget,knightshop,mobshop,type,price,date;
    Button submit,frontcam,scan,backcam,bill;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
     String shop;
    Button ok,back;
    String HttpUrl = "http://www.truemobilecare.com/adminplus/shoplogin.php";
    String URL="http://www.truemobilecare.com/adminplus/shoplist.php";
    ArrayList<String> CountryName;
    // Creating Volley RequestQueue.
    RequestQueue requestQueue;
    // Creating Progress dialog.
    ProgressDialog progressDialog;
    String selectshop,ids,shopname;
    Spinner shoplist;

    private int GALLERY = 1, CAMERA = 2,GALLERYB = 3,CAMERAB = 4,CAMERABILL = 5,GALLERYBILL = 6;
    public static final String DEFAULT = "N/A";
    String    mail,nm,phone,idprf,imeinmbr,mbp,gdgt,invcdt,selectedItem,typ,prc,kntsp,mbsp,path,pathb,pathbill,shopid;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private int mYear, mMonth, mDay;
    String[] country = { "--Choose a id proof--", "Driving License", "Passport", "Adhar card", "Voters id","Others"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activation);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        final Spinner spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the country list
        final ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);


        CountryName=new ArrayList<>();
        shoplist=(Spinner)findViewById(R.id.spinner4);
        loadSpinnerData(URL);

        shoplist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                 selectshop =   shoplist.getItemAtPosition(shoplist.getSelectedItemPosition()).toString();
                knightshop.setText(selectshop);
                mobshop.setText(selectshop);
                Login();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });


        type=(EditText)findViewById(R.id.editText20);
        type.setEnabled(false);

        price=(EditText)findViewById(R.id.editText21);
        price.setEnabled(false);

        date=(EditText)findViewById(R.id.editText24);
        date.setOnClickListener(this);

        name=(EditText)findViewById(R.id.editText5);
        emailid=(EditText)findViewById(R.id.editText6);
        contact=(EditText)findViewById(R.id.editText7);
        id=(EditText)findViewById(R.id.editText8);
        imei=(EditText)findViewById(R.id.editText9);
        mobprice=(EditText)findViewById(R.id.editText10);
        gadget=(EditText)findViewById(R.id.editText11);

        final Activity activity = this;

        scan=(Button)findViewById(R.id.button13);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });


        knightshop=(EditText)findViewById(R.id.editText12);
        knightshop.setText(shop);
        knightshop.setEnabled(false);

        mobshop=(EditText)findViewById(R.id.editText13);
        mobshop.setText(shop);

        frontcam=(Button)findViewById(R.id.button5);
        backcam=(Button)findViewById(R.id.button6);
        submit=(Button)findViewById(R.id.button7);
        bill=(Button)findViewById(R.id.button15);
        bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialogbill();
            }
        });

        mobprice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                final String mbp = editable.toString();
                int mobprize = 0;
                try {
                    if (mbp != null) {
                        mobprize = Integer.parseInt(mbp);
                        if (mobprize <= 5999) {
                            type.setText("Knightshield Bronze");
                            price.setText("699");
                        } else if (mobprize >= 6000 && mobprize <= 9999) {
                            type.setText("Knightshield BronzePlus");
                            price.setText("999");
                        } else if (mobprize >= 10000 && mobprize <= 14999) {
                            type.setText("Knightshield Silver");
                            price.setText("1499");
                        } else if (mobprize >= 15000 && mobprize <= 19999) {
                            type.setText("Knightshield SilverPlus");
                            price.setText("1999");
                        } else if (mobprize >= 20000 && mobprize <= 29999) {
                            type.setText("Knightshield Gold");
                            price.setText("2499");
                        } else if (mobprize >= 30000 && mobprize <= 39999) {
                            type.setText("Knightshield GoldPlus");
                            price.setText("3299");
                        } else if (mobprize >= 40000 && mobprize <= 49999) {
                            type.setText("Knightshield Platinum");
                            price.setText("3799");
                        } else if (mobprize >= 50000 && mobprize <= 69999) {
                            type.setText("Knightshield PlatinumPlus");
                            price.setText("4499");
                        } else if (mobprize >= 70000 && mobprize <= 90000) {
                            type.setText("Knightshield Diamond");
                            price.setText("5499");
                        } else {
                            type.setText(" ");
                            price.setError(" ");
                        }
                    } else {
                        mobprice.setText("");
                        type.setText(" ");
                        price.setError(" ");
                    }
                } catch (NumberFormatException e) {
                    mobprize = 0;
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nm = name.getText().toString();
                mail = emailid.getText().toString();
                phone = contact.getText().toString();
                idprf = id.getText().toString();
                imeinmbr = imei.getText().toString();
                mbp = mobprice.getText().toString();
                gdgt = gadget.getText().toString();
                invcdt = date.getText().toString();
                selectedItem = spin.getSelectedItem().toString();
               // selectshop=shoplist.getSelectedItem().toString();
                typ=type.getText().toString();
                prc=price.getText().toString();
                kntsp=knightshop.getText().toString();
                mbsp=mobshop.getText().toString();
                validUserDetailsData();
             //   Toast.makeText(ActivationActivity.this, invcdt, Toast.LENGTH_SHORT).show();
            }
        });
        frontcam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog();
            }
        });
        backcam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialogb();
            }
        });
    }
    @Override
    protected void onResume() {
        PopulateFromPrefer();
        super.onResume();
    }

    private void PopulateFromPrefer() {
        name.setText(sharedpreferences.getString("names", ""));
        emailid.setText(sharedpreferences.getString("email", ""));
        contact.setText(sharedpreferences.getString("phone", ""));
        id.setText(sharedpreferences.getString("id", ""));
        imei.setText(sharedpreferences.getString("imei", ""));
        mobprice.setText(sharedpreferences.getString("mob", ""));
        gadget.setText(sharedpreferences.getString("gadget", ""));
        knightshop.setText(sharedpreferences.getString("shopreg_name", ""));
        mobshop.setText(sharedpreferences.getString("mobshop", shop));
        type.setText(sharedpreferences.getString("typ", ""));
        price.setText(sharedpreferences.getString("price", ""));
        date.setText(sharedpreferences.getString("invoice_date", ""));
    }

    /* @Override
     protected void onPause() {
         SaveToPrefer();
         super.onPause();
     }*/
    private void SaveToPrefer() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("names", nm);
        editor.putString("email", mail);
        editor.putString("phone", phone);
        editor.putString("proof",selectedItem);
        editor.putString("id", idprf);
        editor.putString("front",path );
        editor.putString("back",pathb );
        editor.putString("bill",pathbill );
        editor.putString("imei", imeinmbr);
        editor.putString("mob", mbp);
        editor.putString("typ", typ);
        editor.putString("price", prc);
        editor.putString("invoice_date", invcdt);
        editor.putString("gadget", gdgt);
        editor.putString("shopreg_name", selectshop);
        editor.putString("mobshop", mbsp);
        editor.putString("shopreg_id",ids);
        editor.apply();
    }

    private void loadSpinnerData(String url) {
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
                            String country=jsonObject1.getString("shopreg_name");
                            CountryName.add(country);
                        }

                        shoplist.setAdapter(new ArrayAdapter<String>(ActivationActivity.this, android.R.layout.simple_spinner_dropdown_item, CountryName));
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
                            try {
                                //getting the whole json object from the response
                                JSONObject obj = new JSONObject(ServerResponse);
                                ids = obj.getString("shopreg_id");
                                 shopname = obj.getString("shopreg_name");
                                // shopid=Integer.toString(id);
                               /* Toast.makeText(ActivationActivity.this, shopname, Toast.LENGTH_SHORT).show();
                                Toast.makeText(ActivationActivity.this, ids, Toast.LENGTH_SHORT).show();
                          */
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            // If response matched then show the toast.
                            //  Toast.makeText(ShopLoginActivity.this, "Logged In Successfully", Toast.LENGTH_LONG).show();
                            // Finish the current Login activity.
                            //  finish();

                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString("shopreg_id", ids);
                            editor.putString("shopreg_name",shopname);
                            editor.apply();
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
                            Toast.makeText(ActivationActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Hiding the progress dialog after all task complete.
                      //  progressDialog.dismiss();
                        // Showing error message if something goes wrong.
                        Toast.makeText(ActivationActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("shopreg_name", selectshop);
                // params.put("shopreg_password", password);
                return params;
            }
        };
        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(ActivationActivity.this);
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }

    private void showPictureDialogbill() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallarybill();
                               break;
                            case 1:
                                captureImagebill();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    private void captureImagebill() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERABILL);
    }

    private void choosePhotoFromGallarybill() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERYBILL);
    }

    private void showPictureDialogb() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallaryb();
                                break;
                            case 1:
                                captureImageb();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }
    private void captureImageb() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERAB);
    }
    private void choosePhotoFromGallaryb() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERYB);
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                captureImage();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }
    private void captureImage() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }
    private void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.d("MainActivity", "Scanned");
                imei.setText(result.getContents());
                //Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    // path = Calendar.getInstance().getTimeInMillis() + ".jpg";
                    // Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
                    // Toast.makeText(ActivationActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    uploadBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    // Toast.makeText(ActivationActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            // saveImage(thumbnail);
            // path = Calendar.getInstance().getTimeInMillis() + ".jpg";
            uploadBitmap(thumbnail);
            // Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
            // Toast.makeText(ActivationActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
        else   if (requestCode == GALLERYB) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    // path = Calendar.getInstance().getTimeInMillis() + ".jpg";
                    // Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
                    // Toast.makeText(ActivationActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    uploadBitmapb(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    // Toast.makeText(ActivationActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == CAMERAB) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            // saveImage(thumbnail);
            // path = Calendar.getInstance().getTimeInMillis() + ".jpg";
            uploadBitmapb(thumbnail);
            // Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
            // Toast.makeText(ActivationActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
        else   if (requestCode == GALLERYBILL) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    // path = Calendar.getInstance().getTimeInMillis() + ".jpg";
                    // Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
                    // Toast.makeText(ActivationActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    uploadBitmapbill(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    // Toast.makeText(ActivationActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == CAMERABILL) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            // saveImage(thumbnail);
            // path = Calendar.getInstance().getTimeInMillis() + ".jpg";
            uploadBitmapbill(thumbnail);
            // Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
            // Toast.makeText(ActivationActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }
    }

    public byte[] getFileDataFromDrawablebill(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    public byte[] getFileDataFromDrawableb(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    private void uploadBitmap(final Bitmap bitmap) {
        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, EndPoints.UPLOAD_URL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            path=obj.getString("message");
                            frontcam.setText(path);
                           // Toast.makeText(getApplicationContext(), path, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                       /* SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("message",path);
                        editor.clear();
                        editor.commit();*/
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("activation_idfront", new DataPart(imagename + ".jpg", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };
        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

    private void uploadBitmapb(final Bitmap bitmap) {
        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, EndPoints.UPLOAD_URLB, new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            pathb=obj.getString("messageb");
                            backcam.setText(pathb);
                          //  Toast.makeText(getApplicationContext(), pathb, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        /*SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("messageb",pathb);
                        editor.clear();
                        editor.commit();*/
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagenameb = System.currentTimeMillis();
                params.put("activation_idback", new DataPart(imagenameb + ".jpg", getFileDataFromDrawableb(bitmap)));
                return params;
            }
        };
        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }
    private void uploadBitmapbill(final Bitmap bitmap) {
        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, EndPoints.UPLOAD_URLBILL, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                try {
                    JSONObject obj = new JSONObject(new String(response.data));
                    pathbill=obj.getString("messagep");
                    bill.setText(pathbill);
                   // Toast.makeText(getApplicationContext(), pathbill, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                /*SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("messagep",pathbill);
                editor.clear();
                editor.commit();*/
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagenamebill = System.currentTimeMillis();
                params.put("activation_bill", new DataPart(imagenamebill + ".jpg", getFileDataFromDrawablebill(bitmap)));
                return params;
            }
        };
        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }
    private void validUserDetailsData() {
     //   if (TextUtils.isEmpty(nm) || TextUtils.isEmpty(mail)|| TextUtils.isEmpty(phone)|| TextUtils.isEmpty(idprf)|| TextUtils.isEmpty(imeinmbr)|| TextUtils.isEmpty(mbp)|| TextUtils.isEmpty(gdgt)|| TextUtils.isEmpty(invcdt)) {
            if (name.getText() == null || name.getText().toString().equalsIgnoreCase("")) {
                name.setError("Enter Name");
                //name.setText("");
            }
            else if (selectedItem.equals ("--Choose a id proof--")){
                Toast.makeText(this, "Select Proof", Toast.LENGTH_SHORT).show();
            }
              else if (emailid.getText() == null || emailid.getText().toString().equalsIgnoreCase("")) {
                emailid.setError("Enter Email id");
               // emailid.setText("");
            } else if (!(emailid.getText().toString().matches(emailPattern) && emailid.getText().toString().length() > 0)) {
                emailid.setError("Invalid Email Id");
            }
              else if (contact.getText() == null || contact.getText().toString().equalsIgnoreCase("")) {
                contact.setError("Enter Phone Number");
               // contact.setText("");
            }
             /* else  if (id.getText() == null || id.getText().toString().equalsIgnoreCase("")) {
                id.setError("Select id proof");
                id.setText("");
            }*/
              else if (imei.getText() == null || imei.getText().toString().equalsIgnoreCase("")) {
                imei.setError("Enter imei Number");
               // imei.setText("");
            }
              else if ((imei.getText().toString()).length() != 15) {
                imei.setError("Invalid IMEI Number");
            }
              else if (mobprice.getText() == null || mobprice.getText().toString().equalsIgnoreCase("")) {
                mobprice.setError("Enter Mobile Price");
               // mobprice.setText("");
            }
            else if (type.getText() == null || type.getText().toString().equalsIgnoreCase("")) {
                type.setError("Enter product ");
               // type.setText("");
            }
            else if (price.getText() == null || price.getText().toString().equalsIgnoreCase("")) {
                price.setError("Enter product Price");
               // price.setText("");
            }
              else if (gadget.getText() == null || gadget.getText().toString().equalsIgnoreCase("")) {
                gadget.setError("Enter Gadget Name and Model");
               // gadget.setText("");
            }
              else if (date.getText() == null || date.getText().toString().equalsIgnoreCase("")) {
                date.setError("Select Invoice Date");
               // date.setText("");
            }
              else {
                  SaveToPrefer();
              /*  SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("names", nm);
                editor.putString("email", mail);
                editor.putString("phone", phone);
                editor.putString("proof",selectedItem);
                editor.putString("id", idprf);
                editor.putString("front",path );
                editor.putString("back",pathb );
                editor.putString("bill",pathbill );
                editor.putString("imei", imeinmbr);
                editor.putString("mob", mbp);
                editor.putString("typ", typ);
                editor.putString("price", prc);
                editor.putString("invoice_date", invcdt);
                editor.putString("gadget", gdgt);
                editor.putString("shopreg_name", selectshop);
                editor.putString("mobshop", mbsp);
                editor.putString("shopreg_id",ids);
                editor.apply();*/
               // Toast.makeText(this, shopid, Toast.LENGTH_SHORT).show();
                Intent in = new Intent(ActivationActivity.this, KeyActivity.class);
                startActivity(in);
                finish();
            }
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
       // Toast.makeText(getApplipcationContext(),country[i] , Toast.LENGTH_LONG).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    @Override
    public void onClick(View view) {
        if (view == date) {
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }
     @Override
    public void onBackPressed()
    {
         /* SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.putString("shopreg_name", shop);
            editor.putString("shopreg_id", shopid);
            editor.apply();
            Intent in = new Intent(ActivationActivity.this, HomeActivity.class);
            startActivity(in);
            finish();*/
        Intent in=new Intent(ActivationActivity.this,AloginActivity.class);
        startActivity(in);
        finish();


    }
}
