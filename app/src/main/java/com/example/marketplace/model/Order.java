package com.example.marketplace.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "orders_table")
public class Order {
    @PrimaryKey
    public long orderId;
    public String userId;
    public String date;
    public double totalAmount;

    public Order(long orderId, String userId, String date, double totalAmount) {
        this.orderId = orderId;
        this.userId = userId;
        this.date = date;
        this.totalAmount = totalAmount;
    }
}