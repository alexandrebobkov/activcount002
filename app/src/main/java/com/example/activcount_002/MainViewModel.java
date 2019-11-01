/**
 *
 *  Date Created:       October 31, 2019
 *  Last time updated:  October 31, 2019
 *  Revision:           2
 *
 *  Author: Alexandre Bobkov
 *  Company: Alexandre Comptabilite Specialise Ltee.
 *
 *  Objective: main class for handling all UX items (textboxes, buttons, etc).
 *
 **/
package com.example.activcount_002;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel
{
    private static String status_msg         = "initial value";         // define static variable: one for all instances.
    private static String home_status_msg    = "initial home status";   // define static variable: one for all instances.
    private MutableLiveData<String> statusText;
    private MutableLiveData<String> homeStatusText;

    public MainViewModel()
    {
        statusText      = new MutableLiveData<>();
        homeStatusText  = new MutableLiveData<>();

        statusText.setValue(status_msg);
        homeStatusText.setValue(home_status_msg);
    }

    public void setStatus_msg(String s)     {   status_msg = s;             }
    public void setHomeStatus_msg(String s) {   home_status_msg = s;        }
    public String get_home_status_msg()     {   return home_status_msg;     }
    public LiveData<String> getStatus_msg() {   return statusText;          }
}
