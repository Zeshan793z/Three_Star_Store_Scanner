package com.example.three_star_store_scanner.ui.scanner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.three_star_store_scanner.data.entity.Product;
import com.example.three_star_store_scanner.data.repository.ProductRepository;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class BarcodeScannerActivity extends AppCompatActivity {

    private ProductRepository repository;
    private String scanMode; // "billing" or "add"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        repository = new ProductRepository(this);

        // Get mode from intent (default = billing)
        scanMode = getIntent().getStringExtra("scanMode");
        if (scanMode == null) {
            scanMode = "billing";
        }

        // Start ZXing scanner
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setPrompt("Scan a product barcode");
        integrator.setOrientationLocked(false);
        integrator.setBeepEnabled(true);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                String scannedBarcode = result.getContents();

                if (scanMode.equals("billing")) {
                    // Lookup product in DB
                    Product product = repository.getProductByBarcode(scannedBarcode);
                    if (product != null) {
                        Toast.makeText(this,
                                "Found: " + product.name + " | $" + product.price,
                                Toast.LENGTH_LONG).show();

                        // Send product back to BillingActivity
                        Intent intent = new Intent();
                        intent.putExtra("productName", product.name);
                        intent.putExtra("productBarcode", product.barcode);
                        intent.putExtra("productPrice", product.price);
                        setResult(RESULT_OK, intent);
                    } else {
                        Toast.makeText(this, "Product not found!", Toast.LENGTH_LONG).show();
                        setResult(RESULT_CANCELED);
                    }
                } else if (scanMode.equals("add")) {
                    // Return raw barcode to AddProductActivity
                    Intent intent = new Intent();
                    intent.putExtra("productBarcode", scannedBarcode);
                    setResult(RESULT_OK, intent);
                }

                finish();
            } else {
                Toast.makeText(this, "Scan cancelled", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
