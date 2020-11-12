package com.example.covid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class PlayPrecautionActivity extends AppCompatActivity {

    YouTubePlayerView youTubePlayerView;
    TextView title_tv;
    public WebView mywebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_precaution);
        youTubePlayerView = findViewById(R.id.youtube);
        mywebView = (WebView) findViewById(R.id.webview);
        title_tv = findViewById(R.id.title_video);
        Intent i = getIntent();
        title_tv.setText(i.getStringExtra("title"));
        getLifecycle().addObserver(youTubePlayerView);
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                Intent intent = getIntent();
                youTubePlayer.loadVideo(intent.getStringExtra("type"), 0);
            }
        });
        mywebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url == null || url.startsWith("http://") || url.startsWith("https://") || url.startsWith("https://www.messenger.com"))
                    return false;
                if (url.startsWith("intent")) {
                    ///  Intent sendIntent = new Intent();
                    //  sendIntent.setAction(Intent.ACTION_VIEW);
                    // sendIntent.setPackage("com.facebook.orca");
                    // sendIntent.setData(Uri.parse("https://m.me/"+"unameit.pk"));
                    Intent sendIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.hopkinsmedicine.org/health/conditions-and-diseases/coronavirus/proper-mask-wearing-coronavirus-prevention-infographic"));

                    if (sendIntent.resolveActivity(getPackageManager()) != null) {

                        startActivity(sendIntent);
                    }
                    return true;
                }


                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    view.getContext().startActivity(intent);
                    return true;
                } catch (Exception e) {

                    view.loadUrl(url);
                    return true;
                }


            }
        });
        mywebView.setWebChromeClient(new WebChromeClient());

        mywebView.loadUrl("https://www.hopkinsmedicine.org/health/conditions-and-diseases/coronavirus/proper-mask-wearing-coronavirus-prevention-infographic");
        WebSettings webSettings = mywebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String USER_AGENT = "Mozilla/5.0 (Linux; Android 4.1.1; Galaxy Nexus Build/JRO03C) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19";
        mywebView.getSettings().setUserAgentString(USER_AGENT);






    }
    @Override
    public void onBackPressed(){
        if(mywebView.canGoBack()) {
            mywebView.goBack();
        }
        else{
            super.onBackPressed();
        }
    }
}