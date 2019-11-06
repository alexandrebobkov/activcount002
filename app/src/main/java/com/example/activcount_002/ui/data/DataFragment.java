package com.example.activcount_002.ui.data;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.activcount_002.MainActivity;
import com.example.activcount_002.MainViewModel;
import com.example.activcount_002.MainData;
import com.example.activcount_002.R;
import com.example.activcount_002.db.DBManager;

import org.w3c.dom.Text;

import java.util.Calendar;

public class DataFragment extends Fragment {

    private MainViewModel mainViewModel;
    private MainData mainData;
    private DBManager dbManager;
    private View view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        mainData = new MainData();
        view = getView();
        mainViewModel   = ViewModelProviders.of(this).get(MainViewModel.class);
        View root = inflater.inflate(R.layout.fragment_data, container, false);

        dbManager = new DBManager(getContext());

        final Button    btn_ok              = root.findViewById(R.id.btn_data_submit);
        final TextView  assets_current      = root.findViewById(R.id.data_cash_and_deposits);
        final TextView  assets_supplies     = root.findViewById(R.id.data_inventory);
        final TextView  assets_total        = root.findViewById(R.id.data_total_assets);
        final TextView  net_revenues        = root.findViewById(R.id.data_net_revenues);
        final TextView  direct_costs        = root.findViewById(R.id.data_direct_costs);

        btn_ok.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Update MainData variables
                mainData.setAssetsCurrent(Float.parseFloat(""+assets_current.getText()));
                mainData.setAssetsSupplies(Float.parseFloat(""+assets_supplies.getText()));
                mainData.setAssetsTotal(Float.parseFloat(""+assets_total.getText()));

                mainData.setNetRevenues(Float.parseFloat(""+net_revenues.getText()));
                mainData.setDirectCosts(Float.parseFloat(""+direct_costs.getText()));

                mainViewModel.setAssetsCurrent(assets_current);
                mainViewModel.setAssetsSupplies(assets_supplies);
                mainViewModel.setAssetsTotal(assets_total);

                mainViewModel.setNetRevenues(net_revenues);
                mainViewModel.setDirectCosts(direct_costs);

                mainViewModel.setHomeStatus_msg("data submitted!"
                        +"\nCA: " +mainData.getAssetsCurrent()
                        +"\nLA: " +mainData.getAssetsSupplies()
                        +"\nTA: "+mainData.getAssetsTotal());

                dbManager.open();
                dbManager.insert("created", "" + Calendar.getInstance().getTime());
                dbManager.insert("business name", "activcount.ca");
                dbManager.insert("business number", "145-5897-25");
                dbManager.insert("anniversary date", "Jan 1, 2019");
            }
        });

        mainViewModel.getAssetsCurrentText().observe(this, new Observer<String>()
        {
            @Override
            public void onChanged(@Nullable String s)   {   assets_current.setText(s);   }
        });
        mainViewModel.getAssetsSuppliesText().observe(this, new Observer<String>()
        {
            @Override
            public void onChanged(@Nullable String s)   {   assets_supplies.setText(s);   }
        });
        mainViewModel.getAssetsTotalText().observe(this, new Observer<String>()
        {
            @Override
            public void onChanged(@Nullable String s)   {   assets_total.setText(s);   }
        });
        mainViewModel.getNetRevenuesText().observe(this, new Observer<String>()
        {
            @Override
            public void onChanged(@Nullable String s)   {   net_revenues.setText(s);   }
        });
        mainViewModel.getDirectCostsText().observe(this, new Observer<String>()
        {
            @Override
            public void onChanged(@Nullable String s)   {   direct_costs.setText(s);   }
        });

        return root;
    }
}