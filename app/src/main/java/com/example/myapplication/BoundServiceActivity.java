package com.example.myapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BoundServiceActivity extends AppCompatActivity {

    private MyBoundService.DownloadBinder downloadBinder;
    private boolean mBound = false;
    private TextView tvResult;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            downloadBinder = (MyBoundService.DownloadBinder) service;
            mBound = true;
            Toast.makeText(BoundServiceActivity.this, "已连接到 Service", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
            Toast.makeText(BoundServiceActivity.this, "Service 意外断开", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bound_service);

        tvResult = findViewById(R.id.tv_result);
        Button btnBind = findViewById(R.id.btn_bind_service);
        Button btnUnbind = findViewById(R.id.btn_unbind_service);
        Button btnGetData = findViewById(R.id.btn_get_data);

        btnBind.setOnClickListener(v -> {
            Intent intent = new Intent(this, MyBoundService.class);
            bindService(intent, connection, Context.BIND_AUTO_CREATE);
        });

        btnUnbind.setOnClickListener(v -> {
            if (mBound) {
                unbindService(connection);
                mBound = false;
                Toast.makeText(this, "已解绑 Service", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "当前未绑定", Toast.LENGTH_SHORT).show();
            }
        });

        btnGetData.setOnClickListener(v -> {
            if (mBound) {
                downloadBinder.startDownload();
                int progress = downloadBinder.getProgress();
                tvResult.setText("下载进度: " + progress + "%");
            } else {
                Toast.makeText(this, "请先绑定 Service！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(connection);
            mBound = false;
        }
    }
}
