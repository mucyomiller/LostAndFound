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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.regismutangana.lostandfound.Model.Card;
import com.regismutangana.lostandfound.R;
import com.regismutangana.lostandfound.ViewHolder.CardsHolder;

/**
 * Created by miller on 6/18/17.
 */

public class FoundFragmentCards extends Fragment {
    private static final String TAG = "FoundFragmentCards";
    //declare_auth
    private FirebaseAuth mAuth;
    private DatabaseReference mFirebaseDbRef;
    private FirebaseDatabase mFirebaseInstance;
    private FirebaseRecyclerAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_found_cards,container,false);
        RecyclerView foundcardsrec = (RecyclerView) view.findViewById(R.id.foundcardsrec);
        foundcardsrec.setLayoutManager(new LinearLayoutManager(getContext()));
        //initialize_auth
        mAuth = FirebaseAuth.getInstance();
        //initialize firebase
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDbRef = mFirebaseInstance.getReference("cards").child("lost");
        mAdapter = new FirebaseRecyclerAdapter<Card, CardsHolder>(
                Card.class,
                R.layout.card_item,
                CardsHolder.class,
                mFirebaseDbRef) {
            @Override
            public void populateViewHolder(CardsHolder holder, Card card, int position) {
                holder.setOwner(card.getOwnerName());
                holder.setLocation(card.getLostLocation());
            }
        };
        foundcardsrec.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mAdapter.cleanup();
    }
}
