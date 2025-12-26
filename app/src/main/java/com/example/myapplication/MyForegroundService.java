package com.example.myapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class MyForegroundService extends Service {

    private static final String TAG = "MyForegroundService";
    private static final String CHANNEL_ID = "foreground_demo_channel";

    private Thread workThread;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        createNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("前台Service示例")
                .setContentText("正在执行一个长时间任务...")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setOngoing(true)
                .build();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(1, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC);
        } else {
            startForeground(1, notification);
        }

        if (workThread == null || !workThread.isAlive()) {
            workThread = new Thread(() -> {
                try {
                    for (int i = 0; i < 10; i++) {
                        if (Thread.currentThread().isInterrupted()) {
                            Log.d(TAG, "Thread interrupted, stop working");
                            return;
                        }
                        Log.d(TAG, "Foreground task running... " + i);
                        Thread.sleep(1000);
                    }
                    Log.d(TAG, "Foreground task finished");
                    stopSelf();
                } catch (InterruptedException e) {
                    Log.d(TAG, "InterruptedException, stop working");
                }
            });
            workThread.start();
        }

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        if (workThread != null) {
            workThread.interrupt();
        }
        stopForeground(true);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "前台Service学习用渠道",
                    NotificationManager.IMPORTANCE_LOW
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }
}
