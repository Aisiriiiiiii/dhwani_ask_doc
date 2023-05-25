package com.example.d;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<ViewHolder> {
    Context context;
    List<Item> items;

    public Adapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.patient_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nametw.setText(items.get(position).getFn());
        holder.emailtw.setText(items.get(position).getEm());
        holder.meditw.setText(items.get(position).getMs());
        holder.bdaytw.setText(items.get(position).getBdy());


    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
