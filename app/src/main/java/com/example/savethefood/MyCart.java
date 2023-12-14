package com.example.savethefood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class MyCart extends AppCompatActivity {
    RecyclerView rv_cart;
    rv_adapter_view_cart adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);



        rv_cart=(RecyclerView) findViewById(R.id.rv_cart);

        rv_cart.setLayoutManager(new GridLayoutManager(MyCart.this,1));

        String uid= FirebaseAuth.getInstance().getUid().toString();
        Query query = FirebaseDatabase.getInstance().getReference("cart").child(uid);

        FirebaseRecyclerOptions<data_cart> options = new FirebaseRecyclerOptions.Builder<data_cart>()
                .setQuery(query, data_cart.class)
                .build();


        adapter=new rv_adapter_view_cart(options,MyCart.this);
        rv_cart.setAdapter(adapter);
    }
    @Override public void onStart()
    {
        super.onStart();
        adapter.startListening();
    }

    @Override public void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }
}