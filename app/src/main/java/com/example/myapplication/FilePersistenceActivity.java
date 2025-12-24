package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FilePersistenceActivity extends AppCompatActivity {

    private EditText etInput;
    private TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_persistence);

        etInput = findViewById(R.id.et_input);
        tvStatus = findViewById(R.id.tv_status);

        // 揭秘：看看是谁在真正干活
        Context mBase = getBaseContext();
        String implName = mBase.getClass().getName();
        tvStatus.setText("当前 mBase 的真实身份是：\n" + implName);

        Button btnSave = findViewById(R.id.btn_save);
        Button btnLoad = findViewById(R.id.btn_load);

        btnSave.setOnClickListener(v -> {
            String inputText = etInput.getText().toString();
            save(inputText);
        });

        btnLoad.setOnClickListener(v -> {
            String content = load();
            if (!TextUtils.isEmpty(content)) {
                etInput.setText(content);
                etInput.setSelection(content.length()); // Move cursor to end
                Toast.makeText(this, "Restored successfully", Toast.LENGTH_SHORT).show();
                tvStatus.setText("Loaded from file: data.txt");
            } else {
                Toast.makeText(this, "File is empty or not found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void save(String inputText) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            // MODE_PRIVATE: The default mode, where the created file can only be accessed by the calling application
//            out = openFileOutput("data.txt", Context.MODE_PRIVATE);
            out=openFileOutput("data.txt", Context.MODE_APPEND);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(inputText);
            tvStatus.setText("Saved to: " + getFilesDir() + "/data.txt");
            Toast.makeText(this, "Saved successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String load() {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            in = openFileInput("data.txt");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }
}
