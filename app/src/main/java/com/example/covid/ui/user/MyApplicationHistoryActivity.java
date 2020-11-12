package com.example.covid.ui.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toolbar;

import com.example.covid.R;
import com.example.covid.adapter.ApplicationHistoryAdapter;
import com.example.covid.model.DonationFormModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class MyApplicationHistoryActivity extends AppCompatActivity {

    ApplicationHistoryAdapter applicationHistoryAdapter;
    ArrayList<DonationFormModel> historyList = new ArrayList<>();
    RecyclerView rvHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_donation_request);
        rvHistory = findViewById(R.id.rvAssignment);
        applicationHistoryAdapter = new ApplicationHistoryAdapter(this, historyList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvHistory.setLayoutManager(layoutManager);
        rvHistory.setAdapter(applicationHistoryAdapter);
        DatabaseReference nm = FirebaseDatabase.getInstance().getReference("donationrequests");
        nm.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (FirebaseAuth.getInstance().getUid().equals(snapshot.getKey())) {
                            DatabaseReference list = FirebaseDatabase.getInstance().getReference().child("donationrequests");
                            list.child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        DonationFormModel post = snapshot.getValue(DonationFormModel.class);
                                        historyList.add(post);
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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}