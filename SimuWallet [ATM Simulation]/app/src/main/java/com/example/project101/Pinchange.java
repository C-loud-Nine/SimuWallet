package com.example.project101;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Pinchange extends AppCompatActivity {

    TextInputLayout newpin,oldpin;
    Button savebtn,backpinchangebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pinchange);

        newpin = findViewById(R.id.newpin);
        oldpin = findViewById(R.id.oldpin);
        savebtn = findViewById(R.id.btnpinchange);
        backpinchangebtn = findViewById(R.id.btnpinchangeback);

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pinUser(view);
                Toast.makeText(Pinchange.this, "Saved", Toast.LENGTH_SHORT).show();
            }
        });

        backpinchangebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Pinchange.this, Dashboard.class);
                startActivity(intent);
            }
        });


    }

    private Boolean old_pin_valid() {
        String val = oldpin.getEditText().toString();
        if (val.isEmpty()) {
            oldpin.setError("Field Cannot be Empty");
            return false;
        } else {
            oldpin.setError(null);
            oldpin.setErrorEnabled(false);
            return true;
        }
    }

    public void pinUser(View view) {
        if (!old_pin_valid()) {
            return;
        } else {
            okPin();
        }
    }

    private boolean isPinChanged() {
        final String userpinchange = oldpin.getEditText().getText().toString().trim();
        final String newPin = newpin.getEditText().getText().toString().trim();

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String cardno = sharedPreferences.getString("cardno", "");

        if (userpinchange.equals(sharedPreferences.getString("pin", ""))){
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
            reference.child(cardno).child("pin").setValue(newPin);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("pin", newPin);
            editor.apply();
            return true;
        } else {
            return false;
        }
    }


    public void okPin(){
        if(isPinChanged()){
            Intent intent = new Intent(Pinchange.this, UserProfile.class);
            startActivity(intent);
        } else {
            newpin.setError("Wrong PIN");
            newpin.requestFocus();
        }
    }

}
