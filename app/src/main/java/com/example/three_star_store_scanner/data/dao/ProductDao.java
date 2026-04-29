package com.example.three_star_store_scanner.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.three_star_store_scanner.data.entity.Product;

import java.util.List;

@Dao
public interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Product product);

    @Query("SELECT * FROM products WHERE barcode = :barcode LIMIT 1")
    Product findByBarcode(String barcode);

    @Query("SELECT * FROM products")
    List<Product> getAllProducts();

    @Delete
    void delete(Product product);
}
