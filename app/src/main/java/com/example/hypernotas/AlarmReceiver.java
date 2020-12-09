package com.example.hypernotas;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "SAMPLE_CHANNEL";

    @Override
    public void onReceive(Context context, Intent recibir) {
        int idnotificacion= recibir.getIntExtra("notificacion",0);
        String titulo = recibir.getStringExtra("titulo");
        Intent intent = new Intent(context,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            CharSequence channel_name="My notificacion";
            int importancia = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,channel_name,importancia);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder notificacion = new NotificationCompat.Builder(context,CHANNEL_ID);
        notificacion.setAutoCancel(true);
        notificacion.setSmallIcon(R.mipmap.ic_launcher);
        notificacion.setTicker("Recordatorio de HyperNotas");
        notificacion.setContentTitle("Es hora de realizar la tarea:");
        notificacion.setContentText(titulo);
        notificacion.setContentIntent(pendingIntent);
        notificacion.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        notificationManager.notify(idnotificacion,notificacion.build());
    }
}
