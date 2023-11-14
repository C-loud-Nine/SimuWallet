package com.example.project101;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class StateListActivity extends AppCompatActivity {

    private ArrayList<String> stateNames = new ArrayList<>();
    private ListView stateListView;
    private JSONObject jsonData;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_state_list);

        stateListView = findViewById(R.id.stateListView);

        // Get the selected country and jsonData from the intent
        String selectedCountry = getIntent().getStringExtra("selectedCountry");
        String jsonString = getIntent().getStringExtra("jsonData");

        try {
            if (selectedCountry != null && jsonString != null) {
                // Convert the string to a JSONObject
                jsonData = new JSONObject(jsonString);

                loadSelectedCountryData(selectedCountry);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stateNames);
                stateListView.setAdapter(adapter);

                // Set item click listener
                stateListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selectedState = stateNames.get(position);

                        // Create intent for BankDetailsActivity
                        Intent intent = new Intent(StateListActivity.this, BankDetailsActivity.class);

                        // Pass selectedState and jsonData to BankDetailsActivity
                        intent.putExtra("selectedState", selectedState);
                        intent.putExtra("jsonData", jsonData.toString());

                        // Start BankDetailsActivity
                        startActivity(intent);
                    }
                });
            } else {
                showToast("Selected country or jsonData is null");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadSelectedCountryData(String selectedCountry) {
        try {
            // Initialize the stateNames array to avoid null reference
            stateNames = new ArrayList<>();

            // Check if jsonData is not null
            if (jsonData != null) {
                // Check if the "data" key exists
                if (jsonData.has("data")) {
                    // Get the "data" object
                    JSONObject dataObject = jsonData.getJSONObject("data");

                    // Check if the "Simuwallet" key exists
                    if (dataObject.has("Simuwallet")) {
                        // Get the "Simuwallet" array
                        JSONArray simuwalletArray = dataObject.getJSONArray("Simuwallet");

                        // Iterate through the "Simuwallet" array
                        for (int i = 0; i < simuwalletArray.length(); i++) {
                            // Get each element in the array
                            JSONObject simuwalletElement = simuwalletArray.getJSONObject(i);

                            // Check if it contains the "bank" key
                            if (simuwalletElement.has("bank")) {
                                // Extract the "bank" array
                                JSONArray bankArray = simuwalletElement.getJSONArray("bank");

                                // Iterate through the "bank" array
                                for (int j = 0; j < bankArray.length(); j++) {
                                    // Get each bank in the array
                                    JSONObject bankObject = bankArray.getJSONObject(j);

                                    // Check if it contains the "State" key
                                    if (bankObject.has("State")) {
                                        // Extract the state name
                                        String stateName = bankObject.getString("State");

                                        // Check if the country matches the selected country
                                        if (simuwalletElement.getString("country").equals(selectedCountry)) {
                                            stateNames.add(stateName);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                // Handle case where jsonData is null
                showToast("JsonData is null");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
