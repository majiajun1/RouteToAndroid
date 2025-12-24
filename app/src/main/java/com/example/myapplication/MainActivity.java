package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.ComponentActivity;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity { //MainActivity 是继承自AppCompatActivity的
    private ActivityMainBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        Log.d("MainActivity", "onCreate execute mjjjjjj");


        //kotlin更方便 不像java那样那么麻烦
        //viewbinding 插件也不错
//        Button botton1 = findViewById(R.id.button1);
//        botton1.setOnClickListener( v->
//            Toast.makeText(this,"You clicked Button 1", Toast.LENGTH_SHORT).show()
//        );
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
//        binding.button1.setOnClickListener( v->
//             Toast.makeText(this,"You clicked Button 1", Toast.LENGTH_SHORT).show()
//         );
        binding.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent= new Intent(MainActivity.this, SecondActivity.class);
                Intent intent = new Intent("com.example.myapplication.ACTION_START");
                intent.addCategory("com.example.myapplication.MY_CATEGORY");
                startActivity(intent);
            }
        });

        binding.printType.setOnClickListener(v ->
        {
            String text = binding.editText.getText().toString();
            if (text != null && text.length() != 0) {
                Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Input empty", Toast.LENGTH_SHORT).show();
                binding.imageview.setImageResource(R.drawable.ic_launcher_background);
            }

        });

        binding.btnFragmentDemo.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FragmentTestActivity.class);
            startActivity(intent);
        });

        binding.btnSplitFragment.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SplitFragmentActivity.class);
            startActivity(intent);
        });

        binding.btnSendBroadcast.setOnClickListener(v -> {
            // 发送广播
            Intent intent = new Intent("com.example.myapplication.MY_BROADCAST");
            // Android 8.0+ 静态注册的广播必须指定包名，或者使用动态注册。
            // 为了简单演示，我们这里指定包名，这样静态注册的 Receiver 也能收到。
            intent.setPackage(getPackageName());
            sendBroadcast(intent);
            Toast.makeText(this, "Broadcast Sent!", Toast.LENGTH_SHORT).show();
        });

        binding.btnSendOrderedBroadcast.setOnClickListener(v -> {
            Intent intent = new Intent("com.example.myapplication.ORDERED_BROADCAST");
            intent.setPackage(getPackageName());
            // 发送有序广播
            // 第一个参数：Intent
            // 第二个参数：接收者需要的权限（这里填 null）
            sendOrderedBroadcast(intent, null);
            Toast.makeText(this, "Ordered Broadcast Sent!", Toast.LENGTH_SHORT).show();
        });

        binding.btnFilePersistence.setOnClickListener(v -> {
            Intent intent = new Intent(this, FilePersistenceActivity.class);
            startActivity(intent);
        });

        binding.btnSharedPreferences.setOnClickListener(v -> {
            Intent intent = new Intent(this, SharedPreferencesActivity.class);
            startActivity(intent);
        });

        binding.btnSqliteDemo.setOnClickListener(v -> {
            Intent intent = new Intent(this, DatabaseActivity.class);
            startActivity(intent);
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu); //因为继承了Activity ，所以直接复用父类方法。
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        if (itemId == R.id.add_item) {
            Toast.makeText(this, "You clicked Add", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.remove_item) {
            Toast.makeText(this, "You clicked Remove", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.quit_item) {
            finish();
        }

        return true;


    }
}


