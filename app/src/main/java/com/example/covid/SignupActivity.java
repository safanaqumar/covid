package com.example.covid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    public FirebaseAuth firebaseAuth;
    private Button registerbtn;
    private ProgressBar progressBar;
    public Spinner spinner_position;
    public EditText email, password , confirm_password;
    DatabaseReference UserDatabaseReference;
    TextView login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_signup);
        email=(EditText) findViewById(R.id.regEmail);
        password = (EditText) findViewById(R.id.regpass);
        confirm_password = (EditText)findViewById(R.id.regconfirmpass);
        spinner_position= (Spinner) findViewById(R.id.regposition);
        progressBar=findViewById(R.id.loadingbar);
        registerbtn = (Button) findViewById(R.id.registerbtn);
        login =findViewById(R.id.login_tv);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(i);
            }
        });
        ArrayAdapter<String> spinadapter = new ArrayAdapter<>(SignupActivity.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.positions));
        spinadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_position.setAdapter(spinadapter);
        UserDatabaseReference = FirebaseDatabase.getInstance().getReference("users");
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String USEREMAIL = email.getText().toString();


                final String USERPASS = password.getText().toString();
                final String USERCONPASS = confirm_password.getText().toString();

                final String USERPOSITION = spinner_position.getSelectedItem().toString();

                if (TextUtils.isEmpty(USEREMAIL)) {
                    Toast.makeText(SignupActivity.this, " ENTER EMAIL", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(USERPASS)) {
                    Toast.makeText(SignupActivity.this, " ENTER PASSWORD", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(USERCONPASS)) {
                    Toast.makeText(SignupActivity.this, " ENTER CONFIRM PASSWORD", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (USERPASS.length() < 6) {
                    password.setError("Password should be of minimum 6 characters long");
                }
                if (USERPASS.equals(USERCONPASS)) {

                    firebaseAuth.createUserWithEmailAndPassword(USEREMAIL, USERPASS)
                            .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        User user = new User(

                                                USERPASS,

                                                USEREMAIL,

                                                USERPOSITION

                                        );



                                        FirebaseDatabase.getInstance().getReference("users")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(SignupActivity.this, "registration complete", Toast.LENGTH_SHORT).show();
                                                //  startActivity(new Intent(getApplicationContext(), MainActivity.class));

                                            }
                                        });
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(SignupActivity.this, "registration failed email already exists", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                } else {
                    confirm_password.setError("Incorrect Password");
                }

            }
        });
    }


        }



