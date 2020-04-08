package com.example.activcount_002.db;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

import java.util.ArrayList;

public class JournalDB
{
    static final String     DB_GENERAL_JOURNAL_NAME     =   "ACTIVCOUNT_GJ_19-12-29.DB";     // Database Information
    static final int        DB_VERSION                  =   1;                          // Database version

    // TABLE NAMES
    private static final String TBL_GJ          =   "GENERAL_JOURNAL";                  // General Journal v.2
    private static final String TBL_JE          =   "JOURNAL_ENTRY";                    // Journal entries
    private static final String TBL_CAO         =   "CHART_OF_ACCOUNTS";                // Chart of Accounts

    // Entries Table Columns
    // Chart of Accounts
    private static final String CAO_ID          =   "ID";
    private static final String CAO_ACCT_NUM    =   "ACCT_NUM";
    private static final String CAO_ACCT_NAME   =   "ACCT_NAME";
    // General Journal
    private static final String GJ_ID           =   "ID";
    private static final String GJ_JE_ID        =   "JE";                               // Posting reference; Folio
    private static final String GJ_DATE         =   "DATE";
    private static final String GJ_MEMO         =   "MEMO";                             // Particulars
    private static final String GJ_DR_ACCT      =   "DR_ACCOUNT";
    private static final String GJ_CR_ACCT      =   "CR_ACCOUNT";
    private static final String GJ_AMOUNT       =   "AMOUNT";
    // Journal Entries
    private static final String KEY_ENTRY_ID     =   "ID";
    private static final String KEY_ENTRY_DATE   =   "DATE";
    private static final String KEY_ENTRY_MEMO   =   "MEMO";

    private static final String TBL_CAO_CREATE  =   "CREATE TABLE " +TBL_CAO +" ("
            +CAO_ID +" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, "
            +CAO_ACCT_NUM +" TEXT NOT NULL UNIQUE, "
            +CAO_ACCT_NAME +" TEXT );";

    private static final String TBL_JE_CREATE   =   "CREATE TABLE " +TBL_JE +" ("
            +KEY_ENTRY_ID +" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, "
            +KEY_ENTRY_DATE +" TEXT NOT NULL, " +KEY_ENTRY_MEMO +" TEXT);";

    private static final String TBL_GJ_CREATE   =   "CREATE TABLE " +TBL_GJ  +" ("
            +GJ_ID      +" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, "
            +GJ_JE_ID   +" INTEGER, "
            +GJ_DATE    +" TEXT, "
            +GJ_MEMO    +" TEXT, "
            +GJ_DR_ACCT +" TEXT, "
            +GJ_CR_ACCT +" TEXT, "
            +GJ_AMOUNT  +" NUMERIC, "
            +"FOREIGN KEY (" +GJ_JE_ID      +") REFERENCES " +TBL_JE +"(" +KEY_ENTRY_ID +"), "
            +"FOREIGN KEY (" +GJ_DR_ACCT    +") REFERENCES " +TBL_CAO +"(" +CAO_ACCT_NUM +"), "
            +"FOREIGN KEY (" +GJ_CR_ACCT    +") REFERENCES " +TBL_CAO +"(" +CAO_ACCT_NUM +"));";

   // Initializing table SQLite statements
   private static final String TBL_CAO_INIT_DATA = "INSERT INTO " +TBL_CAO +
           " (" +CAO_ACCT_NUM +", " +CAO_ACCT_NAME +") " +
           "VALUES (\"\",\"\"), (\"1000\", \"ASSETS\"), (\"1010\",\"Cash\"), (\"3000\", \"LIABILITIES\"), (\"5000\", \"EQUITY\"), (\"5100\", \"Common Shares\"), (\"6000\", \"REVENUES\"), (\"7000\", \"EXPENSES\");";

    private static final String TBL_JE_INIT_DATA = "INSERT INTO " +TBL_JE +
            "( " +KEY_ENTRY_DATE +", " +KEY_ENTRY_MEMO +") " +
            "VALUES (\"2019-12-29\", \"OPENING BALANCES\"), (\"2019-12-29\", \"OPENING BALANCES\");";

    private static final String TBL_GJ_INIT_DATA = "INSERT INTO " +TBL_GJ +
            " (" +GJ_JE_ID +", " +GJ_DATE +", " +GJ_MEMO +", " +GJ_DR_ACCT +", " +GJ_CR_ACCT +", " +GJ_AMOUNT +") " +
            "VALUES (1, \"2019-12-29\", \"INITIAL VALUE\", \"1010\", \"\",500), (1, \"2019-12-29\", \"INITIAL VALUE\", \"1010\", \"\",700), (1,\"2019-12-29\", \"INITIAL VALUE\", \"\", \"5100\",-1200);";

