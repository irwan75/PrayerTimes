package com.example.primsalarmmanger.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.widget.Toast;

public class TimeAlarmIsyaNormal extends BroadcastReceiver {

    AudioManager modee;

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            modee = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
            modee.setRingerMode(modee.RINGER_MODE_NORMAL);
            Toast.makeText(context, "Smartphone Tidak Ter-Silent", Toast.LENGTH_LONG).show();
        }catch (Exception ex){
            Toast.makeText(context, "Aktifkan On Do Not Disturb Terlebih Dahulu : "+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
