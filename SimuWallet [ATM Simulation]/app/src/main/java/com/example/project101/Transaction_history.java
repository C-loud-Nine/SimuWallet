package com.example.project101;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Transaction_history extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_transaction_history);

        // Replace the initial content with the AllTransactionsFragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, new AllTransactionsFragment())
                .commit();

        // Set up BottomNavigationView
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }

    // BottomNavigationView item click listener
    private final BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    if (item.getItemId() == R.id.nav_all) {
                        selectedFragment = new AllTransactionsFragment();
                    } else {
                        if (item.getItemId() == R.id.nav_cash_in) {
                            selectedFragment = new CashInFragment();
                        } else if (item.getItemId() == R.id.nav_cash_out) {
                            selectedFragment = new CashOutFragment();
                        }
                    }

                    // Replace the fragment with the selected one
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainer, selectedFragment)
                            .commit();

                    return true;
                }
            };
}
