package com.example.marketplace.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.marketplace.R;
import com.example.marketplace.data.AppDatabase;
import com.example.marketplace.model.CartItem;
import com.example.marketplace.model.Product;
import com.google.firebase.auth.FirebaseAuth;

public class ProductDetailActivity extends AppCompatActivity {

    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Detalle del Producto");
        }

        product = (Product) getIntent().getSerializableExtra("PRODUCT");

        if (product != null) {
            setupUI();
        }
    }

    private void setupUI() {
        ImageView ivImage = findViewById(R.id.ivDetailImage);
        TextView tvName = findViewById(R.id.tvDetailName);
        TextView tvPrice = findViewById(R.id.tvDetailPrice);
        TextView tvDesc = findViewById(R.id.tvDetailDescription);
        Button btnAdd = findViewById(R.id.btnAddToCart);

        tvName.setText(product.getName());
        tvPrice.setText(String.format("%.2f €", product.getPrice()));
        tvDesc.setText(product.getDescription());

        Glide.with(this).load(product.getImageUrl()).into(ivImage);

        btnAdd.setOnClickListener(v -> {
            String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

            CartItem item = new CartItem(
                    product.getName(),
                    product.getPrice(),
                    product.getImageUrl(),
                    currentUserId
            );

            AppDatabase.getDatabase(this).cartDao().insert(item);

            Toast.makeText(this, "¡Añadido al carrito!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}