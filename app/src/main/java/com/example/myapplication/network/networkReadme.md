# Módulo de Red - CryptoTracker

Este módulo se encarga de la comunicación con la API pública de CoinGecko, permitiendo obtener información actualizada sobre criptomonedas y sus históricos de precios. Se utiliza Retrofit para simplificar las solicitudes HTTP y Gson para la conversión del JSON a objetos Java.

---

## Tabla de Contenidos

- [CoinGeckoService.java](#coingeckoservicejava)
- [CryptoCurrencyResponse.java](#cryptocurrencyresponsejava)
- [MarketChartResponse.java](#marketchartresponsejava)
- [RetrofitClient.java](#retrofitclientjava)
- [Conclusión](#conclusión)

---

## CoinGeckoService.java

**Propósito:**  
Define la interfaz de la API de CoinGecko para gestionar las solicitudes HTTP.

**Características Principales:**

- **Método `getMarkets`:**
    - Realiza una solicitud GET a `coins/markets` para obtener la lista de criptomonedas.
    - Recibe parámetros para configurar la moneda de referencia (`vs_currency`), el orden de los resultados (`order`), la cantidad de resultados por página (`per_page`), la página deseada (`page`) y si se desea incluir datos de sparkline (`sparkline`).
    - Devuelve un objeto `Call<List<CryptoCurrencyResponse>>` que encapsula la respuesta.

- **Método `getMarketChart`:**
    - Realiza una solicitud GET a `coins/{id}/market_chart` para obtener el historial de precios de una criptomoneda.
    - Utiliza el parámetro de ruta `{id}` para especificar la criptomoneda y parámetros de consulta para la moneda de referencia y el número de días de historial (`days`).
    - Devuelve un objeto `Call<MarketChartResponse>` con el historial de precios.

---

## CryptoCurrencyResponse.java

**Propósito:**  
Representar la respuesta JSON obtenida de la API para la información del mercado de criptomonedas.

**Características Principales:**

- **Campos:**
    - `id`: Identificador de la criptomoneda en CoinGecko.
    - `symbol`: Símbolo de la criptomoneda.
    - `name`: Nombre de la criptomoneda.
    - `current_price`: Precio actual.
    - `market_cap`: Capitalización de mercado.
    - `price_change_percentage_24h`: Variación porcentual en 24 horas.
    - `last_updated`: Fecha y hora de la última actualización.

- **Uso:**
    - Estos datos se transforman en objetos Java que serán usados para actualizar la base de datos y la interfaz de usuario.

---

## MarketChartResponse.java

**Propósito:**  
Modelar la respuesta JSON del endpoint que retorna el historial de precios (market chart) de una criptomoneda.

**Características Principales:**

- **Campo Principal:**
    - `prices`: Una lista de listas de `Double` que contiene los precios históricos. Cada sublista normalmente contiene un timestamp y el precio correspondiente.

- **Uso:**
    - La información se procesa para visualizar gráficos que muestran la evolución de los precios a lo largo del tiempo.

---

## RetrofitClient.java

**Propósito:**  
Configurar y proporcionar una instancia única de Retrofit para realizar las solicitudes a la API de CoinGecko.

**Características Principales:**

- **Configuración de Retrofit:**
    - Define la URL base (`https://api.coingecko.com/api/v3/`).
    - Configura Retrofit para utilizar `GsonConverterFactory` para la conversión de JSON a objetos Java.

- **Patrón Singleton:**
    - Implementa un enfoque singleton para garantizar que solo exista una instancia de Retrofit durante la vida de la aplicación.

- **Servicio de CoinGecko:**
    - Proporciona el método `getService()` que retorna una instancia de `CoinGeckoService`, facilitando el acceso a los endpoints definidos.

---

## Conclusión

Este módulo de red es fundamental para la aplicación CryptoTracker, ya que:
- Permite obtener datos en tiempo real de la API de CoinGecko.
- Facilita la transformación de respuestas JSON en objetos Java utilizables dentro de la aplicación.
- Simplifica la gestión de las solicitudes HTTP mediante Retrofit y Gson.

Con esta arquitectura, la aplicación está preparada para sincronizar datos actualizados sobre criptomonedas y su evolución histórica, mejorando la experiencia del usuario y garantizando la precisión de la información mostrada.
