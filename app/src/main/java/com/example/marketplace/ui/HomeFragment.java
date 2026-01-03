package com.example.marketplace.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marketplace.R;
import com.example.marketplace.data.AppDatabase;
import com.example.marketplace.model.Product;

import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView rvProducts;
    private AppDatabase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvProducts = view.findViewById(R.id.rvProducts);

        db = AppDatabase.getDatabase(requireContext());

        if (db.productDao().getAllProducts().isEmpty()) {
            preloadData();
        }

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        List<Product> products = db.productDao().getAllProducts();
        ProductAdapter adapter = new ProductAdapter(requireContext(), products);

        rvProducts.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        rvProducts.setAdapter(adapter);
    }

    private void preloadData() {
        Product p1 = new Product("Smartphone X", "Último modelo 5G", 999.99, "https://via.placeholder.com/300/0000FF/808080?text=Phone", "Electronics");
        Product p2 = new Product("Zapatillas Run", "Para correr maratones", 59.99, "https://via.placeholder.com/300/FF0000/FFFFFF?text=Shoes", "Sports");
        Product p3 = new Product("Auriculares Pro", "Cancelación de ruido", 199.50, "https://via.placeholder.com/300/FFFF00/000000?text=Audio", "Electronics");
        Product p4 = new Product("Cafetera Auto", "El mejor café", 89.90, "https://via.placeholder.com/300/008000/FFFFFF?text=Coffee", "Home");
        Product p5 = new Product("Reloj Smart", "Mide tus pasos", 150.00, "https://via.placeholder.com/300/000000/FFFFFF?text=Watch", "Electronics");
        Product p6 = new Product("Mochila Viaje", "Resistente al agua", 45.00, "https://via.placeholder.com/300/FFA500/000000?text=Bag", "Travel");

        db.productDao().insertAll(p1, p2, p3, p4, p5, p6);
        Toast.makeText(requireContext(), "Datos de prueba cargados", Toast.LENGTH_SHORT).show();
    }
}