package com.example.savethefood;
//
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class rv_adapter_food_admin extends FirebaseRecyclerAdapter<data_avaiable_food, holder_food_admin> {

    Context context;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public rv_adapter_food_admin(@NonNull FirebaseRecyclerOptions<data_avaiable_food> options, Context context) {
        super(options);
        this.context=context;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onBindViewHolder(@NonNull holder_food_admin holder, int position, @NonNull data_avaiable_food model) {

        holder.cardView.setCardBackgroundColor(context.getColor(R.color.reddeep));
        holder.food.setText(model.food_name);
        holder.restaurant.setText(model.food_restaurant);
        holder.price.setText(model.food_selling_price+" RM (Original Price: "+model.food_buying_price+" RM)");
        holder.location.setText("Location: "+model.food_location);


        Glide.with(holder.imageView.getContext()).load(model.food_img).into(holder.imageView);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("avaiable_foods").child("all");

                    ref.child(model.food_id).removeValue();


               //

            }
        });
    }

    @NonNull
    @Override
    public holder_food_admin onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.card_item_food_admin,parent,false);
        return new holder_food_admin(view);
    }

}
