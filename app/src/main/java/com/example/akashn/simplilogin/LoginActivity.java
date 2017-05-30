package com.example.akashn.simplilogin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    public static final String DEFAULT="n/a";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final AutoCompleteTextView email=(AutoCompleteTextView)findViewById(R.id.email);
        final EditText password=(EditText)findViewById(R.id.password);


        Button button =(Button)findViewById(R.id.email_sign_in_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


// prepare the Request
                JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET,Constants.url+"/Customers/"+email.getText(), null,
                        new Response.Listener<JSONObject>()
                        {
                            @Override
                            public void onResponse(JSONObject response) {

                                Log.d("rd", response.toString());

                                try {
                                    //Do it with this it will work
                                    JSONObject person = response;
                                    String name = person.getString("customerid");
                                    String pass = person.getString("password");

                                    //Store session

                                    SharedPreferences sp=getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor=sp.edit();
                                    editor.putString("name",name);
                                    editor.putString("password",pass);
                                    editor.commit();
                                    // Staring MainActivity
                                    if(pass.equals(password.getText().toString()))
                                     {
                                        Intent intent = new Intent(getApplicationContext(), UserPage.class);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        Toast.makeText(LoginActivity.this, "Login Credentials incorrect", Toast.LENGTH_SHORT).show();
                                    }
                                    //}

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
                                Log.d("Error.Response","error");
                            }
                        }
                );

                RequestQueue requestQueue= Volley.newRequestQueue(getBaseContext());
                requestQueue.add(getRequest);
            }
        });
        Button button2 =(Button)findViewById(R.id.register1);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  Intent intent=new Intent(LoginActivity.this,Registration.class);
                  startActivity(intent);
            }
        });


    }
}