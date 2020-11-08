package com.example.covid;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import tr.xip.errorview.ErrorView;

public class Newsandupdates  extends Fragment {

    WebView webView;
    ErrorView errorView;

View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.newsandupdates,container,false);
       webView =  view.findViewById(R.id.vebvie);
      //  mAdView = view.findViewById(R.id.adView);
errorView = view.findViewById(R.id.specialErrorView);



if(checknetwork.isInternetAvailable(getActivity().getApplicationContext()) )//returns true if internet available
            {
                webView.loadUrl("https://news.google.com/topics/CAAqIggKIhxDQkFTRHdvSkwyMHZNREZqY0hsNUVnSmxiaWdBUAE?hl=en-PK&gl=PK&ceid=PK%3Aen");
                webView.setWebViewClient(new WebViewClient());
               /* MobileAds.initialize(getActivity().getApplicationContext(), new OnInitializationCompleteListener() {
                    @Override
                    public void onInitializationComplete(InitializationStatus initializationStatus) {
                    }
                });
*/
/*

                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.loadAd(adRequest);

                webView.setClickable(false);
*/

                //do something. loadwebview.



            }
        else
            {
               webView.setVisibility(View.GONE);
                errorView.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity().getApplicationContext(),"No Internet Connection",1000).show();
            }

        return view;

    }

   /* @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }*/
    Newsandupdates(){

    }


}
