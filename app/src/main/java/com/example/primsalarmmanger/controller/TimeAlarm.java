package com.example.primsalarmmanger.controller;

import android.app.Instrumentation;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import static androidx.core.app.ActivityCompat.startActivityForResult;

public class TimeAlarm extends BroadcastReceiver {

    AudioManager mode;
//    static final int ON_DO_NOT_DISTURB_CALLBACK_CODE= 1;
    static final String TAG = "MyActivity";

    @Override
    public void onReceive(Context context, Intent intent) {
        requestMutePermissions(context);
        Toast.makeText(context, "TerSet", Toast.LENGTH_LONG).show();
    }

    private void requestMutePermissions(Context context) {
        try {
            if (Build.VERSION.SDK_INT < 23) {
                mode = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
                mode.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                Toast.makeText(context, "Bisabisaji", Toast.LENGTH_SHORT).show();
            } else if( Build.VERSION.SDK_INT >= 23 ) {
                this.requestForDoNotDisturbPermissionOrSetDoNotDisturbForApi23AndUp(context);
            }
        } catch ( SecurityException e ) {

        }
    }

    private void requestForDoNotDisturbPermissionOrSetDoNotDisturbForApi23AndUp(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // if user granted access else ask for permission
        if ( notificationManager.isNotificationPolicyAccessGranted()) {
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        } else{
            //Dibagian sini mau diperbaiki

            // Open Setting screen to ask for permisssion
//            Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
//            startActivityForResult( intent, ON_DO_NOT_DISTURB_CALLBACK_CODE);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivityForResult(intent);
//            context.startActivity(intent);
            Log.i(TAG, "Masih Kurang Jelas");
//            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
//            audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);

            //Dibagian sini mau diperbaiki
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // Check which request we're responding to
//        if (requestCode == ON_DO_NOT_DISTURB_CALLBACK_CODE ) {
//            this.requestForDoNotDisturbPermissionOrSetDoNotDisturbForApi23AndUp();
//        }
//    }


}
