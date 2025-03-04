package com.example.myapplication.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.myapplication.data.AppDatabase;
import com.example.myapplication.data.CryptoCurrency;
import com.example.myapplication.data.CryptoDao;
import java.util.List;

public class CryptoRepository {
    private CryptoDao cryptoDao;
    private LiveData<List<CryptoCurrency>> favoriteCryptos;

    public CryptoRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        cryptoDao = db.cryptoDao();
        favoriteCryptos = cryptoDao.getFavoriteCryptos();
    }

    public LiveData<List<CryptoCurrency>> getFavoriteCryptos() {
        return favoriteCryptos;
    }
    public void updateCrypto(final CryptoCurrency crypto) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                cryptoDao.updateCrypto(crypto);
            }
        });
    }

    public void insertCrypto(final CryptoCurrency crypto) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                cryptoDao.insertCrypto(crypto);
            }
        });
    }
}
