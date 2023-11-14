package com.example.project101;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener;

import java.util.ArrayList;


public class Dashboard extends AppCompatActivity implements OnNavigationItemSelectedListener, View.OnClickListener {

    //Variables

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;

    TextView textView;
    CardView c1,c2,c3,c4,c5,c6;

    @SuppressLint("MissingInflatedId")
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);

    /*---------------------Hooks------------------------*/

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView=findViewById(R.id.navigationView);
        textView=findViewById(R.id.textView);
        toolbar=findViewById(R.id.toolbar);
        c1=findViewById(R.id.cashwithdraw);
        c2=findViewById(R.id.balancecheck);
        c3=findViewById(R.id.pinchange);
        c4=findViewById(R.id.accinfo);
        c5=findViewById(R.id.addmoni);
        c6=findViewById(R.id.fundtrans);

        c1.setOnClickListener(this);
        c2.setOnClickListener(this);
        c3.setOnClickListener(this);
        c4.setOnClickListener(this);
        c5.setOnClickListener(this);
        c6.setOnClickListener(this);



        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        if (drawerLayout != null) {
            drawerLayout.addDrawerListener(toggle);
        }
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        if (menuItem.getItemId() == R.id.nav_home) {
            // Check if you are already on the home screen.
            Intent intent = new Intent(Dashboard.this, Dashboard.class);
            startActivity(intent);
        }
        else if (menuItem.getItemId() == R.id.website) {
            Intent i=new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://tinyurl.com/simuwallet"));
            startActivity(i);
            //Toast.makeText(this, "Website Opened Successfully", Toast.LENGTH_SHORT).show();
        }
        else if (menuItem.getItemId() == R.id.exit) {
            Toast.makeText(this, "Exited App Successfully", Toast.LENGTH_SHORT).show();
            finishAffinity();
            return true;
        }
        else if (menuItem.getItemId() == R.id.login) {
            Intent intent = new Intent(Dashboard.this, Register.class);
            startActivity(intent);
        }
        else if (menuItem.getItemId() == R.id.profile) {

//            String name=getIntent().getStringExtra("name");
//            String balance=getIntent().getStringExtra("balance");
//            String branch=getIntent().getStringExtra("branch");
//            String cardno=getIntent().getStringExtra("cardno");
//            String pin=getIntent().getStringExtra("pin");
//            Intent intent = new Intent(getApplicationContext(), UserProfile.class);
//            intent.putExtra("name", name);
//            intent.putExtra("branch", branch);
//            intent.putExtra("balance", balance);
//            intent.putExtra("cardno", cardno);
//            intent.putExtra("pin", pin);
//            startActivity(intent);

            SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
            String name = sharedPreferences.getString("name", "");
            String branch = sharedPreferences.getString("branch", "");
            String balance = sharedPreferences.getString("balance", "");
            String cardno = sharedPreferences.getString("cardno", "");
            String pin = sharedPreferences.getString("pin", "");


            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("name", name);
            editor.putString("branch", branch);
            editor.putString("balance", balance);
            editor.putString("cardno", cardno);
            editor.putString("pin", pin);
            editor.apply();

            Intent intent = new Intent(getApplicationContext(), UserProfile.class);
            // ...

            // Start the Dashboard activity
            startActivity(intent);

            Toast.makeText(this, "Profile Opened Successfully", Toast.LENGTH_SHORT).show();
        }
        else if (menuItem.getItemId() == R.id.contact) {
            Toast.makeText(this, "Contacted Successfully", Toast.LENGTH_SHORT).show();
            // Replace "1000" with the actual phone number you want to call
            String phoneNumber = "999";
            Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
            startActivity(callIntent);
            Toast.makeText(this, "Contacted Successfully", Toast.LENGTH_SHORT).show();
        }
        else if (menuItem.getItemId() == R.id.logout) {
            Intent intent = new Intent(Dashboard.this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show();
        }
        else if (menuItem.getItemId() == R.id.branch) {
            // Create a URI for the location you want to show on Google Maps.
            Uri gmmIntentUri = Uri.parse("geo:latitude,longitude?q=SimuWallet+Bank");

            // Create an Intent with ACTION_VIEW and the URI.
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            startActivity(mapIntent);

        }
        else if (menuItem.getItemId() == R.id.atm) {
            Uri gmmIntentUri = Uri.parse("geo:latitude,longitude?q=ATM");

            // Create an Intent with ACTION_VIEW and the URI.
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            startActivity(mapIntent);
            Toast.makeText(this, "Found ATM Successfully", Toast.LENGTH_SHORT).show();

        }
        else if (menuItem.getItemId() == R.id.about) {
            Intent intent = new Intent(Dashboard.this, JsonMain.class);
            startActivity(intent);
            Toast.makeText(this, "About Opened Successfully", Toast.LENGTH_SHORT).show();
        }
        else if (menuItem.getItemId() == R.id.share) {
            // Create an intent for sharing
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this app: [SimuWallet]");
            shareIntent.setType("text/plain");

            Intent chooser = Intent.createChooser(shareIntent, "Share via");

            if (shareIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(chooser);
            } else {
                Toast.makeText(this, "No apps available for sharing", Toast.LENGTH_SHORT).show();
            }
        }

        else if (menuItem.getItemId() == R.id.rate) {
            Toast.makeText(this, "Rated App Successfully", Toast.LENGTH_SHORT).show();
        }
        else if (menuItem.getItemId() == R.id.news) {
            Intent intent = new Intent(Dashboard.this, Splash.class);
            startActivity(intent);
            Toast.makeText(this, "NEWS", Toast.LENGTH_SHORT).show();
        }
        return true;
    }


    @Override
    public void onClick(View v) {


        if (v.getId() == R.id.cashwithdraw) {
            SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
            String balance = sharedPreferences.getString("balance", "");

            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString("balance", balance);

            editor.apply();

            Intent intent = new Intent(getApplicationContext(), Cashwithdraw.class);
            startActivity(intent);
//            Toast.makeText(this, "Cash Withdrawn Successfully", Toast.LENGTH_SHORT).show();
        }
        else if (v.getId() == R.id.balancecheck) {
            SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
            String balance = sharedPreferences.getString("balance", "");

            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString("balance", balance);

            editor.apply();

            Intent intent = new Intent(getApplicationContext(), Balance_Check.class);
            startActivity(intent);
            Toast.makeText(this, "Balance Checked Successfully", Toast.LENGTH_SHORT).show();
        }
        else if (v.getId() == R.id.pinchange) {

            SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
            String pin = sharedPreferences.getString("pin", "");

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("pin", pin);
            editor.apply();

            Intent intent = new Intent(getApplicationContext(), Pinchange.class);
            startActivity(intent);

        }
        else if (v.getId() == R.id.accinfo) {

            SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
            String name = sharedPreferences.getString("name", "");
            String cardno = sharedPreferences.getString("cardno", "");// Retrieve user's name

            // Retrieve the transaction history from SharedPreferences
           // ArrayList<TransactionHistoryItem> transactionHistory = getIntent().<TransactionHistoryItem>getParcelableArrayListExtra("transactionHistory");

            Intent intent = new Intent(getApplicationContext(), Transaction_history.class);
            intent.putExtra("name", name); // Pass the user's name
            //intent.putParcelableArrayListExtra("transactionHistory", (ArrayList<? extends Parcelable>) transactionHistory); // Pass the transaction history
            startActivity(intent);

        }
        else if (v.getId() == R.id.addmoni) {

            SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
            String balance = sharedPreferences.getString("balance", "");

            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString("balance", balance);

            editor.apply();

            Intent intent = new Intent(getApplicationContext(), Addmoney.class);
            startActivity(intent);
            Toast.makeText(this, "addmoney", Toast.LENGTH_SHORT).show();

        } else if (v.getId() == R.id.fundtrans) {
            SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
            String balance = sharedPreferences.getString("balance", "");
            String cardno = sharedPreferences.getString("cardno", "");


            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("balance", balance);
            editor.putString("cardno", cardno);

            editor.apply();

            Intent intent = new Intent(getApplicationContext(), Fundtransfer.class);
            startActivity(intent);

            Toast.makeText(this, "fundtrans", Toast.LENGTH_SHORT).show();

        }
    }
}