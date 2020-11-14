package com.example.covid.ui.donor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.example.covid.R;
import com.example.covid.databinding.ActivityApplicantDetailsBinding;
import com.example.covid.model.ConfirmRequestModel;
import com.example.covid.model.DonationFormModel;
import com.example.covid.model.DonorRequestModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ApplicantDetailsActivity extends AppCompatActivity {

    ActivityApplicantDetailsBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_applicant_details);
        Intent intent = getIntent();
        DatabaseReference nm = FirebaseDatabase.getInstance().getReference("donationrequests").child(intent.getStringExtra("userId"))
                .child(intent.getStringExtra("donationRequestId"));
        nm.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DonationFormModel model = dataSnapshot.getValue(DonationFormModel.class);
                mBinding.setModel(model);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}