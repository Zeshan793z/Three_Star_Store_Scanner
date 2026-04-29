package com.example.three_star_store_scanner.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.three_star_store_scanner.data.dao.ProductDao;
import com.example.three_star_store_scanner.data.entity.Product;

@Database(entities = {Product.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDao productDao();
}