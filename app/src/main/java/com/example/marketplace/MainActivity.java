package com.example.marketplace;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.marketplace.auth.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. OCULTAR BARRA SUPERIOR (ActionBar)
        // Esto soluciona el problema de que la barra morada tape tu diseño
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // 2. SEGURIDAD (Auth Check)
        // Si no hay usuario logueado, lo mandamos al Login
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish(); // Importante: Cierra esta actividad para que no puedan volver atrás
            return;   // Detiene la ejecución aquí
        }

        // 3. CONFIGURACIÓN DE NAVEGACIÓN
        // Buscamos el contenedor de fragments (NavHost)
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);

            // Vinculamos la barra inferior con el controlador de navegación
            NavigationUI.setupWithNavController(bottomNav, navController);
        }
    }
}