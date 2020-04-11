/**
 *
 *  Date Created:       October 15, 2019
 *  Last time updated:  November 16, 2019
 *  Revision:           2
 *
 *  Author: Alexandre Bobkov
 *  Company: Alexandre Comptabilite Specialise Ltee.
 *
 *  Program purpose: To display key accounting information and financial overview.
 *
 **/

package ca.activcount_002.ui.journal;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Button;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.ListAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import androidx.lifecycle.Observer;

import ca.activcount_002.MainData;
import ca.activcount_002.R;
import ca.activcount_002.MainViewModel;
import ca.activcount_002.db.DBManager;
import org.w3c.dom.Text;

import ca.activcount_002.db.DatabaseHelper;
import ca.activcount_002.db.Entry;
import ca.activcount_002.db.JournalDB;

public class JournalFragment extends Fragment
{
    MainViewModel               mainViewModel;
    private MainData            mainData;
    private ListAdapter         listAdapter;
    //private ListView            journalView;
    private JournalDB           dbJournal;
    private DatabaseHelper      dbHelper;
    private SQLiteDatabase      database, acctdb;
    private ArrayList<Entry>    entriesList;
    private ArrayList<String>   theList2;
    private Cursor              c, journal;
    private String              SELECT_ENTRIES_QUERY;

    private float                dr_ttl, cr_ttl;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        mainData = new MainData();

        SELECT_ENTRIES_QUERY = String.format("SELECT * FROM %s", DatabaseHelper.TBL_GenJrnl);

        mainViewModel   = ViewModelProviders.of(this).get(MainViewModel.class);
        entriesList     = new ArrayList<>();
        //dbManager       = new DBManager(getContext());
        View root       = inflater.inflate(R.layout.fragment_journal, container, false);
        final TextView  debits_total     = root.findViewById(R.id.dr_ttl);
        final TextView  credits_total    = root.findViewById(R.id.cr_ttl);
        final TextView  msg              = root.findViewById(R.id.db_journal_msg);
        final ListView  journalView      = (ListView) root.findViewById(R.id.db_journal_view);
        final Button    btn_jrnl_post   = root.findViewById(R.id.btn_jrnl_post);

        /** DATABASE OPERATIONS **/
        dbJournal = new JournalDB(getContext());
        dbJournal.open();
        journal = dbJournal.getJournal();
        dbJournal.readGJ(entriesList);
        //dr_ttl = dbJournal.getTotalDebits();
        //dbJournal.close();

        dr_ttl = totalDebits(entriesList);  // calculate total Debits
        dr_ttl = dbJournal.getTotalDebits();
        cr_ttl = totalCredits(entriesList); // calculate total Credits
        dbJournal.close();                  // close database

        theList2 = new ArrayList<>();       // list populated from array of Entries
        theList2 = fetchEntriesArray(entriesList);

