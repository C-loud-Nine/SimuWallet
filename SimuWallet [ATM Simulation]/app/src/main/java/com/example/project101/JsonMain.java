package com.example.project101;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class JsonMain extends AppCompatActivity {
    private ListView countryListView;
    private ArrayList<String> countryNames = new ArrayList<>();
    private JSONObject jsonData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_jsonmain);

        countryListView = findViewById(R.id.countryListView);

        parseJson();

        countryListView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedCountry = countryNames.get(position);

            // Start StateListActivity with the selected country
            Intent intent = new Intent(JsonMain.this, StateListActivity.class);

            // Pass the selected country to the next activity
            intent.putExtra("selectedCountry", selectedCountry);

            // Pass the jsonData to the next activity
            intent.putExtra("jsonData", jsonData.toString());

            // Start the StateListActivity
            startActivity(intent);
        });
    }

    private void parseJson() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://api.myjson.online/v1/records/9a9d4017-72fd-4ac5-9891-2c40c1b13cd9";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Check if the "data" key exists
                            if (response.has("data")) {
                                // Get the "data" object
                                JSONObject dataObject = response.getJSONObject("data");

                                // Check if the "Simuwallet" key exists under "data"
                                if (dataObject.has("Simuwallet")) {
                                    // Get the "Simuwallet" array
                                    JSONArray simuwalletArray = dataObject.getJSONArray("Simuwallet");

                                    // Iterate through the "Simuwallet" array
                                    for (int i = 0; i < simuwalletArray.length(); i++) {
                                        // Get each element in the array
                                        JSONObject simuwalletElement = simuwalletArray.getJSONObject(i);

                                        // Check if it contains the "bank" key
                                        if (simuwalletElement.has("bank")) {
                                            // Extract the "country" key from the element
                                            String countryName = simuwalletElement.getString("country");
                                            countryNames.add(countryName);
                                        }
                                    }
                                }
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(JsonMain.this, android.R.layout.simple_list_item_1, countryNames);
                            countryListView.setAdapter(adapter);

                            // Assign the response to jsonData
                            jsonData = response;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Toast.makeText(JsonMain.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }
}
