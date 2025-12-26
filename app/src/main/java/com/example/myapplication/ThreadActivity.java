package com.example.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ThreadActivity extends AppCompatActivity {

    private TextView tvStatus;

    // Handler 1: 负责更新 TextView 背景颜色
    private final Handler handler1 = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                String text = (String) msg.obj;
                tvStatus.setText("Handler 1 Updated:\n" + text);
                tvStatus.setBackgroundColor(0xFF81C784); // Light Green
            }
        }
    };

    // Handler 2: 负责弹 Toast (证明可以有多个 Handler 绑定同一个线程)
    private final Handler handler2 = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 2) {
                Toast.makeText(ThreadActivity.this, "Handler 2 received message!", Toast.LENGTH_SHORT).show();
            }
        }
    };

    // 记录当前的 Task 实例，方便取消
    private DownloadTask currentTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);

        tvStatus = findViewById(R.id.tv_status);
        Button btnCrash = findViewById(R.id.btn_crash);
        Button btnCorrect = findViewById(R.id.btn_correct);

        // 演示错误做法：子线程直接更新 UI
        btnCrash.setOnClickListener(v -> {
            new Thread(() -> {
                try {
                    // 模拟耗时操作
                    Thread.sleep(1000);

                    // ❌ 危险操作！这里是子线程
                    tvStatus.setText("This will crash the app!");

                } catch (Exception e) {
                    // 注意：虽然 App 会崩，但如果这里 catch 住了，可能只会在 Logcat 看到报错
                    // 真实的 Crash 往往是因为 CheckRootPermission 抛出的 ViewRootImpl$CalledFromWrongThreadException
                    e.printStackTrace();

                    // 为了演示效果，我们尝试用 runOnUiThread 弹个 Toast 告诉用户崩了
                    runOnUiThread(() ->
                        Toast.makeText(ThreadActivity.this, "Crash caught: " + e.getMessage(), Toast.LENGTH_LONG).show()
                    );
                }
            }).start();
        });

        // 演示正确做法：使用 Handler
        btnCorrect.setOnClickListener(v -> {
            tvStatus.setText("Loading...");
            tvStatus.setBackgroundColor(0xFFE0E0E0); // Reset color
            
            new Thread(() -> {
                try {
                    // 1. 模拟耗时操作 (比如下载图片)
                    Thread.sleep(2000);
                    
                    // 2. 准备消息给 Handler 1
                    Message msg1 = Message.obtain(handler1); 
                    msg1.what = 1; 
                    msg1.obj = "Handler Update Success!"; 
                    msg1.sendToTarget();

                    // 3. 准备消息给 Handler 2
                    Message msg2 = Message.obtain(handler2);
                    msg2.what = 2;
                    msg2.sendToTarget();
                    
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        });

        // 演示 AsyncTask
        Button btnAsyncTask = findViewById(R.id.btn_async_task);
        btnAsyncTask.setOnClickListener(v -> {
            // 先取消之前的任务（如果还在跑）
            if (currentTask != null && currentTask.getStatus() == android.os.AsyncTask.Status.RUNNING) {
                currentTask.cancel(true);
            }
            
            // 启动新任务
            currentTask = new DownloadTask();
            currentTask.execute("http://www.google.com/image.png");
        });

        // 演示取消任务
        Button btnCancelTask = findViewById(R.id.btn_cancel_task);
        btnCancelTask.setOnClickListener(v -> {
            if (currentTask != null && currentTask.getStatus() == android.os.AsyncTask.Status.RUNNING) {
                // 这里的 true 表示：如果线程正在 sleep 或 IO 阻塞，允许直接打断它 (InterruptedException)
                currentTask.cancel(true);
                Toast.makeText(this, "Cancel Request Sent!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No running task to cancel", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * AsyncTask 示例：模拟文件下载
     */
    private class DownloadTask extends android.os.AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tvStatus.setText("AsyncTask: Start...");
            tvStatus.setBackgroundColor(0xFFE0E0E0);
        }

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            
            try {
                for (int i = 0; i <= 100; i += 10) { // 稍微快一点，每次+10
                    // 【关键点】时刻检查是否被取消了
                    if (isCancelled()) {
                        return null; // 直接返回，后续代码不跑了
                    }

                    Thread.sleep(500); 
                    publishProgress(i);
                }
                return "Downloaded from: " + url;
                
            } catch (InterruptedException e) {
                // 如果在 sleep 时被 cancel(true)，会抛出这个异常
                return null;
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            tvStatus.setText("Downloading: " + values[0] + "%");
        }

        // 任务正常完成时调用
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            tvStatus.setText("AsyncTask Result:\n" + result);
            tvStatus.setBackgroundColor(0xFF64B5F6);
        }

        // 【新知识点】任务被取消时调用（代替 onPostExecute）
        @Override
        protected void onCancelled(String result) {
            super.onCancelled(result);
            tvStatus.setText("Task was Cancelled!");
            tvStatus.setBackgroundColor(0xFFE57373); // Red color
        }
    }
}

