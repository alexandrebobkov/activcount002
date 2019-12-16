package com.example.activcount_002.db;

public class Entry
{
    public long     id;
    public long     je;
    public String   date;
    public String   memo;
    public String   dr_acct;
    public String   cr_acct;
    public long     amount;

    public Entry ()
    {
        id      =   -1;
        je      =   -1;
        date    =   "01-01-2019";
        dr_acct =   null;
        cr_acct =   null;
        amount  =   0;
    }
}
