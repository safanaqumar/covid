package com.example.covid.ui.donor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.covid.R;
import com.example.covid.adapter.ConfirmedApplicationAdapter;
import com.example.covid.adapter.DonationApplicationAdapter;
import com.example.covid.databinding.ActivityConfirmApplicationsBinding;
import com.example.covid.model.ConfirmRequestModel;
import com.example.covid.model.ConfirmedRecipientDetails;
import com.example.covid.model.DonationFormModel;
import com.example.covid.model.DonorRequestModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ConfirmApplicationsActivity extends AppCompatActivity {
    ConfirmedApplicationAdapter applicationAdapter;
    ActivityConfirmApplicationsBinding mBinding;
    ArrayList<ConfirmedRecipientDetails> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_confirm_applications);
        applicationAdapter = new ConfirmedApplicationAdapter(this, list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mBinding.rvConfirmUser.setLayoutManager(layoutManager);
        mBinding.rvConfirmUser.setAdapter(applicationAdapter);
        DatabaseReference nm = FirebaseDatabase.getInstance().getReference("confirmeddonors")
                .child(FirebaseAuth.getInstance().getUid());
        nm.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    final String donationReqId = snapshot.getKey();
                    final ConfirmRequestModel mList = snapshot.getValue(ConfirmRequestModel.class);
                    DatabaseReference nm = FirebaseDatabase.getInstance().getReference("users")
                            .child(mList.getUserId());
                    nm.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ConfirmedRecipientDetails.ConfirmedRecipientDetailsData recipientDetailsData = new ConfirmedRecipientDetails.ConfirmedRecipientDetailsData();
                            recipientDetailsData = dataSnapshot.getValue(ConfirmedRecipientDetails.ConfirmedRecipientDetailsData.class);
                            ConfirmedRecipientDetails confirmedRecipientDetails = new ConfirmedRecipientDetails(recipientDetailsData);
                            confirmedRecipientDetails.setUserId(mList.getUserId());
                            confirmedRecipientDetails.setDonationRequestId(donationReqId);
                            list.add(confirmedRecipientDetails);
                            applicationAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}