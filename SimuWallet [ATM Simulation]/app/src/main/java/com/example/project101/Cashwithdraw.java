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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Cashwithdraw extends AppCompatActivity {

    TextInputLayout amount;
    Button withdrawbtn,backwithdraw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_cashwithdraw);

        amount = findViewById(R.id.withdrawamount);
        withdrawbtn = findViewById(R.id.btncashwithdraw);
        backwithdraw= findViewById(R.id.btn_cashwithdraw_back);


        withdrawbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amountUser(view);
            }
        });

        backwithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Cashwithdraw.this, Dashboard.class);
                startActivity(intent);
            }
        });


    }

    private Boolean amount_valid() {
        String val = amount.getEditText().toString();
        if (val.isEmpty()) {
            amount.setError("Field Cannot be Empty");
            return false;
        }
        else {
            amount.setError(null);
            amount.setErrorEnabled(false);
            return true;
        }
    }

    public void amountUser(View view) {
        if (!amount_valid()) {
            return;
        } else {
            okAmount();
        }
    }

//    private boolean iswithdrawn() {
//        final String enteredAmount = amount.getEditText().getText().toString().trim();
//
//        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
//        String cardno = sharedPreferences.getString("cardno", "");
//        String balance = sharedPreferences.getString("balance", "");
//
//
//        int int_enteredAmount = Integer.parseInt(enteredAmount);
//        int int_balance = Integer.parseInt(balance);
//
//
//        if (int_balance>=int_enteredAmount){
//
//            int int_newbalance=int_balance-int_enteredAmount;
//            String newBalanceStr = Integer.toString(int_newbalance);
//
//            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
//            reference.child(cardno).child("balance").setValue(newBalanceStr);
//
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString("balance", newBalanceStr);
//            editor.apply();
//            return true;
//        } else {
//            Toast.makeText(this, "Insufficient Balance", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//    }

    ///////////

    private boolean isWithdrawn() {
        final String enteredAmount = amount.getEditText().getText().toString().trim();
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        final String cardno = sharedPreferences.getString("cardno", "");
        final String balance = sharedPreferences.getString("balance", "");
        int int_enteredAmount = Integer.parseInt(enteredAmount);
        int int_balance = Integer.parseInt(balance);

        if (int_balance >= int_enteredAmount && int_enteredAmount%500==0) {
            int int_newbalance = int_balance - int_enteredAmount;
            final String newBalanceStr = Integer.toString(int_newbalance);

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
            reference.child(cardno).child("balance").setValue(newBalanceStr);

            // Get the reference to the user's transaction history
            final DatabaseReference transactionHistoryRef = reference.child(cardno).child("transactionHistory");

            // Read the current transaction count
            transactionHistoryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    long transactionCount = dataSnapshot.getChildrenCount();

                    // Check if there are already 10 transactions
                    if (transactionCount >= 10) {
                        // Iterate through and shift transactions
                        for (int i = 10; i >= 2; i--) {
                            String sourceIndex = String.valueOf(i - 1);
                            String destIndex = String.valueOf(i);

                            // Move the transaction from sourceIndex to destIndex
                            Transaction sourceTransaction = dataSnapshot.child(sourceIndex).getValue(Transaction.class);
                            transactionHistoryRef.child(destIndex).setValue(sourceTransaction);
                        }
                    } else {
                        // There are fewer than 10 transactions, increment the count and add the new transaction
                        transactionCount++;

                        // Shift transactions downward
                        for (int i = (int) transactionCount; i >= 2; i--) {
                            String sourceIndex = String.valueOf(i - 1);
                            String destIndex = String.valueOf(i);

                            // Move the transaction from sourceIndex to destIndex
                            Transaction sourceTransaction = dataSnapshot.child(sourceIndex).getValue(Transaction.class);
                            transactionHistoryRef.child(destIndex).setValue(sourceTransaction);
                        }
                    }

                    // Add the new transaction at the top (index 1)
                    String transactionKey = "1";
                    Transaction transaction = new Transaction("Debited", enteredAmount, getCurrentDate());
                    transactionHistoryRef.child(transactionKey).setValue(transaction);
                }





                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle database error, if needed
                }
            });

            // Update the balance in SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("balance", newBalanceStr);
            editor.apply();

            return true;
        } else {
            Toast.makeText(this, "Invalid Balance", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    private String getCurrentDate() {
        // Use SimpleDateFormat to format the date and time
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }


    //////////////////////


    public void okAmount(){
        if(isWithdrawn()){
            Intent intent = new Intent(Cashwithdraw.this, Dashboard.class);
            startActivity(intent);
        } else {
            amount.setError("Wrong Amount");
            amount.requestFocus();
        }
    }

}
