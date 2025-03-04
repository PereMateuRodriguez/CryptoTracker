package com.example.myapplication.data;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "criptomonedas", indices = {@Index(value = {"symbol"}, unique = true)})
public class CryptoCurrency {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String symbol;
    private double current_price;
    private double price_change_percentage_24h;
    private double market_cap;
    private String last_updated;
    private boolean isFavorite;

    public CryptoCurrency(String name, String symbol, double current_price,
                          double price_change_percentage_24h, double market_cap,
                          String last_updated, boolean isFavorite) {
        this.name = name;
        this.symbol = symbol;
        this.current_price = current_price;
        this.price_change_percentage_24h = price_change_percentage_24h;
        this.market_cap = market_cap;
        this.last_updated = last_updated;
        this.isFavorite = isFavorite;
    }

    // Getters y setters...
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public String getSymbol() { return symbol; }
    public double getCurrent_price() { return current_price; }
    public double getPrice_change_percentage_24h() { return price_change_percentage_24h; }
    public double getMarket_cap() { return market_cap; }
    public String getLast_updated() { return last_updated; }
    public boolean isFavorite() { return isFavorite; }
    public void setFavorite(boolean favorite) { isFavorite = favorite; }
}
