package com.example.myapplication.network;

public class

CryptoCurrencyResponse {
    private String id;
    private String symbol;
    private String name;
    private double current_price;
    private double market_cap;
    private double price_change_percentage_24h;
    private String last_updated;

    // Getters y setters
    public String getId() { return id; }
    public String getSymbol() { return symbol; }
    public String getName() { return name; }
    public double getCurrent_price() { return current_price; }
    public double getMarket_cap() { return market_cap; }
    public double getPrice_change_percentage_24h() { return price_change_percentage_24h; }
    public String getLast_updated() { return last_updated; }
}
