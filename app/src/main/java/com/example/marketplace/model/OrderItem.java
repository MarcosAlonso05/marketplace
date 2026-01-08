package com.example.marketplace.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "order_items_table")
public class OrderItem {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public long orderId;
    public String productName;
    public double price;
    public String imageUrl;

    public OrderItem(long orderId, String productName, double price, String imageUrl) {
        this.orderId = orderId;
        this.productName = productName;
        this.price = price;
        this.imageUrl = imageUrl;
    }
}