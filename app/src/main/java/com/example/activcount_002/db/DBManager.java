package com.example.activcount_002.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.example.activcount_002.db.DatabaseHelper;

public class DBManager {

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String name, String desc) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.DATA, name);
        contentValue.put(DatabaseHelper.DESCRIPTION, desc);
        database.insert(DatabaseHelper.DATA_TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() throws SQLException {
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.DATA, DatabaseHelper.DESCRIPTION };
        Cursor cursor = database.query(DatabaseHelper.DATA_TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long _id, String name, String desc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.DATA, name);
        contentValues.put(DatabaseHelper.DESCRIPTION, desc);
        int i = database.update(DatabaseHelper.DATA_TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(DatabaseHelper.DATA_TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
    }

    public boolean doesTableExist(String tableName) {
        Cursor cursor = database.rawQuery("select DISTINCT 'TABLE_DATA' from ACTIVCOUNT_DATA_002.DB where tbl_name = '" + tableName + "'", null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

}
