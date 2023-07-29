package com.example.b07project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;

    Context context;
    List<Store> stores;

    public StoreAdapter(Context context, List<Store> stores, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.stores = stores;
        this.recyclerViewInterface = recyclerViewInterface;
    }


    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StoreViewHolder(LayoutInflater.from(context).inflate(R.layout.store_view, parent,  false), recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder holder, int position) {
        holder.storeNameView.setText(stores.get(position).getStore());
        holder.categoryView.setText(stores.get(position).getCategory());
    }

    @Override
    public int getItemCount() {
        return stores.size();
    }
}
