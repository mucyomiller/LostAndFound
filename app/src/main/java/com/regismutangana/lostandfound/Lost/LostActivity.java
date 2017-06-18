package com.regismutangana.lostandfound.Lost;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.regismutangana.lostandfound.R;
import com.regismutangana.lostandfound.Utils.BottomNavigationViewHelper;

public class LostActivity extends AppCompatActivity {
    private Context mContext = LostActivity.this;
    private static final int ACTIVITY_NUM = 1;

    private static final String TAG = "LostActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost);

        setupBottomNavigationView();
    }

    /**
     * BottomNavigationView Setup
     */
    private void setupBottomNavigationView(){

        Log.d(TAG, "setupBottomNavigationView: setting up bottomNavigationViewEx");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext,bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
