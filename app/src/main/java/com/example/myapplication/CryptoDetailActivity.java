package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.data.CryptoCurrency;
import com.example.myapplication.network.CoinGeckoService;
import com.example.myapplication.network.MarketChartResponse;
import com.example.myapplication.network.RetrofitClient;
import com.example.myapplication.viewmodel.CryptoViewModel;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CryptoDetailActivity extends AppCompatActivity {

    private GraphView graph;
    private TextView tvName, tvSymbol, tvCurrentPrice, tvChangePercentage, tvMarketCap, tvLastUpdated;
    private Button btnRemoveFavorite;

    // Variables para los datos de la criptomoneda
    private int cryptoId;
    private String cryptoName, cryptoSymbol, coinId, lastUpdated;
    private double currentPrice, priceChange, marketCap;

    private CryptoViewModel cryptoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto_detail);

        // Inicializamos las referencias a los componentes de la interfaz
        tvName = findViewById(R.id.tv_crypto_name);
        tvSymbol = findViewById(R.id.tv_crypto_symbol);
        tvCurrentPrice = findViewById(R.id.tv_current_price);
        tvChangePercentage = findViewById(R.id.tv_change_percentage);
        tvMarketCap = findViewById(R.id.tv_market_cap);
        tvLastUpdated = findViewById(R.id.tv_last_updated);
        btnRemoveFavorite = findViewById(R.id.btn_remove_favorite);
        graph = findViewById(R.id.graph);

        // Inicializamos el ViewModel
        cryptoViewModel = new CryptoViewModel(getApplication());

        // Recibimos los datos pasados por Intent
        cryptoId = getIntent().getIntExtra("cryptoId", -1);
        cryptoName = getIntent().getStringExtra("cryptoName");
        cryptoSymbol = getIntent().getStringExtra("cryptoSymbol");
        currentPrice = getIntent().getDoubleExtra("currentPrice", 0);
        priceChange = getIntent().getDoubleExtra("priceChange", 0);
        marketCap = getIntent().getDoubleExtra("marketCap", 0);
        lastUpdated = getIntent().getStringExtra("lastUpdated");

        // Si se pasó coinId real, lo usamos; de lo contrario, derivamos a partir del nombre
        coinId = getIntent().getStringExtra("coinId");
        if (coinId == null || coinId.isEmpty()) {
            coinId = cryptoName.toLowerCase();
        }

        // Actualizamos los TextViews con los datos y las etiquetas
        tvName.setText("Nombre: " + cryptoName);
        tvSymbol.setText("Símbolo: " + cryptoSymbol);
        tvCurrentPrice.setText("Precio Actual: $" + currentPrice);
        tvChangePercentage.setText("Variación 24h: " + priceChange + "%");
        tvMarketCap.setText("Capitalización: $" + marketCap);
        tvLastUpdated.setText("Última Actualización: " + lastUpdated);

        setTitle(cryptoName);

        // Configuramos el botón "Eliminar de favoritos"
        btnRemoveFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Creamos un objeto actualizado, conservando el id original para que Room actualice el registro correcto
                CryptoCurrency updatedCrypto = new CryptoCurrency(
                        cryptoName,
                        cryptoSymbol,
                        currentPrice,
                        priceChange,
                        marketCap,
                        lastUpdated,
                        false  // ya no es favorito
                );
                updatedCrypto.setId(cryptoId); // Es vital preservar el id original

                cryptoViewModel.updateCrypto(updatedCrypto);
                Toast.makeText(CryptoDetailActivity.this, "Eliminado de favoritos", Toast.LENGTH_SHORT).show();
                // Puedes cerrar la actividad o actualizar la UI según prefieras
                finish();
            }
        });

        // Consulta a la API para obtener el historial de precios (últimos 7 días)
        CoinGeckoService service = RetrofitClient.getService();
        Call<MarketChartResponse> call = service.getMarketChart(coinId, "usd", 7);
        call.enqueue(new Callback<MarketChartResponse>() {
            @Override
            public void onResponse(Call<MarketChartResponse> call, Response<MarketChartResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<List<Double>> prices = response.body().getPrices();
                    if (prices != null && !prices.isEmpty()) {
                        // Convertimos la lista de precios en un arreglo de DataPoint.
                        DataPoint[] dataPoints = new DataPoint[prices.size()];
                        for (int i = 0; i < prices.size(); i++) {
                            double price = prices.get(i).get(1);
                            // Se usa el índice como eje X; para mayor precisión, convierte el timestamp a un valor representativo.
                            dataPoints[i] = new DataPoint(i + 1, price);
                        }
                        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
                        graph.removeAllSeries();
                        graph.addSeries(series);
                    } else {
                        Toast.makeText(CryptoDetailActivity.this, "No hay datos de precios disponibles", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int code = response.code();
                    if (code == 429) {
                        Toast.makeText(CryptoDetailActivity.this, "Rate limit alcanzado (429). Espera antes de hacer nuevas solicitudes.", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(CryptoDetailActivity.this, "Rate limit alcanzado (429). Espera antes de hacer nuevas solicitudes.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MarketChartResponse> call, Throwable t) {
                Toast.makeText(CryptoDetailActivity.this, "Fallo en la conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
