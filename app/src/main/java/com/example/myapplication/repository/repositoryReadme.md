# CryptoRepository - Repositorio de Criptomonedas

Este archivo implementa el repositorio para la aplicación CryptoTracker, sirviendo de puente entre la capa de datos (usando Room para SQLite) y la interfaz de usuario. El repositorio centraliza la lógica de acceso a los datos, facilitando la actualización y consulta de criptomonedas de manera asíncrona.

---

## Tabla de Contenidos

- [Introducción](#introducción)
- [Propósito](#propósito)
- [Estructura y Funcionamiento](#estructura-y-funcionamiento)
    - [Inicialización](#inicialización)
    - [Operaciones CRUD](#operaciones-crud)
- [Uso en la Aplicación](#uso-en-la-aplicación)
- [Conclusión](#conclusión)

---

## Introducción

El repositorio actúa como una capa intermedia entre la base de datos local y las capas superiores de la aplicación (como los ViewModels). Esto permite desacoplar la lógica de acceso a datos de la lógica de presentación, facilitando la escalabilidad y el mantenimiento del código.

## Propósito

CryptoRepository se encarga de:
- Proveer acceso a la lista de criptomonedas marcadas como favoritas a través de `LiveData`, lo que permite que la interfaz de usuario se actualice automáticamente al cambiar los datos.
- Ejecutar operaciones de inserción y actualización en la base de datos de manera asíncrona utilizando un `ExecutorService`, para evitar bloquear el hilo principal.

## Estructura y Funcionamiento

### Inicialización

- **Constructor:**
    - Se obtiene una instancia de `AppDatabase` mediante el método `getDatabase()`, que implementa el patrón singleton.
    - Se inicializa el `CryptoDao` a partir de la instancia de la base de datos.
    - Se recupera el listado de criptomonedas favoritas mediante el método `getFavoriteCryptos()` del DAO, el cual retorna un `LiveData<List<CryptoCurrency>>`.

### Operaciones CRUD

- **Obtener Criptomonedas Favoritas:**
    - Método: `getFavoriteCryptos()`
    - Retorna un `LiveData<List<CryptoCurrency>>` que permite a la interfaz de usuario observar los cambios en tiempo real y actualizarse automáticamente cuando la base de datos se modifica.

- **Actualizar una Criptomoneda:**
    - Método: `updateCrypto(final CryptoCurrency crypto)`
    - Utiliza el `databaseWriteExecutor` para ejecutar la actualización en un hilo separado, garantizando que la operación no bloquee la interfaz de usuario.

- **Insertar una Criptomoneda:**
    - Método: `insertCrypto(final CryptoCurrency crypto)`
    - Realiza la inserción de un nuevo registro en la base de datos de forma asíncrona utilizando el `databaseWriteExecutor`.

## Uso en la Aplicación

El repositorio es empleado por las capas superiores, como los ViewModels, para:
- **Observar cambios:** La interfaz de usuario se suscribe al `LiveData` proporcionado por `getFavoriteCryptos()`, recibiendo actualizaciones automáticas cuando se modifica la lista de criptomonedas favoritas.
- **Realizar operaciones de escritura:** Los métodos `insertCrypto()` y `updateCrypto()` permiten modificar la base de datos sin afectar la fluidez de la interfaz, ya que se ejecutan en hilos de fondo.

## Conclusión

CryptoRepository es una parte esencial de la arquitectura de CryptoTracker. Al centralizar el acceso a la base de datos y manejar las operaciones de forma asíncrona, garantiza una experiencia de usuario fluida y una integración limpia entre la capa de datos y la interfaz de usuario. Esto no solo mejora el rendimiento de la aplicación, sino que también simplifica el mantenimiento y la escalabilidad del código.
