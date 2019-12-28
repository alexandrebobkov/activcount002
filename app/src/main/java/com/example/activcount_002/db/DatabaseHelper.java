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

public class DatabaseHelper extends SQLiteOpenHelper
{
    static final String     DB_NAME                     =   "ACTIVCOUNT_DATA_023.DB";       // Database Information
    static final String     DB_GENERAL_JOURNAL_NAME     =   "ACTIVCOUNT_GJ_001.DB";       // Database Information
    static final int        DB_VERSION                  =   1;                              // Database version

    // Tables names
    public  static final String DATA_TABLE_NAME     = "DATA";
    public  static final String ITEMS_TABLE_NAME    = "ITEMS";
    private static final String TABLE_ENTRIES       = "ENTRIES";

    // ACCOUNTING
    public  static final String TBL_GJ          =   "GENERAL_JOURNAL_0";            // General Journal
    public  static final String TBL_GenJrnl     =   "GENERAL_JOURNAL";              // General Journal v.2
    public  static final String TBL_JE          =   "JOURNAL_ENTRY";                // Journal entries
    // Standardized table columns
    public  static final String _ID             =   "_id";                          // Table key
    private static final String ENTRY_ID        =   "JE_ID";                        // Journal entry number
    // General Journal table columns
    private static final String JRNL_NAME       =   "JOURNAL NAME";                 // Journal descriptive name
    // Journal Entry table columns
    private static final String DATE            =   "DATE";                         // Journal entry date
    private static final String MEMO            =   "MEMO";                         // Journal entry description

    // Table columns
    public static final String DATA             = "data";
    public static final String ITEM             = "item";
    public static final String DESCRIPTION      = "description";
    public static final String SUBJECT          = "subject";

    // Entries Table Columns
    public static final String GJ_ID            =   "ID";
    public static final String GJ_JE_ID         =   "JE";           // Posting reference; Folio
    public static final String GJ_DATE          =   "DATE";
    public static final String GJ_MEMO          =   "MEMO";         // Particulars
    public static final String GJ_DR_ACCT       =   "DR_ACCOUNT";
    public static final String GJ_CR_ACCT       =   "CR_ACCOUNT";
    public static final String GJ_AMOUNT        =   "AMOUNT";

    public static final String KEY_ENTRY_ID     =   "id";
    public static final String KEY_ENTRY_ID_FK  =   "entryId";
    public static final String KEY_ENTRY_DATE   =   "date";
    public static final String KEY_ENTRY_MEMO   =   "memo";


    // QUERIES: CREATING TABLES
    // Create General Journal table query
    //private static final String CREATE_GJ_TBL = "CREATE TABLE " +TBL_GJ + "(" +_ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " +JRNL_NAME + " TEXT NOT NULL, " + ENTRY_ID + " TEXT);";

    // Create 2 tables with 1-to-many relationship.
    private static final String CREATE_JOURNAL_ENTRIES_TABLE =
            "CREATE TABLE " +TBL_JE +" ( "
                    +KEY_ENTRY_ID +" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, "
                    +KEY_ENTRY_DATE +" TEXT NOT NULL, "+ KEY_ENTRY_MEMO +" TEXT ); ";

    private static final String CREATE_GENERAL_JOURNAL_TABLE =
            "CREATE TABLE " +TBL_GJ +" ( "
                    +KEY_ENTRY_ID   +" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                    +KEY_ENTRY_MEMO +" TEXT , " +KEY_ENTRY_ID_FK
                    +" INTEGER, FOREIGN KEY(" +KEY_ENTRY_ID_FK + ") REFERENCES "
                    +TBL_GJ +"(" +KEY_ENTRY_ID +"));";

    private static final String CREATE_GenJrnl_TABLE =
            "CREATE TABLE " +TBL_GenJrnl  +" ( "
                    +GJ_ID      +" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, "
                    +GJ_JE_ID   +" INTEGER, "
                    +GJ_DATE    +" TEXT, "
                    +GJ_MEMO    +" TEXT, "
                    +GJ_DR_ACCT +" TEXT, "
                    +GJ_CR_ACCT +" TEXT, "
                    +GJ_AMOUNT  +" NUMERIC, "
                    +"FOREIGN KEY(" +GJ_JE_ID + ") REFERENCES " +TBL_JE +"(" +KEY_ENTRY_ID +"));";
    private static final String GenJrnl_TABLE_CREATE = CREATE_GenJrnl_TABLE;
    private static final String GenJrnl_TABLE_INIT_DATA = "";

    // Creating Data table query
    public static final String CREATE_DATA_TABLE =
            "CREATE TABLE " + DATA_TABLE_NAME + "("
                    + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + DATA + " TEXT NOT NULL, "
                    + DESCRIPTION + " TEXT);";

    public static final String DELETE_DATA_TABLE    = "DROP TABLE IF EXISTS " +DATA_TABLE_NAME;
    private static final String DELETE_TBL_GenJrnl  = "DROP TABLE IF EXISTS " +TBL_GenJrnl;
    private static final String DELETE_TBL_GJ       = "DROP TABLE IF EXISTS " +TBL_GJ;
    private static final String DELETE_TBL_JE       = "DROP TABLE IF EXISTS " +TBL_JE;

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
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_DATA_TABLE);
        //db.execSQL(CREATE_GJ_TBL);

        // Create 2 tables with 1-to-many relationship (one post - many entries).
        db.execSQL(CREATE_JOURNAL_ENTRIES_TABLE);   // Create Journal Entries table
        //db.execSQL(CREATE_GENERAL_JOURNAL_TABLE);   // Create General Journal table
        db.execSQL(CREATE_GenJrnl_TABLE);           // Create General Journal table v.2
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_DATA_TABLE);
        db.execSQL(DELETE_TBL_GenJrnl);
        db.execSQL(DELETE_TBL_GJ);
        db.execSQL(DELETE_TBL_JE);
        onCreate(db);
    }
}
