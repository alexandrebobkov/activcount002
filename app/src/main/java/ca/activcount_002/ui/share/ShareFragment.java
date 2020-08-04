package ca.activcount_002.ui.share;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import ca.activcount_002.R;

public class ShareFragment extends Fragment {

    private ShareViewModel shareViewModel;
    private WebSettings webSettings;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(this).get(ShareViewModel.class);
        View root = inflater.inflate(R.layout.fragment_share, container, false);
        //final TextView textView = root.findViewById(R.id.text_share);
        WebView appWebView = (WebView) root.findViewById(R.id.web_view);
        appWebView.setWebViewClient(new WebViewClient());
        appWebView.getSettings().setJavaScriptEnabled(true);
        appWebView.canGoBack();
        appWebView.loadUrl("https://app.activcount.ca/");


        shareViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        return root;
    }
}