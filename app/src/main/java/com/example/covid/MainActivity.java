package com.example.covid;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.lang.UCharacter;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;

import static androidx.core.view.GravityCompat.*;

public class MainActivity extends AppCompatActivity {
private TabLayout tabLayout ;
private Toolbar toolbar;
private AppBarLayout appBarLayout;
private DrawerLayout drawerLayout;
private ViewPager viewPager;
EditText ed;
private ToggleButton button;
EndDrawerToggle endDrawerToggle;
String mPrefKey = "toggle";
private SharedPreferences mSharedPref;
//private ActionBarDrawerToggle actionBarDrawerToggle;
  private AppCompatImageButton tooglebutton;
    private String openDrawerContentDesc;
    private DrawerArrowDrawable arrowDrawable;
    private String closeDrawerContentDesc;
public static AdView maddview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavigationView navigationView = findViewById(R.id.nav_view);
        tabLayout = (TabLayout) findViewById(R.id.tablayou);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        viewPager = (ViewPager) findViewById(R.id.viewpage);


        ViewPagerAdaptor adaptor = new ViewPagerAdaptor(getSupportFragmentManager());

        //Adding fragments
        adaptor.Addfragment(new Dashboard(),"Dashboard");
        adaptor.Addfragment(new Videos(),"Precautions");
        adaptor.Addfragment(new Washhands(), "Awareness Videos");
        adaptor.Addfragment(new Newsandupdates(),"News and Updates");
        button = (ToggleButton) findViewById(R.id.butoonalaram);
        mSharedPref = getPreferences(Context.MODE_PRIVATE);
      //  ed = (EditText)findViewById(R.id.ed);
        //adaptor setup
        viewPager.setAdapter(adaptor);
        tabLayout.setupWithViewPager(viewPager);
        TabLayout.Tab tab = tabLayout.getTabAt(0);
        //tab.setIcon(new IconicsDrawable(this).icon(FontAwesome.Icon.faw_home));

        setuptoolbar();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean on =((ToggleButton)v).isChecked();

                if (on) {
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                    Intent notificationIntent = new Intent(MainActivity.this, AlarmReceiver.class);
                    PendingIntent broadcast = PendingIntent.getBroadcast(MainActivity.this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                    Toast.makeText(getApplicationContext(),"Reminder is on",Toast.LENGTH_SHORT).show();
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.MINUTE, 2);
                    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),AlarmManager.INTERVAL_DAY,broadcast
);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
                    }

                }
                else {
                    Toast.makeText(getApplicationContext(),"Reminder is off",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }




    @Override
    protected void onResume() {
        super.onResume();

        // if the activty resumes set the toggle state
        boolean enabled = mSharedPref.getBoolean(mPrefKey,true); // if no value found then set it off, in this case this can happen first
        button.setChecked(enabled);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // if the activty closed etc.....
        SharedPreferences.Editor editor = mSharedPref.edit(); // get the pref editor
        editor.putBoolean(mPrefKey,button.isChecked()); // assign value to the key
        editor.commit();  // save the editors modifications
    }
    private void setuptoolbar(){
        final DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbars);
setSupportActionBar(toolbar);


 endDrawerToggle = new EndDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(endDrawerToggle);
        drawerLayout.addDrawerListener(endDrawerToggle);
        endDrawerToggle.syncState();


    }

}