package com.example.covid.ui.donor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.example.covid.R;
import com.example.covid.adapter.DonationApplicationAdapter;
import com.example.covid.model.DonationFormModel;
import com.example.covid.model.DonorRequestModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DonationApplicationsActivity extends AppCompatActivity {
    DonationApplicationAdapter applicationHistoryAdapter;
    ArrayList<DonationFormModel> donationList = new ArrayList<>();
    ArrayList<DonorRequestModel> donorRequestModels = new ArrayList<>();
    RecyclerView rvAssignment;
    LottieAnimationView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_applications);
        rvAssignment = findViewById(R.id.rvAssignment);
        loading =findViewById(R.id.loading);
        loading.setVisibility(View.VISIBLE);
        applicationHistoryAdapter = new DonationApplicationAdapter(DonationApplicationsActivity.this, donationList, donorRequestModels);
        LinearLayoutManager layoutManager = new LinearLayoutManager(DonationApplicationsActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvAssignment.setLayoutManager(layoutManager);
        rvAssignment.setAdapter(applicationHistoryAdapter);
        DatabaseReference nm = FirebaseDatabase.getInstance().getReference("donationrequests");
        nm.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                loading.setVisibility(View.VISIBLE);
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        DatabaseReference list = FirebaseDatabase.getInstance().getReference().child("donationrequests");
                        list.child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    DonationFormModel post = snapshot.getValue(DonationFormModel.class);
                                    donationList.add(post);
                                    DonorRequestModel.DonorRequestDataModel donorRequestDataModel = new DonorRequestModel.DonorRequestDataModel();
                                    DonorRequestModel donorRequestModel = new DonorRequestModel(donorRequestDataModel);
                                    donorRequestModel.setDonationRequestId(snapshot.getKey());
                                    donorRequestModel.getGetData().setConfirm("no");
                                    donorRequestModel.setDonorId(FirebaseAuth.getInstance().getUid());
                                    donorRequestModel.getGetData().setMessage("Hey i want to donote you");
                                    donorRequestModel.getGetData().setName("Aslam");
                                    donorRequestModels.add(donorRequestModel);
                                }
                                applicationHistoryAdapter.notifyDataSetChanged();
                                loading.setVisibility(View.GONE);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                loading.setVisibility(View.GONE);
                            }
                        });
                    }
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                loading.setVisibility(View.GONE);
            }
        });
    }
}