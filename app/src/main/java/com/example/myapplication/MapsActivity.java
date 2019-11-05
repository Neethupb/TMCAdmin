package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, AdapterView.OnItemSelectedListener {
    String lat,lon,name,result,path;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap mMap;
    public static final String MyPREFERENCES = "MyPrefs" ;
    private int  CAMERA = 1;
    SharedPreferences sharedpreferences;
    public static final String DEFAULT = "N/A";
    TextView user;
    LinearLayout map;
  Spinner spin;
    Button attendance,back;
    ImageView cam;
    // Creating Volley RequestQueue.
    RequestQueue requestQueue;
    // Creating Progress dialog.
    ProgressDialog progressDialog;
    // Storing server url into String variable.
    String HttpUrl = "http://www.truemobilecare.com/adminplus/attendance.php";
    String HttpUrlleave = "http://www.truemobilecare.com/adminplus/attendance.php";
    String[] country = { "--Status--","Present ", "Halfday","Leave"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(MapsActivity.this);
        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(MapsActivity.this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        // name = sharedpreferences.getString("shopreg_name",DEFAULT);
        name = sharedpreferences.getString("name",DEFAULT);
        user=(TextView)findViewById(R.id.textView);
         user.setText(name);

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        spin = (Spinner) findViewById(R.id.spinner6);
        spin.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the country list
        final ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);


        map=(LinearLayout)findViewById(R.id.lmap);
        attendance=(Button)findViewById(R.id.button26);
        back=(Button)findViewById(R.id.button25);
        cam=(ImageView) findViewById(R.id.cam);
        cam.setVisibility(View.GONE);
        map.setVisibility(View.GONE);
        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // showPictureDialog();
                captureImage();
            }
        });
        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result=spin.getSelectedItem().toString();
                //  Toast.makeText(this, mode, Toast.LENGTH_SHORT).show();
                if (result.equals("Leave")){
                 Leave();
                }
                else if (result.equals("--Status--")){
                    Toast.makeText(MapsActivity.this, "Select Status", Toast.LENGTH_SHORT).show();
                }
                else {
                   Attendence();
                }
       }
        });
back.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
       // Intent in=new Intent(MapsActivity.this,AloginActivity.class);
        Intent in=new Intent(MapsActivity.this,AloginActivity.class);
        startActivity(in);
        finish();
    }
});
    }

    private void Leave() {
        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrlleave,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        // Matching server responce message to our text.
                        if(ServerResponse.contains("success")) {
                            // Intent intent=new Intent(MapsActivity.this,AloginActivity.class);
                            Intent intent=new Intent(MapsActivity.this,AloginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            AlertDialog alertDialog = new AlertDialog.Builder(MapsActivity.this).create();
                            // Setting Dialog Title
                            alertDialog.setTitle("Failed");
                            // Setting Dialog Message
                            alertDialog.setMessage("Already Marked Attendance");
                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent=new Intent(MapsActivity.this,AloginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
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
                        Toast.makeText(MapsActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("att_name", name);
              /*  params.put("att_lattitude", lat);
                params.put("att_longitude", lon);*/
                params.put("att_status",result);
              //  params.put("att_image",path);
                return params;
            }
        };
        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(MapsActivity.this);
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }


    private void captureImage() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
       /* IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {*/
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == this.RESULT_CANCELED) {
                return;
            }
           if (requestCode == CAMERA) {
               Bitmap bitmap = (Bitmap) data.getExtras().get("data");
               // saveImage(thumbnail);
                // path = Calendar.getInstance().getTimeInMillis() + ".jpg";
               cam.setImageBitmap(bitmap);

               uploadBitmap(bitmap);
                // Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
                // Toast.makeText(ActivationActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
            }
       // }
    }

    private void uploadBitmap(final Bitmap bitmap) {
        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, EndPoints.UPLOAD_URLATTENDANCE,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            path=obj.getString("message");
                          //  cam.setText(path);
                          //  Toast.makeText(getApplicationContext(), path, Toast.LENGTH_SHORT).show();
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
                params.put("att_image", new DataPart(imagename + ".jpg", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };
        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }


    private void Attendence() {
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
                        if(ServerResponse.contains("success")) {
                           // Intent intent=new Intent(MapsActivity.this,AloginActivity.class);
                            Intent intent=new Intent(MapsActivity.this,AloginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                               AlertDialog alertDialog = new AlertDialog.Builder(MapsActivity.this).create();
                            // Setting Dialog Title
                            alertDialog.setTitle("Failed");
                            // Setting Dialog Message
                            alertDialog.setMessage("Already Marked Attendance");
                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent=new Intent(MapsActivity.this,AloginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
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
                        Toast.makeText(MapsActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("att_name", name);
                params.put("att_lattitude", lat);
                params.put("att_longitude", lon);
                params.put("att_status",result);
                params.put("att_image",path);
                return params;
            }
        };
        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(MapsActivity.this);
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMyLocationButtonClickListener(onMyLocationButtonClickListener);
        mMap.setOnMyLocationClickListener(onMyLocationClickListener);
        enableMyLocationIfPermitted();

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMinZoomPreference(11);
    }

    private void enableMyLocationIfPermitted() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else if (mMap != null) {
            mMap.setMyLocationEnabled(true);
        }
    }

    private void showDefaultLocation() {
        Toast.makeText(this, "Location permission not granted, " +
                        "showing default location",
                Toast.LENGTH_SHORT).show();
        LatLng redmond = new LatLng(47.6739881, -122.121512);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(redmond));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            enableMyLocationIfPermitted();
        } else {
            showDefaultLocation();
        }
    }

    private GoogleMap.OnMyLocationButtonClickListener onMyLocationButtonClickListener =
            new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    mMap.setMinZoomPreference(15);
                    return false;
                }
            };

    private GoogleMap.OnMyLocationClickListener onMyLocationClickListener =
            new GoogleMap.OnMyLocationClickListener() {
                @Override
                public void onMyLocationClick(@NonNull Location location) {

                    mMap.setMinZoomPreference(12);

                    CircleOptions circleOptions = new CircleOptions();
                    circleOptions.center(new LatLng(location.getLatitude(),
                            location.getLongitude()));
                    lat= String.valueOf(location.getLatitude());
                    lon= String.valueOf(location.getLongitude());
                //    Toast.makeText(MapsActivity.this, lat, Toast.LENGTH_SHORT).show();

                    circleOptions.radius(200);
                    circleOptions.fillColor(Color.RED);
                    circleOptions.strokeWidth(6);

                    mMap.addCircle(circleOptions);
                }
            };
    @Override
    public void onBackPressed()
    {
        Intent in=new Intent(MapsActivity.this,AloginActivity.class);
        //Intent in=new Intent(MapsActivity.this,HomeActivity.class);
        startActivity(in);
        finish();


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        result=spin.getSelectedItem().toString();
         // Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        if (result.equals("Leave")){
            map.setVisibility(View.GONE);
            cam.setVisibility(View.GONE);
        }
        else {
            map.setVisibility(View.VISIBLE);
            cam.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
