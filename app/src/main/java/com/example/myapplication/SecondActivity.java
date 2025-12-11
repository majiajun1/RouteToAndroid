package com.example.myapplication;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityMain2Binding;

import java.util.List;

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
        binding2.button3.setOnClickListener( v -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    Uri uri = Uri.parse("tel:10086");
                    intent.setData(uri);
                    startActivity(intent);

        });

        binding2.testProgressBar.setOnClickListener(
                v ->
                {

                    new AlertDialog.Builder(this)
                            .setTitle("This is Dialog") // 设置标题
                            .setMessage("Something important.") // 设置提示内容
                            .setCancelable(false) // 设置对话框不可取消（点击外部/返回键都关不掉）
                            // 设置确定按钮（OK）
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 点击OK后的逻辑（可自行补充）
                                    dialog.dismiss(); // 手动关闭对话框（可选，点击按钮默认会关闭）
                                }
                            })
                            // 设置取消按钮（Cancel）
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 点击Cancel后的逻辑（可自行补充）
                                    dialog.dismiss(); // 手动关闭对话框
                                }
                            })
                            .show(); // 显示对话框


                        int visibility = binding2.progressBar.getVisibility();
                        if (visibility == View.VISIBLE) {
                            binding2.progressBar.setVisibility(View.GONE);
                        } else {
                            binding2.progressBar.setVisibility(View.VISIBLE);
                        }

                }
        );
        binding2.testProgressHorizonBar.setOnClickListener(
                v ->
                {

                    binding2.progressHorizonBar.setProgress(binding2.progressHorizonBar.getProgress()+5);

                }
        );



    }
}