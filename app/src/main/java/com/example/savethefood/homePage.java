package com.example.savethefood;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class homePage extends AppCompatActivity {
    FloatingActionButton FAB;
    BottomNavigationView bottomNavigationView;
    Bottom_sheet_Fragment bottom_sheet;

    static FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        fm=getSupportFragmentManager();

        FAB = findViewById(R.id.fab);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottom_sheet=new Bottom_sheet_Fragment();

        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(foodFragment.newInstance("", ""));


        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottom_sheet.show(getSupportFragmentManager(), bottom_sheet.getTag());
            }
        });

    }
    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.frame_layout, fragment);

        transaction.addToBackStack(null);
        transaction.commit();
    }
    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Slide slide=new Slide();
                    switch (item.getItemId()) {
                        case R.id.icon1:

                            openFragment(foodFragment.newInstance("", ""));

                            return true;
                        case R.id.icon2:
                            openFragment(sellFoodFragment.newInstance("", ""));

                            return true;
                        case R.id.icon4:

                            openFragment(addPantryFragment.newInstance("", ""));
                            return true;
                        case R.id.icon5:

                            openFragment(contactFragment.newInstance("", ""));
                            return true;

                    }
                    return false;
                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        int id = item.getItemId();
        switch (id){
            case R.id.profile_menu:
                startActivity(new Intent(homePage.this,profile.class));
                return true;
            case R.id.cart_menu:
                startActivity(new Intent(homePage.this,MyCart.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}