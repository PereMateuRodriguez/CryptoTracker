package com.example.myapplication.data;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "historial_precios",
        foreignKeys = @ForeignKey(entity = CryptoCurrency.class,
                parentColumns = "id",
                childColumns = "crypto_id",
                onDelete = CASCADE),
        indices = {@Index("crypto_id")})
public class PriceHistory {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int crypto_id;
    private double price;
    private String timestamp;

    public PriceHistory(int crypto_id, double price, String timestamp) {
        this.crypto_id = crypto_id;
        this.price = price;
        this.timestamp = timestamp;
    }

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCrypto_id() { return crypto_id; }
    public void setCrypto_id(int crypto_id) { this.crypto_id = crypto_id; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
