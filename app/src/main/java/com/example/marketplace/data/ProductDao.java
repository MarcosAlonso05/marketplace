package com.example.marketplace.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.marketplace.model.Product;
import java.util.List;

@Dao
public interface ProductDao {

    @Query("SELECT * FROM products_table")
    List<Product> getAllProducts();

    @Query("SELECT * FROM products_table WHERE category = :category")
    List<Product> getProductsByCategory(String category);

    @Insert
    void insertAll(Product... products);

    @Query("DELETE FROM products_table")
    void deleteAll();

    @Query("SELECT * FROM products_table WHERE name LIKE '%' || :searchQuery || '%'")
    List<Product> searchProducts(String searchQuery);
}