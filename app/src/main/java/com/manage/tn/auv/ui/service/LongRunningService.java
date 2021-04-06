package com.manage.tn.auv.ui.service;

import android.app.*;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.manage.tn.auv.ui.broadcast.AlarmReceiver;

import java.util.Calendar;
import java.util.Date;

public class LongRunningService extends Service {

    public LongRunningService() {

    }



    @Override

    public void onCreate() {

        super.onCreate();

    }



    @Override

    public IBinder onBind(Intent intent) {

        // TODO: Return the communication channel to the service.

        throw new UnsupportedOperationException("Not yet implemented");

    }



    @Override

    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {

            @Override

            public void run() {

                Log.d("LongRunningService", "executed at " + new Date().

                        toString());

            }

        }).start();
        Calendar cal1 = Calendar.getInstance();
        cal1.set(Calendar.HOUR_OF_DAY,24);
        cal1.set(Calendar.MINUTE,00);
        cal1.set(Calendar.SECOND,00);
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        long triggerAtTime = SystemClock.elapsedRealtime()+1000*30;//10分钟间隔
        Intent itwo = new Intent(this, AlarmReceiver.class);
        itwo.setAction("Update date");
        PendingIntent pi = PendingIntent.getBroadcast(this, 1002, itwo, 0 );
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        String CHANNEL_ID = "my_channel_01";
        NotificationCompat.Builder mBuilder =

                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            //修改安卓8.1以上系统报错
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "My notification",                    NotificationManager.IMPORTANCE_MIN);
            notificationChannel.enableLights(false);//如果使用中的设备支持通知灯，则说明此通知通道是否应显示灯
            notificationChannel.setShowBadge(false);//是否显示角标
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
            NotificationManager managers = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            managers.createNotificationChannel(notificationChannel);
            mBuilder.setChannelId(CHANNEL_ID);
        }

        startForeground(-1,mBuilder.build());

        //   stopForeground(true);

        stopSelf();

        return super.onStartCommand(intent, flags, startId);


    }

}
