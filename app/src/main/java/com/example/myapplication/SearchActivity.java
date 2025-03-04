package com.example.myapplication;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.data.CryptoCurrency;
import com.example.myapplication.network.CoinGeckoService;
import com.example.myapplication.network.CryptoCurrencyResponse;
import com.example.myapplication.network.RetrofitClient;
import com.example.myapplication.ui.CryptoAdapter;
import com.example.myapplication.viewmodel.CryptoViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private CryptoAdapter adapter;
    private List<CryptoCurrency> allCryptos; // Lista de criptomonedas obtenidas de la API
    private CryptoViewModel cryptoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Referencias a los componentes del layout
        searchView = findViewById(R.id.searchView);
        recyclerView = findViewById(R.id.recyclerViewSearch);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inicializa el ViewModel
        cryptoViewModel = new ViewModelProvider(this).get(CryptoViewModel.class);

        // Inicializa el adaptador con lista vacía y listener para agregar el item como favorito
        adapter = new CryptoAdapter(new ArrayList<CryptoCurrency>(), new CryptoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CryptoCurrency crypto) {
                crypto.setFavorite(true);
                cryptoViewModel.insertCrypto(crypto);
                Toast.makeText(SearchActivity.this, crypto.getName() + " agregado a favoritos", Toast.LENGTH_SHORT).show();
                finish(); // Cierra la actividad y vuelve a la pantalla principal
            }
        });
        recyclerView.setAdapter(adapter);

        // Llama a la API para obtener las criptomonedas disponibles
        fetchCryptosFromApi();

        // Configura el SearchView para filtrar la lista conforme se escribe
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterCryptos(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterCryptos(newText);
                return true;
            }
        });
    }

    private void fetchCryptosFromApi() {
        CoinGeckoService service = RetrofitClient.getService();
        // En este ejemplo se piden 50 criptomonedas ordenadas por capitalización de mercado
        Call<List<CryptoCurrencyResponse>> call = service.getMarkets("usd", "market_cap_desc", 50, 1, false);
        call.enqueue(new Callback<List<CryptoCurrencyResponse>>() {
            @Override
            public void onResponse(Call<List<CryptoCurrencyResponse>> call, Response<List<CryptoCurrencyResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    allCryptos = new ArrayList<>();
                    for (CryptoCurrencyResponse resp : response.body()) {
                        // Creamos un objeto CryptoCurrency con isFavorite = false por defecto
                        CryptoCurrency crypto = new CryptoCurrency(
                                resp.getName(),
                                resp.getSymbol(),
                                resp.getCurrent_price(),
                                resp.getPrice_change_percentage_24h(),
                                resp.getMarket_cap(),
                                resp.getLast_updated(),
                                false
                        );
                        allCryptos.add(crypto);
                    }
                    adapter.setCryptos(allCryptos);
                } else {
                    int code = response.code();
                    if (code == 429) {
                        Toast.makeText(SearchActivity.this, "No se pueden hacer más consultas a la API (Rate limit alcanzado).", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(SearchActivity.this, "Error al cargar datos de la API: " + code, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CryptoCurrencyResponse>> call, Throwable t) {
                Toast.makeText(SearchActivity.this, "Fallo en la conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Método para filtrar la lista según el texto ingresado en el SearchView
    private void filterCryptos(String query) {
        if (allCryptos == null) return;
        List<CryptoCurrency> filteredList = new ArrayList<>();
        for (CryptoCurrency crypto : allCryptos) {
            if (crypto.getName().toLowerCase().contains(query.toLowerCase()) ||
                    crypto.getSymbol().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(crypto);
            }
        }
        adapter.setCryptos(filteredList);
    }
}
