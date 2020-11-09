package com.example.covid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.covid.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ApplicationHistoryAdapter extends RecyclerView.Adapter<ApplicationHistoryAdapter.ViewHolder> {
    Context c;
    LayoutInflater inflater;
    ArrayList<String> list;

    public ApplicationHistoryAdapter(Context c, ArrayList<String> list) {
        this.c = c;
        this.list = list;
    }

    @NonNull
    @Override
    public ApplicationHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(c);
        View view = inflater.inflate(R.layout.application_history_adapter, parent, false);
        ApplicationHistoryAdapter.ViewHolder holder = new ApplicationHistoryAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ApplicationHistoryAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
