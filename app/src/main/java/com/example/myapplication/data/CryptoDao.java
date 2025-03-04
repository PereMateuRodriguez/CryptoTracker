package com.example.myapplication.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.OnConflictStrategy;

import java.util.List;

@Dao
public interface CryptoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCrypto(CryptoCurrency crypto);


    @Query("SELECT * FROM criptomonedas")
    LiveData<List<CryptoCurrency>> getAllCryptos();

    @Query("SELECT * FROM criptomonedas WHERE isFavorite = 1")
    LiveData<List<CryptoCurrency>> getFavoriteCryptos();

    @Update
    void updateCrypto(CryptoCurrency crypto);
}

