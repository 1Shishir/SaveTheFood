package com.example.savethefood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Random;

public class order extends AppCompatActivity {

    payment_fragment paymentFrag;

    ImageView odr_img;
    ImageView odr_loc_icon;

    TextView odr_rest;
    TextView odr_name;
    TextView odr_price;
    TextView odr_pickup;
    TextView odr_id;

    TextView odr_date;



    TextInputEditText odr_loc;

    Button confirm;
    Uri uri;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        odr_img=findViewById(R.id.imageodr);
        odr_loc_icon=findViewById(R.id.odr_icon);

        odr_rest=findViewById(R.id.odr_name);
        odr_name=findViewById(R.id.odr_rest);
        odr_price=findViewById(R.id.odr_price);
        odr_pickup=findViewById(R.id.odr_pickup);
        odr_id=findViewById(R.id.odr_id);

        odr_date=findViewById(R.id.odr_date);

        odr_loc=findViewById(R.id.odr_location);
        confirm=findViewById(R.id.oder_confirm);

        intent = getIntent();

        odr_rest.setText("Restaurant: "+intent.getStringExtra("rest"));
        odr_name.setText("Food Name: "+intent.getStringExtra("name"));
        odr_price.setText( "Price: "+intent.getStringExtra("sell_price")+" RM (Original Price "+intent.getStringExtra("buy_price")+" RM)");
        odr_pickup.setText("Address: "+intent.getStringExtra("pickup"));
        odr_date.setText("Expire in: "+intent.getStringExtra("food_date"));
// date terminate
        Glide.with(order.this).load(intent.getStringExtra("img")).into(odr_img);


        paymentFrag=new payment_fragment();

        String uid= FirebaseAuth.getInstance().getUid().toString();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();


        DatabaseReference ref = database.getReference("profile").child(uid);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                odr_loc.setText(String.valueOf(dataSnapshot.child("address").getValue()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        odr_loc_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locate_to_map();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {

                                           paymentFrag.show(getSupportFragmentManager(), paymentFrag.getTag());
                                           addToFirebase();
                                           addtoSellerDbase();
                                         //  Toast.makeText(order.this, "Order Confirmed", Toast.LENGTH_SHORT).show();
                                       }
                                   });
    }

    private void addtoSellerDbase() {

        String uid= FirebaseAuth.getInstance().getUid().toString();
        FirebaseDatabase db=FirebaseDatabase.getInstance();

        data_order data_order=new data_order(uid,intent.getStringExtra("name"),intent.getStringExtra("buy_price"),intent.getStringExtra("sell_price"),intent.getStringExtra("pickup"),intent.getStringExtra("rest"),intent.getStringExtra("img"));


        DatabaseReference node =db.getReference("selling").child(intent.getStringExtra("seller"));

        node.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    if(snapshot.getChildrenCount()>0){
                        node.child(String.valueOf(snapshot.getChildrenCount()+1)).setValue(data_order);

                    }
                    else {
                        node.child("1").setValue(data_order);

                    }
                }
                else {
                    node.child("1").setValue(data_order);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }

    private void locate_to_map() {

    }

    private void addToFirebase() {



        String uid= FirebaseAuth.getInstance().getUid().toString();
        FirebaseDatabase db=FirebaseDatabase.getInstance();

                        data_order data_order=new data_order(uid,intent.getStringExtra("name"),intent.getStringExtra("buy_price"),intent.getStringExtra("sell_price"),intent.getStringExtra("pickup"),intent.getStringExtra("rest"),intent.getStringExtra("img"));

                        DatabaseReference node =db.getReference("order").child(uid);

                        node.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    if(snapshot.getChildrenCount()>0){
                                        node.child(String.valueOf(snapshot.getChildrenCount()+1)).setValue(data_order);

                                    }
                                    else {
                                        node.child("1").setValue(data_order);

                                    }
                                }
                                else {
                                    node.child("1").setValue(data_order);

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });



    }
}