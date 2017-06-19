package com.regismutangana.lostandfound.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.regismutangana.lostandfound.Found.FoundActivity;
import com.regismutangana.lostandfound.Home.HomeActivity;
import com.regismutangana.lostandfound.Lost.LostActivity;
import com.regismutangana.lostandfound.Profile.ProfileActivity;
import com.regismutangana.lostandfound.R;

/**
 * Created by miller on 6/18/17.
 */

public class BottomNavigationViewHelper {
    private static final String TAG = "BottomNavigationViewHel";

    public static void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx){
        Log.d(TAG, "setupBottomNavigationView: setting up bottomNavigationView");
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
    }

    public  static void enableNavigation(final Context context,BottomNavigationViewEx view){

        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.tab_home:
                        Intent intent1 = new Intent(context, HomeActivity.class);
                        context.startActivity(intent1);
                        break;
                    case R.id.tab_lost:
                        Intent intent2 = new Intent(context, LostActivity.class);
                        context.startActivity(intent2);
                        break;
                    case R.id.tab_found:
                        Intent intent3 = new Intent(context, FoundActivity.class);
                        context.startActivity(intent3);
                        break;
                    case R.id.tab_profile:
                        Intent intent4 = new Intent(context, ProfileActivity.class);
                        context.startActivity(intent4);
                        break;
                }
                return false;
            }
        });
    }
}
