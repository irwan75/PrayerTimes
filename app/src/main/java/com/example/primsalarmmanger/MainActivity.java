package com.example.primsalarmmanger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.primsalarmmanger.controller.TimeAlarm;
import com.example.primsalarmmanger.controller.TimePickerFragment;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, View.OnClickListener {

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

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

        startAlarm(c);
    }
    public void buttonStartAction(int i){
        if (i==1){
            btnSetAlarm.setBackground(getDrawable(R.drawable.custom_button));
            btnSetAlarm.setTextColor(getResources().getColor(R.color.colorWhite));
        }else if (i==2){
            btnSetAlarm.setTextColor(getResources().getColor(R.color.colorGreenLight));
            btnSetAlarm.setBackground(getDrawable(R.drawable.normal_button));
        }
    }

    private void startAlarm(Calendar c) {
        Intent intent = new Intent(this, TimeAlarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent,0);
        jadwal.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);

    }

    private void cancelAlarm(){
        Intent intent = new Intent(this, TimeAlarm.class);
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
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
                btnSetAlarm.setText("Auto Silent Nyala");
                tvTextRunning.setVisibility(View.VISIBLE);
                tvTextRunning.setSelected(true);

                buttonStartAction(1);
                i=2;
            }
        }
    }
}
