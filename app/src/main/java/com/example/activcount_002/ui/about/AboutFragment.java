package com.example.activcount_002.ui.about;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.fragment.app.Fragment;

import com.example.activcount_002.MainViewModel;
import com.example.activcount_002.R;
import com.example.activcount_002.ui.home.HomeFragment;

public class AboutFragment extends Fragment
{
    Intent intent;
    private MainViewModel   mainViewModel;
    String  msg     =       "";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle bundle)
    {
        mainViewModel   = ViewModelProviders.of(this).get(MainViewModel.class);
        View root       = inflater.inflate(R.layout.fragment_about, container, false);

        final TextView  credits_text_view   = root.findViewById(R.id.credits_text);
        final Button    btn_ok              = root.findViewById(R.id.btn_001);

        btn_ok.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                updateDetail();
                credits_text_view.setText(msg);
            }
        });

        mainViewModel.getStatus_msg().observe(this, new Observer<String>()
        {
            @Override
            public void onChanged(@Nullable String s)   {   credits_text_view.setText(s);   }
        });

        return root;
    }

    private void updateDetail()
    {
        msg   = "By: Alexandre Bobkov 2020 \n Press OK to initialize data.";
        mainViewModel.setStatus_msg(msg);
        mainViewModel.setHomeStatus_msg("updated!");

        //TextView    sts = (TextView) getView().findViewById(R.id.credits_text);
        //sts.setText(msg);
    }

    /*public void onClick(View v)
    {
        intent = new Intent(AboutFragment.this, HomeFragment.class);
    }*/
}