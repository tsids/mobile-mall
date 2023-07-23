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
    List<List<String>> storesInfo;

    public StoreAdapter(Context context, List<List<String>> storesInfo, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.storesInfo = storesInfo;
        this.recyclerViewInterface = recyclerViewInterface;
    }


    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StoreViewHolder(LayoutInflater.from(context).inflate(R.layout.store_view, parent,  false), recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder holder, int position) {
        holder.storeNameView.setText(storesInfo.get(position).get(0));
        holder.categoryView.setText(storesInfo.get(position).get(1));
    }

    @Override
    public int getItemCount() {
        return storesInfo.size();
    }
}
