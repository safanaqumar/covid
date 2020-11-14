package com.example.covid.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.covid.R;
import com.example.covid.databinding.ApplicationHistoryAdapterBinding;
import com.example.covid.databinding.ConfirmedApplicationsRowViewBinding;
import com.example.covid.model.ConfirmRequestModel;
import com.example.covid.model.ConfirmedRecipientDetails;
import com.example.covid.model.DonationFormModel;
import com.example.covid.ui.donor.ApplicantDetailsActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class ConfirmedApplicationAdapter extends RecyclerView.Adapter<ConfirmedApplicationAdapter.ViewHolder> {
    Context c;
    ArrayList<ConfirmedRecipientDetails> list;

    public ConfirmedApplicationAdapter(Context c, ArrayList<ConfirmedRecipientDetails> list) {
        this.c = c;
        this.list = list;
    }

    @NonNull
    @Override
    public ConfirmedApplicationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ConfirmedApplicationsRowViewBinding mBinding = DataBindingUtil.inflate(layoutInflater, R.layout.confirmed_applications_row_view, parent, false);
        c = parent.getContext();
        return new ConfirmedApplicationAdapter.ViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ConfirmedApplicationAdapter.ViewHolder holder, final int position) {
        holder.mBinding.setModel(list.get(position).getData());
        holder.mBinding.btnDonationRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c, ApplicantDetailsActivity.class);
                intent.putExtra("userId", list.get(position).getUserId());
                intent.putExtra("donationRequestId", list.get(position).getDonationRequestId());
                c.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ConfirmedApplicationsRowViewBinding mBinding;

        public ViewHolder(@NonNull ConfirmedApplicationsRowViewBinding itemView) {
            super(itemView.getRoot());
            this.mBinding = itemView;
        }
    }
}
