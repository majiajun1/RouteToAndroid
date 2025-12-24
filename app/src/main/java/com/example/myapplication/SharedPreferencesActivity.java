package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SharedPreferencesActivity extends AppCompatActivity {

    private EditText etKey, etValue;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preferences);

        etKey = findViewById(R.id.et_sp_key);
        etValue = findViewById(R.id.et_sp_value);
        tvResult = findViewById(R.id.tv_sp_result);
        Button btnSave = findViewById(R.id.btn_sp_save);
        Button btnRead = findViewById(R.id.btn_sp_read);
        Button btnClear = findViewById(R.id.btn_sp_clear);

        // 1. 获取 SharedPreferences 对象
        // "MyPrefs" 是文件名，MODE_PRIVATE 表示只有本应用可以访问
        SharedPreferences sp = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        // 保存数据
        btnSave.setOnClickListener(v -> {
            String key = etKey.getText().toString();
            String value = etValue.getText().toString();

            if (TextUtils.isEmpty(key)) {
                Toast.makeText(this, "Key cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            // 2. 获取 Editor 对象
            SharedPreferences.Editor editor = sp.edit();
            // 3. 放入数据
            editor.putString(key, value);
            // 4. 提交 (apply() 是异步的，推荐使用；commit() 是同步的，会阻塞主线程)
            editor.apply();

            Toast.makeText(this, "Saved successfully!", Toast.LENGTH_SHORT).show();
            tvResult.setText("Saved:\nKey: " + key + "\nValue: " + value);
        });

        // 读取数据
        btnRead.setOnClickListener(v -> {
            String key = etKey.getText().toString();

            if (TextUtils.isEmpty(key)) {
                Toast.makeText(this, "Please enter a Key to read", Toast.LENGTH_SHORT).show();
                return;
            }

            // 直接从 sp 对象读取，第二个参数是默认值（如果找不到key对应的值就返回这个）
            String value = sp.getString(key, "Not Found");

            tvResult.setText("Read Result:\nKey: " + key + "\nValue: " + value);
        });

        // 清除数据
        btnClear.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sp.edit();
            editor.clear(); // 清空所有数据
            editor.apply();
            tvResult.setText("All data cleared!");
            Toast.makeText(this, "All data cleared", Toast.LENGTH_SHORT).show();
        });
    }
}
