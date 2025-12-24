package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class LowPriorityReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "低级接收者：我也收到了...（如果我没被拦截的话）", Toast.LENGTH_SHORT).show();
    }
}
