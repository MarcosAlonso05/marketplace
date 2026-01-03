package com.example.marketplace.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.marketplace.model.Product; // Asegúrate de importar tu clase Product
import java.util.List;

@Dao
public interface ProductDao {

    // Obtener todos los productos
    @Query("SELECT * FROM products_table")
    List<Product> getAllProducts();

    // Obtener productos por categoría (Para el requisito de Búsqueda/Filtro)
    @Query("SELECT * FROM products_table WHERE category = :category")
    List<Product> getProductsByCategory(String category);

    // Insertar productos (Para rellenar la tienda al inicio)
    @Insert
    void insertAll(Product... products);

    // Borrar todo (útil para limpiar al probar)
    @Query("DELETE FROM products_table")
    void deleteAll();

    @Query("SELECT * FROM products_table WHERE name LIKE '%' || :searchQuery || '%'")
    List<Product> searchProducts(String searchQuery);
}