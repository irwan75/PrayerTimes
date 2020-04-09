package com.example.primsalarmmanger.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.widget.Toast;

public class TimeAlarmAshar extends BroadcastReceiver {

    AudioManager mode;

    @Override
    public void onReceive(Context context, Intent intent) {
        try{
            mode = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
            mode.setRingerMode(mode.RINGER_MODE_SILENT);
            Toast.makeText(context, "Smartphone Ter-Silent", Toast.LENGTH_LONG).show();
        }catch (Exception ex){
            Toast.makeText(context, " Aktifkan On Do Not Disturb Terlebih Dahulu : "+ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
