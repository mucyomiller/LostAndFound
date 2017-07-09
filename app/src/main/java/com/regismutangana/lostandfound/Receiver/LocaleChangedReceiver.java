package com.regismutangana.lostandfound.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by miller on 7/9/17.
 */

public class LocaleChangedReceiver extends BroadcastReceiver {
    private static final String TAG = "LocaleChangedReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {


        if (intent.getAction (). compareTo (Intent.ACTION_LOCALE_CHANGED) == 0)
        {

            //listen for whole system restart
            Log.d(TAG, "received ACTION_LOCALE_CHANGED");
        }

    }
}