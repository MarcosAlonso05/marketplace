package com.example.marketplace.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.marketplace.model.CartItem;
import com.example.marketplace.model.Product;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// IMPORTANTE: Versión 2 y añadimos CartItem.class a las entities
@Database(entities = {Product.class, CartItem.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ProductDao productDao();
    public abstract CartDao cartDao(); // Nuevo DAO para el carrito

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "marketplace_database")
                            .allowMainThreadQueries()
                            // IMPORTANTE: Esto borra y rehace la BD si cambias la versión
                            // Evita que la app crashee al añadir la tabla nueva
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}