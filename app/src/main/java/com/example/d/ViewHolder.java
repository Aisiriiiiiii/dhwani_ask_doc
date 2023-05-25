package com.example.d;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {

    TextView nametw,emailtw,meditw,bdaytw;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        nametw = itemView.findViewById(R.id.textView41);
        emailtw = itemView.findViewById(R.id.textView42);
        meditw = itemView.findViewById(R.id.textView45);
        bdaytw = itemView.findViewById(R.id.textView43);
    }
}
