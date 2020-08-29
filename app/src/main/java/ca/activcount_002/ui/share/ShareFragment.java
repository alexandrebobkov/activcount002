package ca.activcount_002.ui.share;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.TextView;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings;
import android.webkit.WebChromeClient;
import android.app.ProgressDialog;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import ca.activcount_002.R;

public class ShareFragment extends Fragment {

    private ShareViewModel shareViewModel;
    private WebSettings webSettings;
    private WebView appWebView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(this).get(ShareViewModel.class);
        View root = inflater.inflate(R.layout.fragment_share, container, false);
        //final TextView textView = root.findViewById(R.id.text_share);

        //final ProgressDialog progressDialog = new ProgressDialog ();
        //progressDialog.setCancelable(false);
        appWebView = (WebView) root.findViewById(R.id.web_view);

        appWebView.getSettings().setJavaScriptEnabled(true);
        //appWebView.canGoBack();
        appWebView.loadUrl("https://app.activcount.ca/");

        appWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        appWebView.setWebChromeClient (new WebChromeClient() {
           public void onProgressChanged(WebView view, int progress) {

           }
        });

       shareViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);

            }
        });
        return root;
    }

    //@Override
    public void onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && appWebView.canGoBack()) {
            appWebView.goBack();
        }
    }


}