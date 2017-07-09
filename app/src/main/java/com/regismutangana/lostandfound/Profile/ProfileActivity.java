package com.regismutangana.lostandfound.Profile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.regismutangana.lostandfound.LoginActivity;
import com.regismutangana.lostandfound.R;
import com.regismutangana.lostandfound.SettingsActivity;
import com.regismutangana.lostandfound.Utils.BottomNavigationViewHelper;

import org.w3c.dom.Text;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";
    private static final int ACTIVITY_NUM = 2;
    private static final int RESULT_LOAD_IMG = 3838;
    private Context mContext = ProfileActivity.this;
    private FirebaseAuth mAuth;
    private DatabaseReference mFirebaseDbRef;
    private FirebaseDatabase mFirebaseInstance;
    TextView email;
    TextView names;
    CircleImageView circleImageView;
    TextView phone;
    TextView address;
    Uri mFileUri;
    private StorageReference mStorageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //initialize_auth
        mAuth = FirebaseAuth.getInstance();
        //initialize firebase
        mFirebaseInstance = FirebaseDatabase.getInstance();
        //saving other user infos
        mFirebaseDbRef = mFirebaseInstance.getReference("users");
        mStorageRef = FirebaseStorage.getInstance().getReference();
        setupToolbar();
        setupBottomNavigationView();
        circleImageView =(CircleImageView) findViewById(R.id.circleImageView);
        circleImageView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                registerForContextMenu(v);
                return false;
            }
        });

        // Reference to an image file in Firebase Storage
          StorageReference profilestorageReference = FirebaseStorage.getInstance().getReference("profiles").child(mAuth.getCurrentUser().getUid()).child("profile.jpg");
        // Load the image using Glide
          Glide.with(this /* context */)
                            .using(new FirebaseImageLoader())
                            .load(profilestorageReference)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .into(circleImageView);
        names =  (TextView) findViewById(R.id.names);
        email = (TextView) findViewById(R.id.email);
        phone = (TextView) findViewById(R.id.phone);
        address = (TextView) findViewById(R.id.address);
        //setting data
        mFirebaseDbRef.child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                names.setText(dataSnapshot.child("names").getValue(String.class));
                email.setText(mAuth.getCurrentUser().getEmail());
                phone.setText(dataSnapshot.child("phone").getValue(String.class));
                address.setText(dataSnapshot.child("location").getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setupToolbar(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.profileToolBar);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.d(TAG, "onMenuItemClick: MenuItem Clicked"+item);
                switch (item.getItemId()){
                    case R.id.logout:
                        Log.d(TAG, "onMenuItemClick: logging out..");
                        mAuth.signOut();
                        finishAffinity();
                        Intent mIntent = new Intent(getApplicationContext(), LoginActivity.class);
                        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mIntent);
                        break;
                    case R.id.action_settings:
                        Intent sIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                        startActivity(sIntent);
                        break;
                }
                return false;
            }
        });
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.update_profile_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.update_profile:
//                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//                photoPickerIntent.setType("image/*");
//                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMG);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK)
            switch (requestCode){
                case RESULT_LOAD_IMG:
                    mFileUri = data.getData();
                    uploadFromUri(mFileUri);

//                    try {
//                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
//                        //circleImageView.setImageBitmap(bitmap);
//
//                    } catch (IOException e) {
//                        Log.i("TAG", "Some exception " + e);
//                    }
                    break;
            }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu,menu);
        return true;
    }

    /**
     * BottomNavigationView Setup
     */
    private void setupBottomNavigationView(){

        Log.d(TAG, "setupBottomNavigationView: setting up bottomNavigationViewEx");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext,bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    // [START upload_from_uri]
    private void uploadFromUri(final Uri fileUri) {
        Log.d(TAG, "uploadFromUri:src:" + fileUri.toString());

        //displaying a progress dialog while upload is going on
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading");
        progressDialog.show();

        // [START get_child_ref]
        // Get a reference to store file at photos/<FILENAME>.jpg
        final StorageReference photoRef = mStorageRef.child("profiles").child(mAuth.getCurrentUser().getUid())
                .child("profile.jpg");
        // [END get_child_ref]
        // Upload file to Firebase Storage
        Log.d(TAG, "uploadFromUri:dst:" + photoRef.getPath());
        photoRef.putFile(fileUri).
                addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        //calculating progress percentage
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        //displaying percentage in progress dialog
                        progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        // Upload succeeded
                        Log.d(TAG, "uploadFromUri:onSuccess");
                        // Get the public download URL
                        Uri downloadUri = taskSnapshot.getMetadata().getDownloadUrl();
                        //if the upload is successfull
                        //hiding the progress dialog
                        progressDialog.dismiss();
                        //and displaying a success toast
                        Toast.makeText(getApplicationContext(), R.string.profile_update_message, Toast.LENGTH_LONG).show();
                        recreate();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Upload failed
                        Log.w(TAG, "uploadFromUri:onFailure", exception);
                        //if the upload is not successfull
                        //hiding the progress dialog
                        progressDialog.dismiss();
                        //and displaying error message
                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

}
