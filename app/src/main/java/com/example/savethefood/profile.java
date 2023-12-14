package com.example.savethefood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

public class profile extends AppCompatActivity {

    ImageView profile_pic;
    TextView name;
    TextView address;
    TextView phone;

    Button editProfile;
    Button purchase;
    Button sell;
    Button sign_out;
    Button admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        editProfile=findViewById(R.id.profile_edit);
        purchase=findViewById(R.id.profile_order);
        sell=findViewById(R.id.profile_sell);
        sign_out=findViewById(R.id.profile_signout);

        profile_pic=findViewById(R.id.imagePro);

        name=findViewById(R.id.profile_name);
        phone=findViewById(R.id.profile_email);
        address=findViewById(R.id.profile_phone);
        admin=findViewById(R.id.profile_admin);

        admin.setVisibility(View.GONE);

        String uid= FirebaseAuth.getInstance().getUid();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference ref = database.getReference("profile").child(uid);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


               name.setText(String.valueOf(dataSnapshot.child("first_name").getValue())+" "+String.valueOf(dataSnapshot.child("last_name").getValue()));
               address.setText("Address: "+String.valueOf(dataSnapshot.child("address").getValue()));
               phone.setText("Phone: "+String.valueOf(dataSnapshot.child("phone").getValue()));



               if(dataSnapshot.child("image").getValue().equals("null")){
                   profile_pic.setImageResource(R.drawable.ic_baseline_image_24);

               }else {
                   Glide.with(profile.this).load(String.valueOf(dataSnapshot.child("image").getValue())).into(profile_pic);
              }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(profile.this,edit_profile.class));
            }
        });

        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(profile.this,purchase_dashboard.class));
            }
        });

        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(profile.this,sell_dashboard.class));
            }
        });

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(profile.this,admin.class));
            }
        });


        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(profile.this,MainActivity.class));
            }
        });

    }
}