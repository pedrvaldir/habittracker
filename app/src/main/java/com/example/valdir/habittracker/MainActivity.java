package com.example.valdir.habittracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.valdir.habittracker.data.HabitContract;
import com.example.valdir.habittracker.data.HabitDbHelper;
import com.example.valdir.habittracker.data.HabitContract.HabitEntry;


/**
 * Created by VALDIR on 01/03/2018.
 */

public class MainActivity extends AppCompatActivity {

    HabitDbHelper mDbHelper = new HabitDbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_insert_dummy_data:
                insertHabit();
                displayDatabaseInfo();
                return true;
            case R.id.action_delete_all_entries:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Método auxiliar
     */
    private void displayDatabaseInfo() {
        Cursor cursor = readHabit();

        TextView displayView = (TextView) findViewById(R.id.text_view_habit);

        try {

            displayView.setText("A tabela Habitos contém " + cursor.getCount() + " Habitos.\n\n");
            displayView.append(HabitContract.HabitEntry._ID + " - " +
                    HabitContract.HabitEntry.COLUMN_HABIT_NAME + " - " +
                    HabitContract.HabitEntry.COLUMN_HABIT_TIME + "\n");

            // adicionar entrada
            int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_NAME);
            int timeColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_TIME);

            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentTime = cursor.getInt(timeColumnIndex);
                displayView.append(("\n" + String.valueOf(currentID) + " - " +
                        currentName + " - " +
                        String.valueOf(currentTime) ));
            }
        } finally {
            cursor.close();
        }
    }

    /**
     * Método auxiliar para inserir
     */
    private void insertHabit() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_HABIT_NAME, "Estudar");
        values.put(HabitEntry.COLUMN_HABIT_TIME, 30);

        long newRowId = db.insert(HabitEntry.TABLE_NAME, null, values);
        Log.v("MainActivity", String.valueOf(newRowId));
    }

    /**
     *Método auxiliar para ler
     */
    private Cursor readHabit() {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                HabitEntry._ID,
                HabitEntry.COLUMN_HABIT_NAME,
                HabitEntry.COLUMN_HABIT_TIME };

        Cursor cursor = db.query(
                HabitEntry.TABLE_NAME,  // tabela a consultar
                projection,            // Coluna de retorno
                null,
                null,
                null,
                null,
                null);
        return cursor;
    }
}