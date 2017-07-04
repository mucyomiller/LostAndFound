package com.regismutangana.lostandfound.Found;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.regismutangana.lostandfound.Home.SectionsPagerAdapter;
import com.regismutangana.lostandfound.R;
import com.regismutangana.lostandfound.Utils.BottomNavigationViewHelper;

public class FoundActivity extends AppCompatActivity {
    private static final String TAG = "FoundActivity";
    private static final int ACTIVITY_NUM = 1;
    private Context mContext = FoundActivity.this;

    @Override
    protected void onCreate(@Nullable  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found);
        setupBottomNavigationView();
        setupViewPager();
    }

    private void setupViewPager(){
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragemnt(new FoundFragmentCards());
        adapter.addFragemnt(new FoundFragmentAutomobile());
        adapter.addFragemnt(new FoundFragmentGadgets());
        ViewPager viewPager = (ViewPager)findViewById(R.id.container);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_cards);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_cars);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_gadgets);
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