    // SQLite Queries
    private static final String QRY_GET_DR_TTL = "SELECT * FROM " +TBL_GJ;// +" WHERE " +GJ_DR_ACCT +" IS 1010";//NOT \"\"";

    // Upgrade SQLite statements
    private static final String TBL_GJ_DELETE       = "DROP TABLE IF EXISTS " +TBL_GJ;
    private static final String TBL_JE_DELETE       = "DROP TABLE IF EXISTS " +TBL_JE;
    private static final String TBL_CAO_DELETE      = "DROP TABLE IF EXISTS " +TBL_CAO;

    final           Context     context;
    DatabaseHelper              DBHelper;
    SQLiteDatabase              db;

    public JournalDB (Context c)
    {
        this.context = c;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper (Context context)    {   super (context, DB_GENERAL_JOURNAL_NAME, null, DB_VERSION); }

        @Override
        public void onConfigure(SQLiteDatabase db)
        {
            super.onConfigure(db);
            db.setForeignKeyConstraintsEnabled(true);
        }

        @Override
        public void onCreate (SQLiteDatabase db)
        {
            try {
                // Create and initialize Chart of Accounts table
                db.execSQL(TBL_CAO_CREATE);
                db.execSQL(TBL_CAO_INIT_DATA);
                // Create and initialize Journal Entries table
                db.execSQL(TBL_JE_CREATE);
                db.execSQL(TBL_JE_INIT_DATA);
                // Create and initialize General Journal table
                db.execSQL(TBL_GJ_CREATE);
                db.execSQL(TBL_GJ_INIT_DATA);
            } catch (SQLException e) {  e.printStackTrace();    }
        }

        @Override
        public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion)
        {
            db.execSQL(TBL_GJ_DELETE);
            db.execSQL(TBL_JE_DELETE);
            db.execSQL(TBL_CAO_DELETE);
            onCreate(db);
        }
    }

    public JournalDB open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close() {   DBHelper.close();   }

    public Cursor getJournal()
    {
        return db.query(TBL_GJ, new String[] {GJ_ID, GJ_JE_ID, GJ_DATE, GJ_MEMO, GJ_DR_ACCT, GJ_CR_ACCT, GJ_AMOUNT}, null, null, null, null, null);
    }

    // Return array of Entries
    public void readGJ (ArrayList<Entry> entry) throws SQLException
    {
        Cursor c;
        try {
            c = db.query(TBL_GJ, new String[] {GJ_ID, GJ_JE_ID, GJ_DATE, GJ_MEMO, GJ_DR_ACCT, GJ_CR_ACCT, GJ_AMOUNT}, null, null, null, null, GJ_ID);

            if (c != null) {
                c.moveToFirst();
                // Read table rows.
                do {
                    if (c.getCount()>0)
                    {
                        Entry e = new Entry();
                        e.id        = c.getLong     (c.getColumnIndex(GJ_ID));
                        e.je        = c.getLong     (c.getColumnIndex(GJ_JE_ID));
                        e.date      = c.getString   (c.getColumnIndex(GJ_DATE));
                        e.memo      = c.getString   (c.getColumnIndex(GJ_MEMO));
                        e.dr_acct   = c.getString   (c.getColumnIndex(GJ_DR_ACCT));
                        e.cr_acct   = c.getString   (c.getColumnIndex(GJ_CR_ACCT));
                        e.amount    = c.getFloat    (c.getColumnIndex(GJ_AMOUNT));
                        entry.add(e);
                    }
                    else
                        break;
                    // Move to the next row.
                } while (c.moveToNext());
            }
            c.close();
        } catch (SQLException exception) {}
        finally {}
    }

    public float getTotalDebits () throws SQLException
    {
        Cursor c;

        float ttl_dr = 0;

        try {
            // SELECT * FROM TBL_GJ WHERE GJ_CR_ACCT = ""
            c = db.query(TBL_GJ, new String[] {GJ_ID, GJ_JE_ID, GJ_DATE, GJ_MEMO, GJ_DR_ACCT, GJ_CR_ACCT, GJ_AMOUNT}, GJ_CR_ACCT+"=?", new String[] {""}, null, null, GJ_ID);
            if (c != null) {
                c.moveToFirst();
                // Read table rows.
                do {
                    if (c.getCount()>0)
                        ttl_dr += c.getFloat(c.getColumnIndex(GJ_AMOUNT));
                    else
                        break;
                    // Move to the next row.
                } while (c.moveToNext());
            }
            c.close();
        } catch (SQLException exception) {}
        finally {}

        return ttl_dr;
    }
}
