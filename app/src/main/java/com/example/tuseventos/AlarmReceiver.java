package com.example.tuseventos;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.concurrent.ExecutionException;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String titulo = intent.getStringExtra("titulo");
        String id = intent.getStringExtra("id");
        System.out.println("Alarma");

        // Intent para lanzar la actividad al pulsar la notificación
        Intent notificationIntent = new Intent(context, AbrirNoticiaActivity.class);
        notificationIntent.putExtra("id", id);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(notificationIntent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(1001, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(context);
            Notification.Builder builder = new Notification.Builder(context, "1")
                    .setContentTitle(titulo)
                    .setContentText("Ha llegado la hora del evento")
                    .setSmallIcon(R.drawable.logo_periodico)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
            Notification notification = builder.build();
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.notify(1, notification);
        } else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "1")
                    .setContentTitle(titulo).setContentText("Ha llegado la hora del evento")
                    .setSmallIcon(R.drawable.logo_periodico)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
            Notification notification = builder.build();
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.notify(1, notification);
        }
    }

    private void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "canal 1";
            String description = "Descripcion del canal 1";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("1", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
