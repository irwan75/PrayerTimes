package com.example.primsalarmmanger.controller;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.widget.Toast;

import java.util.Calendar;

public class TimeAlarmDhuhur extends BroadcastReceiver {

    AudioManager mode;
    PendingIntent pendingIntent;
    int i;

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent alarmIntent = new Intent(context, TimeAlarmDhuhur.class);
        pendingIntent = PendingIntent.getBroadcast(context, 1, alarmIntent, 0);

        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        try{
            if (calendar(12,04).getTimeInMillis()<System.currentTimeMillis() && calendar(12, 34).getTimeInMillis()>System.currentTimeMillis()){
                modeSilent(context);
            }else if (calendar(15,21).getTimeInMillis()<System.currentTimeMillis() && calendar(15, 46).getTimeInMillis()>System.currentTimeMillis()){
                modeSilent(context);
            }else if (calendar(18,04).getTimeInMillis()<System.currentTimeMillis() && calendar(18, 29).getTimeInMillis()>System.currentTimeMillis()){
                modeSilent(context);
            }else if (calendar(19,14).getTimeInMillis()<System.currentTimeMillis() && calendar(19, 39).getTimeInMillis()>System.currentTimeMillis()){
                modeSilent(context);
            }else if (calendar(04,46).getTimeInMillis()<System.currentTimeMillis() && calendar(05, 11).getTimeInMillis()>System.currentTimeMillis()){
                modeSilent(context);
            }else {
                modeNormal(context);
            }
        }catch (Exception ex){
            Toast.makeText(context, " Aktifkan On Do Not Disturb Terlebih Dahulu : "+ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
    }

    public Calendar calendar(int hour, int minute){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

        return c;
    }

    private void modeSilent(Context mContext){
        mode = (AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE);
        mode.setRingerMode(mode.RINGER_MODE_SILENT);
        Toast.makeText(mContext, "Smartphone Ter-Silent : "+i, Toast.LENGTH_LONG).show();
        i++;
    }

    private void modeNormal(Context mContext){
        mode = (AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE);
        mode.setRingerMode(mode.RINGER_MODE_NORMAL);
        Toast.makeText(mContext, "Smartphone Tidak Ter-Silent"+i, Toast.LENGTH_LONG).show();
        i++;
    }


}
