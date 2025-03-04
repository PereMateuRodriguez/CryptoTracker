# CryptoTracker - Documentación Completa

CryptoTracker es una aplicación Android para el seguimiento en tiempo real de criptomonedas. La aplicación obtiene datos actualizados desde la API de CoinGecko, los almacena en una base de datos local usando Room y los presenta en una interfaz intuitiva. La arquitectura del proyecto sigue el patrón MVVM, facilitando la separación de responsabilidades y el mantenimiento del código.

---

## Tabla de Contenidos

1. [Visión General y Arquitectura](#visión-general-y-arquitectura)
2. [Módulo de Datos](#módulo-de-datos)
    - [AppDatabase.java](#appdatabasejava)
    - [CryptoCurrency.java](#cryptocurrencyjava)
    - [CryptoDao.java](#cryptodaojava)
    - [PriceHistory.java](#pricehistoryjava)
    - [PriceHistoryDao.java](#pricehistorydaojava)
3. [Módulo de Red](#módulo-de-red)
    - [CoinGeckoService.java](#coingeckoservicejava)
    - [CryptoCurrencyResponse.java](#cryptocurrencyresponsejava)
    - [MarketChartResponse.java](#marketchartresponsejava)
    - [RetrofitClient.java](#retrofitclientjava)
4. [Módulo de Repositorio](#módulo-de-repositorio)
    - [CryptoRepository.java](#cryptorepositoryjava)
5. [Módulo de UI](#módulo-de-ui)
    - [CryptoAdapter.java](#cryptoadapterjava)
6. [Módulo de ViewModel](#módulo-de-viewmodel)
    - [CryptoViewModel.java](#cryptoviewmodeljava)
7. [Actividades (UI Principal)](#actividades)
    - [CryptoDetailActivity.java](#cryptodetailactivityjava)
    - [MainActivity.java](#mainactivityjava)
    - [SearchActivity.java](#searchactivityjava)
8. [Conclusión](#conclusión)

---

## Visión General y Arquitectura

CryptoTracker se compone de varios módulos interconectados:

- **Datos:** Utiliza Room para gestionar la persistencia local de las criptomonedas y su historial de precios.
- **Red:** Emplea Retrofit y Gson para comunicarse con la API de CoinGecko y deserializar los datos JSON.
- **Repositorio:** Sirve de puente entre los datos locales y la interfaz de usuario.
- **UI:** Incluye adaptadores y actividades para la visualización y la interacción del usuario.
- **ViewModel:** Gestiona la lógica de presentación y provee datos observables a la UI.

Esta separación permite que la aplicación sea escalable, fácil de mantener y con una experiencia de usuario reactiva.

---

## Módulo de Datos

### AppDatabase.java

- **Propósito:**  
  Configura la base de datos SQLite usando Room y proporciona acceso a los DAOs.

- **Características:**
    - Define las entidades `CryptoCurrency` y `PriceHistory`.
    - Implementa el patrón Singleton para una única instancia de la base de datos.
    - Provee un `ExecutorService` con un pool de 4 hilos para operaciones asíncronas.
    - Usa `fallbackToDestructiveMigration()` para manejar cambios de esquema.

### CryptoCurrency.java

- **Propósito:**  
  Representa una criptomoneda en la base de datos.

- **Características:**
    - Campos: `id`, `name`, `symbol`, `current_price`, `price_change_percentage_24h`, `market_cap`, `last_updated` y `isFavorite`.
    - El campo `symbol` se indexa para evitar duplicados.
    - Incluye métodos getter y setter.

### CryptoDao.java

- **Propósito:**  
  Define los métodos de acceso a datos para la entidad `CryptoCurrency`.

- **Características:**
    - Método de inserción con estrategia de conflicto `IGNORE`.
    - Consultas para obtener todas las criptomonedas y las marcadas como favoritas.
    - Método de actualización para modificar registros existentes.

### PriceHistory.java

- **Propósito:**  
  Representa el historial de precios de una criptomoneda.

- **Características:**
    - Campos: `id`, `crypto_id`, `price` y `timestamp`.
    - Establece una relación foránea con `CryptoCurrency` (con eliminación en cascada).
    - Optimiza consultas mediante un índice en `crypto_id`.

### PriceHistoryDao.java

- **Propósito:**  
  Proporciona métodos para insertar y consultar el historial de precios.

- **Características:**
    - Método para insertar un nuevo registro.
    - Consulta para obtener el historial de una criptomoneda ordenado cronológicamente.

---

## Módulo de Red

### CoinGeckoService.java

- **Propósito:**  
  Define los endpoints de la API de CoinGecko.

- **Características:**
    - `getMarkets`: Solicita datos de mercado de criptomonedas.
    - `getMarketChart`: Solicita el historial de precios para una criptomoneda específica.

### CryptoCurrencyResponse.java

- **Propósito:**  
  Modela la respuesta JSON para los datos del mercado de criptomonedas.

- **Características:**
    - Campos: `id`, `symbol`, `name`, `current_price`, `market_cap`, `price_change_percentage_24h` y `last_updated`.

### MarketChartResponse.java

- **Propósito:**  
  Modela la respuesta JSON del endpoint de historial de precios.

- **Características:**
    - Contiene una lista de listas de `Double` representando los puntos de datos de precios.

### RetrofitClient.java

- **Propósito:**  
  Configura y proporciona una instancia de Retrofit para las solicitudes a la API.

- **Características:**
    - Define la URL base de la API.
    - Usa `GsonConverterFactory` para deserializar JSON.
    - Implementa el patrón Singleton para gestionar la instancia de Retrofit.

---

## Módulo de Repositorio

### CryptoRepository.java

- **Propósito:**  
  Actúa como mediador entre la base de datos y la UI.

- **Características:**
    - Provee acceso a la lista de criptomonedas favoritas a través de `LiveData`.
    - Métodos para actualizar e insertar criptomonedas, ejecutados de forma asíncrona con el `ExecutorService` de `AppDatabase`.

---

## Módulo de UI

### CryptoAdapter.java

- **Propósito:**  
  Es el adaptador del RecyclerView, encargado de mostrar los datos de las criptomonedas en una lista.

- **Características:**
    - Infla el layout `item_crypto.xml` para cada elemento.
    - Vincula los datos (nombre, precio y cambio porcentual) a las vistas.
    - Maneja eventos de clic en cada ítem mediante una interfaz.
    - Permite actualizar la lista de datos dinámicamente.

---

## Módulo de ViewModel

### CryptoViewModel.java

- **Propósito:**  
  Sirve de puente entre el repositorio y la UI, ofreciendo datos observables y métodos para manipularlos.

- **Características:**
    - Expone `LiveData` para la lista de criptomonedas favoritas.
    - Proporciona métodos para actualizar e insertar datos.
    - Sobrevive a cambios de configuración gracias a su naturaleza lifecycle-aware.

---

## Actividades

### CryptoDetailActivity.java

- **Propósito:**  
  Muestra la información detallada de una criptomoneda seleccionada.

- **Características:**
    - Presenta detalles como nombre, símbolo, precio actual, cambio en 24h, capitalización y última actualización.
    - Incorpora un gráfico de precios históricos usando GraphView.
    - Incluye un botón para eliminar la criptomoneda de los favoritos, actualizando la base de datos.
    - Realiza una solicitud a la API para obtener el historial de precios (últimos 7 días) y lo muestra en el gráfico.
    - Proporciona retroalimentación al usuario a través de Toasts para errores y acciones exitosas.

### MainActivity.java

- **Propósito:**  
  Es la pantalla principal de la aplicación, mostrando la lista de criptomonedas favoritas.

- **Características:**
    - Configura un RecyclerView con `CryptoAdapter` para mostrar la lista.
    - Observa el `LiveData` del `CryptoViewModel` para actualizar la UI en tiempo real.
    - Maneja los clics en los ítems para abrir `CryptoDetailActivity` con los detalles del elemento seleccionado.
    - Incluye un menú en el Toolbar que permite lanzar `SearchActivity` para agregar nuevas criptomonedas.

### SearchActivity.java

- **Propósito:**  
  Permite al usuario buscar y agregar nuevas criptomonedas a su lista de favoritos.

- **Características:**
    - Utiliza un SearchView para filtrar criptomonedas obtenidas de la API.
    - Muestra los resultados de búsqueda en un RecyclerView utilizando `CryptoAdapter`.
    - Al hacer clic en un resultado, la criptomoneda se marca como favorita y se inserta en la base de datos.
    - Proporciona retroalimentación al usuario mediante Toasts y maneja errores de conexión o límites de la API.

---

## Conclusión

CryptoTracker integra de manera efectiva múltiples tecnologías y patrones de diseño modernos en Android:

- **Room y SQLite:** Para una gestión robusta del almacenamiento local.
- **Retrofit y Gson:** Para la comunicación con la API de CoinGecko y la conversión de datos.
- **LiveData y ViewModel:** Para asegurar que la UI se mantenga actualizada y responda adecuadamente a los cambios de datos.
- **Arquitectura MVVM:** Para separar responsabilidades y facilitar el mantenimiento y escalabilidad de la aplicación.
- **Experiencia de Usuario:** Con actividades bien definidas (MainActivity, CryptoDetailActivity y SearchActivity) que ofrecen una navegación intuitiva y respuestas inmediatas a las acciones del usuario.

Este diseño modular y bien estructurado garantiza que CryptoTracker proporcione una experiencia de usuario fluida y confiable, facilitando al mismo tiempo futuras mejoras y expansiones de funcionalidades.

