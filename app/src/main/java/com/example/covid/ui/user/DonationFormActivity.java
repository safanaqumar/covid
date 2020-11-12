package com.example.covid.ui.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.covid.MainActivity;
import com.example.covid.R;
import com.example.covid.SignupActivity;
import com.example.covid.databinding.ActivityDonationBinding;
import com.example.covid.map.SelectLocationOnMapActivity;
import com.example.covid.model.DonationFormModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class DonationFormActivity extends AppCompatActivity {
    private static final int WORK_PLACE_LOCATION_SELECT = 123;
    private double latitude;
    private double longitude;
    private String city;
    DatabaseReference UserDatabaseReference;
    FirebaseAuth firebaseAuth;
    ActivityDonationBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_donation);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void onSubmitClicked(View view) {
        if (validation()) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("Are you sure you want to submit your donation request?");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            insertRequest();
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
    }

    private boolean validation() {
        if (TextUtils.isEmpty(mBinding.etName.getText().toString())) {
            Snackbar.make(mBinding.getRoot(), "Please enter your full name", Snackbar.LENGTH_LONG).show();
            return false;
        } else if (TextUtils.isEmpty(mBinding.etAmount.getText().toString())) {
            Snackbar.make(mBinding.getRoot(), "Please enter your needed amount", Snackbar.LENGTH_LONG).show();
            return false;
        } else if (TextUtils.isEmpty(mBinding.personLL.getText().toString())) {
            Snackbar.make(mBinding.getRoot(), "please write some description", Snackbar.LENGTH_LONG).show();
            return false;
        } else if (TextUtils.isEmpty(mBinding.purpose.getText().toString())) {
            Snackbar.make(mBinding.getRoot(), "Please write purpose of your request", Snackbar.LENGTH_LONG).show();
            return false;
        }
        if (TextUtils.isEmpty(mBinding.etContact.getText().toString())) {
            mBinding.etContact.setText("Contact is not public");
        }
        if (TextUtils.isEmpty(mBinding.address.getText().toString())) {
            mBinding.address.setText("Address is not given yet");
        }
        if (TextUtils.isEmpty(mBinding.cnc.getText().toString())) {
            mBinding.cnc.setText("Cnic is not public");
        }
        return true;
    }

    public void onLocationButtonClicked(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(DonationFormActivity.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                        123);
                return;
            }
        }
        Intent i = new Intent(DonationFormActivity.this, SelectLocationOnMapActivity.class);
        startActivityForResult(i, WORK_PLACE_LOCATION_SELECT);
    }

    public void onDonationRequestClicked(View view) {
        Intent i = new Intent(getApplicationContext(), MyApplicationHistoryActivity.class);
        startActivity(i);
    }

    private void insertRequest() {
        DonationFormModel donationFormModel = new DonationFormModel();
        donationFormModel.setFullName(mBinding.etName.getText().toString());
        donationFormModel.setAmountNeeded(mBinding.etAmount.getText().toString());
        donationFormModel.setContact(mBinding.etContact.getText().toString());
        donationFormModel.setAddress(mBinding.address.getText().toString());
        donationFormModel.setDescription(mBinding.personLL.getText().toString());
        donationFormModel.setPurpose(mBinding.purpose.getText().toString());
        donationFormModel.setCnic(mBinding.cnc.getText().toString());
//        donationFormModel.setUserID(FirebaseAuth.getInstance().getCurrentUser().getUid());
        donationFormModel.setDate(java.text.DateFormat.getDateInstance().format(new Date()));
        FirebaseDatabase.getInstance().getReference("donationrequests")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .push()
                .setValue(donationFormModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    finish();
                    Toast.makeText(getApplicationContext(), "You successfully submit the request", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == WORK_PLACE_LOCATION_SELECT) {
            if (resultCode == RESULT_OK) {
                latitude = data.getDoubleExtra("lat", 0.0);
                longitude = data.getDoubleExtra("lng", 0.0);
                city = data.getStringExtra("city");
            }
        }
    }
}