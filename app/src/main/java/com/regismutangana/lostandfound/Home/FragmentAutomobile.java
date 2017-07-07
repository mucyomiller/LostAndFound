package com.regismutangana.lostandfound.Home;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.regismutangana.lostandfound.Model.Automobile;
import com.regismutangana.lostandfound.Model.Gadget;
import com.regismutangana.lostandfound.R;

import static com.basgeekball.awesomevalidation.ValidationStyle.UNDERLABEL;

/**
 * Created by miller on 6/18/17.
 */

public class FragmentAutomobile extends Fragment {
    private Spinner mSpinner;
    private EditText automobile_name;
    private EditText automobile_model_name;
    private EditText automobile_plate_number;
    private EditText found_location;
    private EditText lost_location;
    private Button btnReportFound;
    private Button btnReportLost;
    private String selected;
    ArrayAdapter<CharSequence> adapter;

    //declare_auth
    private FirebaseAuth mAuth;
    private DatabaseReference mFirebaseDbRef;
    private FirebaseDatabase mFirebaseInstance;
    private ProgressDialog pDialog;

    private static final String TAG = "FragmentAutomobile";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_automobile,container,false);
        //initialize_auth
        mAuth = FirebaseAuth.getInstance();
        //initialize firebase
        mFirebaseInstance = FirebaseDatabase.getInstance();
        //init progress dialog
        // Progress dialog
        pDialog = new ProgressDialog(getContext());
        pDialog.setCancelable(false);
        //initialise form validator
        final AwesomeValidation mAwesomeValidation = new AwesomeValidation(UNDERLABEL);
        mAwesomeValidation.setContext(getContext());

        automobile_name = (EditText) view.findViewById(R.id.automobile_name);
        automobile_model_name = (EditText)view.findViewById(R.id.automobile_name);
        automobile_plate_number = (EditText)view.findViewById(R.id.automobile_plate_number);
        found_location = (EditText)view.findViewById(R.id.found_location);
        lost_location = (EditText) view.findViewById(R.id.lost_location);
        btnReportFound = (Button) view.findViewById(R.id.btnReportFound);
        btnReportLost = (Button) view.findViewById(R.id.btnReportLost);
        mSpinner = (Spinner)view.findViewById(R.id.spinner_report);
        adapter = ArrayAdapter.createFromResource(getContext(),R.array.reports,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(((String) parent.getItemAtPosition(position)).equals("Lost"))
                {
                    selected = (String) parent.getItemAtPosition(position);
                    TextView spinnerText = (TextView)mSpinner.getSelectedView();
                    if (spinnerText != null) {
                        spinnerText.setTextColor(Color.WHITE);
                    }
                    //set visibility to VISIBLE on lost item
                    lost_location.setVisibility(View.VISIBLE);
                    btnReportLost.setVisibility(View.VISIBLE);
                    //set visiblility to GONE on found item
                    found_location.setVisibility(View.GONE);
                    btnReportFound.setVisibility(View.GONE);
                }
                else if(((String) parent.getItemAtPosition(position)).equals("Found"))
                {
                    //setting color to white on selected item
                    selected = (String) parent.getItemAtPosition(position);
                    TextView spinnerText = (TextView)mSpinner.getSelectedView();
                    if(spinnerText != null){
                        spinnerText.setTextColor(Color.WHITE);
                    }

                    //set VIsibility to VISIBLE on found items
                    found_location.setVisibility(View.VISIBLE);
                    btnReportFound.setVisibility(View.VISIBLE);

                    //set Visibility to GONE on lost items
                    lost_location.setVisibility(View.GONE);
                    btnReportLost.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /**
         * Handlering Automobiles reports
         */
        btnReportLost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog(getString(R.string.report_lost_automobile));
                Log.d(TAG, "onClick: start reporting automobile lost.....");
                mFirebaseDbRef = mFirebaseInstance.getReference("automobile").child("lost");
                //checking if user has entered all necessary data
               //activate trigger for validating
                boolean valid = mAwesomeValidation.validate();
                if(valid){
                    //model
                    Automobile mAutomobile = new Automobile();
                    mAutomobile.setName(automobile_name.getText().toString());
                    mAutomobile.setModelName(automobile_model_name.getText().toString());
                    mAutomobile.setPlateNumber(automobile_plate_number.getText().toString());
                    mAutomobile.setLostLocation(lost_location.getText().toString());
                    mAutomobile.setOwnerUid(mAuth.getCurrentUser().getUid());
                    //report this automobile lost
                    mFirebaseDbRef.push().setValue(mAutomobile);
                    Log.d(TAG, "onClick: lost automobile reported successful");
                }else{
                    Log.d(TAG, "onClick: validation fails..oops!");
                }
                hideProgressDialog();
            }
        });

        btnReportFound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog(getString(R.string.report_found_automobile));
                Log.d(TAG, "onClick: start reporting found item.....");
                mFirebaseDbRef = mFirebaseInstance.getReference("automobile").child("found");
                boolean valid = mAwesomeValidation.validate();
                if(valid){
                    //model
                    Automobile mAutomobile = new Automobile();
                    mAutomobile.setName(automobile_name.getText().toString());
                    mAutomobile.setModelName(automobile_model_name.getText().toString());
                    mAutomobile.setPlateNumber(automobile_plate_number.getText().toString());
                    mAutomobile.setFoundLocation(found_location.getText().toString());
                    mAutomobile.setFounderUid(mAuth.getCurrentUser().getUid());
                    //report this lost device
                    mFirebaseDbRef.push().setValue(mAutomobile);
                    Log.d(TAG, "onClick: found automobile reported successful");
                    hideProgressDialog();
                }
                else
                {
                    Log.d(TAG, "onClick: automobile found report fails .. Oops!");       
                }
                hideProgressDialog();
            }
        });

        return view;
    }
    public void showProgressDialog(String message){
        if (!pDialog.isShowing())
            pDialog.setMessage(message);
        pDialog.show();
    };
    public void hideProgressDialog(){
        if (pDialog.isShowing())
            pDialog.dismiss();
    };
}
