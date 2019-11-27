package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Constants;
import com.example.myapplication.Models.PlayerModel;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClaimStatusActivity extends AppCompatActivity {
    EditText cin;
    TextView toll;
    Button search;
    String cino,claimid,status,remark,time;
    TextView climid,stus,remrk,date;
    LinearLayout linearLayout;
    // Creating Volley RequestQueue.
    RequestQueue requestQueue;
    // Creating Progress dialog.
    ProgressDialog progressDialog;
    // Storing server url into String variable.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim_status);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(ClaimStatusActivity.this);
        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(ClaimStatusActivity.this);

        linearLayout=(LinearLayout)findViewById(R.id.linear);
        linearLayout.setVisibility(View.GONE);

       // climid=(TextView)findViewById(R.id.clmid);
        stus=(TextView)findViewById(R.id.status);
        remrk=(TextView)findViewById(R.id.remarks);
        date=(TextView)findViewById(R.id.date);

        cin=(EditText)findViewById(R.id.editText19);
        toll=(TextView)findViewById(R.id.textView23);
        search=(Button)findViewById(R.id.button10);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cino=cin.getText().toString().trim();
                if (cin.getText().toString().equalsIgnoreCase("")|| cin.getText()== null){
                    cin.setError("Enter Invoice Number");
                }
                else {
                    linearLayout.setVisibility(View.VISIBLE);
                    Status();
                }
              /*  Intent intent=new Intent(ClaimStatusActivity.this,StatusActivity.class);
                startActivity(intent);*/
            }
        });
        toll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = "1800123366666";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                if (ActivityCompat.checkSelfPermission(ClaimStatusActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(intent);
            }
        });
    }
    private void Status(){
        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.HttpUrlclaimstatus,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        // Matching server responce message to our text.
                        ArrayList<PlayerModel> playersModelArrayList = new ArrayList<>();

                        try {

                                JSONArray array=new JSONArray(ServerResponse);
                                for (int i=0; i < array.length(); i++) {
                                    PlayerModel playerModel = new PlayerModel();
                                    JSONObject product = array.getJSONObject(i);
                                    playerModel.setClaimid(product.getString("claim_id"));
                                    playerModel.setStatus(product.getString("claim_status"));
                                    playerModel.setRemarks(product.getString("claim_remarks"));
                                    playerModel.setTime(product.getString("claim_time"));
                                    playersModelArrayList.add(playerModel);
                                }

                            for (int j = 0; j < playersModelArrayList.size(); j++){
                               // climid.setText(climid.getText()+ playersModelArrayList.get(j).getClaimid()+"\n");
                                stus.setText(stus.getText()+ playersModelArrayList.get(j).getStatus()+"\n");
                                remrk.setText(remrk.getText()+ playersModelArrayList.get(j).getRemarks()+"\n");
                                date.setText(date.getText()+ playersModelArrayList.get(j).getTime()+"\n");

                                 }


                                } catch (JSONException e) {
                                e.printStackTrace();
                            }
                       // Toast.makeText(ClaimStatusActivity.this, ""+climid.getText().toString(), Toast.LENGTH_SHORT).show();
                        }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        // Showing error message if something goes wrong.
                        Toast.makeText(ClaimStatusActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("claim_intimationid", cino);
                return params;
            }
        };
        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(ClaimStatusActivity.this);
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }
    @Override
    public void onBackPressed()
    {
        Intent in=new Intent(ClaimStatusActivity.this, HomeActivity.class);
        startActivity(in);
        finish();
    }
}
