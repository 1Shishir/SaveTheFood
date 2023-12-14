package com.example.savethefood;
//
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


public class rv_adapter_food extends FirebaseRecyclerAdapter<data_avaiable_food, holder> {

    Context context;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public rv_adapter_food(@NonNull FirebaseRecyclerOptions<data_avaiable_food> options,Context context) {
        super(options);
        this.context=context;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onBindViewHolder(@NonNull holder holder, int position, @NonNull data_avaiable_food model) {

        holder.cardView.setCardBackgroundColor(context.getColor(R.color.sell_cardView));
        holder.food.setText(model.food_name);
        holder.restaurant.setText(model.food_restaurant);
        holder.price.setText(model.food_selling_price+" RM (Original Price: "+model.food_buying_price+" RM)");
        holder.location.setText("Location: "+model.food_location);

        Glide.with(holder.imageView.getContext()).load(model.food_img).into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Intent intent=new Intent(context,order.class);
                Intent intent=new Intent(context,cartActivity.class);
                //intent extras
                intent.putExtra("rest",model.food_restaurant);
                intent.putExtra("name",model.food_name);
                intent.putExtra("sell_price",model.food_selling_price);
                intent.putExtra("buy_price",model.food_buying_price);
                intent.putExtra("pickup",model.food_location);
                intent.putExtra("img",model.food_img);
                intent.putExtra("seller",model.food_uid);
                intent.putExtra("date",model.food_date);
                //

                context.startActivity(intent);

            }
        });


    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.card_item,parent,false);
        return new holder(view);
    }

}
