package com.hardway.gnits.databaseoffline;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by DELL on 13-07-2016.
 */
public class Login_DONE {
public static final String SP_NAME = "userLoggedin";
    SharedPreferences locallogin;

    public Login_DONE(Context context)
    {
        locallogin = context.getSharedPreferences(SP_NAME,0);
    }

public void setLoggedIn(boolean loggedin)
{
    SharedPreferences.Editor spEditor = locallogin.edit();
    spEditor.putBoolean("LoggedIn",loggedin);
    spEditor.commit();
}

    public boolean getUserLoggedIn(){
        if(locallogin.getBoolean("LoggedIn",false) == true)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}
