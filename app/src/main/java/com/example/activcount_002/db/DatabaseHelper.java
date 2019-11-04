package com.example.activcount_002.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Tables names
    public static final String DATA_TABLE_NAME = "DATA";
    public static final String ITEMS_TABLE_NAME = "ITEMS";

    // Table columns
    public static final String _ID = "_id";
    public static final String DATA = "data";
    public static final String ITEM = "item";
    public static final String DESCRIPTION = "description";
    public static final String SUBJECT = "subject";
    public static final String DESC = "description";

    // Database Information
    static final String DB_NAME = "ACTIVCOUNT_DATA_005.DB";

    // database version
    static final int DB_VERSION = 2;

    // Creating table query
    private static final String CREATE_DATA_TABLE = "create table " + DATA_TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DATA + " TEXT NOT NULL, " + DESCRIPTION + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DATA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATA_TABLE_NAME);
        onCreate(db);
    }

    public Cursor getListContents()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + DATA_TABLE_NAME, null);
        return data;
    }
}
