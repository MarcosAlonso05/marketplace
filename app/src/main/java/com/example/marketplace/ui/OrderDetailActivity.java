package com.example.marketplace.ui;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marketplace.R;
import com.example.marketplace.data.AppDatabase;
import com.example.marketplace.model.OrderItem;

import java.util.List;

public class OrderDetailActivity extends AppCompatActivity {

    private TextView tvTitle, tvTotal;
    private RecyclerView rvItems;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Detalle del Pedido");
        }

        tvTitle = findViewById(R.id.tvOrderTitle);
        tvTotal = findViewById(R.id.tvOrderTotal);
        rvItems = findViewById(R.id.rvOrderItems);

        long orderId = getIntent().getLongExtra("ORDER_ID", -1);
        double totalAmount = getIntent().getDoubleExtra("ORDER_TOTAL", 0.0);

        if (orderId == -1) {
            Toast.makeText(this, "Error al cargar el pedido", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        tvTitle.setText("Pedido #" + orderId);
        tvTotal.setText(String.format("Total Pagado: %.2f €", totalAmount));

        db = AppDatabase.getDatabase(this);
        List<OrderItem> items = db.orderDao().getItemsByOrderId(orderId);

        setupRecyclerView(items);
    }

    private void setupRecyclerView(List<OrderItem> items) {
        OrderDetailAdapter adapter = new OrderDetailAdapter(items);
        rvItems.setLayoutManager(new LinearLayoutManager(this));
        rvItems.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}