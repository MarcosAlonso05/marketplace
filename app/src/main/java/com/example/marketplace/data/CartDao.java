package com.example.marketplace.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.marketplace.model.CartItem;
import java.util.List;

@Dao
public interface CartDao {

    @Insert
    void insert(CartItem item);

    @Query("SELECT * FROM cart_table")
    List<CartItem> getAllCartItems();

    @Query("DELETE FROM cart_table")
    void clearCart();

    // Opcional: Para borrar un solo item si te da tiempo
    @Query("DELETE FROM cart_table WHERE id = :id")
    void deleteById(int id);

    @androidx.room.Delete
    void delete(CartItem item);
}