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

import com.example.project101.TransactionHistoryAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllTransactionsFragment extends Fragment {
    private ListView transactionListView;
    private TransactionHistoryAdapter adapter;

    public AllTransactionsFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_all_transactions, container, false);

        transactionListView = rootView.findViewById(R.id.transactionListViewall);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserData", getActivity().MODE_PRIVATE);
        String cardno = sharedPreferences.getString("cardno", "");

        // Retrieve the user's transaction history from Firebase
        DatabaseReference historyRef = FirebaseDatabase.getInstance().getReference("users/" + cardno + "/transactionHistory");

        historyRef.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ArrayList<TransactionHistoryItem> transactionHistory = new ArrayList<>();

                    for (DataSnapshot transactionSnapshot : dataSnapshot.getChildren()) {
                        String amount = transactionSnapshot.child("amount").getValue(String.class);
                        String date = transactionSnapshot.child("date").getValue(String.class);
                        String type = transactionSnapshot.child("type").getValue(String.class);
                        TransactionHistoryItem item = new TransactionHistoryItem(amount, date, type);
                        transactionHistory.add(item);
                    }

                    // Set up the adapter to display the transaction history
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
