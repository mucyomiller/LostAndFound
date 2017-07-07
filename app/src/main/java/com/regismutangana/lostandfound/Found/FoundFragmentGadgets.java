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
import com.regismutangana.lostandfound.Model.Gadget;
import com.regismutangana.lostandfound.R;
import com.regismutangana.lostandfound.ViewHolder.GadgetsHolder;

/**
 * Created by miller on 6/18/17.
 */

public class FoundFragmentGadgets extends Fragment {

    private static final String TAG = "FoundFragmentGadgets";
    //declare_auth
    private FirebaseAuth mAuth;
    private DatabaseReference mFirebaseDbRef;
    private FirebaseDatabase mFirebaseInstance;
    private FirebaseRecyclerAdapter mAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_found_gadgets,container,false);
        RecyclerView foundgadgetsrec = (RecyclerView) view.findViewById(R.id.foundgadgetsrec);
        foundgadgetsrec.setLayoutManager(new LinearLayoutManager(getContext()));

        //initialize_auth
        mAuth = FirebaseAuth.getInstance();
        //initialize firebase
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDbRef = mFirebaseInstance.getReference("devices").child("lost");
        mAdapter = new FirebaseRecyclerAdapter<Gadget, GadgetsHolder>(
                Gadget.class,
                R.layout.gadget_item,
                GadgetsHolder.class,
                mFirebaseDbRef) {
            @Override
            public void populateViewHolder(final GadgetsHolder holder, Gadget gadget, int position) {
                mFirebaseInstance.getReference("users").child(gadget.getOwnerUid()).child("names").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        holder.setDeviceOwnerNames(dataSnapshot.getValue(String.class));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        holder.setDeviceOwnerNames("");
                    }
                });
                holder.setDeviceName(gadget.getName());
                holder.setDeviceType(gadget.getType());
                holder.setDeviceModelName(gadget.getModelName());
                holder.setDeviceSerialNumber(gadget.getSerialNumber());
                holder.setDeviceLostLocation(gadget.getLostLocation());
            }
        };
        foundgadgetsrec.setAdapter(mAdapter);

        return view;
    }
}
