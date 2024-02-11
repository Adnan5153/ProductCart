package com.ctos.productcart;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private int selectedQuantity = 0; // Track selected quantity
    // Declare the map as an instance variable
    private final Map<String, Integer> productNameToImageMap = new HashMap<>();

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeProductNameToImageMap(); // Initialize the map

        // Initialize the spinners and TextView
        Spinner spinnerName = findViewById(R.id.spinnername);
        Spinner spinnerQuantity = findViewById(R.id.spinnerquantity);
        TextView textViewPrice = findViewById(R.id.textViewtotal);
        String[] productPrices = getResources().getStringArray(R.array.ProductPrice);

        // Set up the button click listener to start MainActivity2
        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedProductName = spinnerName.getSelectedItem().toString();
                Integer imageResId = productNameToImageMap.get(selectedProductName); // Correctly access the map

                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                int selectedProductPosition = spinnerName.getSelectedItemPosition();
                String selectedQuantity = spinnerQuantity.getSelectedItem().toString();
                double selectedProductPrice = selectedProductPosition > 0 ? Double.parseDouble(productPrices[selectedProductPosition - 1]) : 0;

                // total price calculation
                String totalPrice = textViewPrice.getText().toString();

                // Put extras into Intent
                intent.putExtra("SelectedProductName", selectedProductName);
                intent.putExtra("ImageResId", imageResId); // Pass the image resource ID
                intent.putExtra("SelectedQuantity", selectedQuantity);
                intent.putExtra("SelectedProductPrice", selectedProductPrice);
                intent.putExtra("TotalPrice", totalPrice);

                startActivity(intent);
            }
        });

        // ArrayAdapter setup for spinnerName
        ArrayAdapter<CharSequence> adapterName = ArrayAdapter.createFromResource(this,
                R.array.ProductName, android.R.layout.simple_spinner_item);
        adapterName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerName.setAdapter(adapterName);

        // ArrayAdapter setup for spinnerQuantity
        ArrayAdapter<CharSequence> adapterQuantity = ArrayAdapter.createFromResource(this,
                R.array.ProductQuantity, android.R.layout.simple_spinner_item);
        adapterQuantity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerQuantity.setAdapter(adapterQuantity);

        // Listener for spinnerName
        spinnerName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updatePriceDisplay(); // Call updatePriceDisplay directly
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                textViewPrice.setText("");
            }
        });

        // Listener for spinnerQuantity
        spinnerQuantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) { // Check position validity
                    selectedQuantity = Integer.parseInt(parent.getItemAtPosition(position).toString());
                } else {
                    selectedQuantity = 0;
                }
                updatePriceDisplay(); // Update price display when quantity changes
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedQuantity = 0;
                updatePriceDisplay();
            }
        });
    }

    private void initializeProductNameToImageMap() {
        // Initialize the map with product names and their corresponding drawable IDs
        productNameToImageMap.put("Monitor", R.drawable.monitor);
        productNameToImageMap.put("Keyboard", R.drawable.keyboard);
        productNameToImageMap.put("Mouse", R.drawable.mouse);
        productNameToImageMap.put("Speakers", R.drawable.speaker);
        productNameToImageMap.put("HDMI Cable", R.drawable.hdmi);
        productNameToImageMap.put("USB Data Cable", R.drawable.usb);
    }

    // Update the displayed price based on current selections
    @SuppressLint("DefaultLocale")
    private void updatePriceDisplay() {
        Spinner spinnerName = findViewById(R.id.spinnername);
        TextView textViewPrice = findViewById(R.id.textViewtotal);
        String[] productPrices = getResources().getStringArray(R.array.ProductPrice);

        int position = spinnerName.getSelectedItemPosition();
        if (position > 0 && position <= productPrices.length) {
            double price = Double.parseDouble(productPrices[position - 1]);
            double total = price * selectedQuantity;
            textViewPrice.setText(String.format("%.2f", total));
        } else {
            textViewPrice.setText(""); // Clear when no valid selection
        }
    }
}
