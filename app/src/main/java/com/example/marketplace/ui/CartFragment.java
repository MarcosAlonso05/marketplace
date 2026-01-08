package com.example.marketplace.ui;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marketplace.R;
import com.example.marketplace.data.AppDatabase;
import com.example.marketplace.model.CartItem;
import com.example.marketplace.model.Order;
import com.example.marketplace.model.OrderItem;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CartFragment extends Fragment {

    private RecyclerView rvCart;
    private TextView tvTotal;
    private Button btnCheckout;
    private AppDatabase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvCart = view.findViewById(R.id.rvCart);
        tvTotal = view.findViewById(R.id.tvTotalPrice);
        btnCheckout = view.findViewById(R.id.btnCheckout);

        db = AppDatabase.getDatabase(requireContext());

        loadCart();

        btnCheckout.setOnClickListener(v -> {
            String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

            List<CartItem> currentCart = db.cartDao().getCartItemsForUser(currentUserId);

            if (currentCart.isEmpty()) {
                Toast.makeText(requireContext(), "El carrito está vacío", Toast.LENGTH_SHORT).show();
                return;
            }

            long orderId = System.currentTimeMillis();
            String date = DateFormat.format("dd/MM/yyyy", new Date()).toString();

            double totalAmount = 0;
            for (CartItem item : currentCart) {
                totalAmount += item.getPrice();
            }

            Order newOrder = new Order(orderId, currentUserId, date, totalAmount);

            List<OrderItem> orderItems = new ArrayList<>();
            for (CartItem item : currentCart) {
                OrderItem orderItem = new OrderItem(
                        orderId,
                        item.getName(),
                        item.getPrice(),
                        item.getImageUrl()
                );
                orderItems.add(orderItem);
            }

            db.orderDao().insertOrder(newOrder);
            db.orderDao().insertOrderItems(orderItems);

            db.cartDao().clearCartForUser(currentUserId);

            Toast.makeText(requireContext(), "¡Pedido guardado en tu historial!", Toast.LENGTH_LONG).show();

            loadCart();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadCart();
    }

    private void loadCart() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) return;

        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        List<CartItem> cartItems = db.cartDao().getCartItemsForUser(currentUserId);

        double totalAmount = 0;
        for (CartItem item : cartItems) {
            totalAmount += item.getPrice();
        }
        tvTotal.setText(String.format("%.2f €", totalAmount));

        CartAdapter adapter = new CartAdapter(requireContext(), cartItems, item -> {
            db.cartDao().delete(item);
            Toast.makeText(requireContext(), "Producto eliminado", Toast.LENGTH_SHORT).show();
            loadCart();
        });

        rvCart.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvCart.setAdapter(adapter);
    }
}