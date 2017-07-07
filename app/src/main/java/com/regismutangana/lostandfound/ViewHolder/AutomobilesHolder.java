package com.regismutangana.lostandfound.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.regismutangana.lostandfound.R;

/**
 * Created by miller on 7/4/17.
 */

public class AutomobilesHolder extends RecyclerView.ViewHolder {
    private final TextView automobile_owner_names;
    private final TextView automobile_name;
    private final TextView automobile_model_name;
    private final TextView automobile_plate_number;
    private final TextView automobile_lost_location;

    public AutomobilesHolder(View itemView) {
        super(itemView);
        automobile_owner_names = (TextView) itemView.findViewById(R.id.automobile_owner_names);
        automobile_name = (TextView) itemView.findViewById(R.id.automobile_name);
        automobile_model_name = (TextView) itemView.findViewById(R.id.automobile_model_name);
        automobile_plate_number = (TextView) itemView.findViewById(R.id.automobile_plate_number);
        automobile_lost_location = (TextView) itemView.findViewById(R.id.automobile_lost_location);

    }
    public void setOwnerName(String name) {
        automobile_owner_names.setText(name);
    }

    public void setAutomobileName(String message) {
        automobile_name.setText(message);
    }
    public void setAutomobileModelName(String message) {
        automobile_model_name.setText(message);
    }
    public void setAutomobilePlateNumber(String message) {automobile_plate_number.setText(message);}
    public void setAutomobileLostLocation(String message) {automobile_lost_location.setText(message);}


}
