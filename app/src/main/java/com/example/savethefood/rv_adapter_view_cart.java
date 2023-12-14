package com.example.savethefood;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class rv_adapter_view_cart extends FirebaseRecyclerAdapter<data_cart, holder_view_cart> {

    Context context;
    String location=null;
    static String ffid;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     *
     */
    public rv_adapter_view_cart(@NonNull FirebaseRecyclerOptions<data_cart> options, Context context) {
        super(options);
        this.context=context;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onBindViewHolder(@NonNull holder_view_cart holder, int position, @NonNull data_cart model) {

        holder.cardView.setCardBackgroundColor(context.getColor(R.color.sell_cardView));
        holder.food.setText(model.food_name);
        holder.restaurant.setText(model.restaurant);
        holder.price.setText(model.selling_price+" RM (Original Price: "+model.buying_price+" RM)");
        holder.location.setText("Location: "+model.location);

        Glide.with(holder.imageView.getContext()).load(model.img).into(holder.imageView);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deletFromDb(model.food_id);



            }
        });

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(context,order.class);
                //intent extras
                intent.putExtra("rest",model.restaurant);
                intent.putExtra("name",model.food_name);
                intent.putExtra("sell_price",model.selling_price);
                intent.putExtra("buy_price",model.buying_price);
                intent.putExtra("pickup",model.location);
                intent.putExtra("img",model.img);
                intent.putExtra("food_id",model.food_id);

                intent.putExtra("food_date",model.date);

                //cbi
                intent.putExtra("seller",model.customer);
                //
                ffid=model.food_id;
                context.startActivity(intent);
             //   deletFromDb(model.food_id);

            }
        });




    }

    private void deletFromDb(String fid) {
        String uid= FirebaseAuth.getInstance().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("cart").child(uid);
        ref.child(fid).removeValue();
    }

    @NonNull
    @Override
    public holder_view_cart onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.card_item_food_admin,parent,false);
        return new holder_view_cart(view);
    }

}
