package com.example.project101;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project101.R;
import com.example.project101.TransactionHistoryAdapter;
import com.example.project101.TransactionHistoryItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CashOutFragment extends Fragment {
    private ListView transactionListView;
    private TransactionHistoryAdapter adapter;

    public CashOutFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cash_out, container, false);

        transactionListView = rootView.findViewById(R.id.transactionListViewout);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserData", getActivity().MODE_PRIVATE);
        String cardno = sharedPreferences.getString("cardno", "");

        // Retrieve the user's Cash Out transactions from Firebase
        DatabaseReference cashOutRef = FirebaseDatabase.getInstance().getReference("users/" + cardno + "/transactionHistory");
        // Modify the query based on your data structure, e.g., orderByChild("type").equalTo("Debited")

        cashOutRef.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ArrayList<TransactionHistoryItem> transactionHistory = new ArrayList<>();

                    for (DataSnapshot transactionSnapshot : dataSnapshot.getChildren()) {
                        String amount = transactionSnapshot.child("amount").getValue(String.class);
                        String date = transactionSnapshot.child("date").getValue(String.class);
                        String type = transactionSnapshot.child("type").getValue(String.class);

                        // Check if the transaction is a Cash Out (e.g., Debited)
                        if ("Debited".equals(type) || "Transferred".equals(type)) {
                            TransactionHistoryItem item = new TransactionHistoryItem(amount, date, type);
                            transactionHistory.add(item);
                        }
                    }

                    // Set up the adapter to display the Cash Out transaction history
                    adapter = new TransactionHistoryAdapter(getActivity(), transactionHistory);
                    transactionListView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database errors if needed
            }
        });

        return rootView;
    }
}
