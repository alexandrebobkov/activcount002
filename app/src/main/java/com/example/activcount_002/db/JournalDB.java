package com.example.activcount_002.db;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class JournalDB
{
    static final String     DB_GENERAL_JOURNAL_NAME     =   "ACTIVCOUNT_GJ_19-12-29.DB";     // Database Information
    static final int        DB_VERSION                  =   1;                          // Database version

    // TABLE NAMES
    private  static final String TBL_GJ          =   "GENERAL_JOURNAL";                       // General Journal v.2
    private  static final String TBL_JE          =   "JOURNAL_ENTRY";                    // Journal entries

    // Entries Table Columns
    private static final String GJ_ID            =   "ID";
    private static final String GJ_JE_ID         =   "JE";                               // Posting reference; Folio
    private static final String GJ_DATE          =   "DATE";
    private static final String GJ_MEMO          =   "MEMO";                             // Particulars
    private static final String GJ_DR_ACCT       =   "DR_ACCOUNT";
    private static final String GJ_CR_ACCT       =   "CR_ACCOUNT";
    private static final String GJ_AMOUNT        =   "AMOUNT";

    private static final String KEY_ENTRY_ID     =   "ID";
    //private static final String KEY_ENTRY_ID_FK  =   "EntryID";
    private static final String KEY_ENTRY_DATE   =   "DATE";
    private static final String KEY_ENTRY_MEMO   =   "MEMO";

    private static final String TBL_JE_CREATE   =   "CREATE TABLE " +TBL_JE +" ("
            +KEY_ENTRY_ID +" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, "
            +KEY_ENTRY_DATE +" TEXT NOT NULL, "+ KEY_ENTRY_MEMO +" TEXT);";

    private static final String TBL_GJ_CREATE   =   "CREATE TABLE " +TBL_GJ  +" ("
            +GJ_ID      +" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, "
            +GJ_JE_ID   +" INTEGER, "
            +GJ_DATE    +" TEXT, "
            +GJ_MEMO    +" TEXT, "
            +GJ_DR_ACCT +" TEXT, "
            +GJ_CR_ACCT +" TEXT, "
            +GJ_AMOUNT  +" NUMERIC, "
            +"FOREIGN KEY (" +GJ_JE_ID + ") REFERENCES " +TBL_JE +"(" +KEY_ENTRY_ID +"));";

   // Initializing table SQLite statements
    private static final String TBL_JE_INIT_DATA = "INSERT INTO " +TBL_JE +
            "( " +KEY_ENTRY_DATE +", " +KEY_ENTRY_MEMO +") " +
            "VALUES (\"2019-12-29\", \"OPENING BALANCES\");";

    private static final String TBL_GJ_INIT_DATA = "INSERT INTO " +TBL_GJ +
            " (" +GJ_JE_ID +", " +GJ_DATE +", " +GJ_MEMO +", " +GJ_DR_ACCT +", " +GJ_CR_ACCT +", " +GJ_AMOUNT +") " +
            "VALUES (1, \"2019-12-29\", \"INITIAL VALUE\", 1010,\"\",500), (1,\"2019-12-29\", \"INITIAL VALUE\", 1020,\"\",500);";

    private static final String TBL_GJ_DELETE       = "DROP TABLE IF EXISTS " +TBL_GJ;
    private static final String TBL_JE_DELETE       = "DROP TABLE IF EXISTS " +TBL_JE;

    final Context context;
    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    public JournalDB (Context c)
    {
        this.context = c;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper (Context context)    {   super (context, DB_GENERAL_JOURNAL_NAME, null, DB_VERSION); }

        @Override
        public void onCreate (SQLiteDatabase db)
        {
            try {
                db.execSQL(TBL_JE_CREATE);
                db.execSQL(TBL_GJ_CREATE);

                db.execSQL(TBL_JE_INIT_DATA);
                db.execSQL(TBL_GJ_INIT_DATA);
            } catch (SQLException e) {}
        }
        @Override
        public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion)
        {
            db.execSQL(TBL_GJ_DELETE);
            db.execSQL(TBL_JE_DELETE);
            onCreate(db);
        }
    }

    public JournalDB open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }
    public void close()    {   DBHelper.close();   }

    public Cursor getJournal()
    {
        return db.query(TBL_GJ, new String[] {GJ_ID, GJ_JE_ID, GJ_DATE, GJ_MEMO, GJ_DR_ACCT, GJ_CR_ACCT, GJ_AMOUNT}, null, null, null, null, null);
    }
}
