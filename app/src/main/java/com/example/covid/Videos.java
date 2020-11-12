package com.example.covid;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Videos extends Fragment {
    View view;
    //LinearLayout mask,mouth,distance,hand,senitizer,stayHome;
    Button mask, mouth , distance, hand, senitizer, stayhome;

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
        mask=view.findViewById(R.id.mask);
        mouth=view.findViewById(R.id.mouth);
        distance=view.findViewById(R.id.distance);
        hand=view.findViewById(R.id.hands);

        senitizer=view.findViewById(R.id.sanitizer);
        stayhome=view.findViewById(R.id.stayhome);
        mask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),PlayPrecautionActivity.class);
                intent.putExtra("type","sKF4Tm3Nm7I");
                intent.putExtra("title", "How to wear a mask properly | Covid-19");
                intent.putExtra("article", "https://www.hopkinsmedicine.org/health/conditions-and-diseases/coronavirus/proper-mask-wearing-coronavirus-prevention-infographic");
                startActivity(intent);
            }
        });
        mouth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),PlayPrecautionActivity.class);
                intent.putExtra("type","JwbwvppBGZU");
                intent.putExtra("title", "Stop the spread | Don't touch the mouth | Covid-19");
                startActivity(intent);
            }
        });
        distance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),PlayPrecautionActivity.class);
                intent.putExtra("title", "CoronaVirus Social Distancing | Covid-19");
                intent.putExtra("type","2WCtGFNENYU");
                startActivity(intent);
            }
        });
        hand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),PlayPrecautionActivity.class);
                intent.putExtra("title", "How to wash hands | Covid-19");
                intent.putExtra("type","3PmVJQUCm4E");
                startActivity(intent);
            }
        });
        senitizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),PlayPrecautionActivity.class);
                intent.putExtra("title", "How to use hand sanitizer | Covid-19");
                intent.putExtra("type","4xC-_7ZiQoY");
                startActivity(intent);
            }
        });
        stayhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),PlayPrecautionActivity.class);
                intent.putExtra("title", "Stay at home | Covid-19");
                intent.putExtra("type","1EFRW8dZ7_c");
                startActivity(intent);
            }
        });
    }
}
