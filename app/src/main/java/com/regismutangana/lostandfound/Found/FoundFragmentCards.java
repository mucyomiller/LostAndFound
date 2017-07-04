package com.regismutangana.lostandfound.Found;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.regismutangana.lostandfound.R;

/**
 * Created by miller on 6/18/17.
 */

public class FoundFragmentCards extends Fragment {
    private static final String TAG = "FoundFragmentCards";
    //declare_auth
    private FirebaseAuth mAuth;
    private DatabaseReference mFirebaseDbRef;
    private FirebaseDatabase mFirebaseInstance;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_found_cards,container,false);
        //initialize_auth
        mAuth = FirebaseAuth.getInstance();
        //initialize firebase
        mFirebaseInstance = FirebaseDatabase.getInstance();
        return view;
    }
}
