package com.regismutangana.lostandfound.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.regismutangana.lostandfound.R;

/**
 * Created by miller on 7/4/17.
 */

public class GadgetsHolder extends RecyclerView.ViewHolder {
    private final TextView device_owner_names;
    private final TextView device_name;
    private final TextView device_type;
    private final TextView device_model_name;
    private final TextView device_serial_number;
    private final TextView device_lost_location;


    public GadgetsHolder(View itemView) {
        super(itemView);
        device_owner_names = (TextView) itemView.findViewById(R.id.device_owner_names);
        device_name = (TextView) itemView.findViewById(R.id.device_name);
        device_type = (TextView) itemView.findViewById(R.id.device_type);
        device_model_name = (TextView) itemView.findViewById(R.id.device_model_name);
        device_serial_number = (TextView) itemView.findViewById(R.id.device_serial_number);
        device_lost_location = (TextView) itemView.findViewById(R.id.device_lost_location);

    }
    public void setDeviceOwnerNames(String name) {
        device_owner_names.setText(name);
    }
    public void setDeviceName(String message) {device_name.setText(message);}
    public void setDeviceType(String message) {
        device_type.setText(message);
    }
    public void setDeviceModelName(String message) {
        device_model_name.setText(message);
    }
    public void setDeviceSerialNumber(String message) {device_serial_number.setText(message);}
    public void setDeviceLostLocation(String message) {device_lost_location.setText(message);}
}
