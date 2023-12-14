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

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class rv_adapter_dash_purchase extends FirebaseRecyclerAdapter<data_order, holder> {

    Context context;
    Boolean mode;
    String location=null;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     * @param mode
     */
    public rv_adapter_dash_purchase(@NonNull FirebaseRecyclerOptions<data_order> options, Context context, Boolean mode) {
        super(options);
        this.context=context;
        this.mode=mode;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onBindViewHolder(@NonNull holder holder, int position, @NonNull data_order model) {

        holder.cardView.setCardBackgroundColor(context.getColor(R.color.sell_cardView));
        holder.food.setText(model.food_name);
        holder.restaurant.setText(model.restaurant);
        holder.price.setText(model.selling_price+" RM (Was "+model.buying_price+" RM)");

        if(mode){
            //for seller

            final FirebaseDatabase database = FirebaseDatabase.getInstance();

            DatabaseReference ref = database.getReference("profile").child(model.customer);

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    location=String.valueOf(dataSnapshot.child("address").getValue());
                    holder.location.setText("Deliver to: "+location);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }else {
            //for customer
            location=model.location;
            holder.location.setText("Location: "+location);
        }


        Glide.with(holder.imageView.getContext()).load(model.img).into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,location.class);
                intent.putExtra("rest_loc",location);
                intent.putExtra("mode",mode);
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
