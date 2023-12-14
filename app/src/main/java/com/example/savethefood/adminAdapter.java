package com.example.savethefood;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class adminAdapter extends FragmentPagerAdapter {
    int tabcount;
    public adminAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabcount=behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0:
                return new user();
            case 1:
                return new foodAdmin();
            default:
                return null;


        }
    }

    @Override
    public int getCount() {
        return tabcount;
    }
}
