package com.example.savethefood;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link sellFoodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class sellFoodFragment extends Fragment {
    TextInputEditText food_name;
    TextInputEditText food_restaurant;
    TextInputEditText food_buying_price;
    TextInputEditText food_selling_price;
    TextInputEditText food_location;
    TextInputEditText food_date;

    String str_food_name;
    String str_food_restaurant;
    String str_food_buying_price;
    String str_food_selling_price;
    String str_food_location;
    String str_food_date;

    Button food_pick_img_btn;
    Button food_upload;

    ImageView food_img;
    ImageView food_map_img;

    Uri uri;
    String link;
    String pos="0";



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public sellFoodFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment sellFoodFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static sellFoodFragment newInstance(String param1, String param2) {
        sellFoodFragment fragment = new sellFoodFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sell_food, container, false);

        food_name=(TextInputEditText) view.findViewById(R.id.nameofFood);
        food_restaurant =(TextInputEditText) view.findViewById(R.id.dateOfExire);
        food_buying_price=(TextInputEditText) view.findViewById(R.id.buyingPrice);
        food_selling_price=(TextInputEditText) view.findViewById(R.id.sellingPrice);
        food_location=(TextInputEditText) view.findViewById(R.id.sell_pickup);

        food_date=(TextInputEditText) view.findViewById(R.id.sellingDate);


        food_pick_img_btn=(Button) view.findViewById(R.id.sell_pick_img);
        food_upload=(Button) view.findViewById(R.id.sell_button);

        food_img=(ImageView) view.findViewById(R.id.food_image);
        food_map_img=(ImageView) view.findViewById(R.id.sell_map_icon);

        food_pick_img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //pick the image
                ImagePicker.with(sellFoodFragment.this).crop(1f,1f)
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start();
            }
        });

        food_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get from text box
                str_food_name=food_name.getText().toString();
                str_food_restaurant = food_restaurant.getText().toString();
                str_food_buying_price=food_buying_price.getText().toString();
                str_food_selling_price=food_selling_price.getText().toString();
                str_food_location=food_location.getText().toString();

                str_food_date=food_date.getText().toString();

                //uri=


                if(str_food_restaurant.isEmpty()){
                    //get email and name from dbase
                    final String[] user_name = new String[1];
                    String user_id= FirebaseAuth.getInstance().getUid().toString();
                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference ref = database.getReference("profile").child(user_id);
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            user_name[0] =String.valueOf(dataSnapshot.child("first_name").getValue());
                            str_food_restaurant=user_name[0];
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                    //
                    str_food_restaurant="name";
                }



                if(str_food_name.isEmpty()||str_food_restaurant.isEmpty()||str_food_buying_price.isEmpty()||str_food_selling_price.isEmpty()||str_food_location.isEmpty()||uri==null||str_food_date.isEmpty()){
                    Toast.makeText(getContext(), "Please Fill All Fields including Image", Toast.LENGTH_SHORT).show();
                }
                else {
                    uploading1();
                    food_name.setText("");
                    food_restaurant.setText("");
                    food_buying_price.setText("");
                    food_selling_price.setText("");
                    food_location.setText("");

                    food_date.setText("");

                    food_img.setImageResource(R.drawable.ic_baseline_image_24);

                }



            }
        });

        return view;
    }

    private void uploading1() {

        final String[] imgLink = new String[1];

        String uid;
        FirebaseUser firebase=FirebaseAuth.getInstance().getCurrentUser();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
             uid = currentUser.getUid();
            // rest of your code
        } else {
            // handle case where user is not signed in
            uid=login.uid;
        }


        FirebaseDatabase db=FirebaseDatabase.getInstance();

        FirebaseStorage storage=FirebaseStorage.getInstance();
        StorageReference uploder=storage.getReference("foods").child(uid).child("img"+new Random().nextInt(1000));



        uploder.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                uploder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        imgLink[0] =uri.toString();
                        getLastItem();

                        final data_avaiable_food[] data_avaiable_food = new data_avaiable_food[1];
                        DatabaseReference node =db.getReference("avaiable_foods").child("all");
                        node.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    if(snapshot.getChildrenCount()==0){
                                      //String fid=String.valueOf(snapshot.getChildrenCount()+1);



                                        data_avaiable_food[0] =new data_avaiable_food(uid,str_food_name,str_food_buying_price,str_food_selling_price,str_food_location, str_food_restaurant,imgLink[0],str_food_date,"1");
                                        node.child("1").setValue(data_avaiable_food[0]);

                                    }
                                    else if(snapshot.getChildrenCount()==1){
                                        data_avaiable_food[0] =new data_avaiable_food(uid,str_food_name,str_food_buying_price,str_food_selling_price,str_food_location, str_food_restaurant,imgLink[0],str_food_date,"2");
                                        node.child("2").setValue(data_avaiable_food[0]);
                                    }
                                    else {
                                        String fid=String.valueOf(Integer.parseInt(pos)+1);
                                        data_avaiable_food[0] =new data_avaiable_food(uid,str_food_name,str_food_buying_price,str_food_selling_price,str_food_location, str_food_restaurant,imgLink[0],str_food_date,fid);
                                        node.child(fid).setValue(data_avaiable_food[0]);
                                    }
                                }
                                else {
                                    data_avaiable_food[0] =new data_avaiable_food(uid,str_food_name,str_food_buying_price,str_food_selling_price,str_food_location, str_food_restaurant,imgLink[0],str_food_date,"1");
                                    node.child("1").setValue(data_avaiable_food[0]);

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }
                });

            }
        });


    }

    private void getLastItem() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("/avaiable_foods/all/");

        myRef.orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    if(dataSnapshot.getChildrenCount()>0){
                pos =dataSnapshot.getValue().toString();
                        Log.e("tag", "pos0"+pos);

                pos = String.valueOf(pos.charAt(1));
                Log.e("tag2",pos);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uri=data.getData();
        food_img.setImageURI(uri);

    }
}