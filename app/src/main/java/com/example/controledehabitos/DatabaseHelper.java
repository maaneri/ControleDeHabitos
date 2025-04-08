package com.example.controledehabitos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "habitos.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE habitos (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, descricao TEXT, feitoHoje INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS habitos");
        onCreate(db);
    }

    public void insertHabit(Habits habit) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome", habit.getNome());
        values.put("descricao", habit.getDescricao());
        values.put("feitoHoje", habit.isFeitoHoje() ? 1 : 0);
        db.insert("habitos", null, values);
        db.close();
    }

    public void updateHabit(Habits habit) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome", habit.getNome());
        values.put("descricao", habit.getDescricao());
        values.put("feitoHoje", habit.isFeitoHoje() ? 1 : 0);
        db.update("habitos", values, "id = ?", new String[]{String.valueOf(habit.getId())});
        db.close();
    }

    public Habits getHabitById(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("habitos", null, "id = ?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Habits habit = new Habits(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3) == 1
            );
            cursor.close();
            db.close();
            return habit;
        }
        return null;
    }

    public ArrayList<Habits> getAllHabits() {
        ArrayList<Habits> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM habitos", null);
        if (cursor.moveToFirst()) {
            do {
                list.add(new Habits(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3) == 1
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public void deleteHabit(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("habitos", "id=?", new String[]{String.valueOf(id)});
        db.close();
    }
}
