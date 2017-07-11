package com.regismutangana.lostandfound;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class NotificationActivity extends AppCompatActivity {

    TextView notification_title;
    TextView notification_message;
    ImageView ic_share;
    String title;
    String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        notification_title = (TextView) findViewById(R.id.notification_title);
        notification_message = (TextView) findViewById(R.id.notification_message);
        ic_share = (ImageView) findViewById(R.id.ic_share);
        //checking if there is notification message and display it
        Intent intent = getIntent();
        // Get the extras (if there are any)
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("notification_title")) {
                String title = extras.getString("notification_title");
                String message = extras.getString("notification_message");
                // setting message and title  to view
                notification_title.setText(title);
                notification_message.setText(message);
            }
        }

        ic_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(Intent.ACTION_SEND);
                mIntent.setType("text/plain");
                mIntent.putExtra(Intent.EXTRA_TEXT,title+" "+message);
                startActivity(mIntent);
            }
        });
    }


}
