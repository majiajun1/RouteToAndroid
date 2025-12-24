package com.example.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DatabaseTransactionActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_transaction);

        dbHelper = new MyDatabaseHelper(this, "BookStore.db", null, 2);
        tvResult = findViewById(R.id.tv_transaction_result);

        Button btnReset = findViewById(R.id.btn_reset_data);
        btnReset.setOnClickListener(v -> resetData());

        Button btnSuccess = findViewById(R.id.btn_transaction_success);
        btnSuccess.setOnClickListener(v -> executeTransaction(true));

        Button btnFail = findViewById(R.id.btn_transaction_fail);
        btnFail.setOnClickListener(v -> executeTransaction(false));

        refreshDisplay();
    }

    private void resetData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("Book", null, null);
        ContentValues values = new ContentValues();
        values.put("name", "Old Book");
        values.put("price", 10.0);
        db.insert("Book", null, values);
        refreshDisplay();
        Toast.makeText(this, "Data Reset: Only 'Old Book' exists", Toast.LENGTH_SHORT).show();
    }

    private void executeTransaction(boolean success) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        
        // 1. 开启事务
        db.beginTransaction();
        try {
            // Step 1: Delete "Old Book"
            db.delete("Book", "name = ?", new String[]{"Old Book"});
            
            if (!success) {
                // Simulate a crash or error here!
                throw new NullPointerException("Simulated Crash!");
            }

            // Step 2: Insert "New Book"
            ContentValues values = new ContentValues();
            values.put("name", "New Book");
            values.put("price", 99.0);
            db.insert("Book", null, values);

            // 2. 标记事务成功
            // 如果代码跑到了这里，说明中间没有抛异常
            db.setTransactionSuccessful();
            Toast.makeText(this, "Transaction Committed!", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(this, "Transaction Failed: Rolled Back", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } finally {
            // 3. 结束事务
            // 这一步非常关键！
            // 如果 setTransactionSuccessful() 之前被调用过，这里就是“提交”。
            // 如果没被调用过（因为抛异常跳过了），这里就是“回滚”。
            db.endTransaction();
        }

        refreshDisplay();
    }

    private void refreshDisplay() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Book", null, null, null, null, null, null);
        StringBuilder sb = new StringBuilder();
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
                sb.append("Book: ").append(name).append(" ($").append(price).append(")\n");
            } while (cursor.moveToNext());
        } else {
            sb.append("Database is empty.");
        }
        cursor.close();
        tvResult.setText(sb.toString());
    }
}
