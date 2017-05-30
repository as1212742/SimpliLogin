package com.example.akashn.simplilogin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
        Button b1=(Button)findViewById(R.id.showloan);
        Button b2=(Button)findViewById(R.id.applyloan);
        Button b3=(Button)findViewById(R.id.logout);

        TextView tv=(TextView)findViewById(R.id.welcome);

        SharedPreferences sp=getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
       String customer=sp.getString("name","n/a");
         String str=tv.getText().toString();
        tv.setText(" "+str+customer);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newintent1=new Intent(getApplication(),ShowLoans.class);
                startActivity(newintent1);
            }
        });


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newintent2=new Intent(getApplication(),Loan_Apply.class);
                startActivity(newintent2);
            }
        });


        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newintent3=new Intent(getApplication(),LoginActivity.class);
                startActivity(newintent3);
            }
        });

    }
}
