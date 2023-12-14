package com.example.savethefood;

import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link addPantryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class addPantryFragment extends Fragment {
    RecyclerView rv_pantry;

    DatabaseReference mbase;
    rv_adapter_pantry adapter;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public addPantryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment addPantryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static addPantryFragment newInstance(String param1, String param2) {
        addPantryFragment fragment = new addPantryFragment();
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
        View view= inflater.inflate(R.layout.fragment_add_pantry, container, false);
        // Inflate the layout for this fragment

        rv_pantry=(RecyclerView) view.findViewById(R.id.rv_pantry);
        rv_pantry.setLayoutManager(new GridLayoutManager(view.getContext(),1));

//firebase
        FirebaseStorage storage=FirebaseStorage.getInstance();
        String uid= FirebaseAuth.getInstance().getUid().toString();

        mbase = FirebaseDatabase.getInstance().getReference("pantry").child(uid);

        Query query = FirebaseDatabase.getInstance().getReference("pantry").child(uid);

        FirebaseRecyclerOptions<data_pantry> options = new FirebaseRecyclerOptions.Builder<data_pantry>()
                .setQuery(query, data_pantry.class)
                .build();

        adapter=new rv_adapter_pantry(options,getContext());
        rv_pantry.setAdapter(adapter);


        return view;
    }

    @Override public void onStart()
    {
        super.onStart();
        adapter.startListening();
    }

    @Override public void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }

}