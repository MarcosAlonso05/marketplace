package com.example.marketplace.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.marketplace.R;
import com.example.marketplace.model.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private List<Product> productList;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflamos el diseño de la tarjeta (item_product.xml)
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        // Obtenemos el producto actual de la lista
        Product product = productList.get(position);

        // 1. Asignar textos
        holder.tvName.setText(product.getName());
        // Formateamos el precio para que quede bonito (ej: 10.50 €)
        holder.tvPrice.setText(String.format("%.2f €", product.getPrice()));

        // 2. Cargar imagen con Glide
        Glide.with(context)
                .load(product.getImageUrl())
                .placeholder(android.R.drawable.ic_menu_gallery) // Imagen de espera
                .error(android.R.drawable.ic_delete) // Imagen si falla la URL
                .into(holder.ivImage);

        // 3. EVENTO DE CLIC (NUEVO)
        // Al pulsar en toda la tarjeta (itemView), vamos al detalle
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            // Pasamos el objeto entero (Product implementa Serializable)
            intent.putExtra("PRODUCT", product);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    // Clase interna ViewHolder (Referencia a los elementos visuales)
    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice;
        ImageView ivImage;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvProductName);
            tvPrice = itemView.findViewById(R.id.tvProductPrice);
            ivImage = itemView.findViewById(R.id.ivProductImage);
        }
    }
}