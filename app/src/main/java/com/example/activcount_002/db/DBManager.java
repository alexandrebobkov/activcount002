package com.example.activcount_002.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import com.example.activcount_002.db.DatabaseHelper;
import com.example.activcount_002.db.Entry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DBManager {

    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;
    private static Entry e;

    private List<Entry> entries;

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

    private void insertEntry()
    {
        ContentValues cv = new ContentValues();
        //cv.put(DatabaseHelper.KEY_ENTRY_ID, e.id);
        cv.put(DatabaseHelper.KEY_ENTRY_DATE, ""+e.date);
        cv.put(DatabaseHelper.KEY_ENTRY_MEMO, ""+e.memo);
        database.insert(DatabaseHelper.TBL_JE, null, cv);
    }
    private void insertTransaction()
    {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.KEY_ENTRY_ID, "1");
        cv.put(DatabaseHelper.KEY_ENTRY_MEMO, "Transaction");
        cv.put(DatabaseHelper.KEY_ENTRY_ID_FK, "1");
        database.insert(DatabaseHelper.TBL_GJ, null, cv);
        cv.put(DatabaseHelper.KEY_ENTRY_ID, "2");
        cv.put(DatabaseHelper.KEY_ENTRY_MEMO, "Transaction");
        cv.put(DatabaseHelper.KEY_ENTRY_ID_FK, "1");
        database.insert(DatabaseHelper.TBL_GJ, null, cv);
    }

    public Cursor fetch() throws SQLException {
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.DATA, DatabaseHelper.DESCRIPTION };
        Cursor cursor = database.query(DatabaseHelper.DATA_TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public List<Entry> getListOfEntries() throws SQLException
    {
        entries = new ArrayList<>();
        entries = dbHelper.getAllEntries();

        return entries;
    }

    public int update(long _id, String name, String desc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.DATA, name);
        contentValues.put(DatabaseHelper.DESCRIPTION, desc);
        int i = database.update(DatabaseHelper.DATA_TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }

    public void deleteRow(long _id) {
        database.delete(DatabaseHelper.DATA_TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
    }

    public boolean doesTableExist(String tableName) {
        // String POSTS_SELECT_QUERY = String.format("SELECT * FROM %s LEFT OUTER JOIN %s ON %s.%s = %s.%s", TABLE_POSTS, TABLE_USERS, TABLE_POSTS, KEY_POST_USER_ID_FK, TABLE_USERS, KEY_USER_ID);

        //Cursor cursor = database.rawQuery("select DISTINCT 'TABLE_DATA' from ACTIVCOUNT_DATA_002.DB where tbl_name = '" + tableName + "'", null);
        Cursor cursor = database.rawQuery("select DISTINCT 'TABLE_DATA' from " +DatabaseHelper.DB_NAME +" where tbl_name = '" + tableName + "'", null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    public void prepareDataTable()
    {
        ContentValues cont_val = new ContentValues();

        //cont_val.put(DatabaseHelper.DATA, "created");
        //cont_val.put(DatabaseHelper.DESCRIPTION, "" + Calendar.getInstance().getTime());
        //database.insert(DatabaseHelper.DATA_TABLE_NAME, null, cont_val);
        insert("Incorporated on:", "" + Calendar.getInstance().getTime());

        //cont_val.put(DatabaseHelper.DATA, "business name");
        //cont_val.put(DatabaseHelper.DESCRIPTION, "activcount.ca");
        //database.insert(DatabaseHelper.DATA_TABLE_NAME, null, cont_val);
        insert("Operating Name", "activcount.ca");

        //cont_val.put(DatabaseHelper.DATA, "business number");
        //cont_val.put(DatabaseHelper.DESCRIPTION,  "145-5897-25");
        //database.insert(DatabaseHelper.DATA_TABLE_NAME, null, cont_val);
        insert("Business Number", "145-5897-25");

        //cont_val.put(DatabaseHelper.DATA, "anniversary date");
        //cont_val.put(DatabaseHelper.DESCRIPTION, "" + Calendar.getInstance().getTime());
        //database.insert(DatabaseHelper.DATA_TABLE_NAME, null, cont_val);
        insert("Anniversary Date", "" + Calendar.getInstance().getTime());
    }

    public void prepareEntriesTable()
    {
        e = new Entry();
        e.date = "" +Calendar.getInstance().getTime();
        e.memo = "second entry";
        //addEntry(e);
        insertEntry();
        insertTransaction();
    }
    public void addEntry(Entry entry)
    {
        dbHelper.addEntry(entry);
    }
}
