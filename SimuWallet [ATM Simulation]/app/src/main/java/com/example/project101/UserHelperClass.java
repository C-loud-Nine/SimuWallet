package com.example.project101;
import java.util.ArrayList;
import java.util.List;

public class UserHelperClass {
    private String name;
    private String balance;
    private String pin;
    private String cardno;
    private String branch;
    private List<String> transactionHistory; // New field for transaction history

    public UserHelperClass() {
        // Default constructor required for Firebase
    }

    public UserHelperClass(String name, String balance, String branch, String cardno, String pin) {
        this.name = name;
        this.balance = balance;
        this.branch = branch;
        this.cardno = cardno;
        this.pin = pin;
        this.transactionHistory = new ArrayList<>(); // Initialize an empty transaction history
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public List<String> getTransactionHistory() {
        return transactionHistory;
    }

    public void setTransactionHistory(List<String> transactionHistory) {
        this.transactionHistory = transactionHistory;
    }
}
