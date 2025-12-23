package com.example.myapplication;

import static android.widget.Toast.LENGTH_SHORT;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.databinding.ActivityMain2Binding;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.databinding.ActivityThirdBinding;
import com.example.myapplication.databinding.TitleBinding;

public class ThirdActivity extends AppCompatActivity {
    private ActivityThirdBinding activityBinding;
    private TitleBinding titleBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        activityBinding = ActivityThirdBinding.inflate(getLayoutInflater());
        setContentView(activityBinding.getRoot());
        titleBinding = activityBinding.topTitle;
        titleBinding.titleEdit.setOnClickListener(
                v -> {
                    Toast.makeText(this, "test button", LENGTH_SHORT).show();
                }
        );

    }
}