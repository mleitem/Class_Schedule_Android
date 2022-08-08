package com.example.meganleitem_c196pa.termscheduler.UI;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.meganleitem_c196pa.R;

public class MyReceiver extends BroadcastReceiver {

    String start_assessment_id = "assessment start";
    String start_course_id = "course start";
    String end_assessment_id = "assessment start";
    String end_course_id = "course end";
    static int notificationID;

    @Override
    public void onReceive(Context context, Intent intent) {

        String type = intent.getStringExtra("type");

        // Assessment Start Date
        if(type.contains("starts") && type.contains("Assessment")){
            Toast startToast = Toast.makeText(context,"Assessment Starting Today", Toast.LENGTH_LONG);
            startToast.show();
            createNotificationChannel(context, start_assessment_id);

            Notification assessmentStart = new NotificationCompat.Builder(context, start_assessment_id)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentText(intent.getStringExtra("type"))
                    .setContentTitle("Assessment Starting Today").build();
            NotificationManager startManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            startManager.notify(notificationID++, assessmentStart);
        }

        // Assessment End Date
        if(type.contains("ends") && type.contains("Assessment")){
            Toast endToast = Toast.makeText(context, "Assessment Ending Today", Toast.LENGTH_LONG);
            endToast.show();
            createNotificationChannel(context, end_assessment_id);

            Notification assessmentEnd = new NotificationCompat.Builder(context, end_assessment_id)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentText(intent.getStringExtra("type"))
                    .setContentTitle("Assessment Ending Today").build();
            NotificationManager endManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            endManager.notify(notificationID++, assessmentEnd);
        }

        // Course Start Date
        if(type.contains("starts") && type.contains("Course")){
            Toast startToast = Toast.makeText(context,"Course Starting Today", Toast.LENGTH_LONG);
            startToast.show();
            createNotificationChannel(context, start_course_id);

            Notification courseStart = new NotificationCompat.Builder(context, start_course_id)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentText(intent.getStringExtra("type"))
                    .setContentTitle("Course Starting Today").build();
            NotificationManager startManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            startManager.notify(notificationID++, courseStart);
        }

        // Course End Date
        if(type.contains("ends") && type.contains("Course")){
            Toast endToast = Toast.makeText(context, "Course Ending Today", Toast.LENGTH_LONG);
            endToast.show();
            createNotificationChannel(context, end_course_id);

            Notification courseEnd = new NotificationCompat.Builder(context, end_course_id)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentText(intent.getStringExtra("type"))
                    .setContentTitle("Course Ending Today").build();
            NotificationManager endManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            endManager.notify(notificationID++, courseEnd);
        }
    }

    private void createNotificationChannel(Context context, String CHANNEL_ID) {
        CharSequence name = context.getResources().getString(R.string.channel_name);
        String description = context.getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}