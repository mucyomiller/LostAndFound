package com.regismutangana.lostandfound.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.regismutangana.lostandfound.R;

/**
 * Created by miller on 6/18/17.
 */

public class FragmentAutomobile extends Fragment {
    private Spinner spinner_report;
    private EditText automobile_name;
    private EditText automobile_model_name;
    private EditText automobile_plate_number;
    private EditText found_location;
    private EditText lost_location;
    private Button btnReportFound;
    private Button btnReportLost;
    private String selected;

    private static final String TAG = "FragmentAutomobile";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_automobile,container,false);
        spinner_report = (Spinner)view.findViewById(R.id.spinner_report);
        automobile_name = (EditText) view.findViewById(R.id.automobile_name);
        automobile_model_name = (EditText)view.findViewById(R.id.automobile_name);
        automobile_plate_number = (EditText)view.findViewById(R.id.automobile_plate_number);
        found_location = (EditText)view.findViewById(R.id.found_location);
        lost_location = (EditText) view.findViewById(R.id.lost_location);
        btnReportFound = (Button) view.findViewById(R.id.btnReportFound);
        btnReportLost = (Button) view.findViewById(R.id.btnReportLost);
        spinner_report.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selected = (String)parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }
}
