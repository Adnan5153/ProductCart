package com.ctos.productcart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Initialize your TextViews and ImageView
        TextView tvProductHeading = findViewById(R.id.textViewheading);
        TextView tvProductName = findViewById(R.id.textViewProductName);
        TextView tvProductQuantity = findViewById(R.id.textViewProductQuantity);
        TextView tvTotalPrice = findViewById(R.id.textViewTotal);
        ImageView imageView = findViewById(R.id.imageView); // Initialize ImageView

        // Get data from Intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String selectedProductName = extras.getString("SelectedProductName", "");
            int imageResId = extras.getInt("ImageResId", 0); // Retrieve the image resource ID
            String selectedQuantity = extras.getString("SelectedQuantity", "0");
            double selectedProductPrice = extras.getDouble("SelectedProductPrice", 0.0);
            String totalPrice = extras.getString("TotalPrice", "0");

            // Set text in TextViews
            tvProductHeading.setText(selectedProductName);
            tvProductName.setText(selectedProductName);
            tvProductQuantity.setText(selectedQuantity);
            tvTotalPrice.setText(totalPrice);

            // Set image in ImageView
            if (imageResId != 0) { // Check if a valid image resource ID is received
                imageView.setImageResource(imageResId);
            }
        }
    }
}
