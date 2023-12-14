package com.example.savethefood;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link foodAdmin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class foodAdmin extends Fragment {
    RecyclerView rv_food;
    DatabaseReference mbase;
    rv_adapter_food_admin adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public foodAdmin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment foodAdmin.
     */
    // TODO: Rename and change types and number of parameters
    public static foodAdmin newInstance(String param1, String param2) {
        foodAdmin fragment = new foodAdmin();
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
        View view= inflater.inflate(R.layout.fragment_food_admin, container, false);
        //
        rv_food=(RecyclerView) view.findViewById(R.id.rv_food_admin);

        rv_food.setLayoutManager(new GridLayoutManager(view.getContext(),1));



        Query query = FirebaseDatabase.getInstance().getReference("avaiable_foods").child("all");

        FirebaseRecyclerOptions<data_avaiable_food> options = new FirebaseRecyclerOptions.Builder<data_avaiable_food>()
                .setQuery(query, data_avaiable_food.class)
                .build();

        adapter=new rv_adapter_food_admin(options,getContext());
        rv_food.setAdapter(adapter);
       // rv_food.setAdapter(adapter);

        // Inflate the layout for this fragment
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