package com.example.activcount_002.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.example.activcount_002.R;

import com.example.activcount_002.MainViewModel;

public class HomeFragment extends Fragment
{
    MainViewModel mainViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        mainViewModel               = ViewModelProviders.of(this).get(MainViewModel.class);
        View    root                = inflater.inflate(R.layout.fragment_home, container, false);
        final   TextView statusMsg  = root.findViewById(R.id.status_msg);
        statusMsg.setText(mainViewModel.get_home_status_msg());
        return root;
    }
}