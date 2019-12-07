package com.example.activcount_002.db;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.SQLException;

//import com.example.activcount_002.db.Entry;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Tables names
    public static final String DATA_TABLE_NAME = "DATA";
    public static final String ITEMS_TABLE_NAME = "ITEMS";
    public static final String TABLE_ENTRIES = "ENTRIES";

    // Table columns
    public static final String _ID = "_id";
    public static final String DATA = "data";
    public static final String ITEM = "item";
    public static final String DESCRIPTION = "description";
    public static final String SUBJECT = "subject";

    // Entries Table Columns
    private static final String KEY_ENTRY_ID    = "id";
    private static final String KEY_ENTRY_ID_FK = "entryId";
    private static final String KEY_ENTRY_DATE  = "date";
    private static final String KEY_ENTRY_MEMO  = "memo";

    // Database Information
    static final String DB_NAME = "ACTIVCOUNT_DATA_008.DB";

    // database version
    static final int DB_VERSION = 2;

    // Creating table query
    private static final String CREATE_DATA_TABLE = "create table " + DATA_TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DATA + " TEXT NOT NULL, " + DESCRIPTION + " TEXT);";

    private static final String DELETE_DATA_TABLE = "DROP TABLE IF EXISTS " + DATA_TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db)
    {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DATA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_DATA_TABLE);
        onCreate(db);
    }

    public Cursor getListContents()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + DATA_TABLE_NAME, null);
        return data;
    }

    public void addEntry(Entry entry)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        try
        {
            long id = addOrUpdateEntry(entry);
            ContentValues values = new ContentValues();
            values.put(KEY_ENTRY_ID_FK, id);
            values.put(KEY_ENTRY_MEMO, entry.memo);

            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            db.insertOrThrow(TABLE_ENTRIES, null, values);
            db.setTransactionSuccessful();

        } catch (Exception e) {}
        finally
        {
            db.endTransaction();
        }
    }
    public long addOrUpdateEntry (Entry entry)
    {
        SQLiteDatabase db = getWritableDatabase();
        long id = -1;

        db.beginTransaction();

        try
        {
            ContentValues values = new ContentValues();

            values.put(KEY_ENTRY_DATE, entry.date);
            values.put(KEY_ENTRY_MEMO, entry.memo);

            int rows = db.update(TABLE_ENTRIES, values, KEY_ENTRY_ID + "= ?", new String[]{String.valueOf(entry.id)});

            if (rows == 1)
            {
                // Get the primary key of the user we just updated
                String usersSelectQuery = String.format("SELECT %s FROM %s WHERE %s = ?", KEY_ENTRY_ID, TABLE_ENTRIES, KEY_ENTRY_MEMO);
                Cursor cursor = db.rawQuery(usersSelectQuery, new String[]{String.valueOf(entry.memo)});
                try
                {
                    if (cursor.moveToFirst())
                    {
                        id = cursor.getInt(0);
                        db.setTransactionSuccessful();
                    }
                }
                finally
                {
                    if (cursor != null && !cursor.isClosed())
                    {
                        cursor.close();
                    }
                }
            }
            else
            {
                // user with this userName did not already exist, so insert new user
                id = db.insertOrThrow(TABLE_ENTRIES, null, values);
                db.setTransactionSuccessful();
            }
        }
        catch (SQLException e) {}
        finally
        {
            db.endTransaction();
        }

        return id;
    }

    // Get all entries in the database
    public List<Entry> getAllEntries()
    {
        List<Entry> posts = new ArrayList<>();

        // SELECT * FROM POSTS
        // LEFT OUTER JOIN USERS
        // ON POSTS.KEY_POST_USER_ID_FK = USERS.KEY_USER_ID
        String POSTS_SELECT_QUERY = String.format("SELECT * FROM %s",
                        TABLE_ENTRIES);

        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        try
        {
            if (cursor.moveToFirst())
            {
                do
                {
                    Entry newEntry = new Entry();
                    newEntry.id = cursor.getLong(cursor.getColumnIndex(KEY_ENTRY_ID));
                    newEntry.date = cursor.getString(cursor.getColumnIndex(KEY_ENTRY_DATE));
                    newEntry.memo = cursor.getString(cursor.getColumnIndex(KEY_ENTRY_MEMO));
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
        } finally {
            if (cursor != null && !cursor.isClosed())
            {
                cursor.close();
            }
        }
        return posts;
    }

    // Update the entry memo
    public int updateEntry(Entry entry) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ENTRY_MEMO, entry.memo);

        // Updating profile picture url for user with that userName
        return db.update(TABLE_ENTRIES, values, KEY_ENTRY_MEMO + " = ?",
                new String[] { String.valueOf(entry.memo) });
    }

    // Delete all posts and users in the database
    public void deleteAllEntries() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            // Order of deletions is important when foreign key relationships exist.
            db.delete(TABLE_ENTRIES, null, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
        } finally {
            db.endTransaction();
        }
    }
}
