package com.example.myapplication.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.EndPoints;
import com.example.myapplication.FilePath;
import com.example.myapplication.R;
import com.example.myapplication.VolleyMultipartRequest;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UploadActivity extends AppCompatActivity {
Button photo,idp,resume,bank,submit;
    // Creating URI .
    Uri uri;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    private int GALLERY = 1, CAMERA = 2,GALLERYID = 3,CAMERAID = 4,CAMERABANK = 5,GALLERYBANK = 6;

    // Server URL.
    public static final String PDF_UPLOAD_HTTP_URL = "http://www.truemobilecare.com/adminplus/api5.php";

    // Pdf upload request code.
    public int PDF_REQ_CODE = 1;

    // Define strings to hold given pdf name, path and ID.
    String PdfNameHolder, PdfPathHolder, PdfID,path,pathid,pathphoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        photo=(Button)findViewById(R.id.photo);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });
        idp=(Button)findViewById(R.id.id);
        idp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialogid();
            }
        });
      //  resume=(Button)findViewById(R.id.resume);
      /*  resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // PDF selection code start from here .
                // Creating intent object.
                Intent intent = new Intent();

                // Setting up default file pickup time as PDF.
                intent.setType("application/pdf");

                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PDF_REQ_CODE);

            }
        });*/
        bank=(Button)findViewById(R.id.bank);
        bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialogbank();
            }
        });
        submit=(Button)findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private void showPictureDialogbank() {
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
                                choosePhotoFromGallarybank();
                                break;
                            case 1:
                                captureImagebank();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    private void captureImagebank() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERABANK);
    }

    private void choosePhotoFromGallarybank() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERYBANK);
    }

    private void showPictureDialogid() {
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
                                choosePhotoFromGallaryid();
                                break;
                            case 1:
                                captureImageid();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    private void choosePhotoFromGallaryid() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERYID);
    }

    private void captureImageid() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERAID);
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

    private void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }

    private void captureImage() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {*/
        super.onActivityResult(requestCode, resultCode, data);
      /*  if (requestCode == PDF_REQ_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {

                uri = data.getData();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    PdfUploadFunction();
                }
                // After selecting the PDF set PDF is Selected text inside Button.
            }*/
         // This is important, otherwise the result will not be passed to the fragment
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
            else   if (requestCode == GALLERYID) {
                if (data != null) {
                    Uri contentURI = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                        // path = Calendar.getInstance().getTimeInMillis() + ".jpg";
                        // Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
                        // Toast.makeText(ActivationActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                        uploadBitmapid(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                        // Toast.makeText(ActivationActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            } else if (requestCode == CAMERAID) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                // saveImage(thumbnail);
                // path = Calendar.getInstance().getTimeInMillis() + ".jpg";
                uploadBitmapid(thumbnail);
                // Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
                // Toast.makeText(ActivationActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
            }
            else   if (requestCode == GALLERYBANK) {
                if (data != null) {
                    Uri contentURI = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                        // path = Calendar.getInstance().getTimeInMillis() + ".jpg";
                        // Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
                        // Toast.makeText(ActivationActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                        uploadBitmapbank(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                        // Toast.makeText(ActivationActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            } else if (requestCode == CAMERABANK) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                // saveImage(thumbnail);
                // path = Calendar.getInstance().getTimeInMillis() + ".jpg";
                uploadBitmapbank(thumbnail);
                // Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
                // Toast.makeText(ActivationActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
            }
     //   }

    }

    public byte[] getFileDataFromDrawablebank(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    public byte[] getFileDataFromDrawableid(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    private void uploadBitmapbank(final Bitmap bitmap) {
        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, EndPoints.UPLOAD_URLBANK,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            path=obj.getString("message");
                            bank.setText(path);
                            // Toast.makeText(getApplicationContext(), path, Toast.LENGTH_SHORT).show();
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
                params.put("pro_bank", new DataPart(imagename + ".jpg", getFileDataFromDrawablebank(bitmap)));
                return params;
            }
        };
        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

    private void uploadBitmapid(final Bitmap bitmap) {
        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, EndPoints.UPLOAD_URLID,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            pathid=obj.getString("message");
                            idp.setText(pathid);
                            // Toast.makeText(getApplicationContext(), path, Toast.LENGTH_SHORT).show();
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
                params.put("pro_idproof", new DataPart(imagename + ".jpg", getFileDataFromDrawableid(bitmap)));
                return params;
            }
        };
        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

    private void uploadBitmap(final Bitmap bitmap) {
        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, EndPoints.UPLOAD_ROOT_URLPHOTO,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            pathphoto=obj.getString("message");
                            photo.setText(pathphoto);
                            // Toast.makeText(getApplicationContext(), path, Toast.LENGTH_SHORT).show();
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
                params.put("pro_image", new DataPart(imagename + ".jpg", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };
        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PDF_REQ_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            uri = data.getData();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                PdfUploadFunction();
            }
            // After selecting the PDF set PDF is Selected text inside Button.
        }
    }*/
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void PdfUploadFunction() {


        // Getting file path using Filepath class.
        PdfPathHolder = FilePath.getPath(this, uri);

        // If file path object is null then showing toast message to move file into internal storage.
        if (PdfPathHolder == null) {

            Toast.makeText(this, "Please move your PDF file to internal storage & try again.", Toast.LENGTH_LONG).show();

        }
        // If file path is not null then PDF uploading file process will starts.
        else {

            try {

                PdfID = UUID.randomUUID().toString();

                new MultipartUploadRequest(this, PdfID, PDF_UPLOAD_HTTP_URL)
                        .addFileToUpload(PdfPathHolder, "pdf")
                      //  .addParameter("name", PdfNameHolder)
                        .setNotificationConfig(new UploadNotificationConfig())
                        .setMaxRetries(5)
                        .startUpload();

            } catch (Exception exception) {

                Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Requesting run time permission method starts from here.
    public void RequestRunTimePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(UploadActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE))
        {

            Toast.makeText(UploadActivity.this,"READ_EXTERNAL_STORAGE permission Access Dialog", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(UploadActivity.this,new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] Result) {

        switch (RC) {

            case 1:

                if (Result.length > 0 && Result[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(UploadActivity.this,"Permission Granted", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(UploadActivity.this,"Permission Canceled", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }
    @Override
    public void onBackPressed()
    {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("message",path);
        editor.putString("messageid",pathid);
        editor.putString("messagephoto",pathphoto);
      //  editor.putString("pdf",PdfPathHolder);
        editor.apply();
        Intent in=new Intent(UploadActivity.this, AddpromoterActivity.class);
        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(in);
        finish();
        super.onBackPressed();

    }
}
