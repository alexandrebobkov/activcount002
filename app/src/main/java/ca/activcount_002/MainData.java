package ca.activcount_002;

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
    private static float ttl_dr, ttl_cr;

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

        ttl_dr                  = 0;
        ttl_cr                  = 0;
    }

    public void setAssetsCurrent(Float num)         {   assets_current = num;       }
    public void setAssetsSupplies(Float num)        {   assets_supplies = num;      }
    public void setAssetsTotal(Float num)           {   assets_total = num;         }
    public void setNetRevenues(Float num)           {   net_revenues = num;         }
    public void setDirectCosts(Float num)           {   direct_costs = num;         }
    public void setOperating_expenses(Float num)    {   operating_expenses = num;   }

    public void setTotalDebits(Float num)           {   ttl_dr = num;   }
    public void setTotalCredits(Float num)          {   ttl_cr = num;   }

    public float getAssetsCurrent ()            {   return assets_current;      }
    public float getAssetsSupplies ()           {   return assets_supplies;     }
    public float getAssetsTotal ()              {   return assets_total;        }
    public float getTotalDebits ()              {   return ttl_dr;              }
    public float getTotalCredits ()             {   return ttl_cr;              }

}
