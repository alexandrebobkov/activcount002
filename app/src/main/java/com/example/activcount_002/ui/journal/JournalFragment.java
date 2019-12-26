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
    private ListView        journalView;
    private DBManager       dbManager;
    private Entry           entry;
    private ArrayList       entriesList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        entriesList = new ArrayList<>();
        entry       = new Entry();
        dbManager   = new DBManager(getContext());
        View root   = inflater.inflate(R.layout.fragment_journal, container, false);

        journalView = (ListView) root.findViewById(R.id.db_journal_view);

        dbManager.open();
        dbManager.close();

        return root;
    }
}