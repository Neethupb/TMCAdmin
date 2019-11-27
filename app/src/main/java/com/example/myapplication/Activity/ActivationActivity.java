package com.example.myapplication.Activity;

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
import com.example.myapplication.Constants;
import com.example.myapplication.EndPoints;
import com.example.myapplication.R;
import com.example.myapplication.VolleyMultipartRequest;
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
    Button submit, frontcam, scan, backcam, bill,mobfront,mobback;
    SharedPreferences sharedpreferences;
    String shop;
    Bundle b;
    Spinner spin;
    int a=0;
    ArrayList<String> CountryName;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    String selectshop;
    String ids;
    String shopname;
    Spinner shoplist;
    private static int ageGroup = 0,shopitem = 0;
    private int GALLERY = 1, CAMERA = 2, GALLERYB = 3, CAMERAB = 4, CAMERABILL = 5, GALLERYBILL = 6,MOBILEF = 7,MOBILEB=8;
    public static final String DEFAULT = "N/A";
    String  mobf,mobb, asm, mail,nm,phone,idprf,imeinmbr,mbp,gdgt,invcdt,selectedItem,typ,prc,kntsp,mbsp,path,pathb,pathbill,shopid;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private int mYear, mMonth, mDay;
    String[] country = { "--Choose a id proof--", "Driving License", "Passport", "Adhar card", "Voters id","Others"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_activation);
        sharedpreferences = getSharedPreferences(Constants.MyPREFERENCES, Context.MODE_PRIVATE);
        sharedpreferences = getSharedPreferences(Constants.MyPREFERENCES, Context.MODE_PRIVATE);
        asm = sharedpreferences.getString("name",DEFAULT);
        selectshop = sharedpreferences.getString("shopreg_name",DEFAULT);
        ids = sharedpreferences.getString("shopreg_id",DEFAULT);

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the country list
        final ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

      /*  CountryName=new ArrayList<>();
        shoplist=(Spinner)findViewById(R.id.spinner4);
        loadSpinnerData(Constants.URLshoplist);
        shoplist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                shopitem =i;
                 selectshop =   shoplist.getItemAtPosition(shoplist.getSelectedItemPosition()).toString();
                Login();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });*/

        type=(EditText)findViewById(R.id.editText20);
        price=(EditText)findViewById(R.id.editText21);
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
        mobfront=findViewById(R.id.button9);
        mobback=findViewById(R.id.button14);
        mobfront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImageMobileFront();
            }
        });
        mobback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImageMobileBack();
            }
        });
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveToPrefer();
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
        knightshop.setText(selectshop);
        mobshop=(EditText)findViewById(R.id.editText13);
        mobshop.setText(selectshop);
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
                        }
                        else {
                            mobprice.setError("Invalid Amount");
                            type.setText(" ");
                            price.setText(" ");
                        }
                    }
                    else{
                        mobprice.setError("");
                        type.setText(" ");
                        price.setText(" ");
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
                typ=type.getText().toString();
                prc=price.getText().toString();
                kntsp=knightshop.getText().toString();
                mbsp=mobshop.getText().toString();
                validUserDetailsData();
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
    @Override
    protected void onPause() {
        SaveToPrefer();
        super.onPause();
    }
    private void PopulateFromPrefer() {
        name.setText(sharedpreferences.getString("customer", ""));
        emailid.setText(sharedpreferences.getString("email", ""));
        contact.setText(sharedpreferences.getString("phone", ""));
        id.setText(sharedpreferences.getString("id", ""));
        imei.setText(sharedpreferences.getString("imei", ""));
        mobprice.setText(sharedpreferences.getString("mob", ""));
        gadget.setText(sharedpreferences.getString("gadget", ""));
        knightshop.setText(sharedpreferences.getString("shopreg_name", ""));
        mobshop.setText(sharedpreferences.getString("mobshop", selectshop));
        type.setText(sharedpreferences.getString("typ", ""));
        price.setText(sharedpreferences.getString("price", ""));
        date.setText(sharedpreferences.getString("invoice_date", ""));
        path=sharedpreferences.getString("front","");
        pathb=sharedpreferences.getString("back","");
        pathbill=sharedpreferences.getString("bill","");
        ids=sharedpreferences.getString("shopreg_id","");
        mobf=sharedpreferences.getString("mobf","");
        mobb=sharedpreferences.getString("mobb","");
    }
    private void SaveToPrefer() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("customer", name.getText().toString());
        editor.putString("email", emailid.getText().toString());
        editor.putString("phone", contact.getText().toString());
        editor.putString("proof", spin.getSelectedItem().toString());
        editor.putString("id", id.getText().toString());
        editor.putString("front", path);
        editor.putString("back", pathb);
        editor.putString("bill", pathbill);
        editor.putString("mobf", mobf);
        editor.putString("mobb", mobb);
        editor.putString("imei", imei.getText().toString());
        editor.putString("mob", mobprice.getText().toString());
        editor.putString("typ", type.getText().toString());
        editor.putString("price", price.getText().toString());
        editor.putString("invoice_date", date.getText().toString());
        editor.putString("gadget", gadget.getText().toString());
        editor.putString("shopreg_name", knightshop.getText().toString());
        editor.putString("mobshop", mobshop.getText().toString());
        editor.putString("shopreg_id", ids);
        editor.apply();
    }
  /*  private void loadSpinnerData(String url)
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
                          //  Toast.makeText(ActivationActivity.this, country, Toast.LENGTH_SHORT).show();
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

    private void Login() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.HttpUrlshoplogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        if(!ServerResponse.equalsIgnoreCase("error")) {
                            try {
                                //getting the whole json object from the response
                                JSONObject obj = new JSONObject(ServerResponse);
                                ids = obj.getString("shopreg_id");
                                 shopname = obj.getString("shopreg_name");
                                 knightshop.setText(shopname);
                                 mobshop.setText(shopname);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString("shopreg_id", ids);
                            editor.putString("shopreg_name",shopname);
                            editor.apply();
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
    }*/
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
        SaveToPrefer();
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERABILL);
    }
    private void choosePhotoFromGallarybill() {
        SaveToPrefer();
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
        SaveToPrefer();
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERAB);
    }
    private void choosePhotoFromGallaryb() {
        SaveToPrefer();
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
        SaveToPrefer();
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }
    private void captureImageMobileFront() {
        SaveToPrefer();
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, MOBILEF);
    }

    private void captureImageMobileBack() {
        SaveToPrefer();
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, MOBILEB);
    }
    private void choosePhotoFromGallary() {
        SaveToPrefer();
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Log.d("Check Details", "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.d("Check Details", "Scanned");
                imei.setText(result.getContents());
                //  Toast.makeText(this, ""+result, Toast.LENGTH_SHORT).show();
            }
        }
        else {
            // This is important, otherwise the result will not be passed to the fragment
            if (resultCode == RESULT_CANCELED) {
                return;
            }
            if (requestCode == GALLERY) {
                if (data != null) {
                    Uri contentURI = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                        uploadBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (requestCode == CAMERA) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                uploadBitmap(thumbnail);
            }
            else if (requestCode == MOBILEF) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                uploadBitmapmobilef(bitmap);
            }
            else if (requestCode == MOBILEB) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                uploadBitmapmobileb(bitmap);
            }
            else if (requestCode == GALLERYB) {
                if (data != null) {
                    Uri contentURI = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                        uploadBitmapb(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (requestCode == CAMERAB) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                uploadBitmapb(thumbnail);
            } else if (requestCode == GALLERYBILL) {
                if (data != null) {
                    Uri contentURI = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                        uploadBitmapbill(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (requestCode == CAMERABILL) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                uploadBitmapbill(thumbnail);
            }
        }
    }

    private void uploadBitmapmobileb(final Bitmap bitmap) {
        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, EndPoints.UPLOAD_URLMOBB,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            mobb = obj.getString("message");
                            mobback.setText(mobb);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
                params.put("activation_productimage2", new DataPart(imagename + ".jpg", getFileDataFromDrawablemobileb(bitmap)));
                return params;
            }
        };
        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

    private void uploadBitmapmobilef(final Bitmap bitmap) {
        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, EndPoints.UPLOAD_URLMOBF,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            mobf = obj.getString("message");
                            mobfront.setText(mobf);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
                params.put("activation_productimage1", new DataPart(imagename + ".jpg", getFileDataFromDrawablemobilef(bitmap)));
                return params;
            }
        };
        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }
    public byte[] getFileDataFromDrawablemobilef(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    public byte[] getFileDataFromDrawablemobileb(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
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
            if (name.getText() == null || name.getText().toString().equalsIgnoreCase("")) {
                name.setError("Enter Name");
            }
            else if (selectedItem.equals ("--Choose a id proof--")){
                Toast.makeText(this, "Select Proof", Toast.LENGTH_SHORT).show();
            }
              else if (emailid.getText() == null || emailid.getText().toString().equalsIgnoreCase("")) {
                emailid.setError("Enter Email id");
            } else if (!(emailid.getText().toString().matches(emailPattern) && emailid.getText().toString().length() > 0)) {
                emailid.setError("Invalid Email Id");
            }
              else if (contact.getText() == null || contact.getText().toString().equalsIgnoreCase("")) {
                contact.setError("Enter Phone Number");
            }
            else if ((contact.getText().toString()).length() > 10) {
                contact.setError("Invalid phone Number");
            }
              else if (imei.getText() == null || imei.getText().toString().equalsIgnoreCase("")) {
                imei.setError("Enter imei Number");
            }
              else if ((imei.getText().toString()).length() != 15) {
                imei.setError("Invalid IMEI Number");
            }
              else if (mobprice.getText() == null || mobprice.getText().toString().equalsIgnoreCase("")) {
                mobprice.setError("Enter Mobile Price");
            }
            else if (type.getText() == null || type.getText().toString().equalsIgnoreCase("")) {
                type.setError("Enter product ");
            }
            else if (price.getText() == null || price.getText().toString().equalsIgnoreCase("")) {
                price.setError("Enter product Price");
            }
              else if (gadget.getText() == null || gadget.getText().toString().equalsIgnoreCase("")) {
                gadget.setError("Enter Gadget Name and Model");
            }
              else if (date.getText() == null || date.getText().toString().equalsIgnoreCase("")) {
                date.setError("Select Invoice Date");
            }
              else {
                  SaveToPrefer();
              //  Toast.makeText(this, shopid, Toast.LENGTH_SHORT).show();
                Intent in = new Intent(ActivationActivity.this, KeyActivity.class);
                startActivity(in);
                finish();
            }
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ageGroup = i;
      //  Toast.makeText(this, ""+i, Toast.LENGTH_SHORT).show();
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
         AlertDialog.Builder builder = new AlertDialog.Builder(ActivationActivity.this);
         builder.setTitle(R.string.app_name);
         builder.setIcon(R.mipmap.logo);
         builder.setMessage("Do you want to exit Activation?")
                 .setCancelable(false)
                 .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int id) {
                         SharedPreferences.Editor editor = sharedpreferences.edit();
                         editor.clear();
                         editor.putString("name",asm);
                         editor.putString("shopreg_name", shop);
                         editor.putString("shopreg_id", shopid);
                         editor.apply();
                         Intent in = new Intent(ActivationActivity.this, AloginActivity.class);
                         // in.putExtras(b);
                         startActivity(in);
                         finish();
                     }
                 })
                 .setNegativeButton("No", new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int id) {
                         SaveToPrefer();
                         dialog.cancel();
                     }
                 });
         AlertDialog alert = builder.create();
         alert.show();
     }
}
