package com.example.three_star_store_scanner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.three_star_store_scanner.ui.addproduct.AddProductActivity;
import com.example.three_star_store_scanner.ui.productlist.ProductListActivity;

public class MainActivity extends AppCompatActivity {

    private Button addProductButton, viewProductsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addProductButton = findViewById(R.id.addProductButton);
        viewProductsButton = findViewById(R.id.viewProductsButton);

        addProductButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddProductActivity.class);
            startActivity(intent);
        });

        viewProductsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProductListActivity.class);
            startActivity(intent);
        });
    }
}
