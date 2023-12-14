package com.example.savethefood;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

public class rv_adapter_customer extends RecyclerView.Adapter<holder_del> {


    private int img1[];
    private String food[];
    private String restaurant[];
    private String price[];
    private String location[];
    Context context;

    public rv_adapter_customer(int[] img1, String[] food, String[] restaurant, String[] price, String[] location, Context context) {
        this.img1 = img1;
        this.food = food;
        this.restaurant = restaurant;
        this.price = price;
        this.location = location;
        this.context = context;
    }

    @NonNull
    @Override
    public holder_del onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.card_item,parent,false);
        return new holder_del(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull holder_del holder, int position) {
        holder.cardView.setCardBackgroundColor(context.getColor(R.color.sell_cardView));
        holder.food.setText(food[position]);
        holder.restaurant.setText(restaurant[position]);
        holder.price.setText(price[position]);
        holder.location.setText(location[position]);

        holder.imageView.setImageResource(img1[position]);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              context.startActivity(new Intent(context, com.example.savethefood.location.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return food.length;
    }
}
