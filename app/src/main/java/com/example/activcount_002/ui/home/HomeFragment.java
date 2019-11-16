/**
 *
 *  Date Created:       October 15, 2019
 *  Last time updated:  November 7, 2019
 *  Revision:           2
 *
 *  Author: Alexandre Bobkov
 *  Company: Alexandre Comptabilite Specialise Ltee.
 *
 *  Program purpose: To display key accounting information and financial overview.
 *
 **/

package com.example.activcount_002.ui.home;

import android.app.Dialog;
import android.database.Cursor;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.widget.Button;
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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import java.util.ArrayList;

import com.example.activcount_002.R;

import com.example.activcount_002.MainViewModel;
import com.example.activcount_002.db.DBManager;
import com.example.activcount_002.db.DatabaseHelper;

import org.w3c.dom.Text;

public class HomeFragment extends Fragment
{
    MainViewModel               mainViewModel;
    private ListView            listView;
    private DBManager           dbManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        mainViewModel                   = ViewModelProviders.of(this).get(MainViewModel.class);
        View    root                    = inflater.inflate(R.layout.fragment_home, container, false);
        final   TextView statusMsg      = root.findViewById(R.id.status_msg);

        statusMsg.setText(mainViewModel.get_home_status_msg());

        listView = (ListView) root.findViewById(R.id.db_list_view);
        ArrayList<String> theList = new ArrayList<>();
        dbManager = new DBManager(getContext());
        dbManager.open();
        //boolean table_exists = dbManager.doesTableExist("TABLE_DATA");

        try {
            // Define cursor for db table data
            Cursor cursor = dbManager.fetch();

            // Read table rows.
            do {
                // Combine 3 table fields into 1 string
                theList.add("_id: " +cursor.getString(0) + ". " +cursor.getString(1) + "   " +cursor.getString(2));
                ListAdapter listAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, theList);
                listView.setAdapter(listAdapter);
                // Move to the next row.
            } while (cursor.moveToNext());
        } catch (SQLException e) {}

        listView.setOnItemClickListener(new OnItemClickListener()
        {
            Button btn_save, btn_close;
            TextView field_value;

            // Execute if list item was clicked.
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
            {
                final String msg;
                final Dialog dialog = new Dialog(getContext());
                dialog.setTitle("SQLITE DATA");
                dialog.setContentView(R.layout.app_dialog_layout);

                btn_save    =   (Button) dialog.findViewById(R.id.dialog_btn_update);
                btn_close   =   (Button) dialog.findViewById(R.id.dialog_btn_close);
                field_value =   (TextView) dialog.findViewById(R.id.dialog_field);

                msg = "_id: " +(long)(arg2+1);
                field_value.setText(msg);

                btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        field_value.setText("updated!");

                        //dialog.cancel();
                    }
                });
                btn_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });


                dialog.show();


                /*final int _id = arg2;
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Item Selected " +((long)arg2+1))
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                Toast.makeText(getContext(), "Selected item # " +((long)1+_id), Toast.LENGTH_SHORT).show();
                                dbManager.update(1+(long)_id, "__DELETED__", "__NA__");
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });

                AlertDialog d = builder.create();
                d.setTitle("Update selected item?");
                d.show();*/
            }
        });

        // ADD VIEW MODEL LIST VIEW REFRESH CODE
        /*mainViewModel.getAssetsCurrentText().observe(this, new Observer<String>()
        {
            @Override
            public void onChanged(@Nullable String s)   {   assets_current.setText(s);   }
        });*/

        return root;
    }
}