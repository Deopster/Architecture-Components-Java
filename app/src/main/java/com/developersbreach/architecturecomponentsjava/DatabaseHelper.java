package com.developersbreach.architecturecomponentsjava;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.developersbreach.architecturecomponentsjava.model.Attendance;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "11zon";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "attendance";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query;
        query = "CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY, Student TEXT, Subject TEXT,Date TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //add the new note
    public void addNotes(String title, String des,String Stud) {
        SQLiteDatabase sqLiteDatabase = this .getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Student", Stud);
        values.put("Subject", title);
        values.put("Date", des);

        //inserting new row
        sqLiteDatabase.insert(TABLE_NAME, null , values);
        //close database connection
        sqLiteDatabase.close();
    }

    //get the all notes
    public ArrayList<Attendance> getNotes(String ss) {
        ArrayList<Attendance> arrayList = new ArrayList<>();

        // select all query
        String select_query= "SELECT *FROM " + TABLE_NAME;

        SQLiteDatabase db = this .getWritableDatabase();
        Cursor cursor = db.rawQuery(select_query, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Attendance noteModel = new Attendance();
                noteModel.setID(cursor.getString(0));
                noteModel.setTitle(cursor.getString(2));
                noteModel.setDes(cursor.getString(3));
                System.out.println("----");
                System.out.println(ss);
                System.out.println(cursor.getString(1));
                if (cursor.getString(1).equals(ss)) {
                    System.out.println("Да");
                    arrayList.add(noteModel);
                }
            }while (cursor.moveToNext());
        }
        return arrayList;
    }

    //delete the note
    public void delete(String ID) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //deleting row
        sqLiteDatabase.delete(TABLE_NAME, "ID=" + ID, null);
        sqLiteDatabase.close();
    }

}