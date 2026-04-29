package com.example.three_star_store_scanner.data.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "products")
public class Product {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String name;

    @NonNull
    public String barcode;

    public double price;

    public Product() {
        name = "";
    }

    // Optional: add stock, category, unit, etc.
}
