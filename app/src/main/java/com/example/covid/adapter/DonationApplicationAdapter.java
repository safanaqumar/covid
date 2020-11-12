package com.example.covid.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covid.MainActivity;
import com.example.covid.R;
import com.example.covid.databinding.DonationApplicationRowViewBinding;
import com.example.covid.model.DonationFormModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class DonationApplicationAdapter extends RecyclerView.Adapter<DonationApplicationAdapter.ViewHolder> {
    Context c;
    LayoutInflater inflater;
    ArrayList<DonationFormModel> list;

    public DonationApplicationAdapter(Context c, ArrayList<DonationFormModel> list) {
        this.c = c;
        this.list = list;
    }

    @NonNull
    @Override
    public DonationApplicationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        DonationApplicationRowViewBinding mBinding = DataBindingUtil.inflate(layoutInflater, R.layout.donation_application_row_view, parent, false);
        c = parent.getContext();
        return new DonationApplicationAdapter.ViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull DonationApplicationAdapter.ViewHolder holder, int position) {
        holder.mBinding.setModel(list.get(position));
        holder.mBinding.tvNo.setText(""+(position+1));
        holder.mBinding.tvtitle.setText("Application # 019" + (position + 1)+"\nPosted on: "+list.get(position).getDate());
        holder.mBinding.btnDonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(c);
                builder1.setMessage("Are you sure you want to donate for this applicant?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(c, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                c.startActivity(intent);
                                Toast.makeText(c, "You have submit your donation request", Toast.LENGTH_SHORT).show();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        DonationApplicationRowViewBinding mBinding;

        public ViewHolder(@NonNull DonationApplicationRowViewBinding itemView) {
            super(itemView.getRoot());
            this.mBinding = itemView;
        }
    }
}
