package com.example.three_star_store_scanner;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.three_star_store_scanner.R;
import com.example.three_star_store_scanner.ui.addproduct.AddProductActivity;
import com.example.three_star_store_scanner.ui.billing.BillingActivity;
import com.example.three_star_store_scanner.ui.productlist.ProductListActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_billing) {
                startActivity(new Intent(this, BillingActivity.class));
                return true;
            } else if (id == R.id.nav_add) {
                startActivity(new Intent(this, AddProductActivity.class));
                return true;
            } else if (id == R.id.nav_list) {
                startActivity(new Intent(this, ProductListActivity.class));
                return true;
            }
            return false;
        });


    }
}
