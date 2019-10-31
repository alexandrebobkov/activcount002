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
    public static String status_msg = "it works!";  // define static variable: one for all instances.
    private MutableLiveData<String> statusText;

    public MainViewModel() {
        statusText = new MutableLiveData<>();
        statusText.setValue(status_msg);
    }

    /*public String getStatus_msg()
    {
        return status_msg;
    }*/
    public void setStatus_msg(String s)
    {
        status_msg = s;
    }
    public LiveData<String> getStatus_msg() {
        return statusText;
    }
}
