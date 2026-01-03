package com.example.marketplace.ui;

import android.os.Bundle;
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

import java.util.List;

public class CartFragment extends Fragment {

    private RecyclerView rvCart;
    private TextView tvTotal;
    private Button btnCheckout;
    private AppDatabase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflamos el diseño fragment_cart.xml
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1. Vincular vistas
        rvCart = view.findViewById(R.id.rvCart);
        tvTotal = view.findViewById(R.id.tvTotalPrice);
        btnCheckout = view.findViewById(R.id.btnCheckout);

        // 2. Inicializar Base de Datos
        db = AppDatabase.getDatabase(requireContext());

        // 3. Cargar datos iniciales
        loadCart();

        // 4. Botón de Finalizar Compra
        btnCheckout.setOnClickListener(v -> {
            // Simulamos el pago
            Toast.makeText(requireContext(), "¡Pedido realizado con éxito!", Toast.LENGTH_LONG).show();

            // Opcional: Vaciar el carrito al comprar
            db.cartDao().clearCart();

            // Recargar la pantalla (se quedará vacía)
            loadCart();
        });
    }

    // Cada vez que la pantalla se vuelve visible, recargamos por si acaso
    @Override
    public void onResume() {
        super.onResume();
        loadCart();
    }

    private void loadCart() {
        // A) Obtener todos los items de la BD
        List<CartItem> cartItems = db.cartDao().getAllCartItems();

        // B) Calcular el precio Total
        double totalAmount = 0;
        for (CartItem item : cartItems) {
            totalAmount += item.getPrice();
        }
        tvTotal.setText(String.format("%.2f €", totalAmount));

        // C) Configurar el Adaptador con el "Listener" de borrado
        CartAdapter adapter = new CartAdapter(requireContext(), cartItems, item -> {

            // --- ESTO OCURRE AL PULSAR LA X ROJA ---

            // 1. Borrar de la base de datos
            db.cartDao().delete(item);

            // 2. Mostrar mensaje
            Toast.makeText(requireContext(), "Eliminado: " + item.getName(), Toast.LENGTH_SHORT).show();

            // 3. RECURSIVIDAD: Volvemos a llamar a loadCart para que:
            //    - Se actualice la lista (el item desaparezca visualmente)
            //    - Se recalcule el precio total
            loadCart();
        });

        // D) Asignar al RecyclerView
        rvCart.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvCart.setAdapter(adapter);
    }
}