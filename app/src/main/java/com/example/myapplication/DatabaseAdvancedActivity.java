package com.example.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DatabaseAdvancedActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_advanced);

        dbHelper = new MyDatabaseHelper(this, "BookStore.db", null, 2);
        tvResult = findViewById(R.id.tv_advanced_result);

        Button btnPrepare = findViewById(R.id.btn_prepare_data);
        btnPrepare.setOnClickListener(v -> prepareData());

        Button btnQuery = findViewById(R.id.btn_execute_complex_query);
        btnQuery.setOnClickListener(v -> executeComplexQuery());
    }

    private void prepareData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Clear old data to avoid duplicates
        db.delete("Book", null, null);

        // Insert test data
        // Author A: Cheap books
        insertBook(db, "Book A1", "AuthorA", 10.0, 200);
        insertBook(db, "Book A2", "AuthorA", 15.0, 300); // Avg: 12.5 (Should be filtered out by HAVING)

        // Author B: Expensive books
        insertBook(db, "Book B1", "AuthorB", 50.0, 400);
        insertBook(db, "Book B2", "AuthorB", 60.0, 500); // Avg: 55.0

        // Author C: Mixed but high avg
        insertBook(db, "Book C1", "AuthorC", 25.0, 150);
        insertBook(db, "Book C2", "AuthorC", 35.0, 250); // Avg: 30.0

        // Author D: Short book (Should be filtered out by WHERE)
        insertBook(db, "Book D1", "AuthorD", 100.0, 50); // pages < 100

        Toast.makeText(this, "Test Data Prepared!", Toast.LENGTH_SHORT).show();
        tvResult.setText("Data Inserted:\n" +
                "AuthorA: 10.0, 15.0 (Avg 12.5)\n" +
                "AuthorB: 50.0, 60.0 (Avg 55.0)\n" +
                "AuthorC: 25.0, 35.0 (Avg 30.0)\n" +
                "AuthorD: 100.0 (Pages 50, Ignored)");
    }

    private void insertBook(SQLiteDatabase db, String name, String author, double price, int pages) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("author", author);
        values.put("price", price);
        values.put("pages", pages);
        db.insert("Book", null, values);
    }

    private void executeComplexQuery() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Goal: Find authors whose average book price is > 20, considering only books with pages > 100.
        // Sort by average price descending.

        // 1. Table
        String tableName = "Book";

        // 2. Columns: Select author and calculate average price
        String[] columns = new String[] { "author", "AVG(price) as avg_price" };

        // 3. Selection (WHERE): Filter books first (pages > ?)
        String selection = "pages > ?";

        // 4. SelectionArgs: Parameters for WHERE
        String[] selectionArgs = new String[] { "100" };

        // 5. GroupBy: Group results by author
        String groupBy = "author";

        // 6. Having: Filter groups (average price > 20)
        String having = "avg_price > 20";

        // 7. OrderBy: Sort by average price descending
        String orderBy = "avg_price DESC";

        Cursor cursor = null;
        try {
            cursor = db.query(
                    tableName,      // FROM Book
                    columns,        // SELECT author, AVG(price)
                    selection,      // WHERE pages > 100
                    selectionArgs,  // 100
                    groupBy,        // GROUP BY author
                    having,         // HAVING avg_price > 20
                    orderBy         // ORDER BY avg_price DESC
            );

            StringBuilder sb = new StringBuilder();
            if (cursor.moveToFirst()) {
                do {
                    String author = cursor.getString(cursor.getColumnIndexOrThrow("author"));
                    double avgPrice = cursor.getDouble(cursor.getColumnIndexOrThrow("avg_price"));
                    sb.append("Author: ").append(author)
                      .append(" | Avg Price: ").append(String.format("%.2f", avgPrice))
                      .append("\n");
                } while (cursor.moveToNext());
            } else {
                sb.append("No results found.");
            }
            tvResult.setText(sb.toString());

        } catch (Exception e) {
            e.printStackTrace();
            tvResult.setText("Error: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
