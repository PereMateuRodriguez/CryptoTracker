package com.example.myapplication.network;

import java.util.List;

public class MarketChartResponse {
    // Solo nos interesa la lista de precios
    private List<List<Double>> prices;

    public List<List<Double>> getPrices() {
        return prices;
    }
}
