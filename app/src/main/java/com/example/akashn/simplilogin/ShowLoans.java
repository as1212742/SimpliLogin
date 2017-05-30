package com.example.akashn.simplilogin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ShowLoans extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_loans);


        final TextView t2=(TextView)findViewById(R.id.amount);
        final TextView t3=(TextView)findViewById(R.id.purpose);
        final TextView t4=(TextView)findViewById(R.id.Time);
        final TextView t5=(TextView)findViewById(R.id.Address);
        final TextView t1=(TextView)findViewById(R.id.name);



        SharedPreferences sp=getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String customer=sp.getString("name","n/a");
   t1.setText(customer);
        JsonObjectRequest getRequest2 = new JsonObjectRequest(Request.Method.GET,Constants.url+"/Customers/"+customer+"/Loans", null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("rd", response.toString());

                        try {
                            //Do it with this it will work


                            JSONObject person = response;
                            String name = person.getString("loanamount");
                            String purpose = person.getString("purpose");
                            String time = person.getString("time");
                            String street = person.getString("street");
                            String city = person.getString("city");
                            String state = person.getString("state");
                            String pincode = person.getString("pincode");

                            //Store session

                           t2.setText(""+name);
                            t3.setText(""+purpose);
                            t4.setText(""+time);
                            t5.setText(""+street+" ,"+city+ " ,"+state+ " ,"+pincode);
                            // Staring MainActivity


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }


                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response",error.getMessage());
                    }
                }
        );

        RequestQueue requestQueue= Volley.newRequestQueue(getBaseContext());
        requestQueue.add(getRequest2);
    }
}











