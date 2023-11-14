package com.example.project101;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {

    Button enter,callReg;
    TextInputLayout cardno, pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        enter = findViewById(R.id.btnlogin);
        cardno = findViewById(R.id.Card_no);
        pin = findViewById(R.id.pin);
        callReg=findViewById(R.id.callreg);

        callReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser(view);
                Toast.makeText(Login.this, "pressed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @NonNull
    private Boolean Validatecardno() {
        String val = cardno.getEditText().toString();
        if (val.isEmpty()) {
            cardno.setError("Field Cannot be Empty");
            return false;
        } else {
            cardno.setError(null);
            cardno.setErrorEnabled(false);
            return true;
        }
    }

    @NonNull
    private Boolean Validatepin() {
        String val = pin.getEditText().getText().toString();
        if (val.isEmpty()) {
            pin.setError("Field Cannot be Empty");
            return false;
        } else {
            pin.setError(null);
            pin.setErrorEnabled(false);
            return true;
        }
    }

    public void loginUser(View view) {
        if (!Validatepin() | !Validatecardno()) {
            return;
        } else {
            isUser();
        }
    }




//    private void isUser() {
//        final String userEnteredcardno = cardno.getEditText().getText().toString().trim();
//        final String userEnteredpin = pin.getEditText().getText().toString().trim();
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
//        Query checkUser = reference.orderByChild("cardno").equalTo(userEnteredcardno);
//        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    cardno.setError(null);
//                    cardno.setErrorEnabled(false);
//
//                    DataSnapshot userSnapshot = dataSnapshot.getChildren().iterator().next(); // Get the first result
//                    String pinfromDB = userSnapshot.child("pin").getValue(String.class);
//
//                    if (pinfromDB != null && pinfromDB.equals(userEnteredpin)) {
//                        // Valid PIN
//                        cardno.setError(null);
//                        cardno.setErrorEnabled(false);
//
//                        String namefromDB = userSnapshot.child("name").getValue(String.class);
//                        String branchfromDB = userSnapshot.child("branch").getValue(String.class);
//                        String balancefromDB = userSnapshot.child("balance").getValue(String.class);
//                        String cardnofromDB = userSnapshot.child("cardno").getValue(String.class);
//
//                        // Retrieve the transactionHistory
//                        DataSnapshot transactionHistorySnapshot = userSnapshot.child("transactionHistory");
//                        Iterable<DataSnapshot> transactionHistoryChildren = transactionHistorySnapshot.getChildren();
//                        List<TransactionHistoryItem> transactionHistoryList = new ArrayList<>();
//
//                        for (DataSnapshot transaction : transactionHistoryChildren) {
//                            String amount = transaction.child("amount").getValue(String.class);
//                            String date = transaction.child("date").getValue(String.class);
//                            String type = transaction.child("type").getValue(String.class);
//
//                            TransactionHistoryItem transactionItem = new TransactionHistoryItem(amount, date, type);
//                            transactionHistoryList.add(transactionItem);
//                        }
//
//                        // Now you have the transactionHistoryList
//                        // You can use it as needed
//
//                        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
//                        intent.putExtra("name", namefromDB);
//                        intent.putExtra("branch", branchfromDB);
//                        intent.putExtra("balance", balancefromDB);
//                        intent.putExtra("cardno", cardnofromDB);
//                        intent.putParcelableArrayListExtra("transactionHistory", (ArrayList<? extends Parcelable>) transactionHistoryList);
//                        startActivity(intent);
//                    } else {
//                        // Wrong PIN
//                        pin.setError("Wrong PIN");
//                        pin.requestFocus();
//                    }
//                } else {
//                    // User not found
//                    cardno.setError("Wrong Cardno");
//                    cardno.requestFocus();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                // Handle database errors if needed
//            }
//        });
//    }


    private void isUser() {
        final String userEnteredcardno = cardno.getEditText().getText().toString().trim();
        final String userEnteredpin = pin.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUser = reference.orderByChild("cardno").equalTo(userEnteredcardno);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    cardno.setError(null);
                    cardno.setErrorEnabled(false);

                    DataSnapshot userSnapshot = dataSnapshot.getChildren().iterator().next(); // Get the first result
                    String pinfromDB = userSnapshot.child("pin").getValue(String.class);

                    if (pinfromDB != null && pinfromDB.equals(userEnteredpin)) {
                        // Valid PIN
                        cardno.setError(null);
                        cardno.setErrorEnabled(false);

                        String namefromDB = userSnapshot.child("name").getValue(String.class);
                        String branchfromDB = userSnapshot.child("branch").getValue(String.class);
                        String balancefromDB = userSnapshot.child("balance").getValue(String.class);
                        String cardnofromDB = userSnapshot.child("cardno").getValue(String.class);

                        // Save user data in SharedPreferences
                        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("name", namefromDB);
                        editor.putString("branch", branchfromDB);
                        editor.putString("balance", balancefromDB);
                        editor.putString("cardno", cardnofromDB);
                        editor.putString("pin", pinfromDB);
                        editor.apply();

                        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                        startActivity(intent);
                    }
                    else {
                        // Wrong PIN
                        pin.setError("Wrong PIN");
                        pin.requestFocus();
                    }
                }
                else {
                    // User not found
                    cardno.setError("Wrong Cardno");
                    cardno.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database errors if needed
            }
        });
    }


//    private void isUser() {
//        final String userEnteredcardno = cardno.getEditText().getText().toString().trim();
//        final String userEnteredpin = pin.getEditText().getText().toString().trim();
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
//        Query checkUser = reference.orderByChild("cardno").equalTo(userEnteredcardno);
//        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    cardno.setError(null);
//                    cardno.setErrorEnabled(false);
//
//                    DataSnapshot userSnapshot = dataSnapshot.getChildren().iterator().next(); // Get the first result
//                    String pinfromDB = userSnapshot.child("pin").getValue(String.class);
//
//                    if (pinfromDB != null && pinfromDB.equals(userEnteredpin)) {
//                        // Valid PIN
//                        cardno.setError(null);
//                        cardno.setErrorEnabled(false);
//
//                        String namefromDB = userSnapshot.child("name").getValue(String.class);
//                        String branchfromDB = userSnapshot.child("branch").getValue(String.class);
//                        String balancefromDB = userSnapshot.child("balance").getValue(String.class);
//                        String cardnofromDB = userSnapshot.child("cardno").getValue(String.class);
//
//
//                        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
//
//                        intent.putExtra("name", namefromDB);
//                        intent.putExtra("pin", pinfromDB);
//                        intent.putExtra("branch", branchfromDB);
//                        intent.putExtra("balance", balancefromDB);
//                        intent.putExtra("cardno", cardnofromDB);
//
//                        startActivity(intent);
//                    } else {
//                        // Wrong PIN
//                        pin.setError("Wrong PIN");
//                        pin.requestFocus();
//                    }
//                } else {
//                    // User not found
//                    cardno.setError("Wrong Cardno");
//                    cardno.requestFocus();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                // Handle database errors if needed
//            }
//        });
//    }

}

