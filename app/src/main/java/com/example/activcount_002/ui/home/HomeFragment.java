package com.example.activcount_002.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.activcount_002.R;

import com.example.activcount_002.MainViewModel;

public class HomeFragment extends Fragment
{

    MainViewModel mainViewModel;
    /** PULL DATA AT HOME FRAGMENT **/
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        final TextView statusMsg = root.findViewById(R.id.status_msg);
        //mainViewModel.setHomeStatus_msg("updated!");
        //statusMsg.setText("updated 2");
        statusMsg.setText(mainViewModel.get_home_status_msg());
        return root;
    }

    public void setText(String text) {
        TextView view = (TextView) getView().findViewById(R.id.status_msg);
        view.setText("22");
    }
}