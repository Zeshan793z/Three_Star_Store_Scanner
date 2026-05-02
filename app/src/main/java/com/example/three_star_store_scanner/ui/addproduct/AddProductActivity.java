package com.example.three_star_store_scanner.ui.addproduct;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.three_star_store_scanner.R;
import com.example.three_star_store_scanner.data.entity.Product;
import com.example.three_star_store_scanner.data.repository.ProductRepository;
import com.example.three_star_store_scanner.ui.scanner.BarcodeScannerActivity;

public class AddProductActivity extends AppCompatActivity {

    private EditText nameInput, barcodeInput, priceInput;
    private Button saveButton, scanBarcodeButton;
    private ProductRepository repository;

    private static final int SCAN_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        nameInput = findViewById(R.id.nameInput);
        barcodeInput = findViewById(R.id.barcodeInput);
        priceInput = findViewById(R.id.priceInput);
        saveButton = findViewById(R.id.saveButton);
        scanBarcodeButton = findViewById(R.id.scanBarcodeButton);

        repository = new ProductRepository(this);

        // Save product manually
        saveButton.setOnClickListener(v -> {
            String name = nameInput.getText().toString().trim();
            String barcode = barcodeInput.getText().toString().trim();
            String priceStr = priceInput.getText().toString().trim();

            if (name.isEmpty() || barcode.isEmpty() || priceStr.isEmpty()) {
                Toast.makeText(this, "Fill all fields!", Toast.LENGTH_SHORT).show();
                return;
            }

            double price = Double.parseDouble(priceStr);

            // Option 1: if you added a constructor in Product.java
            Product product = new Product(name, barcode, price);

            // Option 2: if Product only has no-arg constructor
            // Product product = new Product();
            // product.name = name;
            // product.barcode = barcode;
            // product.price = price;

            repository.insertProduct(product);

            Toast.makeText(this, "Product saved!", Toast.LENGTH_SHORT).show();
            finish();
        });

        // Scan barcode to auto-fill
        scanBarcodeButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, BarcodeScannerActivity.class);
            intent.putExtra("scanMode", "add");  // 👈 tell scanner we’re adding
            startActivityForResult(intent, SCAN_REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SCAN_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            String scannedBarcode = data.getStringExtra("productBarcode");
            if (scannedBarcode != null) {
                barcodeInput.setText(scannedBarcode);  // 👈 auto-fill barcode field
            }
        }
    }
}
