# Módulo de Gestión de Datos - CryptoTracker

Este módulo se encarga de la persistencia y manejo de la información relacionada con las criptomonedas y su historial de precios. Utiliza Room, un ORM para SQLite en Android, para facilitar el almacenamiento, consulta y actualización de datos. A continuación se describe en detalle cada uno de los archivos que componen este módulo.

---

## Tabla de Contenidos

- [AppDatabase.java](#appdatabasejava)
- [CryptoCurrency.java](#cryptocurrencyjava)
- [CryptoDao.java](#cryptodaojava)
- [PriceHistory.java](#pricehistoryjava)
- [PriceHistoryDao.java](#pricehistorydaojava)
- [Conclusión](#conclusión)

---

## AppDatabase.java

**Propósito:**  
Configurar la base de datos de la aplicación utilizando Room y proveer acceso a los DAOs para las entidades.

**Características Principales:**

- **Definición de la Base de Datos:**
    - Utiliza la anotación `@Database` para declarar que la base de datos contiene las entidades `CryptoCurrency` y `PriceHistory`.
    - La versión de la base de datos se establece en `2` y se desactiva la exportación del esquema (`exportSchema = false`).

- **Patrón Singleton:**
    - Implementa el patrón Singleton para asegurar que exista una única instancia de la base de datos en toda la aplicación.
    - Se sincroniza la creación de la instancia para evitar condiciones de carrera en entornos multihilo.

- **Manejo Asíncrono:**
    - Define un `ExecutorService` con un pool de 4 hilos para realizar operaciones de escritura de forma asíncrona, evitando bloquear la UI.

- **Migraciones Destructivas:**
    - Se utiliza `fallbackToDestructiveMigration()`, lo que permite recrear la base de datos en caso de cambios en el esquema, eliminando los datos previos.

---

## CryptoCurrency.java

**Propósito:**  
Representar una criptomoneda en la base de datos, definiendo sus atributos y restricciones.

**Características Principales:**

- **Entidad de Room:**
    - Anotada con `@Entity` para definir la tabla `criptomonedas`.
    - Se establece un índice único en el campo `symbol` para evitar registros duplicados.

- **Atributos de la Criptomoneda:**
    - `id`: Clave primaria auto-generada.
    - `name`: Nombre de la criptomoneda.
    - `symbol`: Símbolo único de la criptomoneda.
    - `current_price`: Precio actual.
    - `price_change_percentage_24h`: Variación porcentual en 24 horas.
    - `market_cap`: Capitalización de mercado.
    - `last_updated`: Fecha y hora de la última actualización.
    - `isFavorite`: Indicador booleano para marcar la criptomoneda como favorita.

- **Métodos:**
    - Constructor para inicializar la entidad.
    - Métodos getter y setter para cada atributo.

---

## CryptoDao.java

**Propósito:**  
Definir los métodos de acceso a la base de datos para la entidad `CryptoCurrency`.

**Operaciones Principales:**

- **Inserción:**
    - `insertCrypto(CryptoCurrency crypto)`: Inserta una nueva criptomoneda en la base de datos. Utiliza la estrategia `OnConflictStrategy.IGNORE` para evitar duplicados en caso de conflicto.

- **Consultas:**
    - `getAllCryptos()`: Recupera todas las criptomonedas almacenadas, devolviendo un `LiveData<List<CryptoCurrency>>` para permitir la observación de cambios.
    - `getFavoriteCryptos()`: Recupera únicamente las criptomonedas marcadas como favoritas.

- **Actualización:**
    - `updateCrypto(CryptoCurrency crypto)`: Actualiza la información de una criptomoneda existente.

---

## PriceHistory.java

**Propósito:**  
Representar el historial de precios de cada criptomoneda, permitiendo llevar un registro de la evolución de los precios.

**Características Principales:**

- **Entidad de Room:**
    - Anotada con `@Entity` para definir la tabla `historial_precios`.

- **Relación con CryptoCurrency:**
    - Define una clave foránea `crypto_id` que referencia el campo `id` de la entidad `CryptoCurrency`.
    - Se establece `onDelete = CASCADE`, lo que garantiza que al eliminar una criptomoneda, se elimine automáticamente su historial asociado.
    - Se crea un índice en `crypto_id` para optimizar las consultas.

- **Atributos del Historial:**
    - `id`: Clave primaria auto-generada.
    - `crypto_id`: Identificador de la criptomoneda a la que pertenece el registro.
    - `price`: Precio en el momento del registro.
    - `timestamp`: Marca temporal del registro.

- **Métodos:**
    - Constructor para inicializar la entidad.
    - Métodos getter y setter para cada atributo.

---

## PriceHistoryDao.java

**Propósito:**  
Definir los métodos de acceso a la base de datos para el historial de precios.

**Operaciones Principales:**

- **Inserción:**
    - `insertPriceHistory(PriceHistory priceHistory)`: Inserta un nuevo registro en el historial de precios.

- **Consulta:**
    - `getHistoryForCrypto(int cryptoId)`: Recupera la lista de registros del historial de precios para una criptomoneda específica, ordenados cronológicamente por `timestamp`.

---

## Conclusión

Este módulo de gestión de datos es fundamental para la aplicación CryptoTracker, ya que:
- Permite almacenar de forma persistente la información actualizada de las criptomonedas.
- Guarda un historial de precios que facilita el análisis y la visualización de tendencias a lo largo del tiempo.
- Utiliza Room para simplificar la implementación y el mantenimiento del almacenamiento local.
- Proporciona un conjunto claro de DAOs que facilitan las operaciones CRUD sobre las entidades.

Con esta estructura, la aplicación está bien equipada para manejar tanto datos en tiempo real (obtenidos de la API) como datos históricos, garantizando una experiencia de usuario robusta y confiable.

