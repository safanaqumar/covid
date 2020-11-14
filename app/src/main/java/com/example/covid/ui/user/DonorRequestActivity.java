package com.example.covid.ui.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.covid.R;
import com.example.covid.adapter.ApplicationHistoryAdapter;
import com.example.covid.adapter.DonorRequestAdapter;
import com.example.covid.model.DonationFormModel;
import com.example.covid.model.DonorRequestModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DonorRequestActivity extends AppCompatActivity {
    DonorRequestAdapter donorRequestAdapter;
    ArrayList<String> DonationFormUid = new ArrayList<>();
    ArrayList<DonorRequestModel> donorRequest = new ArrayList<>();
    RecyclerView rvAssignment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_request);
        rvAssignment = findViewById(R.id.rvAssignment);
        donorRequestAdapter = new DonorRequestAdapter(this, donorRequest);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvAssignment.setLayoutManager(layoutManager);
        rvAssignment.setAdapter(donorRequestAdapter);

        DatabaseReference nm = FirebaseDatabase.getInstance().getReference("donationrequests")
                .child(FirebaseAuth.getInstance().getUid());
        nm.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        DonationFormUid.add(snapshot.getKey());
                    }
                }
                getRequestList();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getRequestList() {
        for (int i = 0; i < DonationFormUid.size(); i++) {
            final DatabaseReference nm = FirebaseDatabase.getInstance().getReference("donorrequest")
                    .child(DonationFormUid.get(i));
            final int finalI = i;
            nm.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            DonorRequestModel.DonorRequestDataModel list =new DonorRequestModel.DonorRequestDataModel();
                            list = snapshot.getValue(DonorRequestModel.DonorRequestDataModel.class);
                            DonorRequestModel donorRequestModel=new DonorRequestModel(list);
                            donorRequestModel.setDonorId(snapshot.getKey());
                            donorRequestModel.setDonationRequestId(DonationFormUid.get(finalI));
                            donorRequest.add(donorRequestModel);
                        }
                        donorRequestAdapter.notifyDataSetChanged();
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}