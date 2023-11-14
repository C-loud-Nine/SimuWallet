package com.example.project101;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Fundtransfer extends AppCompatActivity {

    private TextInputLayout receiverCardNo, transAmount;
    private Button transferButton, backButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_fundtransfer);

        receiverCardNo = findViewById(R.id.tranfer_to);
        transAmount = findViewById(R.id.tran);
        transferButton = findViewById(R.id.btnfundtrans);
        backButton = findViewById(R.id.btn_fundtraans_back);

        transferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transferFunds();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Fundtransfer.this, Dashboard.class);
                startActivity(intent);
            }
        });
    }

    private void transferFunds() {
        final String receiverCardNoText = receiverCardNo.getEditText().getText().toString().trim();
        final String transAmountText = transAmount.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUser = reference.orderByChild("cardno").equalTo(receiverCardNoText);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String transAmountText = transAmount.getEditText().getText().toString().trim();
                if (dataSnapshot.exists()) {
                    // User with the provided card number exists
                    receiverCardNo.setError(null);
                    receiverCardNo.setErrorEnabled(false);

                    SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                    final String senderCardNo = sharedPreferences.getString("cardno", "");
                    final String senderBalance = sharedPreferences.getString("balance", "");

                    int senderBalanceInt = Integer.parseInt(senderBalance);
                    int transAmountInt = Integer.parseInt(transAmountText);

                    DataSnapshot userSnapshot = dataSnapshot.getChildren().iterator().next();
                    String receiverBalance = userSnapshot.child("balance").getValue(String.class);
                    int receiverBalanceInt = Integer.parseInt(receiverBalance);

                    if (transAmountInt <= senderBalanceInt) {
                        // Sufficient balance for the transfer
                        transAmount.setError(null);
                        transAmount.setErrorEnabled(false);

                        int newReceiverBalance = receiverBalanceInt + transAmountInt;
                        final String newReceiverBalanceStr = Integer.toString(newReceiverBalance);

                        int newSenderBalance = senderBalanceInt - transAmountInt;
                        final String newSenderBalanceStr = Integer.toString(newSenderBalance);

                        // Update balances in the database
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                        reference.child(receiverCardNoText).child("balance").setValue(newReceiverBalanceStr);
                        reference.child(senderCardNo).child("balance").setValue(newSenderBalanceStr);

                        // Update transaction history
                        updateTransactionHistory(senderCardNo, receiverCardNoText, transAmountText);

                        // Update balance in SharedPreferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("balance", newSenderBalanceStr);
                        editor.apply();

                        Intent intent = new Intent(Fundtransfer.this, Dashboard.class);
                        startActivity(intent);
                    } else {
                        // Insufficient balance for the transfer
                        transAmount.setError("Insufficient Balance");
                        transAmount.requestFocus();
                    }
                } else {
                    // User not found
                    receiverCardNo.setError("Invalid Card Number");
                    receiverCardNo.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database errors if needed
            }
        });
    }

    private void updateTransactionHistory(final String senderCardNo, final String receiverCardNo, final String amount) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        final DatabaseReference senderTransactionHistoryRef = reference.child(senderCardNo).child("transactionHistory");
        final DatabaseReference receiverTransactionHistoryRef = reference.child(receiverCardNo).child("transactionHistory");
        final String transAmountText = transAmount.getEditText().getText().toString().trim();

        senderTransactionHistoryRef.addListenerForSingleValueEvent(new ValueEventListener() {
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
                        senderTransactionHistoryRef.child(destIndex).setValue(sourceTransaction);
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
                        senderTransactionHistoryRef.child(destIndex).setValue(sourceTransaction);
                    }
                }

                // Add the new transaction at the top (index 1)
                String transactionKey = "1";
                Transaction transaction = new Transaction("Transferred", transAmountText, getCurrentDate());
                senderTransactionHistoryRef.child(transactionKey).setValue(transaction);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error, if needed
            }
        });

        // Update receiver's transaction history
        receiverTransactionHistoryRef.addListenerForSingleValueEvent(new ValueEventListener() {

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
                        receiverTransactionHistoryRef.child(destIndex).setValue(sourceTransaction);
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
                        receiverTransactionHistoryRef.child(destIndex).setValue(sourceTransaction);
                    }
                }

                // Add the new transaction at the top (index 1)
                String transactionKey = "1";
                Transaction transaction = new Transaction("Received", transAmountText, getCurrentDate());
                receiverTransactionHistoryRef.child(transactionKey).setValue(transaction);
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error, if needed
            }
        });
    }

    private String getCurrentDate() {
        // Use SimpleDateFormat to format the date and time
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }
}
