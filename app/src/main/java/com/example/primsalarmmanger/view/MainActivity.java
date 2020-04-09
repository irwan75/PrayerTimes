package com.example.primsalarmmanger.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.primsalarmmanger.R;
import com.example.primsalarmmanger.controller.TimeAlarmAshar;
import com.example.primsalarmmanger.controller.TimeAlarmAsharNormal;
import com.example.primsalarmmanger.controller.TimeAlarmDhuhur;
import com.example.primsalarmmanger.controller.TimeAlarmDhuhurNormal;
import com.example.primsalarmmanger.controller.TimeAlarmIsya;
import com.example.primsalarmmanger.controller.TimeAlarmIsyaNormal;
import com.example.primsalarmmanger.controller.TimeAlarmMaghrib;
import com.example.primsalarmmanger.controller.TimeAlarmMaghribNormal;
import com.example.primsalarmmanger.controller.TimeAlarmSubuh;
import com.example.primsalarmmanger.controller.TimeAlarmSubuhNormal;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    AlarmManager jadwal;
    Button btnSetAlarm;
    Context mContext;
    Boolean kondisi = false;
    int i = 1;

    TextView tvTextRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jadwal = (AlarmManager) getSystemService(ALARM_SERVICE);
        btnSetAlarm = findViewById(R.id.btnSetAlarm);
        tvTextRunning = findViewById(R.id.marqueeText);
//        tvStatus = findViewById(R.id.statusButton);
//        tvStatus.setBackground(getDrawable(R.drawable.custom_button));
        tvTextRunning.setVisibility(View.INVISIBLE);

        buttonStartAction(2);

        mContext = this;
        notificationManager(this);

        btnSetAlarm.setOnClickListener(this);

    }

    public Calendar calendar(int hour, int minute){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

        return c;
    }

    public void buttonStartAction(int i){
        if (i==1){
            btnSetAlarm.setBackground(getDrawable(R.drawable.custom_button));
            btnSetAlarm.setTextColor(getResources().getColor(R.color.colorWhite));
        }else if (i==2){
            btnSetAlarm.setTextColor(getResources().getColor(R.color.colorWhite));
            btnSetAlarm.setBackground(getDrawable(R.drawable.normal_button));
        }
    }

    private void startAlarm() {
        Intent dhuhur = new Intent(this, TimeAlarmDhuhur.class);
        Intent ashar = new Intent(this, TimeAlarmAshar.class);
        Intent maghrib = new Intent(this, TimeAlarmMaghrib.class);
        Intent isya = new Intent(this, TimeAlarmIsya.class);
        Intent subuh = new Intent(this, TimeAlarmSubuh.class);
        Intent dhuhur1 = new Intent(this, TimeAlarmDhuhurNormal.class);
        Intent ashar1 = new Intent(this, TimeAlarmAsharNormal.class);
        Intent maghrib1 = new Intent(this, TimeAlarmMaghribNormal.class);
        Intent isya1 = new Intent(this, TimeAlarmIsyaNormal.class);
        Intent subuh1 = new Intent(this, TimeAlarmSubuhNormal.class);

        PendingIntent piDhuhur = PendingIntent.getBroadcast(this, 1, dhuhur,0);
        PendingIntent piDhuhur1 = PendingIntent.getBroadcast(this, 1, dhuhur1, 0);
        PendingIntent piAshar = PendingIntent.getBroadcast(this, 1, ashar,0);
        PendingIntent piAshar1 = PendingIntent.getBroadcast(this, 1, ashar1, 0);
        PendingIntent piMaghrib = PendingIntent.getBroadcast(this, 1, maghrib,0);
        PendingIntent piMaghrib1 = PendingIntent.getBroadcast(this, 1, maghrib1, 0);
        PendingIntent piIsya = PendingIntent.getBroadcast(this, 1, isya,0);
        PendingIntent piIsya1 = PendingIntent.getBroadcast(this, 1, isya1, 0);
        PendingIntent piSubuh = PendingIntent.getBroadcast(this, 1, subuh,0);
        PendingIntent piSubuh1 = PendingIntent.getBroadcast(this, 1, subuh1, 0);
        jadwal.setRepeating(AlarmManager.RTC, calendar(12, 04).getTimeInMillis(), AlarmManager.INTERVAL_DAY, piDhuhur);
        jadwal.setRepeating(AlarmManager.RTC, calendar(12, 24).getTimeInMillis(), AlarmManager.INTERVAL_DAY, piDhuhur1);
        jadwal.setRepeating(AlarmManager.RTC, calendar(15, 21).getTimeInMillis(), AlarmManager.INTERVAL_DAY, piAshar);
        jadwal.setRepeating(AlarmManager.RTC, calendar(15, 41).getTimeInMillis(), AlarmManager.INTERVAL_DAY, piAshar1);
        jadwal.setRepeating(AlarmManager.RTC, calendar(18, 04).getTimeInMillis(), AlarmManager.INTERVAL_DAY, piMaghrib);
        jadwal.setRepeating(AlarmManager.RTC, calendar(18, 24).getTimeInMillis(), AlarmManager.INTERVAL_DAY, piMaghrib1);
        jadwal.setRepeating(AlarmManager.RTC, calendar(19, 14).getTimeInMillis(), AlarmManager.INTERVAL_DAY, piIsya);
        jadwal.setRepeating(AlarmManager.RTC, calendar(19, 34).getTimeInMillis(), AlarmManager.INTERVAL_DAY, piIsya1);
        jadwal.setRepeating(AlarmManager.RTC, calendar(04, 46).getTimeInMillis(), AlarmManager.INTERVAL_DAY, piSubuh);
        jadwal.setRepeating(AlarmManager.RTC, calendar(05, 06).getTimeInMillis(), AlarmManager.INTERVAL_DAY, piSubuh1);
    }

    private void cancelAlarm(){
        Intent intent = new Intent(this, TimeAlarmDhuhur.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent,0);
        btnSetAlarm.setText("Auto Silent Mati");
        tvTextRunning.setVisibility(View.INVISIBLE);
        buttonStartAction(2);
        jadwal.cancel(pendingIntent);
    }

    public void notificationManager(final Context context){

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !notificationManager.isNotificationPolicyAccessGranted()) {

            //Yang berhasil
            Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
            startActivity(intent);

//            notificationManager.ACTION_NOTIFICATION_POLICY_ACCESS_GRANTED_CHANGED;

//            Intent intent = new Intent(Manifest.permission.ACCESS_NOTIFICATION_POLICY);
//             startActivity(intent);
//            mNotificationManager.setInterruptionFilter(interruptionFilter);
//            int previous_notification_interrupt_setting = notificationManager.getCurrentInterruptionFilter();
//            notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_PRIORITY);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btnSetAlarm) {
            kondisi = true;
            if (kondisi && i == 2){
                cancelAlarm();
                i=1;
            }else if (kondisi){

                startAlarm();
                btnSetAlarm.setText("Auto Silent Nyala");
                tvTextRunning.setVisibility(View.VISIBLE);
                tvTextRunning.setSelected(true);

                buttonStartAction(1);
                i=2;
            }
        }
    }
}
