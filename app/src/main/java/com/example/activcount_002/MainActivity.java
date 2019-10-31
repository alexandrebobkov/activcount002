/**
 *
 *  Date Created:       October 15, 2019
 *  Last time updated:  October 30, 2019
 *  Revision:           2
 *
 *  Author: Alexandre Bobkov
 *  Company: Alexandre Comptabilite Specialise Ltee.
 *
 *  Program purpose: To display key accounting information and financial overview.
 *
**/

package com.example.activcount_002;

import android.os.Bundle;

import com.example.activcount_002.ui.about.AboutFragment;
import com.example.activcount_002.ui.home.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.LayoutInflater;
import android.view.View;
import androidx.fragment.app.Fragment;

import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import android.widget.Button;

import android.view.Menu;

public class MainActivity extends AppCompatActivity implements AboutFragment.Listener //View.OnClickListener //
{
    MainViewModel mainViewModel;
    Button ok_btn;
    TextView status_txt;

    private AppBarConfiguration mAppBarConfiguration;
    public String status_msg = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        // MainViewModel
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        status_txt = (TextView) findViewById(R.id.status_msg);
        ok_btn = findViewById(R.id.btn_001);
        ok_btn.setOnClickListener(this);
        */

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_about, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        /*TextView status_txt = (TextView) findViewById(R.id.status_msg);
        status_txt.setText("Status: updated");*/
        //setAboutMsg("link");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        //setAboutMsg("link");
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //setAboutMsg("link");
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /*@Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_001:
                break;
        }
    }*/

    @Override
    public void onItemSelected (String str)
    {
        HomeFragment home_frg = (HomeFragment) getSupportFragmentManager().findFragmentById(R.id.nav_home);
        if (home_frg != null)//  && home_frg.isInLayout())
            home_frg.setText(str);

        AboutFragment about_frg = (AboutFragment) getSupportFragmentManager().findFragmentById(R.id.nav_about);
        if (about_frg != null)
            about_frg.setMsgValue(str);
        //TextView status_view = (TextView) findViewById(R.id.status_msg);
        //status_view.append("works!");
    }

    public void setAboutMsg (String link)
    {
        AboutFragment about_frg = new AboutFragment();

        //AboutFragment about_frg = (AboutFragment) findFragmentById(R.id.nav_about);
        Bundle bundle = new Bundle();
        String msg_value = "ok ok";
        bundle.putString("link", msg_value);
        //bundle.putString("link");
        about_frg.setArguments(bundle);
        about_frg.setMsgValue("link");
    }

    public void setStatusMsg(String s)
    {
        status_msg = s;
    }
}
