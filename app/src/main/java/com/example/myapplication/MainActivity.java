package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.data.CryptoCurrency;
import com.example.myapplication.ui.CryptoAdapter;
import com.example.myapplication.viewmodel.CryptoViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CryptoViewModel cryptoViewModel;
    private RecyclerView recyclerView;
    private CryptoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configura el Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Configura el RecyclerView y el adaptador
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new CryptoAdapter(new ArrayList<CryptoCurrency>(), new CryptoAdapter.OnItemClickListener() {
            // En MainActivity.java, al hacer clic en un ítem:
            @Override
            public void onItemClick(CryptoCurrency crypto) {
                Intent intent = new Intent(MainActivity.this, CryptoDetailActivity.class);
                intent.putExtra("cryptoId", crypto.getId());
                intent.putExtra("cryptoName", crypto.getName());
                intent.putExtra("cryptoSymbol", crypto.getSymbol());
                intent.putExtra("currentPrice", crypto.getCurrent_price());
                intent.putExtra("priceChange", crypto.getPrice_change_percentage_24h());
                intent.putExtra("marketCap", crypto.getMarket_cap());
                intent.putExtra("lastUpdated", crypto.getLast_updated());
                startActivity(intent);
            }

        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Obtiene el ViewModel y observa el LiveData de criptomonedas favoritas
        cryptoViewModel = new ViewModelProvider(this).get(CryptoViewModel.class);
        cryptoViewModel.getFavoriteCryptos().observe(this, new Observer<List<CryptoCurrency>>() {
            @Override
            public void onChanged(List<CryptoCurrency> cryptos) {
                adapter.setCryptos(cryptos);
            }
        });
    }

    // Infla el menú en el Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    // Maneja la selección de un ítem del menú
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_crypto) {
            // Lanza SearchActivity para agregar nuevas criptomonedas favoritas
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
