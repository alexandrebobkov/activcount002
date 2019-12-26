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
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
    private ArrayList<Entry>    entriesList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        mainViewModel   = ViewModelProviders.of(this).get(MainViewModel.class);
        entriesList     = new ArrayList<>();
        dbManager       = new DBManager(getContext());
        View root       = inflater.inflate(R.layout.fragment_journal, container, false);

        journalView = (ListView) root.findViewById(R.id.db_journal_view);

        dbManager.open();
        readEntries(dbManager.fetch(), entriesList);
        dbManager.close();

        return root;
    }

    public void readEntries (Cursor c, ArrayList<Entry> entry) throws SQLException
    {
        try {
            // Define cursor for db table data
            c = dbManager.fetchEntries();

            // Read table rows.
            do {

                Entry e = new Entry ();
                e.id        = Long.getLong(c.getString(0));
                e.je        = Long.getLong(c.getString(1));
                e.date      = c.getString(2);
                e.memo      = c.getString(3);
                e.dr_acct   = c.getString(4);
                e.cr_acct   = c.getString(5);
                e.amount    = Long.getLong(c.getString(6));
                entry.add(e);

            // Move to the next row.
            } while (c.moveToNext());

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

}