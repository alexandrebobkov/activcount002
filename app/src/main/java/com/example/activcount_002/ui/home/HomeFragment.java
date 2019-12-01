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

package com.example.activcount_002.ui.home;

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
import com.example.activcount_002.R;
import com.example.activcount_002.MainViewModel;
import com.example.activcount_002.db.DBManager;

import org.w3c.dom.Text;

public class HomeFragment extends Fragment
{
    MainViewModel               mainViewModel;
    private ListView            listView;
    private ListAdapter         listAdapter;
    private DBManager           dbManager;
    private ArrayList<String>   theList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        mainViewModel                   = ViewModelProviders.of(this).get(MainViewModel.class);
        View    root                    = inflater.inflate(R.layout.fragment_home, container, false);
        final   TextView statusMsg      = root.findViewById(R.id.status_msg);

        statusMsg.setText(mainViewModel.get_home_status_msg());

        listView = (ListView) root.findViewById(R.id.db_list_view);
        theList = new ArrayList<>();

        dbManager = new DBManager(getContext());
        dbManager.open();

        fetchEntries(dbManager.fetch(), theList);

        listView.setOnItemClickListener(new OnItemClickListener()
        {
            Button      btn_save, btn_del, btn_close;
            TextView    field_value, field_value_1, field_value_2;

            // Execute if list item was clicked.
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
            {
                final   int     argument_2;
                final   String  msg;
                final   Dialog  dialog = new Dialog(getContext());

                dialog.setTitle("SQLITE DATA");
                dialog.setContentView(R.layout.app_dialog_layout);

                btn_save        =   (Button)    dialog.findViewById(R.id.dialog_btn_update);
                btn_del         =   (Button)    dialog.findViewById(R.id.dialog_btn_delete);
                btn_close       =   (Button)    dialog.findViewById(R.id.dialog_btn_close);

                field_value     =   (TextView)  dialog.findViewById(R.id.dialog_field);
                field_value_1   =   (TextView)  dialog.findViewById(R.id.dialog_field_1);
                field_value_2   =   (TextView)  dialog.findViewById(R.id.dialog_field_2);

                msg = "_id: " +(long)(arg2+1);
                argument_2 = arg2;
                field_value.setText(msg);

                // UPDATE button. Store entered value into database.
                btn_save.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        update_field(false, argument_2, "" +field_value_1.getText(), "" +field_value_2.getText());
                        Toast.makeText(getContext(), "Updated!", Toast.LENGTH_SHORT).show();
                        theList = new ArrayList<>();
                        fetchEntries(dbManager.fetch(), theList);
                        dialog.cancel();
                    }
                });

                // DELETE button. Delete entry.
                btn_del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        update_field(true, argument_2, "__DELETED__",  "__NA__");
                        Toast.makeText(getContext(), "Deleted!", Toast.LENGTH_SHORT).show();
                        theList = new ArrayList<>();
                        fetchEntries(dbManager.fetch(), theList);
                        dialog.cancel();
                    }
                });

                // CLOSE button. Close the dialog.
                btn_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        //update_field(true, argument_2, "__DELETED__");
                        Toast.makeText(getContext(), "No changes were made!", Toast.LENGTH_SHORT).show();
                        theList = new ArrayList<>();
                        fetchEntries(dbManager.fetch(), theList);
                        dialog.cancel();
                    }
                });

                //theList = new ArrayList<>();
                //fetchEntries(dbManager.fetch(), theList);
                dialog.show();
            }
        });

        return root;
    }

    private void update_field (boolean delete, int arg2, String field, String description)
    {
        if (delete)
        {
            dbManager.update(1+(long)arg2, "__DELETED__", "__NA__");
        }
        else
        {
            dbManager.update(1+(long)arg2, field, description);
        }
    }

    private void fetchEntries(Cursor c, ArrayList<String> l)
    {
        try {
            // Define cursor for db table data
            c = dbManager.fetch();

            // Read table rows.
            do {
                // Combine 3 table fields into 1 string
                l.add("_id: " +c.getString(0) + ". " +c.getString(1) + "   " +c.getString(2));
                // Move to the next row.
            } while (c.moveToNext());

            mainViewModel.loadEntries(l);
            listAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mainViewModel.getEntriesList());
            listView.setAdapter(listAdapter);

        } catch (SQLException e) {}
    }
}