package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class SecondBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // 第二个接收者
        Toast.makeText(context, "Receiver 2 也收到消息啦！", Toast.LENGTH_SHORT).show();


    }
}
