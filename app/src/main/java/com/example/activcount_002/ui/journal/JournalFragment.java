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

package com.example.activcount_002.ui.journal;

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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.example.activcount_002.R;
import com.example.activcount_002.MainViewModel;
import com.example.activcount_002.db.DBManager;

import org.w3c.dom.Text;

import com.example.activcount_002.db.DatabaseHelper;
import com.example.activcount_002.db.Entry;

public class JournalFragment extends Fragment
{
    MainViewModel               mainViewModel;
    private ListAdapter         listAdapter;
    private ListView            journalView;
    private DBManager           dbManager;
    private DatabaseHelper      dbHelper;
    private SQLiteDatabase      database;
    private ArrayList<Entry>    entriesList;
    private ArrayList<String>         theList;
    //private ListView            listView;
    private Cursor              c;

    private String              SELECT_ENTRIES_QUERY;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        SELECT_ENTRIES_QUERY = String.format(
            "SELECT * FROM %s",
            DatabaseHelper.TBL_GenJrnl);

        mainViewModel   = ViewModelProviders.of(this).get(MainViewModel.class);
        entriesList     = new ArrayList<>();
        dbManager       = new DBManager(getContext());
        View root       = inflater.inflate(R.layout.fragment_journal, container, false);

        journalView = (ListView) root.findViewById(R.id.db_journal_view);

        /** DATABASE OPERATIONS **/
        dbHelper = new DatabaseHelper(getContext());
        database = dbHelper.getWritableDatabase();
        initJournal();
        initGenJrnl();
        readEntries(entriesList);
        database.close();

        theList = new ArrayList<>();
        fetchEntries(theList);
        mainViewModel.loadEntries(theList);
        listAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mainViewModel.getEntriesList());
        journalView.setAdapter(listAdapter);

        return root;
    }

    public void readEntries (ArrayList<Entry> entry) throws SQLException
    {
        /*String[] columns = new String[] {
                DatabaseHelper.GJ_ID,
                DatabaseHelper.GJ_JE_ID,
                DatabaseHelper.GJ_DATE,
                DatabaseHelper.GJ_MEMO,
                DatabaseHelper.GJ_DR_ACCT,
                DatabaseHelper.GJ_CR_ACCT,
                DatabaseHelper.GJ_AMOUNT };

        String SELECT_ENTRIES_QUERY = String.format(
                "SELECT * FROM %s",
                DatabaseHelper.TBL_GenJrnl);*/

        try {
            c = database.rawQuery(SELECT_ENTRIES_QUERY, null);

            if (c != null) {
                c.moveToFirst();

                // Read table rows.
                do {
                    if (c.getCount()>0)
                    {
                        Entry e = new Entry();
                        e.id        = Long.parseLong(c.getString(c.getColumnIndex(  DatabaseHelper.GJ_ID)));
                        e.je        = Long.parseLong(c.getString(c.getColumnIndex(  DatabaseHelper.GJ_JE_ID)));
                        e.date      = c.getString(c.getColumnIndex(                 DatabaseHelper.GJ_DATE));
                        e.memo      = c.getString(c.getColumnIndex(                 DatabaseHelper.GJ_MEMO));
                        e.dr_acct   = c.getString(c.getColumnIndex(                 DatabaseHelper.GJ_DR_ACCT));
                        e.cr_acct   = c.getString(c.getColumnIndex(                 DatabaseHelper.GJ_CR_ACCT));
                        e.amount    = Long.parseLong(c.getString(c.getColumnIndex(  DatabaseHelper.GJ_AMOUNT)));
                        entry.add(e);
                    }
                    else
                        break;
                    // Move to the next row.
                } while (c.moveToNext());
            }

            /*mainViewModel.loadJournalEntries(entry);
            listAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mainViewModel.getJournalEntriesList());
            journalView.setAdapter(listAdapter);*/

        } catch (SQLException e) {}
    }

    public void listEntries (ArrayList<Entry> entry)
    {
        ArrayList<String> je = new ArrayList<>();

        for (int i = 0; i < entry.size(); i++)
        {
            entry.indexOf(entry);
        }
        mainViewModel.loadJournalEntries(entry);
        listAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mainViewModel.getJournalEntriesList());
        journalView.setAdapter(listAdapter);
    }

    public void initJournal ()
    {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.KEY_ENTRY_DATE, "" + Calendar.getInstance().getTime());
        cv.put(DatabaseHelper.KEY_ENTRY_MEMO, "Balance forward");
        database.insert(DatabaseHelper.TBL_JE, null, cv);
    }

    public void initGenJrnl () throws SQLException
    {
        try
        {
            //database.beginTransaction();

            ContentValues v = new ContentValues();
            //v.put(DatabaseHelper.GJ_ID, 1);
            v.put(DatabaseHelper.GJ_JE_ID, 1);
            v.put(DatabaseHelper.GJ_DATE, "26-12-2019");
            v.put(DatabaseHelper.GJ_MEMO, "Balance forward");
            v.put(DatabaseHelper.GJ_DR_ACCT, "Cash");
            v.put(DatabaseHelper.GJ_CR_ACCT, "");
            v.put(DatabaseHelper.GJ_AMOUNT, 1500);
            database.insert(DatabaseHelper.TBL_GenJrnl, null, v);
            v.put(DatabaseHelper.GJ_JE_ID, 1);
            v.put(DatabaseHelper.GJ_DATE, "26-12-2019");
            v.put(DatabaseHelper.GJ_MEMO, "Balance forward");
            v.put(DatabaseHelper.GJ_DR_ACCT, "");
            v.put(DatabaseHelper.GJ_CR_ACCT, "Equity");
            v.put(DatabaseHelper.GJ_AMOUNT, 1500);
            database.insert(DatabaseHelper.TBL_GenJrnl, null, v);
            //database.setTransactionSuccessful();
        }
        catch (SQLException e) {}
    }

    private void fetchEntries(ArrayList<String> l) throws SQLException
    {
        try {
            // Define cursor for db table data
            //c = database.rawQuery(SELECT_ENTRIES_QUERY, null);

            // Read table rows.
            c.moveToFirst();
            do {
                // Combine columns into 1 string

                l.add("[ " +DatabaseHelper.GJ_ID + " " +c.getString(c.getColumnIndex(DatabaseHelper.GJ_ID)) + " ; " +
                                DatabaseHelper.GJ_JE_ID + " " +c.getString(c.getColumnIndex(DatabaseHelper.GJ_JE_ID)) + " ]" +
                                " on " +c.getString(c.getColumnIndex(DatabaseHelper.GJ_DATE)) + ". " +
                                c.getString(c.getColumnIndex(DatabaseHelper.GJ_MEMO)) + " " +
                                " DR " +c.getString(c.getColumnIndex(DatabaseHelper.GJ_DR_ACCT)) + " | " +
                                " CR " +c.getString(c.getColumnIndex(DatabaseHelper.GJ_CR_ACCT)) + " | " +
                                " $" +c.getString(c.getColumnIndex(DatabaseHelper.GJ_AMOUNT)));
                // Move to the next row.
            } while (c.moveToNext());

            /*mainViewModel.loadEntries(l);
            listAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mainViewModel.getEntriesList());
            journalView.setAdapter(listAdapter);*/

        } catch (SQLException e) {}
    }
}