package com.example.project101;//package com.example.project101;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//import java.util.List;
//
//public class TransactionHistoryAdapter extends BaseAdapter {
//    private Context context;
//    private List<TransactionHistoryItem> transactionHistory;
//
//    public TransactionHistoryAdapter(Context context, List<TransactionHistoryItem> transactionHistory) {
//        this.context = context;
//        this.transactionHistory = transactionHistory;
//    }
//
//    @Override
//    public int getCount() {
//        return transactionHistory.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return transactionHistory.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        TransactionHistoryItem item = transactionHistory.get(position);
//
//        if (convertView == null) {
//            convertView = LayoutInflater.from(context).inflate(R.layout.transaction_history_item, parent, false);
//        }
//
//        TextView mainTransactionType = convertView.findViewById(R.id.mainTransactionType);
//        TextView amountTextView = convertView.findViewById(R.id.amountTextView);
//        TextView dateTextView = convertView.findViewById(R.id.dateTextView);
//
//        mainTransactionType.setText(item.getType());
//        amountTextView.setText("Amount: " + item.getAmount());
//        dateTextView.setText("Date: " + item.getDate());
//
//        // Set background color of amountTextView based on transaction type
//        if ("Debited".equals(item.getType()) || "Transferred".equals(item.getType())) {
//            amountTextView.setBackgroundColor(context.getResources().getColor(android.R.color.holo_red_light));
//        } else if ("Credited".equals(item.getType()) || "Received".equals(item.getType())) {
//            amountTextView.setBackgroundColor(context.getResources().getColor(android.R.color.holo_green_light));
//        }
//
//        return convertView;
//    }
//}

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public class TransactionHistoryAdapter extends BaseAdapter {
    private Context context;
    private List<TransactionHistoryItem> transactionHistory;

    public TransactionHistoryAdapter(Context context, List<TransactionHistoryItem> transactionHistory) {
        this.context = context;
        this.transactionHistory = transactionHistory;
    }

    @Override
    public int getCount() {
        return transactionHistory.size();
    }

    @Override
    public Object getItem(int position) {
        return transactionHistory.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TransactionHistoryItem item = transactionHistory.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.transaction_history_item, parent, false);
        }

        TextView mainTransactionType = convertView.findViewById(R.id.mainTransactionType);
        TextView amountTextView = convertView.findViewById(R.id.amountTextView);
        TextView dateTextView = convertView.findViewById(R.id.dateTextView);

        mainTransactionType.setText(item.getType());
        amountTextView.setText("Amount: " + item.getAmount());
        dateTextView.setText("Date: " + item.getDate());

        // Set background color of amountTextView based on transaction type
        if ("Debited".equals(item.getType()) || "Transferred".equals(item.getType())) {
            mainTransactionType.setTextColor(context.getResources().getColor(android.R.color.holo_red_light));
            amountTextView.setBackgroundColor(context.getResources().getColor(android.R.color.holo_red_light));
        } else if ("Credited".equals(item.getType()) || "Received".equals(item.getType())) {
            mainTransactionType.setTextColor(context.getResources().getColor(android.R.color.holo_green_light));
            amountTextView.setBackgroundColor(context.getResources().getColor(android.R.color.holo_green_light));
        }

        return convertView;
    }
}
