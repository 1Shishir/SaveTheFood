package com.example.savethefood;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class holder_pantry extends RecyclerView.ViewHolder {

    public CardView cardView;
    public ImageView imageView;
    public TextView food;
    public TextView qut;
    public TextView time;



    public holder_pantry(@NonNull View itemView) {
        super(itemView);

        cardView=(CardView) itemView.findViewById(R.id.cv_pantry);
        imageView=(ImageView) itemView.findViewById(R.id.cardViewImage_pantry);
        food=(TextView) itemView.findViewById(R.id.cardViewFood_pantry);
        qut=(TextView) itemView.findViewById(R.id.cardViewQut_pantry);
        time=(TextView) itemView.findViewById(R.id.cardViewTime_pantry);

    }
}