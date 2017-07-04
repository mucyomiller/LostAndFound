package com.regismutangana.lostandfound.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.regismutangana.lostandfound.R;

/**
 * Created by miller on 7/4/17.
 */

public class CardsHolder extends RecyclerView.ViewHolder {
    private final TextView card_idnumber;
    private final TextView card_owner;
    private final TextView card_location;

    public CardsHolder(View itemView) {
        super(itemView);
        card_idnumber = (TextView) itemView.findViewById(R.id.card_idnumber);
        card_owner = (TextView) itemView.findViewById(R.id.card_owner);
        card_location = (TextView) itemView.findViewById(R.id.card_location);
    }
    public void setIdnumber(String name) {
        card_idnumber.setText(name);
    }

    public void setOwner(String message) {
        card_owner.setText(message);
    }
    public void setLocation(String message) {
        card_location.setText(message);
    }
}
