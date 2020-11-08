package com.example.covid;

import android.graphics.Color;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.JsonObject;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tr.xip.errorview.ErrorView;

import static android.widget.Toast.LENGTH_SHORT;

public class Dashboard extends Fragment {
    View view;
    TextView textView;
    ErrorView errorView;
    String server_url = "https://disease.sh/v3/covid-19/all";
    String pak_url = "https://api.apify.com/v2/key-value-stores/QhfG8Kj6tVYMgud6R/records/LATEST?disableRedirect=true";
    TextView dialog_title1, tvCases1, tvRecovered1, tvActive1, tvTotalDeaths1, tvpakCases1, tvpakActive1, tvpakRecovered1, tvpakTotalDeaths1;


    PieChart pieChart, pieChart1;
    private static final String TAG = Dashboard.class.getSimpleName();

    public Dashboard() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dashboard, container, false);
        dialog_title1 = view.findViewById(R.id.dialog_title1);
        tvpakRecovered1 = view.findViewById(R.id.tvpakRecovered1);
        tvpakActive1 = view.findViewById(R.id.tvpakActive1);
        tvpakCases1 = view.findViewById(R.id.tvpakCases1);
        tvpakTotalDeaths1 = view.findViewById(R.id.tvpakTotalDeaths1);
        pieChart = view.findViewById(R.id.piechart);
        pieChart1 = view.findViewById(R.id.piechart1);
        errorView = view.findViewById(R.id.specialErrorView);
        tvActive1 = view.findViewById(R.id.tvActive1);
        tvCases1 = view.findViewById(R.id.tvCases1);
        tvRecovered1 = view.findViewById(R.id.tvRecovered1);
        tvTotalDeaths1 = view.findViewById(R.id.tvTotalDeaths1);

        // MobileAds.initialize(getActivity().getApplicationContext(),"ca-app-pub-3940256099942544/6300978111");
       /* AdView maddview = (AdView) view.findViewById(R.id.adView3);
        AdRequest adRequest = new AdRequest.Builder().build();
        maddview.loadAd(adRequest);
*/
        StringRequest stringRequest = new StringRequest(Request.Method.GET, server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            tvActive1.setText(jsonObject.getString("active"));
                            tvCases1.setText(jsonObject.getString("cases"));
                            tvRecovered1.setText(jsonObject.getString("recovered"));
                            tvTotalDeaths1.setText(jsonObject.getString("deaths"));
                            pieChart.addPieSlice(new PieModel("Cases", Integer.parseInt(tvCases1.getText().toString()), Color.parseColor("#FFA726")));
                            pieChart.addPieSlice(new PieModel("Recoverd", Integer.parseInt(tvRecovered1.getText().toString()), Color.parseColor("#66BB6A")));
                            pieChart.addPieSlice(new PieModel("Deaths", Integer.parseInt(tvTotalDeaths1.getText().toString()), Color.parseColor("#EF5350")));
                            pieChart.addPieSlice(new PieModel("Active", Integer.parseInt(tvActive1.getText().toString()), Color.parseColor("#29B6F6")));
                            pieChart.startAnimation();

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                errorView.setVisibility(View.VISIBLE);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);


        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, pak_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject1 = new JSONObject(response.toString());

                            tvpakActive1.setText(jsonObject1.getString("tested"));
                            tvpakCases1.setText(jsonObject1.getString("infected"));
                            tvpakRecovered1.setText(jsonObject1.getString("recovered"));
                            tvpakTotalDeaths1.setText(jsonObject1.getString("deceased"));
                            pieChart1.addPieSlice(new PieModel("Infected", Integer.parseInt(tvpakCases1.getText().toString()), Color.parseColor("#FFA726")));
                            pieChart1.addPieSlice(new PieModel("Recovered", Integer.parseInt(tvpakRecovered1.getText().toString()), Color.parseColor("#66BB6A")));
                            pieChart1.addPieSlice(new PieModel("Deceased", Integer.parseInt(tvpakTotalDeaths1.getText().toString()), Color.parseColor("#EF5350")));
                            pieChart1.addPieSlice(new PieModel("Tested", Integer.parseInt(tvpakActive1.getText().toString()), Color.parseColor("#29B6F6")));
                            pieChart1.startAnimation();

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                errorView.setVisibility(View.VISIBLE);

//Toast.makeText(getActivity().getApplicationContext(),error.getMessage(), LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue1 = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue1.add(stringRequest1);
        return view;
    }


}
