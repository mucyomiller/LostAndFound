package com.regismutangana.lostandfound.Home;

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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_cards,container,false);
        //initialize_auth
        mAuth = FirebaseAuth.getInstance();
        //initialize firebase
        mFirebaseInstance = FirebaseDatabase.getInstance();

        //initialise form validator
        final AwesomeValidation mAwesomeValidation = new AwesomeValidation(UNDERLABEL);
        mAwesomeValidation.setContext(getContext());

        mSpinner = (Spinner) view.findViewById(R.id.spinner_report);
        owner_name = (EditText) view.findViewById(R.id.owner_name);
        id_number= (EditText) view.findViewById(R.id.id_number);
        found_location = (EditText) view.findViewById(R.id.found_location);
        lost_location = (EditText) view.findViewById(R.id.lost_location);
        btnReportFound = (Button) view.findViewById(R.id.btnReportFound);
        btnReportLost = (Button) view.findViewById(R.id.btnReportLost);
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

        btnReportLost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAwesomeValidation.clear();
                Log.d(TAG, "onCreateView: start reporting lost item....");
                mFirebaseDbRef = mFirebaseInstance.getReference("cards").child("lost");
                //checking if user has entered all necessary data
                mAwesomeValidation.addValidation(getActivity(), R.id.owner_name, "[a-zA-Z\\s]+", R.string.err_owner_name);
                mAwesomeValidation.addValidation(getActivity(), R.id.id_number, "[0-9]+", R.string.err_id_number);
                mAwesomeValidation.addValidation(getActivity(), R.id.lost_location, "[a-zA-Z\\s]+", R.string.err_lost_location);

                //activate trigger for validating
                mAwesomeValidation.validate();
                //Card Model
                Card mCard = new Card(Long.parseLong(id_number.getText().toString()),owner_name.getText().toString(),lost_location.getText().toString(),mAuth.getCurrentUser().getUid());
                mFirebaseDbRef.push().setValue(mCard);
                Log.d(TAG, "onClick: lost item reported successful");

            }
        });

        btnReportFound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAwesomeValidation.clear();
                Log.d(TAG, "onClick: start reporting founded item.....");
                mFirebaseDbRef = mFirebaseInstance.getReference("cards").child("found");
                //checking if user has entered all necessary data
                mAwesomeValidation.addValidation(getActivity(), R.id.owner_name, "[a-zA-Z\\s]+", R.string.err_owner_name);
                mAwesomeValidation.addValidation(getActivity(), R.id.id_number, "[0-9]+", R.string.err_id_number);
                mAwesomeValidation.addValidation(getActivity(), R.id.found_location, "[a-zA-Z\\s]+", R.string.err_found_location);
                //activate trigger for validating
                mAwesomeValidation.validate();
                //Card Model
                Card mCard = new Card(owner_name.getText().toString(),found_location.getText().toString(),mAuth.getCurrentUser().getUid(),Long.parseLong(id_number.getText().toString()));
                mFirebaseDbRef.push().setValue(mCard);
                Log.d(TAG, "onClick: founded item reported successful");

//                Log.d(TAG,"Getting fcm Token....");
//                // Get token
//                String token = FirebaseInstanceId.getInstance().getToken();
//                Log.d(TAG, "onCreate: logging fcm token...."+token);


            }
        });
        return view;
    }
}
