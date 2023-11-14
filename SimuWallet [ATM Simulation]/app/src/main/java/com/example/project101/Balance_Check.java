package com.example.project101;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class Balance_Check extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_balance_check);

        TextView balanceCheck;
        Button back_bal;

        balanceCheck=findViewById(R.id.balcheck);
        back_bal=findViewById(R.id.backbalcheck);

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "");
        String branch = sharedPreferences.getString("branch", "");
        String balance = sharedPreferences.getString("balance", "");
        String cardno = sharedPreferences.getString("cardno", "");
        String pin = sharedPreferences.getString("pin", "");


        balanceCheck.setText(balance);

        back_bal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Balance_Check.this, Dashboard.class);
                startActivity(intent);
            }
        });




    }
}