package com.example.project101;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class UserProfile extends AppCompatActivity {

     TextView proname,probranch,propin,titname,titbalance,titcardno;

     Button profile_back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_profile);

        titname=findViewById(R.id.titleName);
        titcardno=findViewById(R.id.profileBranch);
        titbalance=findViewById(R.id.showbalance);
        proname=findViewById(R.id.profileName);
        probranch=findViewById(R.id.titleUsername);
        propin=findViewById(R.id.profilepin);
        profile_back_btn=findViewById(R.id.backButton);



//        String name=getIntent().getStringExtra("name");
//        String balance=getIntent().getStringExtra("balance");
//        String branch=getIntent().getStringExtra("branch");
//        String cardno=getIntent().getStringExtra("cardno");
//        String pin=getIntent().getStringExtra("pin");

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "");
        String branch = sharedPreferences.getString("branch", "");
        String balance = sharedPreferences.getString("balance", "");
        String cardno = sharedPreferences.getString("cardno", "");
        String pin = sharedPreferences.getString("pin", "");



        titname.setText(name);
        titcardno.setText(cardno);
        titbalance.setText(balance);
        proname.setText(name);
        probranch.setText(branch);
        propin.setText(pin);


        profile_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserProfile.this, Dashboard.class);
                startActivity(intent);
            }
        });



    }
}