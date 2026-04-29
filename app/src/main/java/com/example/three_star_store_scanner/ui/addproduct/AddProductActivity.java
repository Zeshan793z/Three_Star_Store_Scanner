package com.example.three_star_store_scanner.ui.addproduct;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.three_star_store_scanner.R;
import com.example.three_star_store_scanner.data.entity.Product;
import com.example.three_star_store_scanner.data.repository.ProductRepository;

public class AddProductActivity extends AppCompatActivity {

    private EditText nameInput, barcodeInput, priceInput;
    private Button saveButton;
    private ProductRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        // Initialize repository
        repository = new ProductRepository(this);

        // Bind UI elements
        nameInput = findViewById(R.id.nameInput);
        barcodeInput = findViewById(R.id.barcodeInput);
        priceInput = findViewById(R.id.priceInput);
        saveButton = findViewById(R.id.saveButton);

        // Save button logic
        saveButton.setOnClickListener(v -> {
            String name = nameInput.getText().toString().trim();
            String barcode = barcodeInput.getText().toString().trim();
            String priceText = priceInput.getText().toString().trim();

            if (name.isEmpty() || barcode.isEmpty() || priceText.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            double price = Double.parseDouble(priceText);

            Product product = new Product();
            product.name = name;
            product.barcode = barcode;
            product.price = price;

            repository.insertProduct(product);

            Toast.makeText(this, "Product saved!", Toast.LENGTH_SHORT).show();
            finish(); // Close activity after saving
        });
    }
}
