package com.example.savethefood;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Bottom_sheet_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Bottom_sheet_Fragment extends BottomSheetDialogFragment {


    TextInputEditText name;
    TextInputEditText qut;
    TextInputEditText expire_date;


    String str_name;
    String str_qut;
    String str_expire_date;

    ImageView img;

    ImageView pickImg;
    ImageView addToP;
    ImageView calender;
    Uri uri;
    String days;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Bottom_sheet_Fragment(){
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Bottom_sheet_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Bottom_sheet_Fragment newInstance(String param1, String param2) {
        Bottom_sheet_Fragment fragment = new Bottom_sheet_Fragment();
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

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_bottom_sheet_, container, false);


        name=(TextInputEditText) view.findViewById(R.id.btm_name);
        qut=(TextInputEditText) view.findViewById(R.id.btm_qut);
        expire_date=(TextInputEditText) view.findViewById(R.id.btm_date);

        img=(ImageView) view.findViewById(R.id.btm_image);

        pickImg=(ImageView) view.findViewById(R.id.pickImageBtn);
        addToP=(ImageView) view.findViewById(R.id.addPantryBtn);
        calender=(ImageView) view.findViewById(R.id.calender_icon);



        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);



                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),R.style.MyDatePickerDialogTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                if(monthOfYear<9) {
                                    expire_date.setText(dayOfMonth + "/0" + (monthOfYear + 1) + "/" + year);
                                }
                                else {
                                    expire_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                }
                            }
                        },
                        year, month, day);


                datePickerDialog.show();

//
            }
        });




        pickImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //pick the image
               ImagePicker.with(Bottom_sheet_Fragment.this).crop(1f,1f)
                       .compress(1024)
                       .maxResultSize(1080, 1080)
                       .start();
            }
        });

        addToP.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                // add to pantry

                str_name=name.getText().toString();
                str_qut=qut.getText().toString();
                str_expire_date=expire_date.getText().toString();

                if(str_name.isEmpty()||str_qut.isEmpty()||str_expire_date.isEmpty()||uri==null){
                    Toast.makeText(getContext(), "Please Fill All Fields including Image", Toast.LENGTH_SHORT).show();
                }
                else {

                    String TodayDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate start = LocalDate.parse(TodayDate, formatter);
                    LocalDate end = LocalDate.parse(str_expire_date, formatter);

                    days = String.valueOf(ChronoUnit.DAYS.between(start, end));

                    //fbase

                    uploadToFirebase();

                    //else
                }



            }
        });

        return view;
    }

    private void uploadToFirebase() {

        String uid= FirebaseAuth.getInstance().getUid().toString();

        FirebaseStorage storage=FirebaseStorage.getInstance();
        StorageReference uploder=storage.getReference("pantry").child(uid).child("img"+new Random().nextInt(1000));

        uploder.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                uploder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        data_pantry data_pantry=new data_pantry(days,uri.toString(),str_name,str_qut);


                        FirebaseDatabase db=FirebaseDatabase.getInstance();
                        DatabaseReference node =db.getReference("pantry").child(uid);

                        node.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    if(snapshot.getChildrenCount()>0){
                                        node.child(String.valueOf(snapshot.getChildrenCount()+1)).setValue(data_pantry);

                                    }
                                    else {
                                        node.child("1").setValue(data_pantry);

                                    }
                                }
                                else {
                                    node.child("1").setValue(data_pantry);

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        //
                        name.setText("");
                        qut.setText("");
                        expire_date.setText("");
                        img.setImageResource(R.drawable.ic_baseline_image_24);
                        dismiss();

                    }
                });

            }
        });




    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


            uri=data.getData();
            img.setImageURI(uri);




    }



}