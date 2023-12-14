package com.example.savethefood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class payment_successful extends AppCompatActivity {
LottieAnimationView animationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_successful);

        animationView = findViewById(R.id.animation_view);
        animationView.setScale(3f);

//remove cart item
        String uid = FirebaseAuth.getInstance().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("cart").child(uid);
        ref.child(rv_adapter_view_cart.ffid).removeValue();

    }

    @Override
    public void onBackPressed() {
        Intent customIntent = new Intent(payment_successful.this, homePage.class);
        startActivity(customIntent);
        finish();
    }


}