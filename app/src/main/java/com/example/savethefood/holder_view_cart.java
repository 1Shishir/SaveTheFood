package com.example.savethefood;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class holder_view_cart extends RecyclerView.ViewHolder {

    public CardView cardView;
    public ImageView imageView;
    public TextView food;
    public TextView restaurant;
    public TextView price;
    public TextView location;
    LinearLayout linearLayout;
    ConstraintLayout constraintLayout;



    public holder_view_cart(@NonNull View itemView) {
        super(itemView);
        cardView=(CardView) itemView.findViewById(R.id.cv);
        imageView=(ImageView) itemView.findViewById(R.id.cardViewImage);
        food=(TextView) itemView.findViewById(R.id.cardViewFood);
        restaurant=(TextView) itemView.findViewById(R.id.cardViewResturant);
        price=(TextView) itemView.findViewById(R.id.cardViewPrice);
        location=(TextView) itemView.findViewById(R.id.cardViewLocation);
        linearLayout=(LinearLayout) itemView.findViewById(R.id.ll);
        constraintLayout=(ConstraintLayout) itemView.findViewById(R.id.constraintLayout2);

    }
}