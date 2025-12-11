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

public class MainActivity extends AppCompatActivity  { //MainActivity 是继承自AppCompatActivity的
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
                Intent intent=new Intent("com.example.myapplication.ACTION_START");
                intent.addCategory("com.example.myapplication.MY_CATEGORY");
                startActivity(intent);
            }
        });

        binding.printType.setOnClickListener(v ->
        {
            String text= binding.editText.getText().toString();
            if(text!=null&&text.length()!=0)
            {
                Toast.makeText(MainActivity.this,text,Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MainActivity.this,"Input empty",Toast.LENGTH_SHORT).show();
            }

        });






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main,menu); //因为继承了Activity ，所以直接复用父类方法。
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        int itemId = item.getItemId();
        if(itemId == R.id.add_item)
        {
            Toast.makeText(this,"You clicked Add",Toast.LENGTH_SHORT).show();
        }else if(itemId ==R.id.remove_item)
        {
            Toast.makeText(this,"You clicked Remove",Toast.LENGTH_SHORT).show();
        }else if(itemId ==R.id.quit_item)
        {
            finish();
        }

        return true;


    }
}

