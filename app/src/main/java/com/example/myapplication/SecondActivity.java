package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityMain2Binding;

public class SecondActivity extends AppCompatActivity {

    private ActivityMain2Binding binding2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ViewBinding 初始化（避免布局文件名写错）
        binding2=ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding2.getRoot());
//        binding2.button2.setOnClickListener(v ->Toast.makeText(this,"You clicked Button 2", Toast.LENGTH_SHORT).show());
        binding2.button2.setOnClickListener(
                v ->
                {
                    Intent intent=new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.baidu.com"));
                    startActivity(intent);
                }
        );
    }
}