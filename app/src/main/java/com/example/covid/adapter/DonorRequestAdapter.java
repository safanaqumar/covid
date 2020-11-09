package com.example.covid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.covid.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DonorRequestAdapter extends RecyclerView.Adapter<DonorRequestAdapter.ViewHolder>{
    Context c;
    LayoutInflater inflater;
    ArrayList<String> list;
    public DonorRequestAdapter(Context c, ArrayList<String> list) {
        this.c = c;
        this.list = list;
    }
    @NonNull
    @Override
    public DonorRequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(c);
        View view = inflater.inflate(R.layout.donor_request_row_view, parent, false);
        DonorRequestAdapter.ViewHolder holder = new DonorRequestAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DonorRequestAdapter.ViewHolder holder, int position) {
        holder.tvDoctorSpecialization.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDoctorSpecialization;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDoctorSpecialization=itemView.findViewById(R.id.tvDoctorSpecialization);
        }
    }
}
