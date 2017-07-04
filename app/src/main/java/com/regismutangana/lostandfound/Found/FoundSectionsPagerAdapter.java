package com.regismutangana.lostandfound.Found;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by miller on 6/18/17.
 */


/**
 * class that stores fragments for tabs
 */

public class FoundSectionsPagerAdapter extends FragmentPagerAdapter {

    private static final String TAG = "FoundSectionsPagerAdapter";
    private final List<Fragment> mFragmentList = new ArrayList<>();
    public FoundSectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragemnt(Fragment fragment){
        mFragmentList.add(fragment);
    }
}
