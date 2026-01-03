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

public class ProductDetailActivity extends AppCompatActivity {

    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Configurar la flecha de volver atrás (ActionBar)
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Detalle del Producto");
        }

        // Recibir el objeto Product del Intent
        product = (Product) getIntent().getSerializableExtra("PRODUCT");

        if (product != null) {
            setupUI();
        }
    }

    private void setupUI() {
        // Vincular vistas
        ImageView ivImage = findViewById(R.id.ivDetailImage);
        TextView tvName = findViewById(R.id.tvDetailName);
        TextView tvPrice = findViewById(R.id.tvDetailPrice);
        TextView tvDesc = findViewById(R.id.tvDetailDescription);
        Button btnAdd = findViewById(R.id.btnAddToCart);

        // Rellenar datos
        tvName.setText(product.getName());
        tvPrice.setText(String.format("%.2f €", product.getPrice()));
        tvDesc.setText(product.getDescription());

        Glide.with(this).load(product.getImageUrl()).into(ivImage);

        // --- LÓGICA DEL CARRITO ---
        btnAdd.setOnClickListener(v -> {
            // 1. Crear el objeto para el carrito
            CartItem item = new CartItem(
                    product.getName(),
                    product.getPrice(),
                    product.getImageUrl()
            );

            // 2. Insertar en la Base de Datos
            AppDatabase.getDatabase(this).cartDao().insert(item);

            // 3. Feedback al usuario
            Toast.makeText(this, "¡Añadido al carrito!", Toast.LENGTH_SHORT).show();

            // 4. Volver a la lista (Opcional, pero recomendado)
            finish();
        });
    }

    // Funcionalidad de la flecha atrás
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}