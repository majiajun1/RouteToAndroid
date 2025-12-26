package com.example.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyBoundService extends Service {

    private static final String TAG = "MyBoundService";

    private final DownloadBinder mBinder = new DownloadBinder();

    public class DownloadBinder extends Binder {

        public void startDownload() {
            Log.d(TAG, "startDownload executed");
        }

        public int getProgress() {
            Log.d(TAG, "getProgress executed");
            return 0;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: 服务已创建");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: 客户端已绑定");
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: 服务已销毁");
    }
}
