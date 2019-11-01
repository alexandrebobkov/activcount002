/**
 *
 *  Date Created:       October 31, 2019
 *  Last time updated:  October 31, 2019
 *  Revision:           2
 *
 *  Author: Alexandre Bobkov
 *  Company: Alexandre Comptabilite Specialise Ltee.
 *
 *  Objecive: main class for handling all UX items (textboxes, buttons, etc).
 *
 **/
package com.example.activcount_002;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel
{
    public static String status_msg = "initial value";  // define static variable: one for all instances.
    public static String home_status_msg = "home initial status";
    private MutableLiveData<String> statusText;
    private MutableLiveData<String> homeStatusText;

    public MainViewModel() {
        statusText = new MutableLiveData<>();
        homeStatusText = new MutableLiveData<>();
        statusText.setValue(status_msg);
        homeStatusText.setValue(home_status_msg);
    }

    public String get_status_msg() {    return status_msg;  }
    public String get_home_status_msg() {    return home_status_msg;  }

    public void setStatus_msg(String s)
    {
        status_msg = s;
    }
    public void setHomeStatus_msg(String s)
    {
        home_status_msg = s;
    }
    public LiveData<String> getStatus_msg() {
        return statusText;
    }
    public LiveData<String> getHomeStatus_msg() {
        return homeStatusText;
    }
}
