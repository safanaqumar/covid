package com.example.covid.ui.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.covid.R;
import com.example.covid.adapter.ApplicationHistoryAdapter;
import com.example.covid.adapter.DonorRequestAdapter;

import java.util.ArrayList;

public class DonorRequestActivity extends AppCompatActivity {
    DonorRequestAdapter donorRequestAdapter;
    ArrayList<String> list=new ArrayList<>();
    RecyclerView rvAssignment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_request);
        rvAssignment=findViewById(R.id.rvAssignment);
        list.add("Hassan Ali");
        list.add("Ahmed Asad");
        list.add("Mohammad tahir");
        list.add("Abrar khan");
        list.add("faisal");
        list.add("Agha ali");
        donorRequestAdapter=new DonorRequestAdapter(this,list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvAssignment.setLayoutManager(layoutManager);
        rvAssignment.setAdapter(donorRequestAdapter);
        donorRequestAdapter.notifyDataSetChanged();
    }
}