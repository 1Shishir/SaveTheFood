package com.example.savethefood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class cartActivity extends AppCompatActivity {

    ImageView cart_img;

    TextView cart_rest;
    TextView cart_name;
    TextView cart_price;
    TextView cart_pickup;
    TextView cart_id;

    TextView cart_date;


    Button confirm;
    Uri uri;
    Intent intent;
    String pos="0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cart_img=findViewById(R.id.imageCart);

        cart_rest=findViewById(R.id.Cart_name);
        cart_name=findViewById(R.id.Cart_rest);
        cart_price=findViewById(R.id.Cart_price);
        cart_pickup=findViewById(R.id.Cart_pickup);
        cart_id=findViewById(R.id.Cart_id);

        cart_date=findViewById(R.id.Cart_date);

        confirm=findViewById(R.id.Cart_confirm);

        intent = getIntent();

        cart_rest.setText("Restaurant: "+intent.getStringExtra("rest"));
        cart_name.setText("Food Name: "+intent.getStringExtra("name"));
        cart_price.setText( "Price: "+intent.getStringExtra("sell_price")+" RM (Original Price "+intent.getStringExtra("buy_price")+" RM)");
        cart_pickup.setText("Address: "+intent.getStringExtra("pickup"));
        cart_date.setText("Expire date: "+intent.getStringExtra("date"));

        Glide.with(cartActivity.this).load(intent.getStringExtra("img")).into(cart_img);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addToCartDbase();

                Toast.makeText(cartActivity.this, "Added to Cart", Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void addToCartDbase() {
        String uid= FirebaseAuth.getInstance().getUid().toString();
        FirebaseDatabase db=FirebaseDatabase.getInstance();


        DatabaseReference node =db.getReference("cart").child(uid);

            getLastItem();

            final data_cart[] data_cart = new data_cart[1];
            node.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    if(snapshot.getChildrenCount()==0){
//1
                        data_cart[0] =new data_cart(intent.getStringExtra("name"),intent.getStringExtra("rest"),intent.getStringExtra("buy_price"),intent.getStringExtra("sell_price"),intent.getStringExtra("pickup"),intent.getStringExtra("img"),uid,"1",intent.getStringExtra("date"));
                        node.child("1").setValue(data_cart[0]);

                    }
                    else if(snapshot.getChildrenCount()==1){
                        //2
                        data_cart[0] =new data_cart(intent.getStringExtra("name"),intent.getStringExtra("rest"),intent.getStringExtra("buy_price"),intent.getStringExtra("sell_price"),intent.getStringExtra("pickup"),intent.getStringExtra("img"),uid,"2",intent.getStringExtra("date"));
                        node.child("2").setValue(data_cart[0]);
                    }
                    else {
                        //fid
                        String fid=String.valueOf(Integer.parseInt(pos)+1);
                        data_cart[0] =new data_cart(intent.getStringExtra("name"),intent.getStringExtra("rest"),intent.getStringExtra("buy_price"),intent.getStringExtra("sell_price"),intent.getStringExtra("pickup"),intent.getStringExtra("img"),uid,fid,intent.getStringExtra("date"));
                        node.child(fid).setValue(data_cart[0]);
                    }
                }
                else {
                    //1
                    data_cart[0] =new data_cart(intent.getStringExtra("name"),intent.getStringExtra("rest"),intent.getStringExtra("buy_price"),intent.getStringExtra("sell_price"),intent.getStringExtra("pickup"),intent.getStringExtra("img"),uid,"1",intent.getStringExtra("date"));
                    node.child("1").setValue(data_cart[0]);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void getLastItem() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String uid= FirebaseAuth.getInstance().getUid();
        DatabaseReference myRef = database.getReference("cart").child(uid);

        myRef.orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    if(dataSnapshot.getChildrenCount()>0){
                        pos =dataSnapshot.getValue().toString();
                        pos = String.valueOf(pos.charAt(1));
                    }
                    else pos="0";
                }
                else pos="0";
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
}