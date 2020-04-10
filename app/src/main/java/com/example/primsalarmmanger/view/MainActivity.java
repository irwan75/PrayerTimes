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
import com.example.primsalarmmanger.controller.TimeAlarmDhuhur;

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

        PendingIntent piDhuhur = PendingIntent.getBroadcast(this, 1, dhuhur,0);
        jadwal.setInexactRepeating(AlarmManager.RTC, System.currentTimeMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, piDhuhur);
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
