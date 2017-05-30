package com.example.akashn.simplilogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static android.R.attr.button;

public class Registration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        final EditText name=(EditText)findViewById(R.id.name);
        final EditText email=(EditText)findViewById(R.id.email);
        final EditText mobile=(EditText)findViewById(R.id.mobile);
        final EditText userid=(EditText)findViewById(R.id.userid);
        final EditText password=(EditText)findViewById(R.id.password);

        Button button=(Button)findViewById(R.id.submit);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("customerid", userid.getText().toString());

                    jsonBody.put("email", email.getText().toString());
                    jsonBody.put("mobile", mobile.getText().toString());
                    jsonBody.put("name", name.getText().toString());
                    jsonBody.put("password", password.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final String mRequestBody = jsonBody.toString();
                StringRequest request=new StringRequest(Request.Method.POST, Constants.url+"/Customers", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response",response);
                        Intent intent1=new Intent(Registration.this,LoginActivity.class);
                        startActivity(intent1);
                        Toast.makeText(Registration.this, "Successfully registered", Toast.LENGTH_SHORT).show();
                    }
                },new Response.ErrorListener(){

                    public void onErrorResponse(VolleyError error)
                    {
                        Log.d("error","error"+ error.getMessage());}

                }){





                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        try {
                            return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                        } catch (UnsupportedEncodingException uee) {
                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                            return null;
                        }
                    }

                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        String responseString = "";
                        if (response != null) {

                            responseString = String.valueOf(response.statusCode);

                        }
                        return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                    }

                };


                RequestQueue requestQueue= Volley.newRequestQueue(getBaseContext());
                requestQueue.add(request);


            }
        });

    }
}
