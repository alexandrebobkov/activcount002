package com.example.activcount_002.ui.about;

import android.app.Activity;
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

public class AboutFragment extends Fragment {

    Activity context;

    // This interface can be implemented by the Activity, parent Fragment,
    // or a separate test implementation.
    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(int position);
    }


    private AboutViewModel aboutViewModel;
    String msg = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle bundle)
    {
        context = getActivity();

        aboutViewModel = ViewModelProviders.of(this).get(AboutViewModel.class);
        View root = inflater.inflate(R.layout.fragment_about, container, false);


       //String msg_value = getArguments().getString("link");

       final TextView textView = root.findViewById(R.id.credits_text);
       aboutViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(msg);
            }
       });
       return root;
    }

    public void setMsg(String text) {
        TextView view = (TextView) getView().findViewById(R.id.credits_text);
        view.setText(text);
    }

    public void setMsgValue (String text)
    {
        msg = text;
    }

    public static AboutFragment newInstance (String msg)
    {
        AboutFragment af = new AboutFragment();
        Bundle b = new Bundle();
        b.putString("link", msg);
        af.setArguments(b);
        return (af);
    }
}