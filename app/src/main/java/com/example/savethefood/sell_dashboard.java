package com.example.savethefood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class sell_dashboard extends AppCompatActivity {

    RecyclerView rv_delivery;
    rv_adapter_dash_purchase adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_dashboard);

        rv_delivery=(RecyclerView) findViewById(R.id.rv_f1_sell);

        rv_delivery.setLayoutManager(new GridLayoutManager(sell_dashboard.this,1));

        String uid= FirebaseAuth.getInstance().getUid().toString();

        Query query = FirebaseDatabase.getInstance().getReference("selling").child(uid);

        FirebaseRecyclerOptions<data_order> options = new FirebaseRecyclerOptions.Builder<data_order>()
                .setQuery(query, data_order.class)
                .build();


        adapter=new rv_adapter_dash_purchase(options,sell_dashboard.this,true);
        rv_delivery.setAdapter(adapter);
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