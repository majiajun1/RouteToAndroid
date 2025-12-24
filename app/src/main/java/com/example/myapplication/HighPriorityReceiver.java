package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class HighPriorityReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "高级接收者：我先收到了！我要拦截它！", Toast.LENGTH_SHORT).show();
        
        // 核心功能：拦截广播
        // 一旦调用这个方法，后面的接收者（LowPriorityReceiver）就收不到了
//        abortBroadcast();
    }
}