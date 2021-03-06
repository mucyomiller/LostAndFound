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
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.regismutangana.lostandfound.Model.Card;
import com.regismutangana.lostandfound.R;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;
import static com.basgeekball.awesomevalidation.ValidationStyle.COLORATION;
import static com.basgeekball.awesomevalidation.ValidationStyle.UNDERLABEL;

/**
 * Created by miller on 6/18/17.
 */

public class FragmentCards extends Fragment {
    private static final String TAG = "FragmentCards";
    private Spinner  mSpinner;
    private Spinner  mCardTypes;
    private EditText owner_name;
    private EditText id_number;
    private EditText found_location;
    private EditText lost_location;
    private Button btnReportFound;
    private Button btnReportLost;
    ArrayAdapter<CharSequence> adapter;
    private String selected;

    //declare_auth
    private FirebaseAuth mAuth;
    private DatabaseReference mFirebaseDbRef;
    private FirebaseDatabase mFirebaseInstance;
    private ProgressDialog pDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_cards,container,false);
        //initialize_auth
        mAuth = FirebaseAuth.getInstance();
        //initialize firebase
        mFirebaseInstance = FirebaseDatabase.getInstance();
        //init progress dialog
        // Progress dialog
        pDialog = new ProgressDialog(this.getActivity());
        pDialog.setCancelable(false);

        mSpinner = (Spinner) view.findViewById(R.id.spinner_report);
        mCardTypes = (Spinner) view.findViewById(R.id.spinner_cardtypes);
        owner_name = (EditText) view.findViewById(R.id.owner_name);
        id_number= (EditText) view.findViewById(R.id.id_number);
        found_location = (EditText) view.findViewById(R.id.found_location);
        lost_location = (EditText) view.findViewById(R.id.lost_location);

        btnReportFound = (Button) view.findViewById(R.id.btnReportFound);
        btnReportLost = (Button) view.findViewById(R.id.btnReportLost);
//        adapter = ArrayAdapter.createFromResource(getContext(),R.array.reports,R.layout.support_simple_spinner_dropdown_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0)
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
                else if(position == 1)
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
        mCardTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        id_number.setHint(R.string.hint_id_number);
                        break;
                    case 1:
                        id_number.setHint(R.string.hint_license_number);
                        break;
                    case 2:
                        id_number.setHint(R.string.hint_card_number);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        
        btnReportLost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog(getString(R.string.report_lost_item));
                Log.d(TAG, "onCreateView: start reporting lost item....");
                mFirebaseDbRef = mFirebaseInstance.getReference("cards").child("lost");
                //initialise form validator
                AwesomeValidation mAwesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
                //adding validations
                mAwesomeValidation.addValidation(getActivity(),R.id.owner_name,"^[a-zA-Z\\\\\\s]+( [a-zA-Z\\\\\\s]+){0,1}$",R.string.err_owner_name);
                mAwesomeValidation.addValidation(getActivity(),R.id.id_number,"^1(1|2)+[0-9]{3}+(7|8){1}+[0-9]{10}$",R.string.err_id_number);
                mAwesomeValidation.addValidation(getActivity(),R.id.lost_location,"^[a-zA-Z]{1,15}$",R.string.err_lost_location);
                //activate trigger for validating
                if(mAwesomeValidation.validate()){
                    //Card Model
                    Card mCard = new Card();
                    mCard.setIdNumber(id_number.getText().toString());
                    mCard.setOwnerName(owner_name.getText().toString());
                    mCard.setLostLocation(lost_location.getText().toString());
                    mCard.setOwnerUid(mAuth.getCurrentUser().getUid());
                    mFirebaseDbRef.push().setValue(mCard);
                    Log.d(TAG, "onClick: lost item reported successful");
                    Toast.makeText(getContext(), R.string.report_lost_message_success,Toast.LENGTH_LONG).show();
                    //cleaning form
                    mAwesomeValidation.clear();
                    owner_name.setText("");
                    id_number.setText("");
                    lost_location.setText("");
                    hideProgressDialog();
                }
                else
                {
                    Toast.makeText(getActivity(),R.string.validation_failed,Toast.LENGTH_LONG).show();
                    hideProgressDialog();
                }

            }
        });

        btnReportFound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog(getString(R.string.report_found_item));
                Log.d(TAG, "onClick: start reporting founded item.....");
                mFirebaseDbRef = mFirebaseInstance.getReference("cards").child("found");

                //initialise form validator
                AwesomeValidation mAwesomeValidation2 = new AwesomeValidation(ValidationStyle.BASIC);
                //adding validations
                mAwesomeValidation2.addValidation(getActivity(),R.id.owner_name,"^[a-zA-Z\\s]+( [a-zA-Z\\s]+){0,1}$",R.string.err_owner_name);
                mAwesomeValidation2.addValidation(getActivity(),R.id.id_number,"^1(1|2)+[0-9]{3}+(7|8){1}+[0-9]{10}$",R.string.err_id_number);
                mAwesomeValidation2.addValidation(getActivity(),R.id.found_location,"^[a-zA-Z]{1,15}$",R.string.err_found_location);
                //activate trigger for validating
                if(mAwesomeValidation2.validate()){
                    //Card Model
                    Card mCard = new Card();
                    mCard.setOwnerName(owner_name.getText().toString());
                    mCard.setFoundLocation(found_location.getText().toString());
                    mCard.setFounderUid(mAuth.getCurrentUser().getUid());
                    mCard.setIdNumber(id_number.getText().toString());
                    mFirebaseDbRef.push().setValue(mCard);
                    Log.d(TAG, "onClick: founded item reported successful");
                    Toast.makeText(getActivity(), R.string.report_found_message_success,Toast.LENGTH_LONG).show();
                    //clean form
                    mAwesomeValidation2.clear();
                    owner_name.setText("");
                    id_number.setText("");
                    found_location.setText("");
                    hideProgressDialog();
                }else
                {
                    Toast.makeText(getActivity(), R.string.validation_failed,Toast.LENGTH_LONG).show();
                    hideProgressDialog();
                }
//                Log.d(TAG,"Getting fcm Token....");
//                // Get token
//                String token = FirebaseInstanceId.getInstance().getToken();
//                Log.d(TAG, "onCreate: logging fcm token...."+token);


            }
        });
        return view;
    }
    public void showProgressDialog(String message){
        if (!pDialog.isShowing())
            pDialog.setMessage(message);
        pDialog.show();
    }

    public void hideProgressDialog(){
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
