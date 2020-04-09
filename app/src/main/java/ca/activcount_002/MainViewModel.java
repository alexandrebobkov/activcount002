/**
 *
 *  Date Created:       October 31, 2019
 *  Last time updated:  October 31, 2019
 *  Revision:           2
 *
 *  Author:             Alexandre Bobkov
 *  Company:            Alexandre Comptabilite Specialise Ltee.
 *
 *  Objective:          main class for handling all UX items (textboxes, buttons, list views, etc).
 *
 **/
package ca.activcount_002;

import android.widget.TextView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;
import ca.activcount_002.db.Entry;

public class MainViewModel extends ViewModel
{
    private static String status_msg                = "By: Alexandre Bobkov\n2020\n\nPRESS OK TO INITIALIZE DATA";         // define static variable: one for all instances.
    private static String home_status_msg           = "initial home status";   // define static variable: one for all instances.

    private static String               str_assets_current          = "20.25";
    private static String               str_assets_supplies         = "10.15";
    private static String               str_assets_total            = "35.75";
    private static String               str_liabilities_current     = "0.00";
    private static String               str_liabilities_long_term   = "0.00";
    private static String               str_liabilities_total       = "0.00";
    private static String               str_net_revenues            = "15000.00";
    private static String               str_direct_costs            = "500.00";
    private static String               str_operating_expenses      = "2500.00";
    private static String               str_dr_ttl                  = "0.00";
    private static String               str_cr_ttl                  = "0.00";
    private static ArrayList<String>    theList;
    private static ArrayList<Entry>     entriesList;

    private MutableLiveData<String>     statusText, homeStatusText;
    private MutableLiveData<String>     assetsCurrentText, assetsSuppliesText, assetsTotalText;
    private MutableLiveData<String>     net_revenues, direct_costs, operating_expenses, total_debits, total_credits;

    public MainViewModel()
    {
        statusText          = new MutableLiveData<>();
        homeStatusText      = new MutableLiveData<>();
        assetsCurrentText   = new MutableLiveData<>();
        assetsSuppliesText  = new MutableLiveData<>();
        assetsTotalText     = new MutableLiveData<>();
        net_revenues        = new MutableLiveData<>();
        direct_costs        = new MutableLiveData<>();
        operating_expenses  = new MutableLiveData<>();
        total_debits        = new MutableLiveData<>();
        total_credits        = new MutableLiveData<>();

        theList             = new ArrayList<>();
        entriesList         = new ArrayList<>();

        statusText.setValue(status_msg);
        homeStatusText.setValue(home_status_msg);
        total_debits.setValue(str_dr_ttl);
        total_credits.setValue(str_cr_ttl);

        assetsCurrentText.setValue(str_assets_current);
        assetsSuppliesText.setValue(str_assets_supplies);
        assetsTotalText.setValue(str_assets_total);

        net_revenues.setValue(str_net_revenues);
        direct_costs.setValue(str_direct_costs);
        operating_expenses.setValue(str_operating_expenses);
    }

    public void setStatus_msg(String s)                 {   status_msg = s;             }
    public void setHomeStatus_msg(String s)             {   home_status_msg = s;        }
    public void setDebitsTotal(String s)                {   str_dr_ttl = s;             }
    public void setCreditsTotal(String s)               {   str_cr_ttl = s;             }

    public void setAssetsCurrent(TextView tv)           {   str_assets_current = ""+tv.getText();       }
    public void setAssetsSupplies(TextView tv)          {   str_assets_supplies = ""+tv.getText();      }
    public void setAssetsTotal(TextView tv)             {   str_assets_total = ""+tv.getText();         }
    public void setNetRevenues(TextView tv)             {   str_net_revenues = ""+tv.getText();         }
    public void setDirectCosts(TextView tv)             {   str_direct_costs = ""+tv.getText();         }

    public void loadEntries(ArrayList<String> list)     {   theList = list;                             }
    public void syncEntries(ArrayList<Entry> entries)   {   entriesList = entries;                      }

    public String getAssetsCurrent()                    {   return str_assets_current;  }
    public String getAssetsSupplies()                   {   return str_assets_supplies; }
    public String getAssetsTotal()                      {   return str_assets_total;    }
    public String getDebitsTotal()                      {   return str_dr_ttl;          }
    public String getCreditsTotal()                     {   return str_cr_ttl;          }

    public String get_home_status_msg()                 {   return home_status_msg;     }

    public LiveData<String> getStatus_msg()             {   return statusText;          }
    public LiveData<String> getAssetsCurrentText()      {   return assetsCurrentText;   }
    public LiveData<String> getAssetsSuppliesText()     {   return assetsSuppliesText;  }
    public LiveData<String> getAssetsTotalText()        {   return assetsTotalText;     }
    public LiveData<String> getNetRevenuesText()        {   return net_revenues;        }
    public LiveData<String> getDirectCostsText()        {   return direct_costs;        }
    public LiveData<String> getOperatingExpensesText()  {   return operating_expenses;  }
    public LiveData<String> getTotalDebits()            {   return total_debits;        }
    public LiveData<String> getTotalCredits()           {   return total_credits;        }

    public ArrayList<String> getEntriesList()           {   return theList;             }
    public ArrayList<Entry> getJournalEntriesList()     {   return entriesList;         }
}
