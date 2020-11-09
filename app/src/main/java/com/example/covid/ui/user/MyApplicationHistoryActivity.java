package com.example.covid.ui.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.covid.R;
import com.example.covid.adapter.ApplicationHistoryAdapter;

import java.util.ArrayList;

public class MyApplicationHistoryActivity extends AppCompatActivity {

    ApplicationHistoryAdapter applicationHistoryAdapter;
    ArrayList<String> list=new ArrayList<>();
    RecyclerView rvAssignment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_donation_request);
        rvAssignment=findViewById(R.id.rvAssignment);
        list.add("");
        list.add("");
        list.add("");
        applicationHistoryAdapter=new ApplicationHistoryAdapter(this,list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvAssignment.setLayoutManager(layoutManager);
        rvAssignment.setAdapter(applicationHistoryAdapter);
        applicationHistoryAdapter.notifyDataSetChanged();
    }
}