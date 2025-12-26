package com.example.myapplication;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class ServiceActivity extends AppCompatActivity {

    private TaskReceiver taskReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        Button btnStart = findViewById(R.id.btn_start_service);
        Button btnStop = findViewById(R.id.btn_stop_service);
        Button btnStartForeground = findViewById(R.id.btn_start_foreground);
        Button btnStopForeground = findViewById(R.id.btn_stop_foreground);

        btnStart.setOnClickListener(v -> {
            Intent intent = new Intent(this, MyStartedService.class);
            // 1. 发送数据给 Service
            intent.putExtra("task_name", "Download Movie");
            startService(intent);
        });

        btnStop.setOnClickListener(v -> {
            Intent intent = new Intent(this, MyStartedService.class);
            stopService(intent);
        });

        btnStartForeground.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                int perm = ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS);
                if (perm != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.POST_NOTIFICATIONS},
                            1001);
                    return;
                }
            }

            Intent intent = new Intent(this, MyForegroundService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                ContextCompat.startForegroundService(this, intent);
            } else {
                startService(intent);
            }
            Toast.makeText(this, "Start Foreground Service", Toast.LENGTH_SHORT).show();
        });

        btnStopForeground.setOnClickListener(v -> {
            Intent intent = new Intent(this, MyForegroundService.class);
            stopService(intent);
            Toast.makeText(this, "Stop Foreground Service", Toast.LENGTH_SHORT).show();
        });

        // 注册广播接收器，监听 Service 的汇报
        taskReceiver = new TaskReceiver();
        IntentFilter filter = new IntentFilter("com.example.myapplication.TASK_DONE");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(taskReceiver, filter, Context.RECEIVER_NOT_EXPORTED);
        } else {
            registerReceiver(taskReceiver, filter);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 别忘了注销广播
        if (taskReceiver != null) {
            unregisterReceiver(taskReceiver);
        }
    }

    // 内部类：广播接收器
    class TaskReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 2. 接收 Service 的汇报
            String result = intent.getStringExtra("result");
            Toast.makeText(context, "Activity Received: " + result, Toast.LENGTH_LONG).show();
        }
    }
}
