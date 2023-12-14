package com.example.savethefood;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link user#newInstance} factory method to
 * create an instance of this fragment.
 */
public class user extends Fragment {
    RecyclerView recyclerView;
    rv_adapter_admin_user adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public user() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment user.
     */
    // TODO: Rename and change types and number of parameters
    public static user newInstance(String param1, String param2) {
        user fragment = new user();
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
        View view = inflater.inflate(R.layout.fragment_user, container, false);


        recyclerView = (RecyclerView) view.findViewById(R.id.rv_user);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("profile");
        Query query = ref.orderByKey();

        FirebaseRecyclerOptions<data_admin_user> options =
                new FirebaseRecyclerOptions.Builder<data_admin_user>()
                        .setQuery(query, data_admin_user.class)
                        .build();

        rv_adapter_admin_user adapter = new rv_adapter_admin_user(options,getContext());
        recyclerView.setAdapter(adapter);
        adapter.startListening();


        //  String uid= FirebaseAuth.getInstance().getUid().toString();

        //get all user

//       DatabaseReference node =FirebaseDatabase.getInstance().getReference("profile");
//node.addListenerForSingleValueEvent(new ValueEventListener() {
//  @Override
//  public void onDataChange(@NonNull DataSnapshot snapshot) {
//    for (DataSnapshot profileSnapshot : snapshot.getChildren()) {
//       String id = profileSnapshot.getKey();
//          String address = profileSnapshot.child("address").getValue(String.class);
//          String firstName = profileSnapshot.child("first_name").getValue(String.class);
//          String lastName = profileSnapshot.child("last_name").getValue(String.class);
//          String phone = profileSnapshot.child("phone").getValue(String.class);
//           String imageUrl = profileSnapshot.child("image").getValue(String.class);
//
//           }
//   }
//
//    @Override
//    public void onCancelled(@NonNull DatabaseError error) {
//
//    }
//});


        //




//        FirebaseRecyclerOptions<data_profile> options = new FirebaseRecyclerOptions.Builder<data_profile>()
//                .setQuery(query, data_profile.class)
//                .build();

      //  adapter=new rv_adapter_admin_user(options,getContext()," ");

      //  recyclerView.setAdapter(adapter);


        return view;
    }
//    @Override public void onStart()
//    {
//        super.onStart();
//        adapter.startListening();
//    }
//
//    @Override public void onStop()
//    {
//        super.onStop();
//        adapter.stopListening();
//    }
}