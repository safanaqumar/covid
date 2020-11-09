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

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

public class DonationApplicationAdapter extends RecyclerView.Adapter<DonationApplicationAdapter.ViewHolder> {
    Context c;
    LayoutInflater inflater;
    ArrayList<String> list;

    public DonationApplicationAdapter(Context c, ArrayList<String> list) {
        this.c = c;
        this.list = list;
    }

    @NonNull
    @Override
    public DonationApplicationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(c);
        View view = inflater.inflate(R.layout.donation_application_row_view, parent, false);
        DonationApplicationAdapter.ViewHolder holder = new DonationApplicationAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DonationApplicationAdapter.ViewHolder holder, int position) {
        holder.tvDoctorSpecialization.setText(list.get(position));
        holder.tvAssignment.setText("Application #04" + (position + 31));
        holder.button.setOnClickListener(new View.OnClickListener() {
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
        TextView tvDoctorSpecialization;
        TextView tvAssignment;
        AppCompatButton button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDoctorSpecialization = itemView.findViewById(R.id.tvDoctorSpecialization);
            tvAssignment = itemView.findViewById(R.id.tvAssignment);
            button = itemView.findViewById(R.id.btnDonate);
        }
    }
}
