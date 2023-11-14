package com.example.project101;

public class Transaction {
    private String type;  // "Credited" or "Debited"
    private String amount;
    private String date;  // You can add a date field if needed

    public Transaction() {
    }

    public Transaction(String type, String amount, String date) {
        this.type = type;
        this.amount = amount;
        this.date = date;
    }

    // Getter and setter methods for Transaction fields

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
