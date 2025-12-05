package com.example.myapplication;

import android.os.Bundle;
import androidx.activity.ComponentActivity;
import android.util.Log;

public class MainActivity extends ComponentActivity { //MainActivity 是继承自AppCompatActivity的
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("MainActivity", "onCreate execute mjjjjjj");

    }
}

