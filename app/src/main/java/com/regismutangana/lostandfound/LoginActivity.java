package com.regismutangana.lostandfound;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.regismutangana.lostandfound.Home.HomeActivity;
import com.regismutangana.lostandfound.Model.User;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

        private static final String TAG = LoginActivity.class.getSimpleName();
        private Button btnLogin;
        private Button btnLinkToRegister;
        private EditText inputUsername;
        private EditText inputPassword;
        private ProgressDialog pDialog;
        private FirebaseAuth mAuth;
        private DatabaseReference mFirebaseDbRef;
        private FirebaseDatabase mFirebaseInstance;
        private String[] email = {null};
        private Spinner lang_spinner;
        private Locale locale;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            //setting language
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
            Configuration config = getBaseContext().getResources().getConfiguration();
            Log.d(TAG, "onCreate: LOCALE"+config.locale.getLanguage());
            String lang = settings.getString("language", "");
            if (! "".equals(lang) && !config.locale.getLanguage().equals(lang)) {
                Locale locale = new Locale(lang);
                Locale.setDefault(locale);
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                recreate();
            }

            //initialize_auth
            mAuth = FirebaseAuth.getInstance();
            //initialize firebase
            mFirebaseInstance = FirebaseDatabase.getInstance();

            inputUsername = (EditText) findViewById(R.id.username);
            inputPassword = (EditText) findViewById(R.id.password);
            lang_spinner = (Spinner) findViewById(R.id.lang_spinner);
            //handling changing language on spinner.
            lang_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    Configuration config = getBaseContext().getResources().getConfiguration();
                    String lang = mSharedPreferences.getString("language", "");
                    switch (position){
                        case 0:
                            break;
                        case 1:
                            if(!lang.equals("en"))
                            {
                                mSharedPreferences.edit().putString("language", "en").commit();
                                setLangRecreate("en");
                            }
                            break;
                        case 2:
                            if(!lang.equals("rw")){
                                mSharedPreferences.edit().putString("language", "rw").commit();
                                setLangRecreate("rw");
                            }
                            break;
                        case 3:
                            if(!lang.equals("fr")){
                                mSharedPreferences.edit().putString("language", "fr").commit();
                                setLangRecreate("fr");
                            }
                            break;

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            btnLogin = (Button) findViewById(R.id.btnLogin);
            btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);

            // Progress dialog
            pDialog = new ProgressDialog(this);
            pDialog.setCancelable(false);

            mAuth = FirebaseAuth.getInstance();
            // Login button Click Event
            btnLogin.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {
                    String username = inputUsername.getText().toString().trim();
                    String password = inputPassword.getText().toString().trim();
                    // Check for empty data in the form
                    if (!username.isEmpty() && !password.isEmpty()) {
                        // login user
                        checkLogin(username, password);
                    } else {
//                        Intent mIntent = new Intent(getApplicationContext(),SettingsActivity.class);
//                        startActivity(mIntent);
//                         Prompt user to enter credentials
                        Toast.makeText(getApplicationContext(),
                                R.string.creadential_required, Toast.LENGTH_LONG)
                                .show();
                    }
                }

            });

            // Link to Register Screen
            btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(),RegisterActivity.class);
                    startActivity(i);
                    finish();
                }
            });
        }

        public void checkLogin(final String username, final String password){
            showProgressDialog(getResources().getString(R.string.logging_in_message));
            Log.w(TAG, "Username is "+username+" Password is "+password);
            //getting ref
            mFirebaseDbRef = mFirebaseInstance.getReference();
            //getting email from firebase
            mFirebaseDbRef.child("users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for(DataSnapshot adress : dataSnapshot.getChildren()) {
                        if(adress.getValue(User.class).getUsername().equals(username)) {
                            email[0] = adress.getValue(User.class).getEmail();
                            Log.d("EMAIL_FOUND", email[0]+" password :"+password);
                            mAuth.signInWithEmailAndPassword(email[0], password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                hideProgressDialog();
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent i = new Intent(getApplicationContext(),HomeActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                startActivity(i);
                                finish();
                                //updateUI(user);
                            } else {
                                hideProgressDialog();
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, R.string.auth_failed ,
                                        Toast.LENGTH_SHORT).show();
                            }
                                }
                            });
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    hideProgressDialog();
                }
            });
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

    public void setLangRecreate(String langval) {
        Configuration config = getBaseContext().getResources().getConfiguration();
        locale = new Locale(langval);
        Locale.setDefault(locale);
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        //recreate();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
    }
