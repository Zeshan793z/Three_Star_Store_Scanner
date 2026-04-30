package com.example.three_star_store_scanner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.three_star_store_scanner.ui.addproduct.AddProductActivity;
import com.example.three_star_store_scanner.ui.billing.BillingActivity;
import com.example.three_star_store_scanner.ui.productlist.ProductListActivity;

public class MainActivity extends AppCompatActivity {

    private Button addProductButton, viewProductsButton, billingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addProductButton = findViewById(R.id.addProductButton);
        viewProductsButton = findViewById(R.id.viewProductsButton);
        billingButton = findViewById(R.id.billingButton);

        addProductButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddProductActivity.class));
        });

        viewProductsButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ProductListActivity.class));
        });

        billingButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, BillingActivity.class));
        });
    }
}
