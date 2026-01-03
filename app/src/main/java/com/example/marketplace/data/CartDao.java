package com.example.marketplace.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.marketplace.model.CartItem;

import java.util.List;

@Dao
public interface CartDao {

    @Insert
    void insert(CartItem item);

    @Delete
    void delete(CartItem item);


    @Query("SELECT * FROM cart_table WHERE user_id = :userId")
    List<CartItem> getCartItemsForUser(String userId);

    @Query("DELETE FROM cart_table WHERE user_id = :userId")
    void clearCartForUser(String userId);

    @Query("DELETE FROM cart_table")
    void clearAllTable();
}