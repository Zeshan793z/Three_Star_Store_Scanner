package com.example.three_star_store_scanner.data.repository;

import android.content.Context;

import androidx.room.Room;

import com.example.three_star_store_scanner.data.dao.ProductDao;
import com.example.three_star_store_scanner.data.database.AppDatabase;
import com.example.three_star_store_scanner.data.entity.Product;

import java.util.List;

public class ProductRepository {

    private final ProductDao productDao;

    //Constructor initializes Room database and DAO
    public ProductRepository(Context context) {
        AppDatabase db = Room.databaseBuilder(
                context.getApplicationContext(),
                AppDatabase.class,
                "three_star_store_db"
        ).allowMainThreadQueries().build(); // For simplicity; use background threads in production
        productDao = db.productDao();
    }

    // Insert product
    public void insertProduct(Product product) {
        productDao.insert(product);
    }

    // Find product by barcode
    public Product getProductByBarcode(String barcode) {
        return productDao.findByBarcode(barcode);
    }

    // Get all products
    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }

    // Delete product
    public void deleteProduct(Product product) {
        productDao.delete(product);
    }
}

