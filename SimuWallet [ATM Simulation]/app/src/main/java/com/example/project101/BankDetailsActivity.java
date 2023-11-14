package com.example.project101;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BankDetailsActivity extends AppCompatActivity {
    private TextView informationTextView;
    private TextView establishmentTextView;
    private TextView addressTextView;
    private TextView zipCodeTextView;

    private TextView atmTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_bank_details);

        informationTextView = findViewById(R.id.informationTextView);
        establishmentTextView = findViewById(R.id.establishmentTextView);
        addressTextView = findViewById(R.id.addressTextView);
        zipCodeTextView = findViewById(R.id.zipCodeTextView);
        atmTextView=findViewById(R.id.atmTextView);

        // Retrieve data from the intent
        String selectedState = getIntent().getStringExtra("selectedState");
        String jsonString = getIntent().getStringExtra("jsonData");

        try {
            if (selectedState != null && jsonString != null) {
                JSONObject jsonData = new JSONObject(jsonString);

                // Find the selected state in the data
                JSONArray simuwalletArray = jsonData.getJSONObject("data").getJSONArray("Simuwallet");
                for (int i = 0; i < simuwalletArray.length(); i++) {
                    JSONObject stateObject = simuwalletArray.getJSONObject(i);
                    JSONArray bankArray = stateObject.getJSONArray("bank");

                    for (int j = 0; j < bankArray.length(); j++) {
                        JSONObject bankObject = bankArray.getJSONObject(j);
                        if (bankObject.has("State") && bankObject.getString("State").equals(selectedState)) {
                            // Display details in TextViews
                            displayDetails(bankObject);
                            break;
                        }
                    }
                }
            } else {
                showToast("Selected state or jsonData is null");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void displayDetails(JSONObject selectedStateData) {
        try {
            JSONObject information = selectedStateData.getJSONObject("information");
            JSONObject address = selectedStateData.getJSONObject("address");

            // Display information
            String informationText = "Email: " + information.getString("email") +
                    "\nContact: " + information.getString("contact");
            informationTextView.setText(informationText);

            // Display establishment information
            String establishmentText = "Establishment: " + selectedStateData.getString("Establishment");
            establishmentTextView.setText(establishmentText);

            // Display address information
            String addressText = "City: " + address.getString("city") +
                    "\nStreet: " + address.getString("street");
            addressTextView.setText(addressText);

            // Display zip code
            String zipCodeText = "Zip Code: " + address.getString("zipCode");
            zipCodeTextView.setText(zipCodeText);

            // Display ATM information
            boolean hasATM = information.getBoolean("hasATM");
            String atmText = "ATM: " + (hasATM ? "Yes" : "No");
            atmTextView.setText(atmText);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showToast(String message) {
        // Existing code for showing a toast
    }
}
