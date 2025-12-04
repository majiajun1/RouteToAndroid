package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() { //MainActivity 是继承自AppCompatActivity的
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}

