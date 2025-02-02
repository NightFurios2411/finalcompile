package com.example.pratical_testing_1.dataBind.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.pratical_testing_1.dataBind.Model.AnimalLog;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "animal_logs.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable =
                "CREATE TABLE animals (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " name TEXT, species TEXT," +
                        " description TEXT," +
                        " location TEXT," +
                        " audioPath TEXT," +
                        " imagePath TEXT," +
                        " userID TEXT" +
                        ")";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS animals");
        onCreate(db);
    }

    public boolean insertAnimal(String name, String species, String description, String location, String audioPath, String imagePath, String userID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("species", species);
        values.put("description", description);
        values.put("location", location);
        values.put("audioPath", audioPath);
        values.put("imagePath", imagePath);
        values.put("userID", userID);

        long result = db.insert("animals", null, values); // Returns row ID or -1 if failed
        db.close();

        return result != -1; // Returns true if insertion was successful
    }

    public List<AnimalLog> getAllAnimalLogs() {
        List<AnimalLog> animalLogs = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM animals", null);

        if (cursor.moveToFirst()) {
            do {
                AnimalLog animalLog = new AnimalLog(
                        cursor.getInt(cursor.getColumnIndexOrThrow("userID")), // ID
                        cursor.getString(cursor.getColumnIndexOrThrow("name")), // Name
                        cursor.getString(cursor.getColumnIndexOrThrow("species")), // Species
                        cursor.getString(cursor.getColumnIndexOrThrow("description")), // Description
                        cursor.getString(cursor.getColumnIndexOrThrow("location")), // Location
                        cursor.getString(cursor.getColumnIndexOrThrow("audioPath")), // Audio Path
                        cursor.getString(cursor.getColumnIndexOrThrow("imagePath")) // Image Path
                );
                animalLogs.add(animalLog);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return animalLogs;
    }
    public List<AnimalLog> getAllAnimalLogsByUserId(int userId) {
        List<AnimalLog> animalLogs = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM animals WHERE userID = ?", new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do {
                AnimalLog animalLog = new AnimalLog(
                        cursor.getInt(cursor.getColumnIndexOrThrow("userID")), // ID
                        cursor.getString(cursor.getColumnIndexOrThrow("name")), // Name
                        cursor.getString(cursor.getColumnIndexOrThrow("species")), // Species
                        cursor.getString(cursor.getColumnIndexOrThrow("description")), // Description
                        cursor.getString(cursor.getColumnIndexOrThrow("location")), // Location
                        cursor.getString(cursor.getColumnIndexOrThrow("audioPath")), // Audio Path
                        cursor.getString(cursor.getColumnIndexOrThrow("imagePath")) // Image Path
                );
                animalLogs.add(animalLog);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return animalLogs;
    }


    public boolean updateAnimal(AnimalLog animal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name", animal.getName());
        values.put("species", animal.getSpecies());
        values.put("description", animal.getDescription());
        values.put("location", animal.getLocation());
        values.put("imagePath", animal.getImagePath());

        int result = db.update("animals", values, "userID=?", new String[]{String.valueOf(animal.getId())});
        db.close();
        return result > 0; // Returns true if update was successful
    }


}