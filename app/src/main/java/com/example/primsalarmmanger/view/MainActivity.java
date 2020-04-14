package com.example.primsalarmmanger.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.primsalarmmanger.controller.SQLiteHelper;
import com.example.primsalarmmanger.model.ActionTbPenanganan;
import com.example.primsalarmmanger.view.*;
import com.example.primsalarmmanger.R;
import com.example.primsalarmmanger.controller.TimeAlarmDhuhur;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    AlarmManager jadwal;
    Button btnSetAlarm;
    Context mContext;
    Boolean kondisi = false;
    int i = 1;

    TextView tvTextRunning, tvKeterangan;
    ActionTbPenanganan tbp;
    PendingIntent piDhuhur;

    SQLiteDatabase db;
    SQLiteHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jadwal = (AlarmManager) getSystemService(ALARM_SERVICE);
        btnSetAlarm = findViewById(R.id.btnSetAlarm);
        tvTextRunning = findViewById(R.id.marqueeText);
        tvTextRunning.setVisibility(View.INVISIBLE);
        tvKeterangan = findViewById(R.id.tvKeterangan);

        helper = new SQLiteHelper(getApplicationContext());
        db = helper.getReadableDatabase();

        tbp = new ActionTbPenanganan(db);

        Intent dhuhur = new Intent(this, TimeAlarmDhuhur.class);
        piDhuhur = PendingIntent.getBroadcast(this, 1, dhuhur,0);

        if (tbp.select("75").equals("A1")){
            buttonStartAction(2);
        }else {
            buttonStartAction(1);
        }

        mContext = this;
        notificationManager(this);

        btnSetAlarm.setOnClickListener(this);

    }

    public void buttonStartAction(int i){
        if (i==1){
            tvKeterangan.setText("Klik Button Dibawah Untuk Mematikan");
            btnSetAlarm.setBackground(getDrawable(R.drawable.custom_button));
            btnSetAlarm.setTextColor(getResources().getColor(R.color.colorWhite));
            btnSetAlarm.setText("Auto Silent Nyala");
            tvTextRunning.setVisibility(View.VISIBLE);
            tvTextRunning.setSelected(true);
            startAlarm();
        }else if (i==2){
            tvKeterangan.setText("Klik Button Dibawah Untuk Menyalakan");
            btnSetAlarm.setTextColor(getResources().getColor(R.color.colorWhite));
            btnSetAlarm.setBackground(getDrawable(R.drawable.normal_button));
        }
    }

    private void startAlarm() {
        tbp.updateData("A2","75");
        jadwal.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, piDhuhur);
    }

    private void cancelAlarm(){
        Intent intent = new Intent(this, TimeAlarmDhuhur.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent,0);
        btnSetAlarm.setText("Auto Silent Mati");
        tvTextRunning.setVisibility(View.INVISIBLE);
        tbp.updateData("A1","75");
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
                buttonStartAction(1);
                i=2;
            }
        }
    }

}
