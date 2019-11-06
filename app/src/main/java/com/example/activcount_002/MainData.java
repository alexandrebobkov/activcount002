package com.example.activcount_002;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public final class MainData
{
    private static float assets_current, assets_supplies, assets_total;
    private static float liabilities_current, liabilities_long_term, liabilities_total;
    private static float net_revenues, direct_costs, operating_expenses;

    /*
    // SQLite variables
    private static final int    DATABASE_VERSION    = 1;
    private static final String TABLE_NAME          = "DATA";
    private static final String SUBJECT             = "Subject";
    private static final String DESCRIPTION         = "Description";
    private static final String DATABASE_NAME       = "activcount_database.db";    // Database name
    private static final String TABLE_Data          = "Data";            	       // Table name
    private static final String KEY_ID              = "id";                 	    // Column name
    private static final String KEY_NAME            = "value";

    private static final String CREATE_TABLE        = "create table " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SUBJECT + " TEXT NOT NULL, " + DESCRIPTION + " TEXT);";
    */

    public MainData()
    {
        assets_current          = 0;
        assets_supplies         = 0;
        assets_total            = 0;

        liabilities_current     = 0;
        liabilities_long_term   = 0;
        liabilities_total       = 0;

        net_revenues            = 0;
        direct_costs            = 0;
        operating_expenses      = 0;
    }

    public void setAssetsCurrent(Float num)         {   assets_current = num;       }
    public void setAssetsSupplies(Float num)        {   assets_supplies = num;      }
    public void setAssetsTotal(Float num)           {   assets_total = num;         }
    public void setNetRevenues(Float num)           {   net_revenues = num;         }
    public void setDirectCosts(Float num)           {   direct_costs = num;         }
    public void setOperating_expenses(Float num)    {   operating_expenses = num;   }

    public float getAssetsCurrent ()            {   return assets_current;   }
    public float getAssetsSupplies ()           {   return assets_supplies;  }
    public float getAssetsTotal ()              {   return assets_total;     }

}
