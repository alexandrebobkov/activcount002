package com.example.activcount_002.ui.home;

import android.database.Cursor;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.activcount_002.MainActivity;
import com.example.activcount_002.R;

import com.example.activcount_002.MainViewModel;
import com.example.activcount_002.db.DBManager;
import com.example.activcount_002.db.DatabaseHelper;
import com.example.activcount_002.ui.about.AboutFragment;

public class HomeFragment extends Fragment
{
    Intent intent;
    MainViewModel mainViewModel;
    private ListView listView;
    private DBManager dbManager;
    private SimpleCursorAdapter adapter;
    final String[] from = new String[] {DatabaseHelper._ID, DatabaseHelper.SUBJECT, DatabaseHelper.DESC};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        mainViewModel               = ViewModelProviders.of(this).get(MainViewModel.class);
        View    root                = inflater.inflate(R.layout.fragment_home, container, false);
        final   TextView statusMsg  = root.findViewById(R.id.status_msg);
        statusMsg.setText(mainViewModel.get_home_status_msg());

        dbManager = new DBManager(getContext());
        dbManager.open();
        Cursor cursor = dbManager.fetch();

        return root;
    }

    /*public void onClick(View v)
    {
        intent = new Intent(HomeFragment.this, AboutFragment.class);
    }*/
}