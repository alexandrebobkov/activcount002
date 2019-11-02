package com.example.activcount_002;

import android.widget.TextView;

public class MainData
{
    private static float assets_current, assets_supplies, assets_total;
    private static float liabilities_current, liabilities_long_term, liabilities_total;
    private static float net_revenues, direct_costs, operating_expenses;

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
