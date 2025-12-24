package com.example.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

public class DatabaseActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;
    private EditText etName, etAuthor, etPages, etPrice;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        dbHelper = new MyDatabaseHelper(this, "BookStore.db", null, 2);

        etName = findViewById(R.id.et_book_name);
        etAuthor = findViewById(R.id.et_book_author);
        etPages = findViewById(R.id.et_book_pages);
        etPrice = findViewById(R.id.et_book_price);
        tvResult = findViewById(R.id.tv_db_result);
        tvResult.setMovementMethod(new ScrollingMovementMethod());

        Button createDb = findViewById(R.id.btn_create_db);
        createDb.setOnClickListener(v -> {
            dbHelper.getWritableDatabase();
        });

        Button addData = findViewById(R.id.btn_add_data);
        addData.setOnClickListener(v -> {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            
            String name = etName.getText().toString();
            String author = etAuthor.getText().toString();
            String pagesStr = etPages.getText().toString();
            String priceStr = etPrice.getText().toString();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(priceStr)) {
                Toast.makeText(this, "Name and Price are required", Toast.LENGTH_SHORT).show();
                return;
            }

            values.put("name", name);
            values.put("author", author);
            values.put("pages", Integer.parseInt(pagesStr.isEmpty() ? "0" : pagesStr));
            values.put("price", Double.parseDouble(priceStr));
            
            long id = db.insert("Book", null, values);
            Toast.makeText(this, "Added Book ID: " + id, Toast.LENGTH_SHORT).show();
            values.clear();
        });

        Button updateData = findViewById(R.id.btn_update_data);
        updateData.setOnClickListener(v -> {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("price", 10.99);
            // Update all books where name is the current input
            String name = etName.getText().toString();
            if (TextUtils.isEmpty(name)) {
                Toast.makeText(this, "Enter Book Name to update its price to 10.99", Toast.LENGTH_SHORT).show();
                return;
            }
            int rows = db.update("Book", values, "name = ?", new String[]{name});
            Toast.makeText(this, "Updated " + rows + " rows", Toast.LENGTH_SHORT).show();
        });

        Button deleteData = findViewById(R.id.btn_delete_data);
        deleteData.setOnClickListener(v -> {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String name = etName.getText().toString();
            if (TextUtils.isEmpty(name)) {
                Toast.makeText(this, "Enter Book Name to delete", Toast.LENGTH_SHORT).show();
                return;
            }
            // Delete books where pages > 500 (just an example condition) or name matches
            int rows = db.delete("Book", "name = ?", new String[]{name});
            Toast.makeText(this, "Deleted " + rows + " rows", Toast.LENGTH_SHORT).show();
        });

        Button queryData = findViewById(R.id.btn_query_data);
        queryData.setOnClickListener(v -> {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            // Query all books
            Cursor cursor = db.query("Book", null, null, null, null, null, null);
            StringBuilder result = new StringBuilder();
            if (cursor.moveToFirst()) {
                do {
                    // Traverse Cursor
                    String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                    String author = cursor.getString(cursor.getColumnIndexOrThrow("author"));
                    int pages = cursor.getInt(cursor.getColumnIndexOrThrow("pages"));
                    double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
                    
                    result.append("Book: ").append(name).append("\n");
                    result.append("Author: ").append(author).append("\n");
                    result.append("Pages: ").append(pages).append("\n");
                    result.append("Price: ").append(price).append("\n");
                    result.append("----------------\n");
                } while (cursor.moveToNext());
            } else {
                result.append("No data found.");
            }
            cursor.close();
            tvResult.setText(result.toString());
        });

        Button gotoAdvanced = findViewById(R.id.btn_goto_advanced);
        gotoAdvanced.setOnClickListener(v -> {
            Intent intent = new Intent(this, DatabaseAdvancedActivity.class);
            startActivity(intent);
        });

        Button gotoTransaction = findViewById(R.id.btn_goto_transaction);
        gotoTransaction.setOnClickListener(v -> {
            Intent intent = new Intent(this, DatabaseTransactionActivity.class);
            startActivity(intent);
        });
    }
}
