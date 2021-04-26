package com.example.reminderbot;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private int notificationId= 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.setButton).setOnClickListener(this);
        findViewById(R.id.cancelButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        EditText editText = findViewById(R.id.editText);
        TimePicker timePicker = findViewById(R.id.timePicker);

        //Set NotificationId and Message

        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        intent.putExtra("notificationId", notificationId);
        intent.putExtra("message", editText.getText().toString());

        //PendingIntent

        PendingIntent alarmIntent = PendingIntent.getBroadcast(
                MainActivity.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT
        );

        //AlarmManager
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        
        switch (v.getId()) {
            case R.id.setButton:
                //Set Alarm
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();

                //Set the Time
                Calendar startAlarm = Calendar.getInstance();
                startAlarm.set(Calendar.HOUR_OF_DAY, hour);
                startAlarm.set(Calendar.MINUTE, minute);
                startAlarm.set(Calendar.SECOND, 0);
                long alarmStartTime = startAlarm.getTimeInMillis();

                // Start Alarm
                alarmManager.set(AlarmManager.RTC_WAKEUP, alarmStartTime, alarmIntent);

                Toast.makeText(this, "Alarm Created", Toast.LENGTH_SHORT).show();
                break;



            case R.id.cancelButton:
                //Cancel Alarm
                alarmManager.cancel(alarmIntent);
                Toast.makeText(this,"Canceled Alarm", Toast.LENGTH_SHORT).show();
                break;

        }

    }
}