        mainViewModel.loadEntries(theList2);     // pass ArrayList<String> to ViewModel.
        listAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mainViewModel.getEntriesList());
        journalView.setAdapter(listAdapter);

        mainData.setTotalDebits(dr_ttl);
        mainData.setTotalDebits(cr_ttl);
        //mainData.setJournalStatusMessage("");
        //mainViewModel.setDebitsTotal(String.valueOf(mainData.getTotalDebits()));
        //mainViewModel.setCreditsTotal(String.valueOf(mainData.getTotalCredits()));
        mainViewModel.setDebitsTotal(String.valueOf(dr_ttl));
        mainViewModel.setCreditsTotal(String.valueOf(cr_ttl));
        //mainViewModel.setJournalStatusMessage("");
        mainViewModel.getTotalDebits().observe(this, new Observer<String>() { @Override public void onChanged(@Nullable String s) { debits_total.setText(s); }});
        mainViewModel.getTotalCredits().observe(this, new Observer<String>() { @Override public void onChanged(@Nullable String s) { credits_total.setText(s); }});
        mainViewModel.getJournalStatusMsg().observe(this, new Observer<String>() { @Override public void onChanged(@Nullable String s) { msg.setText(s); }});

        btn_jrnl_post.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { msg.setText("Posted Journal Entry."); }});

        return root;
    }
    /*public void readEntries (ArrayList<Entry> entry) throws SQLException
    {
        try {
            //c = dbJournal.getJournal();
            //c = journal;
            c = database.rawQuery(SELECT_ENTRIES_QUERY, null);

            if (c != null) {
                c.moveToFirst();
                // Read table rows.
                do {
                    if (c.getCount()>0)
                    {
                        Entry e = new Entry();
                        e.id        = c.getLong     (c.getColumnIndex(DatabaseHelper.GJ_ID));
                        e.je        = c.getLong     (c.getColumnIndex(DatabaseHelper.GJ_JE_ID));
                        e.date      = c.getString   (c.getColumnIndex(DatabaseHelper.GJ_DATE));
                        e.memo      = c.getString   (c.getColumnIndex(DatabaseHelper.GJ_MEMO));
                        e.dr_acct   = c.getString   (c.getColumnIndex(DatabaseHelper.GJ_DR_ACCT));
                        e.cr_acct   = c.getString   (c.getColumnIndex(DatabaseHelper.GJ_CR_ACCT));
                        e.amount    = c.getFloat    (c.getColumnIndex(DatabaseHelper.GJ_AMOUNT));
                        entry.add(e);
                    }
                    else
                        break;
                    // Move to the next row.
                } while (c.moveToNext());
            }
        } catch (SQLException e) {}
    }*/

    /*public void initJournal() throws SQLException
    {
        try {
            ContentValues cv = new ContentValues();
            cv.put(DatabaseHelper.KEY_ENTRY_DATE, "" + Calendar.getInstance().getTime());
            cv.put(DatabaseHelper.KEY_ENTRY_MEMO, "Balance forward");
            database.insert(DatabaseHelper.TBL_JE, null, cv);

        }catch (SQLException e) {}
    }

    /*public void initGenJrnl () throws SQLException
    {
        try
        {
            ContentValues v = new ContentValues();
            v.put(DatabaseHelper.GJ_JE_ID, 1);
            v.put(DatabaseHelper.GJ_DATE, "26-12-2019");
            v.put(DatabaseHelper.GJ_MEMO, "Balance forward");
            v.put(DatabaseHelper.GJ_DR_ACCT, "Cash");
            v.put(DatabaseHelper.GJ_CR_ACCT, "");
            v.put(DatabaseHelper.GJ_AMOUNT, 1500.01f);
            database.insert(DatabaseHelper.TBL_GenJrnl, null, v);
            v.put(DatabaseHelper.GJ_JE_ID, 1);
            v.put(DatabaseHelper.GJ_DATE, "26-12-2019");
            v.put(DatabaseHelper.GJ_MEMO, "Balance forward");
            v.put(DatabaseHelper.GJ_DR_ACCT, "");
            v.put(DatabaseHelper.GJ_CR_ACCT, "Equity");
            v.put(DatabaseHelper.GJ_AMOUNT, 1500.01f);
            database.insert(DatabaseHelper.TBL_GenJrnl, null, v);
        }
        catch (SQLException e) {}
    }*/

    private ArrayList<String> fetchEntriesArray(ArrayList<Entry> e)
    {
        ArrayList<String> l = new ArrayList<>();    // list of String arrays to hold entries information
        Entry entry;                                // Entry construct
        float sum = 0.0f;

        // Loop through the entries array
        for (int i = 0; i < e.size(); i++)
        {
            entry = e.get(i);
            sum += entry.amount;
            l.add("[ " +String.valueOf(entry.id) +" ; " +String.valueOf(entry.je) +" ] DR " +entry.dr_acct +" CR " +entry.cr_acct +" $" +String.valueOf(entry.amount) +" BAL: $" +String.valueOf(sum));
        }
        return l;
    }

    private float totalDebits(ArrayList<Entry> e)
    {
        float sum = 0.0f;
        Entry entry;
        for (int i = 0; i < e.size(); i++)
        {
            entry = e.get(i);
            if (entry.cr_acct.isEmpty()) {  sum += entry.amount;    }
        }
        return sum;
    }
    private float totalCredits(ArrayList<Entry> e)
    {
        float sum = 0.0f;
        Entry entry;
        for (int i = 0; i < e.size(); i++)
        {
            entry = e.get(i);
            if (entry.dr_acct.isEmpty()) {  sum += entry.amount;    }
        }
        return sum;
    }
}