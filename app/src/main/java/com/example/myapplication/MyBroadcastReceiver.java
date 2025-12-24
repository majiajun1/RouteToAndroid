package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // 当收到广播时，会执行这个方法
        Toast.makeText(context, "Receiver 1 收到消息啦！", Toast.LENGTH_SHORT).show();
    }
}
