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
import com.regismutangana.lostandfound.Model.Gadget;
import com.regismutangana.lostandfound.R;

import static com.basgeekball.awesomevalidation.ValidationStyle.UNDERLABEL;

/**
 * Created by miller on 6/18/17.
 */

public class FragmentGadgets extends Fragment {

    private static final String TAG = "FragmentGadgets";
    private Spinner  mSpinner;
    private EditText device_name;
    private Spinner spinner_device_type;
    private EditText device_model_name;
    private EditText device_serial_number;
    private EditText found_location;
    private EditText lost_location;
    private Button btnReportFound;
    private Button btnReportLost;
    ArrayAdapter<CharSequence> adapter;
    ArrayAdapter<CharSequence> mDeviceSpinneradapter;
    private String selected;
    private String mDeviceType;
    //declare_auth
    private FirebaseAuth mAuth;
    private DatabaseReference mFirebaseDbRef;
    private FirebaseDatabase mFirebaseInstance;
    private ProgressDialog pDialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gadgets,container,false);

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

        mSpinner = (Spinner) view.findViewById(R.id.spinner_report);
        device_name = (EditText) view.findViewById(R.id.device_name);
        spinner_device_type= (Spinner) view.findViewById(R.id.spinner_device_type);
        device_model_name= (EditText) view.findViewById(R.id.device_model_name);
        device_serial_number= (EditText) view.findViewById(R.id.device_serial_number);
        found_location = (EditText) view.findViewById(R.id.found_location);
        lost_location = (EditText) view.findViewById(R.id.lost_location);
        btnReportFound = (Button) view.findViewById(R.id.btnReportFound);
        btnReportLost = (Button) view.findViewById(R.id.btnReportLost);

        //spinners
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
                    if(spinnerText != null){
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
                    if(spinnerText !=null){
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
        mDeviceSpinneradapter = ArrayAdapter.createFromResource(getContext(),R.array.device_type,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_device_type.setAdapter(mDeviceSpinneradapter);
        spinner_device_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mDeviceType = (String) parent.getItemAtPosition(position);
                TextView spinnerText = (TextView)spinner_device_type.getSelectedView();
                if(spinnerText !=null){
                    spinnerText.setTextColor(Color.WHITE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        /**
         * Handlering gadget reports
         */
        btnReportLost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog("Reporting Device lost...");
                Log.d(TAG, "onClick: start reporting lost item.....");
                mFirebaseDbRef = mFirebaseInstance.getReference("devices").child("lost");
                //checking if user has entered all necessary data
                //activate trigger for validating
                boolean valid = mAwesomeValidation.validate();
                if(valid){
                    //model
                    Gadget  myGadget = new Gadget();
                    myGadget.setName(device_name.getText().toString());
                    myGadget.setType(mDeviceType);
                    myGadget.setModelName(device_model_name.getText().toString());
                    myGadget.setSerialNumber(device_serial_number.getText().toString());
                    myGadget.setLostLocation(lost_location.getText().toString());
                    myGadget.setOwnerUid(mAuth.getCurrentUser().getUid());
                    //report this lost device
                    mFirebaseDbRef.push().setValue(myGadget);
                    Log.d(TAG, "onClick: lost device reported successful");
                }
                hideProgressDialog();
            }
        });
        
        btnReportFound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog("Reporting Device found...");
                Log.d(TAG, "onClick: start reporting found item.....");
                mFirebaseDbRef = mFirebaseInstance.getReference("devices").child("found");
                boolean valid = mAwesomeValidation.validate();
                if(valid){
                    //model
                    Gadget  myGadget = new Gadget();
                    myGadget.setName(device_name.getText().toString());
                    myGadget.setType(mDeviceType);
                    myGadget.setModelName(device_model_name.getText().toString());
                    myGadget.setSerialNumber(device_serial_number.getText().toString());
                    myGadget.setFoundLocation(found_location.getText().toString());
                    myGadget.setFounderUid(mAuth.getCurrentUser().getUid());
                    //report this lost device
                    mFirebaseDbRef.push().setValue(myGadget);
                    Log.d(TAG, "onClick: found device reported successful");
                    hideProgressDialog();
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
