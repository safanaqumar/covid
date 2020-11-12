package com.example.covid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.covid.R;
import com.example.covid.databinding.ApplicationHistoryAdapterBinding;
import com.example.covid.model.DonationFormModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class ApplicationHistoryAdapter extends RecyclerView.Adapter<ApplicationHistoryAdapter.ViewHolder> {
    Context c;
    LayoutInflater inflater;
    ArrayList<DonationFormModel> list;

    public ApplicationHistoryAdapter(Context c, ArrayList<DonationFormModel> list) {
        this.c = c;
        this.list = list;
    }

    @NonNull
    @Override
    public ApplicationHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ApplicationHistoryAdapterBinding mBinding = DataBindingUtil.inflate(layoutInflater, R.layout.application_history_adapter, parent, false);
        c = parent.getContext();
        return new ApplicationHistoryAdapter.ViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ApplicationHistoryAdapter.ViewHolder holder, int position) {
        holder.mBinding.setModel(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ApplicationHistoryAdapterBinding mBinding;
        public ViewHolder(@NonNull ApplicationHistoryAdapterBinding itemView) {
            super(itemView.getRoot());
            this.mBinding=itemView;
        }
    }
}
