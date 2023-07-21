package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "FactsAboutCats.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_fact";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_DESCRIPTION = "cat_description";
    private static final String COLUMN_FACT = "cat_fact";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Pravimo nasu tabelu
        String query = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_DESCRIPTION + " TEXT, " +
                        COLUMN_FACT + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Update-jemo nasu tabelu
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addFact(String description, String fact){
        //Pravimo objekat baze i u cv smestamo vrednosti prosledjene iz metode
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        //Pristupamo zeljenim kolonama
        cv.put(COLUMN_DESCRIPTION, description);
        cv.put(COLUMN_FACT, fact);

        //Radimo insert operaciju u nasu tabelu jer radimo metodu dodavanja u bazu
        long result = db.insert(TABLE_NAME,null, cv);

        //-1 znaci da smo pogresili da ubacimo podatke u nasu tabelu ispisujemo poruku u konzoli
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Added successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateFact(String row_id, String description, String fact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_DESCRIPTION, description);
        cv.put(COLUMN_FACT, fact);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});

        if(result == -1){
            Toast.makeText(context, "Failed to Update", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteFact(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();

        long result = db.delete(TABLE_NAME,"_id=?", new String[]{row_id});

        if(result == -1){
            Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAllFacts(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

    //Cursor je interfejs vraca set podataka read-write iz baze
    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
}
