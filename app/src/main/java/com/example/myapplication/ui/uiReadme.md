# Módulo UI - CryptoTracker

Este módulo se encarga de la representación de la información de criptomonedas en la interfaz de usuario. Utiliza un `RecyclerView` para mostrar una lista de criptomonedas y se implementa mediante el adaptador `CryptoAdapter`, que se encarga de vincular los datos de cada objeto `CryptoCurrency` con el layout definido para cada ítem.

---

## Tabla de Contenidos

- [CryptoAdapter.java](#cryptoadapterjava)
- [Funcionamiento y Características](#funcionamiento-y-características)
    - [Constructor e Inicialización](#constructor-e-inicialización)
    - [Inflado de Layout e Instanciación del ViewHolder](#inflado-de-layout-e-instanciación-del-viewholder)
    - [Vinculación de Datos](#vinculación-de-datos)
    - [Gestión de Eventos de Clic](#gestión-de-eventos-de-clic)
    - [Actualización de Datos](#actualización-de-datos)
- [Conclusión](#conclusión)

---

## CryptoAdapter.java

### Propósito

El archivo `CryptoAdapter.java` actúa como adaptador para un `RecyclerView` y se encarga de:
- Inflar el layout para cada ítem (definido en `item_crypto.xml`).
- Vincular los datos del objeto `CryptoCurrency` a las vistas correspondientes.
- Gestionar los eventos de clic en cada elemento de la lista para interactuar con el usuario.

### Funcionamiento y Características

#### Constructor e Inicialización

- **Constructor:**  
  El adaptador recibe:
    - Una lista de objetos `CryptoCurrency`.
    - Una interfaz `OnItemClickListener` para manejar los clics en los ítems.

  Esto permite inicializar la lista de datos y definir una acción cuando el usuario selecciona un elemento de la lista.

#### Inflado de Layout e Instanciación del ViewHolder

- **onCreateViewHolder:**
    - Se encarga de inflar el layout de cada ítem usando `LayoutInflater` con el layout definido en `R.layout.item_crypto`.
    - Crea y retorna una instancia de `CryptoViewHolder`, que contendrá las referencias a las vistas individuales del ítem.

#### Vinculación de Datos

- **onBindViewHolder:**
    - Para cada posición de la lista, se extrae el objeto `CryptoCurrency` y se asignan sus valores a las vistas:
        - `nameTextView` se establece con el nombre de la criptomoneda.
        - `priceTextView` muestra el precio actual.
        - `changeTextView` presenta el porcentaje de cambio en 24 horas, acompañado del símbolo `%`.

  Esta vinculación garantiza que la información mostrada sea coherente con los datos almacenados.

#### Gestión de Eventos de Clic

- **Interfaz OnItemClickListener:**
    - Permite definir una acción personalizada que se ejecuta cuando se hace clic en un ítem de la lista.

- **Implementación en onBindViewHolder:**
    - Se asigna un listener al `itemView` de cada ViewHolder que llama al método `onItemClick(crypto)` del listener definido, permitiendo que la aplicación responda a la selección del usuario (por ejemplo, mostrando detalles de la criptomoneda).

#### Actualización de Datos

- **setCryptos:**
    - Este método permite actualizar la lista de criptomonedas.
    - Se asigna la nueva lista y se notifica al adaptador con `notifyDataSetChanged()`, lo que obliga a refrescar el `RecyclerView` y mostrar los datos actualizados.

---

## Conclusión

El adaptador `CryptoAdapter` es un componente esencial en la capa de UI de CryptoTracker. Permite:
- Mostrar de manera clara y organizada la información de cada criptomoneda.
- Facilitar la interacción del usuario mediante eventos de clic en cada ítem.
- Actualizar dinámicamente la lista de datos a medida que se reciben nuevos valores o se modifican los existentes.

Este diseño modular y reutilizable contribuye a una experiencia de usuario fluida y a un código mantenible y escalable, integrándose fácilmente en una arquitectura basada en MVVM o similar.
