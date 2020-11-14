package com.example.covid.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covid.MainActivity;
import com.example.covid.R;
import com.example.covid.databinding.ApplicationHistoryAdapterBinding;
import com.example.covid.databinding.DonorRequestRowViewBinding;
import com.example.covid.model.ConfirmRequestModel;
import com.example.covid.model.DonorRequestModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class DonorRequestAdapter extends RecyclerView.Adapter<DonorRequestAdapter.ViewHolder> {
    Context c;
    LayoutInflater inflater;
    ArrayList<DonorRequestModel> list;

    public DonorRequestAdapter(Context c, ArrayList<DonorRequestModel> list) {
        this.c = c;
        this.list = list;
    }

    @NonNull
    @Override
    public DonorRequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(c);
        DonorRequestRowViewBinding mBinding = DataBindingUtil.inflate(inflater, R.layout.donor_request_row_view, parent, false);
        c = parent.getContext();
        return new DonorRequestAdapter.ViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull DonorRequestAdapter.ViewHolder holder, final int position) {
        if (list.get(position).getGetData().getConfirm().equals("no")) {
            holder.mBinding.setConfirm(false);
        } else {
            holder.mBinding.setConfirm(true);
        }
        holder.mBinding.setModel(list.get(position).getGetData());
        holder.mBinding.btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendConfirmationToDonor(list.get(position));
            }
        });
    }

    private void sendConfirmationToDonor(final DonorRequestModel donorRequestModel) {
        ConfirmRequestModel confirmRequestModel = new ConfirmRequestModel();
        confirmRequestModel.setConfirm("yes");
        confirmRequestModel.setUserId(FirebaseAuth.getInstance().getUid());
        FirebaseDatabase.getInstance().getReference("confirmeddonors")
                .child(donorRequestModel.getDonorId())
                .child(donorRequestModel.getDonationRequestId())
                .setValue(confirmRequestModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    setConfirmation(donorRequestModel.getDonorId(), donorRequestModel.getDonationRequestId());
                }
            }
        });
    }

    private void setConfirmation(String donorId, String donationReqId) {
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("donorrequest").child(donationReqId).child(donorId)
                .child("confirm");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mDatabase.setValue("yes");
                Intent intent = new Intent(c, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                c.startActivity(intent);
                Toast.makeText(c, "You Confirmed the Request", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        DonorRequestRowViewBinding mBinding;

        public ViewHolder(DonorRequestRowViewBinding mBinding) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
        }
    }
}
