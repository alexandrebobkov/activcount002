package com.example.activcount_002.ui.about;

import android.app.Activity;
import android.content.Intent;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
//import android.annotation.SuppressLint;
//import android.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.activcount_002.MainActivity;
import com.example.activcount_002.R;
import com.example.activcount_002.ui.home.HomeFragment;

import org.w3c.dom.Text;

public class AboutFragment extends Fragment {

    Activity context;
    private Listener listener;
    private AboutViewModel aboutViewModel;

    String msg = "";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle bundle)
    {
        context = getActivity();
        aboutViewModel = ViewModelProviders.of(this).get(AboutViewModel.class);
        View root = inflater.inflate(R.layout.fragment_about, container, false);

        final TextView credits_text_view = root.findViewById(R.id.credits_text);
        final Button btn_ok = root.findViewById(R.id.btn_001);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                updateDetail();
            }
        });

        aboutViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {     credits_text_view.setText(s);   }
        });
        return root;
    }

    public interface Listener {     public void onItemSelected(String link);    }

    @Override
    public void onAttach (Activity activity)
    {
        super.onAttach(activity);
        if (activity instanceof Listener)
            listener = (Listener) activity;
        else
            throw new ClassCastException(activity.toString() + " must implement AboutFragment.listener");
    }

    public void setMsgValue (String text) {     msg = text;     }

    private void updateDetail()
    {
        String s = "done!";
        //if (listener != null)
            listener.onItemSelected(s);
        TextView sts = (TextView) getView().findViewById(R.id.credits_text);
        sts.setText(" 2019");
    }
}