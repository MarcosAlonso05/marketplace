package com.example.marketplace.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart_table")
public class CartItem {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "product_name")
    private String name;

    @ColumnInfo(name = "price")
    private double price;

    @ColumnInfo(name = "image_url")
    private String imageUrl;

    @ColumnInfo(name = "user_id")
    private String userId;

    public CartItem() {
    }

    @Ignore
    public CartItem(String name, double price, String imageUrl, String userId) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.userId = userId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
}