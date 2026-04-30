package com.example.three_star_store_scanner.ui.billing;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.three_star_store_scanner.R;
import com.example.three_star_store_scanner.data.entity.Product;
import com.example.three_star_store_scanner.data.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class BillingActivity extends AppCompatActivity {

    private EditText barcodeInput;
    private Button addButton, clearButton;
    private ListView billListView;
    private TextView totalText;

    private ProductRepository repository;
    private List<String> billItems;
    private double totalAmount = 0.0;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);

        repository = new ProductRepository(this);

        barcodeInput = findViewById(R.id.barcodeInput);
        addButton = findViewById(R.id.addButton);
        clearButton = findViewById(R.id.clearButton);
        billListView = findViewById(R.id.billListView);
        totalText = findViewById(R.id.totalText);

        billItems = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, billItems);
        billListView.setAdapter(adapter);

        addButton.setOnClickListener(v -> {
            String barcode = barcodeInput.getText().toString().trim();
            if (barcode.isEmpty()) {
                Toast.makeText(this, "Enter a barcode", Toast.LENGTH_SHORT).show();
                return;
            }

            Product product = repository.getProductByBarcode(barcode);
            if (product != null) {
                billItems.add(product.name + " | " + product.barcode + " | $" + product.price);
                totalAmount += product.price;
                totalText.setText("Total: $" + totalAmount);
                adapter.notifyDataSetChanged();
                barcodeInput.setText("");
            } else {
                Toast.makeText(this, "Product not found!", Toast.LENGTH_SHORT).show();
            }
        });

        clearButton.setOnClickListener(v -> {
            billItems.clear();
            totalAmount = 0.0;
            totalText.setText("Total: $0.0");
            adapter.notifyDataSetChanged();
        });
    }
}

