package com.example.akashn.simplilogin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Loan_Apply extends AppCompatActivity {

    private static final String DEFAULT = "n/a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan__apply);
        final EditText loanamount=(EditText)findViewById(R.id.loan_amount);
        final EditText purpose=(EditText)findViewById(R.id.purpose);
        final EditText spinner=(EditText)findViewById(R.id.spinner);
        final EditText city=(EditText)findViewById(R.id.city);
        final EditText state=(EditText)findViewById(R.id.state);
        final EditText pincode=(EditText)findViewById(R.id.pincode);
        final EditText street=(EditText)findViewById(R.id.street);
        SharedPreferences sp=getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        final String customer=sp.getString("name",DEFAULT);

        Button submitloan=(Button)findViewById(R.id.submitloan);

        submitloan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("loanamount", loanamount.getText().toString());

                    jsonBody.put("purpose", purpose.getText().toString());
                    jsonBody.put("time", spinner.getText().toString());
                    jsonBody.put("street", street.getText().toString());
                    jsonBody.put("city", city.getText().toString());
                    jsonBody.put("state", state.getText().toString());
                    jsonBody.put("pincode", pincode.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final String mRequestBody = jsonBody.toString();
                StringRequest request=new StringRequest(Request.Method.POST, Constants.url+"/Customers/"+customer+"/Loans", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response",response);
                        Intent intent1=new Intent(Loan_Apply.this,UserPage.class);
                        startActivity(intent1);
                        Toast.makeText(getBaseContext(), "Successfully registered", Toast.LENGTH_SHORT).show();
                    }
                },new Response.ErrorListener(){

                    public void onErrorResponse(VolleyError error)
                    {
                        Log.d("error","error");}

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
