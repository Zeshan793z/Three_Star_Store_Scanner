package com.example.three_star_store_scanner.ui.productlist;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.three_star_store_scanner.R;
import com.example.three_star_store_scanner.data.entity.Product;
import com.example.three_star_store_scanner.data.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    private ListView productListView;
    private ProductRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        productListView = findViewById(R.id.productListView);
        repository = new ProductRepository(this);

        // Fetch products from DB
        List<Product> products = repository.getAllProducts();

        // Convert to display strings
        List<String> productStrings = new ArrayList<>();
        for (Product p : products) {
            productStrings.add(p.name + " | " + p.barcode + " | $" + p.price);
        }

        // Bind to ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                productStrings
        );
        productListView.setAdapter(adapter);
    }
}

