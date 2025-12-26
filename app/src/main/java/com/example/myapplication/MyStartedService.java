package com.example.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyStartedService extends Service {

    private static final String TAG = "MyStartedService";
    private Thread myThread;

    public MyStartedService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: Service is created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: Service is started");

        // 1. 接收 Activity 传来的数据
        String taskName = "Default Task";
        if (intent != null) {
            taskName = intent.getStringExtra("task_name");
        }
        
        Toast.makeText(this, "Started Task: " + taskName, Toast.LENGTH_SHORT).show();

        // 模拟后台耗时任务
        final String finalTaskName = taskName;
        if (myThread == null || !myThread.isAlive()) {
            myThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int i = 0; i < 5; i++) {
                            if (Thread.currentThread().isInterrupted()) {
                                break;
                            }
                            Log.d(TAG, "Processing " + finalTaskName + "... " + i);
                            Thread.sleep(1000);
                        }
                        
                        if (!Thread.currentThread().isInterrupted()) {
                             Log.d(TAG, finalTaskName + " Finished!");
                             // 2. 任务做完，发广播通知 Activity
                             Intent broadcastIntent = new Intent("com.example.myapplication.TASK_DONE");
                             broadcastIntent.putExtra("result", finalTaskName + " is done successfully.");
                             broadcastIntent.setPackage(getPackageName()); // 明确指定包名，适配 Android 8.0+
                             sendBroadcast(broadcastIntent);
                        }
                        
                    } catch (InterruptedException e) {
                        Log.d(TAG, "Thread interrupted");
                    }
                }
            });
            myThread.start();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Service is destroyed");
        if (myThread != null && myThread.isAlive()) {
            myThread.interrupt(); 
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
