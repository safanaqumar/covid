package com.example.covid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {
    EditText inputuserid, inputpassword;
    ProgressBar progressBar;
    Button btnlogin;
    TextView btnSignup;
    public static String userid1;
    public String username, position, id;
    DatabaseReference UserDatabaseReference;
    FirebaseAuth firebaseAuth;
    //public String USERID;
    public DatabaseReference mFirebaseDbReferenceCurrentUser;
    public FirebaseUser mFirebaseUser;
    public String positionShow;
    public String UID;
    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        firebaseAuth = FirebaseAuth.getInstance();
        UserDatabaseReference = FirebaseDatabase.getInstance().getReference("users");
        mFirebaseUser = firebaseAuth.getCurrentUser();
         SharedPreferences sharedPreferences= getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

        //  if (firebaseAuth.getCurrentUser() != null){
        //  startActivity(new Intent(SignInActivity.this,MainActivity.class));
        // finish();
        //   Toast.makeText(getApplicationContext(), "user already logged in ", Toast.LENGTH_LONG).show();
        // }


        inputuserid = (EditText) findViewById(R.id.loginemail);
        btnSignup = findViewById(R.id.signupTV);
        inputpassword = (EditText) findViewById(R.id.password);
        btnlogin = (Button) findViewById(R.id.login);

        progressBar = (ProgressBar) findViewById(R.id.loading);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(v);
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    public void login(View v) {
        final String userid = inputuserid.getText().toString();
        userid1 = userid;
        final String password = inputpassword.getText().toString();
        if (TextUtils.isEmpty(userid)) {
            Toast.makeText(getApplicationContext(), "Enter Username!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }
        isShow();

        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.signInWithEmailAndPassword(userid, password)
                .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);

                            User user = new User(id, userid1, password, position);


                            //  startActivity(new Intent(getApplicationContext(), MainActivity.class));

                            //Toast.makeText(SignInActivity.this,position,Toast.LENGTH_LONG).show();
                            Toast.makeText(SignInActivity.this, "Success!!", Toast.LENGTH_LONG).show();

                            SessionManagement sessionManagement = new SessionManagement(SignInActivity.this);
                            sessionManagement.saveSession(user);
                            sessionManagement.saveRole(position);
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);

                        } else {
                            progressBar.setVisibility(View.GONE);

                            Toast.makeText(SignInActivity.this, "email or password incorrect", Toast.LENGTH_LONG).show();


                        }


                    }
                });


    }

    public void isShow() {
        final String email = inputuserid.getText().toString();

        Query query = UserDatabaseReference.orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot datas : dataSnapshot.getChildren()) {
                        id = datas.child("id").getValue(String.class);
                        position = datas.child("position").getValue(String.class);
                        // SharedPreferences sharedPreferences= getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                        // sharedPreferences.edit().putString("user_name", username).apply();
                        //sharedPreferences.edit().putString("user_position", position).apply();


//
                    }


                } else {
                    progressBar.setVisibility(View.GONE);

                    //   Toast.makeText(login_activity.this, "Welcome", Toast.LENGTH_SHORT).show();


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SignInActivity.this, "DB not found", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }



}

