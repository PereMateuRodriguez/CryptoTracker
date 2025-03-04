package com.example.myapplication.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PriceHistoryDao {
    @Insert
    void insertPriceHistory(PriceHistory priceHistory);

    @Query("SELECT * FROM historial_precios WHERE crypto_id = :cryptoId ORDER BY timestamp ASC")
    List<PriceHistory> getHistoryForCrypto(int cryptoId);
}
