package com.example.covid.ui.donor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.covid.R;
import com.example.covid.adapter.ApplicationHistoryAdapter;
import com.example.covid.adapter.DonationApplicationAdapter;
import com.example.covid.adapter.DonorRequestAdapter;

import java.util.ArrayList;

public class DonationApplicationsActivity extends AppCompatActivity {
    DonationApplicationAdapter applicationHistoryAdapter;
    ArrayList<String> list = new ArrayList<>();
    RecyclerView rvAssignment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_applications);
        rvAssignment = findViewById(R.id.rvAssignment);
        list.add("Full name: Ali azhar");
        list.add("Full name: Noman asad");
        list.add("Full name: mazhar");
        applicationHistoryAdapter = new DonationApplicationAdapter(this, list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvAssignment.setLayoutManager(layoutManager);
        rvAssignment.setAdapter(applicationHistoryAdapter);
        applicationHistoryAdapter.notifyDataSetChanged();
    }
}