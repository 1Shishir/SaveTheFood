package com.example.savethefood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;

public class sign_up extends AppCompatActivity {
    Button signup_button;

    ImageView map_icon;

    SupportMapFragment smf;
    FusedLocationProviderClient client;

    TextInputEditText signup_fname;
    TextInputEditText signup_lname;
    TextInputEditText signup_email;
    TextInputEditText signup_password;

    TextInputEditText signup_phone;
    TextInputEditText signup_address;

    String first_name="";
    String last_name="";
    String phone_number="";
    String my_address="";

    Uri uri=Uri.parse("https://firebasestorage.googleapis.com/v0/b/save-the-food-41f07.appspot.com/o/profile.jpg?alt=media&token=69250b5f-79e1-4d68-ae8f-317cb693eb3c");

    String email;
    String password;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();

        signup_button=(Button) findViewById(R.id.sign_signup);

        signup_fname=(TextInputEditText) findViewById(R.id.sign_fname);
        signup_lname=(TextInputEditText) findViewById(R.id.sign_lname);
        signup_email=(TextInputEditText) findViewById(R.id.sign_email);
        signup_password=(TextInputEditText) findViewById(R.id.sign_password);

        signup_phone=(TextInputEditText) findViewById(R.id.sign_phone);
        signup_address=(TextInputEditText) findViewById(R.id.sign_address);


        map_icon=(ImageView) findViewById(R.id.sign_location_icon);

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=signup_email.getText().toString();
                password=signup_password.getText().toString();
                signUpFireBAse(email,password);


            }
        });


    }




    private void uploadToFirebase() {

        first_name=signup_fname.getText().toString();
        last_name=signup_lname.getText().toString();

        phone_number=signup_phone.getText().toString();
        my_address=signup_address.getText().toString();

        if(phone_number.isEmpty()||first_name.isEmpty()||my_address.isEmpty()){
            Toast.makeText(sign_up.this, "Please Fill Required Field", Toast.LENGTH_SHORT).show();
        }

        else {
            //firebase upload
//            Bitmap bitmap=((BitmapDrawable)img.getDrawable()).getBitmap();
//            ByteArrayOutputStream bios=new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG,100,bios);
//            byte[] data=bios.toByteArray();
//
//
//
//            String email = FirebaseAuth.getInstance().getUid().toString();
//            data_profile data_profile=new data_profile(my_address,first_name,"null",last_name,phone_number);
//            FirebaseDatabase db=FirebaseDatabase.getInstance();
//            DatabaseReference node =db.getReference("profile").child(email);
//
//            uploder.putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                    uploder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//                            data_profile data_profile=new data_profile(my_address,first_name,uri.toString(),last_name,phone_number);
//
//                            FirebaseDatabase db=FirebaseDatabase.getInstance();
//                            DatabaseReference node =db.getReference("profile").child(email);
//
//                            node.setValue(data_profile);
//
//                            Toast.makeText(edit_profile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(edit_profile.this,profile.class));
//
//                        }
//                    });
//
//                }
//            });
//            //



                            //
            String uid = FirebaseAuth.getInstance().getUid();
                            data_profile data_profile=new data_profile(my_address,first_name,uri.toString(),last_name,phone_number,uid);

                            FirebaseDatabase db=FirebaseDatabase.getInstance();
                            DatabaseReference node =db.getReference("profile").child(uid);

                            node.setValue(data_profile);

                            signup_fname.setText("");
                            signup_lname.setText("");
                            signup_email.setText("");
                            signup_password.setText("");
                            signup_phone.setText("");
                            signup_address.setText("");

                             FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                 @Override
                                 public void onComplete(@NonNull Task<Void> task) {
                                     if(task.isSuccessful()){
                                         Toast.makeText(sign_up.this, "New user created, Please verify your Email", Toast.LENGTH_SHORT).show();
                                         startActivity(new Intent(sign_up.this,login.class));
                                     }
                                     else Toast.makeText(sign_up.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                 }
                             });




                        }



        }

    private void signUpFireBAse(String email,String password) {
       mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(sign_up.this, new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {

               if (task.isSuccessful()) {

                   uploadToFirebase();

               } else {
                   signup_fname.setText("");
                   signup_lname.setText("");
                   signup_email.setText("");
                   signup_password.setText("");
                   signup_phone.setText("");
                   signup_address.setText("");

                   Toast.makeText(sign_up.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();

               }
           }
       });}



}