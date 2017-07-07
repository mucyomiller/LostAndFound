package com.regismutangana.lostandfound.Found;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.regismutangana.lostandfound.Model.Automobile;
import com.regismutangana.lostandfound.Model.Gadget;
import com.regismutangana.lostandfound.R;
import com.regismutangana.lostandfound.ViewHolder.AutomobilesHolder;

/**
 * Created by miller on 6/18/17.
 */

public class FoundFragmentAutomobile extends Fragment {
    private static final String TAG = "FoundFragmentAutomobile";
    //declare_auth
    private FirebaseAuth mAuth;
    private DatabaseReference mFirebaseDbRef;
    private FirebaseDatabase mFirebaseInstance;
    private FirebaseRecyclerAdapter mAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_found_automobile,container,false);
        RecyclerView foundautomobilerec = (RecyclerView) view.findViewById(R.id.foundautomobilerec);
        foundautomobilerec.setLayoutManager(new LinearLayoutManager(getContext()));
        //initialize_auth
        mAuth = FirebaseAuth.getInstance();
        //initialize firebase
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDbRef = mFirebaseInstance.getReference("automobile").child("lost");
        mAdapter = new FirebaseRecyclerAdapter<Automobile, AutomobilesHolder>(
                Automobile.class,
                R.layout.automobile_item,
                AutomobilesHolder.class,
                mFirebaseDbRef) {
            @Override
            public void populateViewHolder(final AutomobilesHolder holder, Automobile automobile, int position) {
                mFirebaseInstance.getReference("users").child(automobile.getOwnerUid()).child("names").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        holder.setOwnerName(dataSnapshot.getValue(String.class));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        holder.setOwnerName("");
                    }
                });
                holder.setAutomobileName(automobile.getName());
                holder.setAutomobileModelName(automobile.getModelName());
                holder.setAutomobilePlateNumber(automobile.getPlateNumber());
                holder.setAutomobileLostLocation(automobile.getLostLocation());
            }
        };
        foundautomobilerec.setAdapter(mAdapter);
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mAdapter.cleanup();
    }
}
