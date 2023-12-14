package com.example.savethefood;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class holder_admin_user extends RecyclerView.ViewHolder {

    public CardView cardView;
    public ImageView imageView;
    public TextView food;
    public TextView restaurant;
    public TextView price;

    LinearLayout linearLayout;



    public holder_admin_user(@NonNull View itemView) {
        super(itemView);
            cardView=(CardView) itemView.findViewById(R.id.cv);
            imageView=(ImageView) itemView.findViewById(R.id.cardViewImage);
            food=(TextView) itemView.findViewById(R.id.cardViewFood);

            restaurant=(TextView) itemView.findViewById(R.id.cardViewResturant);
            price=(TextView) itemView.findViewById(R.id.cardViewPrice);

            linearLayout=(LinearLayout) itemView.findViewById(R.id.ll);
        //    linearLayout.setVisibility(View.INVISIBLE);

        }
}