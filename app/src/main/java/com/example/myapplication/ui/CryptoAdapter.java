package com.example.myapplication.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.myapplication.R;
import com.example.myapplication.data.CryptoCurrency;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CryptoAdapter extends RecyclerView.Adapter<CryptoAdapter.CryptoViewHolder> {
    private List<CryptoCurrency> cryptoList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(CryptoCurrency crypto);
    }

    public CryptoAdapter(List<CryptoCurrency> cryptoList, OnItemClickListener listener) {
        this.cryptoList = cryptoList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CryptoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_crypto, parent, false);
        return new CryptoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CryptoViewHolder holder, int position) {
        CryptoCurrency crypto = cryptoList.get(position);
        holder.nameTextView.setText(crypto.getName());
        holder.priceTextView.setText(String.valueOf(crypto.getCurrent_price()));
        holder.changeTextView.setText(crypto.getPrice_change_percentage_24h() + "%");
        holder.itemView.setOnClickListener(v -> listener.onItemClick(crypto));
    }

    @Override
    public int getItemCount() {
        return cryptoList == null ? 0 : cryptoList.size();
    }

    public void setCryptos(List<CryptoCurrency> cryptos) {
        this.cryptoList = cryptos;
        notifyDataSetChanged();
    }

    class CryptoViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, priceTextView, changeTextView;

        public CryptoViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.cryptoName);
            priceTextView = itemView.findViewById(R.id.cryptoPrice);
            changeTextView = itemView.findViewById(R.id.cryptoChange);
        }
    }
}
