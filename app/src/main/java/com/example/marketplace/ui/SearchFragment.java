package com.example.marketplace.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marketplace.R;
import com.example.marketplace.data.AppDatabase;
import com.example.marketplace.model.Product;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private RecyclerView rvResults;
    private SearchView searchView;
    private AppDatabase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvResults = view.findViewById(R.id.rvSearchResults);
        searchView = view.findViewById(R.id.svSearch);
        db = AppDatabase.getDatabase(requireContext());

        setupRecyclerView(new ArrayList<>());

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                performSearch(newText);
                return true;
            }
        });
    }

    private void performSearch(String query) {
        if (query.isEmpty()) {
            setupRecyclerView(new ArrayList<>());
            return;
        }

        List<Product> results = db.productDao().searchProducts(query);
        setupRecyclerView(results);
    }

    private void setupRecyclerView(List<Product> products) {
        ProductAdapter adapter = new ProductAdapter(requireContext(), products);
        rvResults.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        rvResults.setAdapter(adapter);
    }
}