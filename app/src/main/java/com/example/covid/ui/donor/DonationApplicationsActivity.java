package com.example.covid.ui.donor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.covid.R;
import com.example.covid.adapter.DonationApplicationAdapter;
import com.example.covid.model.DonationFormModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DonationApplicationsActivity extends AppCompatActivity {
    DonationApplicationAdapter applicationHistoryAdapter;
    ArrayList<DonationFormModel> donationList = new ArrayList<>();
    RecyclerView rvAssignment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_applications);
        rvAssignment = findViewById(R.id.rvAssignment);
        applicationHistoryAdapter = new DonationApplicationAdapter(DonationApplicationsActivity.this, donationList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(DonationApplicationsActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvAssignment.setLayoutManager(layoutManager);
        rvAssignment.setAdapter(applicationHistoryAdapter);
        DatabaseReference nm = FirebaseDatabase.getInstance().getReference("donationrequests");
        nm.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        DatabaseReference list = FirebaseDatabase.getInstance().getReference().child("donationrequests");
                        list.child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    DonationFormModel post = snapshot.getValue(DonationFormModel.class);
                                    donationList.add(post);
                                }
                                applicationHistoryAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}