package com.example.marketplace.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.marketplace.model.Order;
import com.example.marketplace.model.OrderItem;
import java.util.List;

@Dao
public interface OrderDao {
    @Insert
    void insertOrder(Order order);

    @Insert
    void insertOrderItems(List<OrderItem> items);

    @Query("SELECT * FROM orders_table WHERE userId = :userId ORDER BY orderId DESC")
    List<Order> getOrdersByUser(String userId);

    @Query("SELECT * FROM order_items_table WHERE orderId = :orderId")
    List<OrderItem> getItemsByOrderId(long orderId);
}