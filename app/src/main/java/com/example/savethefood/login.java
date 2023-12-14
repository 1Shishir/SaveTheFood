package com.example.savethefood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class login extends AppCompatActivity {
Button login_button;
TextInputEditText login_email;
TextInputEditText login_password;
TextView forgetPass;
static String uid;
private FirebaseAuth mAuth;

String email,password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();

        login_email=(TextInputEditText) findViewById(R.id.login_email);
        login_password=(TextInputEditText) findViewById(R.id.login_password);
        login_button=(Button) findViewById(R.id.login_Login);

        forgetPass=(TextView) findViewById(R.id.forget);

        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = login_email.getText().toString();
                if(email.isEmpty()){
                    Toast.makeText(login.this, "Please fill the email address", Toast.LENGTH_SHORT).show();
                }
                else {

                    mAuth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(login.this, "Please check email", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(login.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }


            }
        });



        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = login_email.getText().toString();
                password = login_password.getText().toString();

                //startActivity(new Intent(login.this, homePage.class));
                loginAuth(email, password);
            }



            private void loginAuth(String email, String password) {

                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            login_email.setText("");
                            login_password.setText("");
                            uid=mAuth.getCurrentUser().getUid();

                            if(uid.equals("6A5THwPvjLNm84PpjsSpRTOfWq73")){
                                startActivity(new Intent(login.this,admin.class));
                            }
                            else {
                                if(FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()){
                                startActivity(new Intent(login.this, homePage.class));}
                                else {
                                    Toast.makeText(login.this, "Please verify your email", Toast.LENGTH_SHORT).show();
                                }
                            }


                        } else {
                            login_email.setText("");
                            login_password.setText("");

                            Toast.makeText(login.this, "Wrong Email or Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });





            }}
