package com.example.savethefood;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class edit_profile extends AppCompatActivity {

    Button mk_change;
    Button pick_img;

    TextInputEditText Fname;
    TextInputEditText Lname;
    TextInputEditText phone;
    TextInputEditText address;

    String first_name;
    String last_name;
    String phone_number;
    String my_address;

    String imgStr;
    Uri uri;

    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        mk_change=findViewById(R.id.edit_pro_mk_change);
        pick_img=findViewById(R.id.edit_pro_btn);

        Fname=findViewById(R.id.edit_pro_fname);
        Lname=findViewById(R.id.edit_pro_lname);
        phone=findViewById(R.id.edit_pro_phone);
        address=findViewById(R.id.editpro_location);

        img=findViewById(R.id.edit_pro_img);

        getFromFirebase();

        pick_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(edit_profile.this).crop(1f,1f)
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start();
            }
        });


        mk_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                first_name=Fname.getText().toString();
                last_name=Lname.getText().toString();
                phone_number=phone.getText().toString();
                my_address=address.getText().toString();


                if(phone_number.isEmpty()||my_address.isEmpty()){

                    Toast.makeText(edit_profile.this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
                }
                else if(!phone_number.isEmpty() && !my_address.isEmpty() && uri==null){
                  uploadDefaultFirebase();
                }
                else {
                        //firebase upload
                        uploadFirebase();
              }

            }
        });


    }

    private void uploadDefaultFirebase() {

        Bitmap bitmap=((BitmapDrawable)img.getDrawable()).getBitmap();
        ByteArrayOutputStream bios=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,bios);
        byte[] data=bios.toByteArray();

        FirebaseStorage storage=FirebaseStorage.getInstance();
        String uid= FirebaseAuth.getInstance().getUid().toString();
        StorageReference uploder=storage.getReference("profile").child(uid);

        uploder.putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                uploder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        data_profile data_profile=new data_profile(my_address,first_name,uri.toString(),last_name,phone_number,uid);

                        FirebaseDatabase db=FirebaseDatabase.getInstance();
                        DatabaseReference node =db.getReference("profile").child(uid);

                        node.setValue(data_profile);

                        Toast.makeText(edit_profile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(edit_profile.this,profile.class));

                    }
                });

            }
        });


    }

    private void uploadFirebase() {

                    FirebaseStorage storage=FirebaseStorage.getInstance();
                    String uid= FirebaseAuth.getInstance().getUid().toString();
                    StorageReference uploder=storage.getReference("profile").child(uid);

                    uploder.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            uploder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    data_profile data_profile=new data_profile(my_address,first_name,uri.toString(),last_name,phone_number,uid);

                                    FirebaseDatabase db=FirebaseDatabase.getInstance();
                                    DatabaseReference node =db.getReference("profile").child(uid);

                                    node.setValue(data_profile);

                                    Toast.makeText(edit_profile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(edit_profile.this,profile.class));

                                }
                            });

                        }
                    });

    }

    private void getFromFirebase() {
        String uid= FirebaseAuth.getInstance().getUid().toString();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("profile").child(uid);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                Fname.setText(String.valueOf(dataSnapshot.child("first_name").getValue()));
                Lname.setText(String.valueOf(dataSnapshot.child("last_name").getValue()));
                address.setText(String.valueOf(dataSnapshot.child("address").getValue()));
                phone.setText(String.valueOf(dataSnapshot.child("phone").getValue()));

                imgStr=String.valueOf(dataSnapshot.child("image").getValue());

                    Glide.with(edit_profile.this).load(String.valueOf(dataSnapshot.child("image").getValue())).into(img);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        uri=data.getData();
        img.setImageURI(uri);
    }
}