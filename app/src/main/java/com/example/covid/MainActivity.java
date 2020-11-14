package com.example.covid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.app.AlarmManager;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.covid.ui.donor.ConfirmApplicationsActivity;
import com.example.covid.ui.donor.DonationApplicationsActivity;
import com.example.covid.ui.user.DonationFormActivity;
import com.example.covid.ui.user.DonorRequestActivity;
import com.google.android.gms.ads.AdView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    public FirebaseAuth firebaseAuth;
    private TabLayout tabLayout;
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
    public Button signup_button;
    ImageView imageView;
    public TextView login;
    FirebaseUser user;
    public TextView logout;
    TextView welcome;
    LinearLayout donation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        donation =findViewById(R.id.donation);
        NavigationView navigationView = findViewById(R.id.nav_view);
        tabLayout = (TabLayout) findViewById(R.id.tablayou);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        viewPager = (ViewPager) findViewById(R.id.viewpage);
        imageView=findViewById(R.id.menu_logo);
         login=  findViewById(R.id.login_button);
         welcome=findViewById(R.id.welcome_tv);
         //logout=findViewById(R.id.logout);
         logout=findViewById(R.id.logout_btn);

         logout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
                 sessionManagement.removeSession();
                 Toast.makeText(getApplicationContext(),"logout", Toast.LENGTH_LONG ).show();
                 String userID = sessionManagement.getSession();
                 if (userID =="default")
                 {
                     Toast.makeText(getApplicationContext(),"logout", Toast.LENGTH_LONG ).show();
                     Intent intent =new Intent();
                     intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                     logout.setVisibility(View.INVISIBLE);
                     login.setVisibility(View.VISIBLE);



                 }
                 else
                 {

                 }






             }
         });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(intent);
            }
        });

        ViewPagerAdaptor adaptor = new ViewPagerAdaptor(getSupportFragmentManager());

        //Adding fragments
        adaptor.Addfragment(new Dashboard(), "Dashboard");
        adaptor.Addfragment(new Videos(), "Precautions");
        adaptor.Addfragment(new Washhands(), "Awareness Videos");
        adaptor.Addfragment(new Newsandupdates(), "News and Updates");
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
                boolean on = ((ToggleButton) v).isChecked();

                if (on) {
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                    Intent notificationIntent = new Intent(MainActivity.this, AlarmReceiver.class);
                    PendingIntent broadcast = PendingIntent.getBroadcast(MainActivity.this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                    Toast.makeText(getApplicationContext(), "Reminder is on", Toast.LENGTH_SHORT).show();
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.MINUTE, 2);
                    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, broadcast
                    );

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Reminder is off", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
    @Override
    public void onStart() {
        super.onStart();
        //Check if user is signed in (non-null) and update UI accordingly.
        // FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        //Toast.makeText(getApplicationContext(), "user already logged in ", Toast.LENGTH_LONG).show();
        SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
        String userID = sessionManagement.getSession();
        if (!userID.equals("default"))
        {
            Toast.makeText(getApplicationContext(),userID, Toast.LENGTH_LONG ).show();

            login.setVisibility(View.INVISIBLE);
            donation.setVisibility(View.VISIBLE);
            // welcome.setVisibility(View.VISIBLE);
           // logout.setVisibility(View.VISIBLE);

        }
        else
        {

            sessionManagement.removeSession();
            Toast.makeText(getApplicationContext(),userID, Toast.LENGTH_LONG ).show();
            logout.setVisibility(View.INVISIBLE);
            login.setVisibility(View.VISIBLE);
        }


    }

   /* public void logoutt()
    {
        SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
        sessionManagement.removeSession();
        changeUI();

    }*/
    public void changeUI()
    {

        Toast.makeText(getApplicationContext(), "logout", Toast.LENGTH_LONG).show();
       //Intent intent = new Intent(MainActivity.this,MainActivity.class);
       //.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLA);
        //startActivity(intent);
        Intent intent =new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
        logout.setVisibility(View.INVISIBLE);
        login.setVisibility(View.VISIBLE);
    }






    @Override
    protected void onResume() {
        super.onResume();

        // if the activty resumes set the toggle state
        boolean enabled = mSharedPref.getBoolean(mPrefKey, true); // if no value found then set it off, in this case this can happen first
        button.setChecked(enabled);



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onPause() {
        super.onPause();
        // if the activty closed etc.....
        SharedPreferences.Editor editor = mSharedPref.edit(); // get the pref editor
        editor.putBoolean(mPrefKey, button.isChecked()); // assign value to the key
        editor.commit();  // save the editors modifications

    }

    private void setuptoolbar() {
        final DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbars);
        setSupportActionBar(toolbar);


        endDrawerToggle = new EndDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(endDrawerToggle);
        drawerLayout.addDrawerListener(endDrawerToggle);
        endDrawerToggle.syncState();


    }

    public void onClicked(View view) {
        Intent i = new Intent(this, DonationFormActivity.class);
        startActivity(i);
    }

    public void onSeeDonor(View view) {
        Intent i = new Intent(this, DonorRequestActivity.class);
        startActivity(i);
    }

    public void onViewClicked(View view) {
        Intent i = new Intent(this, DonationApplicationsActivity.class);
        startActivity(i);
    }

    public void onAboutClicked(View view) {
    }

    public void onLogoutClicked(View view) {
    }

    public void onRecipientConfirmationClicked(View view) {
        Intent i = new Intent(this, ConfirmApplicationsActivity.class);
        startActivity(i);
    }
}
