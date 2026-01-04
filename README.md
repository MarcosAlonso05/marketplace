# Marketplace Android App (POC)

Una aplicación nativa de comercio electrónico desarrollada en **Java** para Android. Esta Prueba de Concepto (POC) demuestra una arquitectura moderna utilizando componentes de Jetpack, persistencia de datos local y autenticación en la nube.

## Características Principales

* **Autenticación Segura:** Login y Registro de usuarios mediante **Firebase Auth**.
* **Catálogo de Productos:** Visualización de productos en cuadrícula con carga de imágenes remota.
* **Búsqueda en Tiempo Real:** Filtrado de productos instantáneo mediante consultas SQL personalizadas.
* **Carrito Persistente y Multi-usuario:**
    * Cada usuario tiene su propio carrito independiente.
    * Los datos persisten localmente (Room Database) incluso si se cierra la app.
    * Cálculo automático del precio total.
* **Detalle de Producto:** Vista ampliada con descripción y botón de compra.
* **Perfil de Usuario:** Visualización de datos de la cuenta y gestión de cierre de sesión.

## Estructura del Proyecto

El código está organizado en paquetes para mantener la separación de responsabilidades:

```text
com.example.marketplace
├── auth/          # Actividades de Login y Registro
├── data/          # Configuración de Room (Database y DAOs)
├── model/         # Entidades de datos (Product, CartItem)
└── ui/            # Fragments, Adapters y Activities de la interfaz
```

## Docs

Mas información en la carpeta docs

## Enlace al repositorio

```
https://github.com/MarcosAlonso05/marketplace
```
