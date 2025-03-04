package com.example.myapplication.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.myapplication.data.CryptoCurrency;
import com.example.myapplication.repository.CryptoRepository;
import java.util.List;

public class CryptoViewModel extends AndroidViewModel {
    private CryptoRepository repository;
    private LiveData<List<CryptoCurrency>> favoriteCryptos;

    public CryptoViewModel(@NonNull Application application) {
        super(application);
        repository = new CryptoRepository(application);
        favoriteCryptos = repository.getFavoriteCryptos();
    }

    public LiveData<List<CryptoCurrency>> getFavoriteCryptos() {
        return favoriteCryptos;
    }
    public void updateCrypto(CryptoCurrency crypto) {
        repository.updateCrypto(crypto);
    }


    public void insertCrypto(CryptoCurrency crypto) {
        repository.insertCrypto(crypto);
    }
}
