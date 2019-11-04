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
import android.widget.ListAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import java.util.ArrayList;

import com.example.activcount_002.R;

import com.example.activcount_002.MainViewModel;
import com.example.activcount_002.db.DBManager;
import com.example.activcount_002.db.DatabaseHelper;

public class HomeFragment extends Fragment
{
    Intent intent;
    MainViewModel mainViewModel;
    private ListView listView;
    private DBManager dbManager;
    private SimpleCursorAdapter adapter;
    //final String[] from = new String[] {DatabaseHelper._ID, DatabaseHelper.DATA, DatabaseHelper.DESCRIPTION};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        mainViewModel               = ViewModelProviders.of(this).get(MainViewModel.class);
        View    root                = inflater.inflate(R.layout.fragment_home, container, false);
        final   TextView statusMsg  = root.findViewById(R.id.status_msg);
        statusMsg.setText(mainViewModel.get_home_status_msg());

        listView = (ListView) root.findViewById(R.id.db_list_view);
        ArrayList<String> theList = new ArrayList<>();
        dbManager = new DBManager(getContext());
        dbManager.open();
        //boolean table_exists = dbManager.doesTableExist("TABLE_DATA");

        Cursor cursor = dbManager.fetch();
        do {
            theList.add(cursor.getString(0));
            theList.add(cursor.getString(1));
            theList.add(cursor.getString(2));
            ListAdapter listAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, theList);
            listView.setAdapter(listAdapter);
        } while (cursor.moveToNext());

        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
            {
                final int _id = arg2;
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Item Selected " +arg2)
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                Toast.makeText(getContext(), "Selected item # " +_id, Toast.LENGTH_SHORT).show();
                                dbManager.update((long)_id, "ASSET", "cash");
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });

                AlertDialog d = builder.create();
                d.setTitle("Update selected item?");
                d.show();
            }
        });

        return root;
    }
}