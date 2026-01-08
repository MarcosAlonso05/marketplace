package com.example.marketplace.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marketplace.R;
import com.example.marketplace.data.AppDatabase;
import com.example.marketplace.model.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView rvProducts;
    private ImageButton btnFilter;
    private AppDatabase db;

    private List<Product> allProductsList = new ArrayList<>();

    private final String[] categories = {"TODOS", "Electronics", "Fashion", "Sports", "Home", "Travel"};
    private boolean[] selectedCategories;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvProducts = view.findViewById(R.id.rvProducts);
        btnFilter = view.findViewById(R.id.btnFilter);
        db = AppDatabase.getDatabase(requireContext());

        selectedCategories = new boolean[categories.length];
        Arrays.fill(selectedCategories, true);

        if (db.productDao().getAllProducts().isEmpty()) {
            preloadData();
        }

        allProductsList = db.productDao().getAllProducts();

        setupRecyclerView(allProductsList);

        btnFilter.setOnClickListener(v -> showFilterDialog());
    }

    private void showFilterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Filtrar por Categoría");

        builder.setMultiChoiceItems(categories, selectedCategories, (dialog, which, isChecked) -> {

            if (which == 0) {
                for (int i = 0; i < selectedCategories.length; i++) {
                    selectedCategories[i] = isChecked;
                    ((AlertDialog) dialog).getListView().setItemChecked(i, isChecked);
                }
            } else {
                selectedCategories[which] = isChecked;

                if (!isChecked) {
                    selectedCategories[0] = false;
                    ((AlertDialog) dialog).getListView().setItemChecked(0, false);
                }
            }
        });

        builder.setPositiveButton("Aplicar", (dialog, which) -> {
            applyFilters();
        });

        builder.setNegativeButton("Cancelar", null);

        builder.show();
    }

    private void applyFilters() {
        List<Product> filteredList = new ArrayList<>();
        boolean isAllSelected = selectedCategories[0];

        if (isAllSelected) {
            filteredList.addAll(allProductsList);
        } else {
            for (Product p : allProductsList) {
                for (int i = 1; i < categories.length; i++) {
                    if (selectedCategories[i] && p.getCategory().equalsIgnoreCase(categories[i])) {
                        filteredList.add(p);
                        break;
                    }
                }
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(requireContext(), "No hay productos con esos filtros", Toast.LENGTH_SHORT).show();
        }

        setupRecyclerView(filteredList);
    }

    private void setupRecyclerView(List<Product> products) {
        ProductAdapter adapter = new ProductAdapter(requireContext(), products);
        rvProducts.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        rvProducts.setAdapter(adapter);
    }

    private void preloadData() {
        Product p1 = new Product("Smartphone X", "Último modelo 5G", 999.99, "https://via.placeholder.com/300", "Electronics");
        Product p2 = new Product("Zapatillas Run", "Para correr maratones", 59.99, "https://via.placeholder.com/300", "Sports");
        Product p3 = new Product("Auriculares Pro", "Cancelación de ruido", 199.50, "https://via.placeholder.com/300", "Electronics");
        Product p4 = new Product("Cafetera Auto", "El mejor café", 89.90, "https://via.placeholder.com/300", "Home");
        Product p5 = new Product("Reloj Smart", "Mide tus pasos", 150.00, "https://via.placeholder.com/300", "Electronics");
        Product p6 = new Product("Mochila Viaje", "Resistente al agua", 45.00, "https://via.placeholder.com/300", "Travel");
        Product p7 = new Product("Camiseta", "Algodón 100%", 19.90, "https://via.placeholder.com/300", "Fashion");

        db.productDao().insertAll(p1, p2, p3, p4, p5, p6, p7);
    }
}