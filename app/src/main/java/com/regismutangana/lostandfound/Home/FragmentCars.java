package com.regismutangana.lostandfound.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.regismutangana.lostandfound.R;

/**
 * Created by miller on 6/18/17.
 */

public class FragmentCars extends Fragment {

    private static final String TAG = "FragmentCars";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cars,container,false);
        return view;
    }
}
