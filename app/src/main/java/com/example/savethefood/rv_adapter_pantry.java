package com.example.savethefood;

import static com.example.savethefood.R.color.reddeep;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


public class rv_adapter_pantry extends FirebaseRecyclerAdapter<data_pantry, holder_pantry> {

Context context;

    public rv_adapter_pantry(FirebaseRecyclerOptions<data_pantry> options, Context context) {
    //public rv_adapter_pantry(FirebaseRecyclerOptions<data_pantry> options, Context context) {
        super(options);
        this.context=context;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onBindViewHolder(@NonNull holder_pantry holder, int position, @NonNull data_pantry model) {

        holder.cardView.setCardBackgroundColor(context.getColor(R.color.sell_cardView));
        holder.food.setText(model.pantry_name);
        holder.qut.setText("Qut: "+model.pantry_qut);
        holder.time.setText("Expiry : in "+model.pantry_day+" days");
        Glide.with(holder.imageView.getContext()).load(model.pantry_img).into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @NonNull
    @Override
    public holder_pantry onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.cardview_pantry,parent,false);
        return new holder_pantry(view);
    }
}