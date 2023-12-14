package com.example.savethefood;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link contactFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class contactFragment extends Fragment {

    Button facebook;
    Button instagram;
    Button email;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public contactFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment contactFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static contactFragment newInstance(String param1, String param2) {
        contactFragment fragment = new contactFragment();
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
        View view=inflater.inflate(R.layout.fragment_contact, container, false);

       email=view.findViewById(R.id.contact_email);
       instagram=view.findViewById(R.id.contact_insta);
       facebook=view.findViewById(R.id.contact_facebook);


       facebook.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               startFb();

           }
       });

       email.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(Intent.ACTION_SEND);
               intent.setType("message/rfc822");
               intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "savethefoodcs@gmail.com" });
               intent.putExtra(Intent.EXTRA_SUBJECT, "Support Email");
               intent.putExtra(Intent.EXTRA_TEXT, " ");
               startActivity(Intent.createChooser(intent, "Send email"));


           }
       });

       instagram.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {


               Uri uri = Uri.parse("http://instagram.com/_u/savethefoodmy/?igshid=YmMyMTA2M2Y%3D&fbclid=IwAR3TKM-TGwdzzIIYZ6ERgArlDRqZW0hSPYuiVHL0UWxqun8zPIABeMtb3ZQ");
               Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

               likeIng.setPackage("com.instagram.android");

               try {
                   startActivity(likeIng);
               } catch (ActivityNotFoundException e) {
                   startActivity(new Intent(Intent.ACTION_VIEW,
                           Uri.parse("https://www.instagram.com/savethefoodmy/?igshid=YmMyMTA2M2Y%3D&fbclid=IwAR3TKM-TGwdzzIIYZ6ERgArlDRqZW0hSPYuiVHL0UWxqun8zPIABeMtb3ZQ")));
               }


           }
       });

        return view;



    }

    private final void startFb() {

            final String urlFb = "fb://page/"+"profile.php?id=100091554717831";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(urlFb));

            final PackageManager packageManager = getContext().getPackageManager();
            List<ResolveInfo> list =
                    packageManager.queryIntentActivities(intent,
                            PackageManager.MATCH_DEFAULT_ONLY);
            if (list.size() == 0) {
                final String urlBrowser = "https://www.facebook.com/profile.php?id=100091554717831";
                intent.setData(Uri.parse(urlBrowser));
            }

            startActivity(intent);
        }





}