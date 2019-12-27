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
        dr_acct =   "";
        cr_acct =   "";
        amount  =   0;
    }
    public Entry (long id, long je, String date, String memo, String dr_acct, String cr_acct, long amount)
    {
        this.id         = id;
        this.je         = je;
        this.date       = date;
        this.memo       = memo;
        this.dr_acct    = dr_acct;
        this.cr_acct    = cr_acct;
        this.amount     = amount;
    }
}
