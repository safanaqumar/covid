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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.covid.MyApplication.getContext;

public class SignupActivity extends AppCompatActivity {
    public FirebaseAuth firebaseAuth;
    private Button registerbtn;
    private ProgressBar progressBar;
    public Spinner spinner_position;
    public EditText email, password , confirm_password , address , cnic , contact;
     public  String USEREMAIL;
    public  String USERID;
    DatabaseReference UserDatabaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_signup);
        email=(EditText) findViewById(R.id.regEmail);
        password = (EditText) findViewById(R.id.regpass);
        confirm_password = (EditText)findViewById(R.id.regconfirmpass);
        spinner_position= (Spinner) findViewById(R.id.regposition);
        address=(EditText) findViewById(R.id.regaddress);
        cnic=(EditText) findViewById(R.id.regcnic);
        contact=(EditText) findViewById(R.id.regcontact);
        progressBar=findViewById(R.id.loadingbar);
        registerbtn = (Button) findViewById(R.id.registerbtn);
        ArrayAdapter<String> spinadapter = new ArrayAdapter<>(SignupActivity.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.positions));
        spinadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_position.setAdapter(spinadapter);
        UserDatabaseReference = FirebaseDatabase.getInstance().getReference("users");
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  USEREMAIL = email.getText().toString();
                  final String USERPASS = password.getText().toString();
                  final String USERCONPASS = confirm_password.getText().toString();

                  final String USERPOSITION = spinner_position.getSelectedItem().toString();
                  final String USERADDRESS = address.getText().toString();
                  final String USERCONTACT =contact.getText().toString();
                  final String USERCNIC = cnic.getText().toString();

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
                if (TextUtils.isEmpty(USERADDRESS)) {
                    Toast.makeText(SignupActivity.this, " ENTER ADDRESS", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(USERCONTACT)) {
                    Toast.makeText(SignupActivity.this, " ENTER CONTACT", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(USERCNIC)) {
                    Toast.makeText(SignupActivity.this, " ENTER CNIC", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (USERPASS.length() < 6) {
                    password.setError("Password should be of minimum 6 characters long");
                }
                if (USERCONTACT.length() < 11) {
                    password.setError("Enter valid contact number");
                }
                if (USERCNIC.length() < 12) {
                    password.setError("Enter valid cnic");
                }
                if (USERPASS.equals(USERCONPASS)) {
                    progressBar.setVisibility(View.VISIBLE);

                    firebaseAuth.createUserWithEmailAndPassword(USEREMAIL, USERPASS)
                            .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        USERID= FirebaseAuth.getInstance().getCurrentUser().getUid();

                                        final User user = new User(

                                                USERID,

                                                USERPASS,

                                                USEREMAIL,

                                                USERPOSITION,
                                                 USERADDRESS,
                                                USERCONTACT,
                                                USERCNIC

                                        );



                                        FirebaseDatabase.getInstance().getReference("users")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                progressBar.setVisibility(View.INVISIBLE);
                                               Toast.makeText(SignupActivity.this, "registration complete", Toast.LENGTH_SHORT).show();

                                               SessionManagement sessionManagement= new SessionManagement(SignupActivity.this);
                                                sessionManagement.saveSession(user);
                                                sessionManagement.saveRole(user.position);
                                                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP  );
                                                startActivity(intent);




                                            }
                                        });
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        progressBar.setVisibility(View.GONE);
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



