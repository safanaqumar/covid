package com.example.covid;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Videos extends Fragment {
    View view;
    LinearLayout mask,mouth,distance,hand,senitizer,stayHome;
    public Videos(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.videos,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mask=view.findViewById(R.id.llMask);
        mouth=view.findViewById(R.id.llMouth);
        distance=view.findViewById(R.id.llDistance);
        hand=view.findViewById(R.id.llCleanHands);
        senitizer=view.findViewById(R.id.llSenitizer);
        stayHome=view.findViewById(R.id.llStayHome);
        mask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),PlayPrecautionActivity.class);
                intent.putExtra("type","sKF4Tm3Nm7I");
                startActivity(intent);
            }
        });
        mouth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),PlayPrecautionActivity.class);
                intent.putExtra("type","JwbwvppBGZU");
                startActivity(intent);
            }
        });
        distance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),PlayPrecautionActivity.class);
                intent.putExtra("type","2WCtGFNENYU");
                startActivity(intent);
            }
        });
        hand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),PlayPrecautionActivity.class);
                intent.putExtra("type","3PmVJQUCm4E");
                startActivity(intent);
            }
        });
        senitizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),PlayPrecautionActivity.class);
                intent.putExtra("type","4xC-_7ZiQoY");
                startActivity(intent);
            }
        });
        stayHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),PlayPrecautionActivity.class);
                intent.putExtra("type","1EFRW8dZ7_c");
                startActivity(intent);
            }
        });
    }
}
