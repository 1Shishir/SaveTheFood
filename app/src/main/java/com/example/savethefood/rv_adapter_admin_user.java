package com.example.savethefood;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.auth.MultiFactor;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.internal.zzz;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class rv_adapter_admin_user extends FirebaseRecyclerAdapter<data_admin_user, holder_admin_user> {

    Context context;
    String uid;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     *
     */
    public rv_adapter_admin_user(@NonNull FirebaseRecyclerOptions<data_admin_user> options, Context context) {
        super(options);
        this.context=context;
        this.uid=uid;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onBindViewHolder(@NonNull holder_admin_user holder, int position, @NonNull data_admin_user model) {

        holder.cardView.setCardBackgroundColor(context.getColor(R.color.sell_cardView));

        holder.restaurant.setText(model.first_name + " " + model.last_name);
        holder.food.setText(model.phone);
        holder.price.setText(model.address);


        Glide.with(holder.imageView.getContext()).load(model.image).into(holder.imageView);


        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("profile");
                ref.child(model.uid).removeValue();

            }
        });


    }
    @NonNull
    @Override
    public holder_admin_user onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.card_view_admin_user,parent,false);
        return new holder_admin_user(view);
    }

}
