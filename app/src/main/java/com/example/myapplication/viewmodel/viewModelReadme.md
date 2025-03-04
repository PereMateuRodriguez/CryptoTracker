# CryptoViewModel - Capa de Presentación (ViewModel)

Este archivo implementa el ViewModel para la aplicación CryptoTracker. La clase `CryptoViewModel` extiende `AndroidViewModel` para proporcionar un contexto de aplicación y se encarga de gestionar y exponer los datos relacionados con las criptomonedas favoritas a la interfaz de usuario. Su función principal es actuar como intermediario entre la capa de repositorio (que gestiona el acceso a los datos) y la UI, siguiendo el patrón de arquitectura MVVM.

---

## Tabla de Contenidos

- [Introducción](#introducción)
- [Propósito](#propósito)
- [Estructura y Funcionamiento](#estructura-y-funcionamiento)
    - [Inicialización](#inicialización)
    - [Exposición de Datos](#exposición-de-datos)
    - [Operaciones de Actualización e Inserción](#operaciones-de-actualización-e-inserción)
- [Uso en la Aplicación](#uso-en-la-aplicación)
- [Conclusión](#conclusión)

---

## Introducción

El `CryptoViewModel` es parte de la capa de presentación en la arquitectura MVVM. Al extender `AndroidViewModel`, este componente se beneficia del ciclo de vida de la aplicación, permitiendo que los datos sobrevivan a cambios en la configuración (como rotaciones de pantalla) y facilitando la comunicación entre la UI y el repositorio.

## Propósito

El ViewModel se encarga de:
- Proveer una fuente de datos observables (`LiveData`) con la lista de criptomonedas marcadas como favoritas.
- Exponer métodos para actualizar e insertar criptomonedas en la base de datos, delegando estas operaciones en el repositorio.
- Asegurar que la UI se actualice de forma reactiva cuando los datos cambian, sin necesidad de gestionar directamente los hilos o las operaciones de base de datos.

## Estructura y Funcionamiento

### Inicialización

- **Constructor:**  
  En el constructor se realiza lo siguiente:
    - Se invoca el constructor de `AndroidViewModel` pasándole el contexto de la aplicación.
    - Se crea una instancia de `CryptoRepository`, que se encarga de las operaciones de acceso a datos.
    - Se obtiene un `LiveData<List<CryptoCurrency>>` mediante el método `getFavoriteCryptos()` del repositorio, el cual contiene las criptomonedas marcadas como favoritas.

### Exposición de Datos

- **Método `getFavoriteCryptos()`:**  
  Este método retorna el `LiveData` que contiene la lista de criptomonedas favoritas. La UI puede observar este `LiveData` para actualizarse automáticamente cuando se produzcan cambios en los datos.

### Operaciones de Actualización e Inserción

- **Método `updateCrypto(CryptoCurrency crypto)`:**  
  Permite actualizar la información de una criptomoneda existente. Llama al método correspondiente en el repositorio para ejecutar la operación en un hilo separado.

- **Método `insertCrypto(CryptoCurrency crypto)`:**  
  Permite insertar una nueva criptomoneda en la base de datos. Este método también delega la operación en el repositorio, asegurando que se realice de forma asíncrona.

## Uso en la Aplicación

El `CryptoViewModel` se integra en la arquitectura MVVM de la aplicación, siendo utilizado por la UI (por ejemplo, en actividades o fragmentos) para:
- Observar y mostrar la lista de criptomonedas favoritas.
- Ejecutar operaciones de inserción y actualización sin preocuparse por la gestión de hilos o el acceso directo a la base de datos.
- Mantener los datos actualizados y persistentes durante los cambios de configuración.

## Conclusión

El `CryptoViewModel` es un componente esencial en la estructura MVVM de CryptoTracker, ya que:
- Facilita la separación de responsabilidades entre la lógica de negocio (repositorio) y la presentación.
- Proporciona datos observables que garantizan una UI reactiva y actualizada.
- Simplifica la interacción con la capa de datos al delegar operaciones de inserción y actualización en el repositorio, asegurando un manejo adecuado de los hilos.

Con este diseño, la aplicación logra una arquitectura escalable y mantenible, mejorando tanto la experiencia del usuario como la calidad del código.
