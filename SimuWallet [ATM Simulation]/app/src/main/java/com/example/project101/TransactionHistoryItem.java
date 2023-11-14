package com.example.project101;

import android.os.Parcel;
import android.os.Parcelable;

public class TransactionHistoryItem implements Parcelable {
    private String amount;
    private String date;
    private String type;

    public TransactionHistoryItem(String amount, String date, String type) {
        this.amount = amount;
        this.date = date;
        this.type = type;
    }

    protected TransactionHistoryItem(Parcel in) {
        amount = in.readString();
        date = in.readString();
        type = in.readString();
    }

    public static final Creator<TransactionHistoryItem> CREATOR = new Creator<TransactionHistoryItem>() {
        @Override
        public TransactionHistoryItem createFromParcel(Parcel in) {
            return new TransactionHistoryItem(in);
        }

        @Override
        public TransactionHistoryItem[] newArray(int size) {
            return new TransactionHistoryItem[size];
        }
    };

    public String getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(amount);
        dest.writeString(date);
        dest.writeString(type);
    }

    public boolean isHeader() {
        return false; // You can implement this based on your requirements.
    }
}
