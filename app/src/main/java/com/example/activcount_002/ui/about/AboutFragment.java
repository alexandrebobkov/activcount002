package com.example.activcount_002.ui.about;

import android.app.Activity;
//import android.app.Fragment;
import androidx.fragment.app.Fragment;
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

import org.w3c.dom.Text;

public class AboutFragment extends Fragment {

    Activity context;

    private Listener listener;

    // This interface can be implemented by the Activity, parent Fragment,
    // or a separate test implementation.


    private AboutViewModel aboutViewModel;
    String msg = "";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle bundle)
    {
        context = getActivity();


        //aboutViewModel = ViewModelProviders.of(this).get(AboutViewModel.class);
        View root = inflater.inflate(R.layout.fragment_about, container, false);
        Button btn_ok = (Button) root.findViewById(R.id.btn_001);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDetail();
            }
        });

        TextView msg_txt = (TextView) context.findViewById(R.id.text_home);
        //msg_txt.setText("ok");

       //String msg_value = getArguments().getString("link");
       /* TextView msg_txt = (TextView) context.findViewById(R.id.status_msg);

        msg_txt.setText("ok");*/

       final TextView textView = root.findViewById(R.id.credits_text);
       /*aboutViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(msg);
                //textView.setText(context.setStatusMsg("ok"));
            }
       });*/
        //updateDetail();
       return root;
    }
    public interface Listener {
        public void onItemSelected(String link);
    }
    @Override
    public void onAttach (Activity activity)
    {
        super.onAttach(activity);
        if (activity instanceof Listener)
            listener = (Listener) activity;
        else
            throw new ClassCastException(activity.toString() + " must implement AboutFragment.listener");
    }

    public void setMsg(String text) {
        TextView view = (TextView) getView().findViewById(R.id.credits_text);
        view.append(" n" +text);
    }

    public void setMsgValue (String text)
    {
        msg = text;
        //TextView sts = (TextView) getView().findViewById(R.id.app_objective);
        //sts.append(text);
    }

    /*public static AboutFragment newInstance (String msg)
    {
        AboutFragment af = new AboutFragment();
        Bundle b = new Bundle();
        b.putString("link", msg);
        af.setArguments(b);
        return (af);
    }*/

    public void updateDetail()
    {
        String s = "done!";
        //if (listener != null)
            listener.onItemSelected(s);
        TextView sts = (TextView) getView().findViewById(R.id.credits_text);
        sts.append(" 2019");
        sts.append(" " +msg);
    }
}