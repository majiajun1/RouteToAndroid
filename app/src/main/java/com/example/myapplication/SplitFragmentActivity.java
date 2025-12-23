package com.example.myapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class SplitFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_split);

        if (savedInstanceState == null) {
            // Load FirstFragment into the top container
            getSupportFragmentManager().beginTransaction()
                .add(R.id.container_top, new FirstFragment())
                .commit();

            // Load SecondFragment into the bottom container
            getSupportFragmentManager().beginTransaction()
                .add(R.id.container_bottom, new SecondFragment())
                .commit();
        }
    }
}
