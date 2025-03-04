package com.example.myapplication.network;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CoinGeckoService {
    @GET("coins/markets")
    Call<List<CryptoCurrencyResponse>> getMarkets(
            @Query("vs_currency") String vs_currency,
            @Query("order") String order,
            @Query("per_page") int per_page,
            @Query("page") int page,
            @Query("sparkline") boolean sparkline
    );

    // Nuevo método para obtener el historial de precios
    @GET("coins/{id}/market_chart")
    Call<MarketChartResponse> getMarketChart(
            @Path("id") String coinId,
            @Query("vs_currency") String vsCurrency,
            @Query("days") int days
    );
}
