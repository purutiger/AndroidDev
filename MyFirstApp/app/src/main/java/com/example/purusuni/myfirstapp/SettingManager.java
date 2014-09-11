package com.example.purusuni.myfirstapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

/**
 * Created by PuruSuni on 02/09/2014.
 */
public class SettingManager
{
    private Context mContext;
    public static final String HOURS = "HOURS";
    public static final String MINUTES = "MINUTES";
    public static final String SYNC_INTERVAL = "SYNCINTERVAL";
    public static final String SYNC_DURATION = "SYNCDURATION";
    public static final String WIFI_ON_TIME = "WIFIONTIME";
    public static final String WIFI_OFF_TIME = "WIFIOFFTIME";
    public static final String DATA_ON_TIME = "DATAONTIME";
    public static final String DATA_OFF_TIME = "DATAOFFTIME";
    public static final String BT_ON_TIME = "BTONTIME";
    public static final String BT_OFF_TIME = "BTOFFTIME";


    public static final String SETTINGS_FILENAME = "CMSettings";

    public  SettingManager (Context context)
    {
        mContext = context;
    }

    public void SetNameValue (String name, int value)
    {
        SharedPreferences AppSettings = mContext.getSharedPreferences(SETTINGS_FILENAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor EditSettings = AppSettings.edit();
        EditSettings.putInt(name, value);
        EditSettings.commit();
    }

    public void SetNameValue(String name, String value)
    {
        SharedPreferences AppSettings = mContext.getSharedPreferences(SETTINGS_FILENAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor EditSettings = AppSettings.edit();
        EditSettings.putString(name, value);
        EditSettings.commit();
    }

    public int GetIntValue(String name)
    {
        int iRetValue;
        SharedPreferences AppSettings = mContext.getSharedPreferences(SETTINGS_FILENAME, Context.MODE_PRIVATE);

        iRetValue = AppSettings.getInt(name,-1);

        return iRetValue;

    }

    public String GetStringValue(String name)
    {
        String sReturn;
        SharedPreferences AppSettings = mContext.getSharedPreferences(SETTINGS_FILENAME, Context.MODE_PRIVATE);
        sReturn = AppSettings.getString(name,"");
        return sReturn;

    }

    public void ClearAll()
    {
        SharedPreferences AppSettings = mContext.getSharedPreferences(SETTINGS_FILENAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor EditSettings = AppSettings.edit();
        EditSettings.clear();
        EditSettings.commit();

    }

    public int GetSyncDropDownValue(int iSelectedItem, String HourMinute)
    {
        int lReturnValue;
        lReturnValue = 60;
        if(HourMinute == MINUTES)
        {
            switch (iSelectedItem) {
                case 0: //30 seconds
                    //ShowMsg(dropdown.getSelectedItem().toString(), "Save");
                    lReturnValue = 30;
                    Toast.makeText(mContext, "Sync Will Run for 30 Seconds!", Toast.LENGTH_LONG).show();
                    break;
                case 1: //1 Min
                    //ShowMsg(dropdown.getSelectedItem().toString(), "Save");
                    lReturnValue = 60;
                    Toast.makeText(mContext, "Sync Will Run for 1 Minute!", Toast.LENGTH_LONG).show();
                    break;
                case 2: //2 Mins
                    //ShowMsg(dropdown.getSelectedItem().toString(), "Save");
                    lReturnValue = 120;
                    Toast.makeText(mContext, "Sync Will Run for 2 Minutes!", Toast.LENGTH_LONG).show();
                    break;
                case 3: //5 Mins
                    //ShowMsg(dropdown.getSelectedItem().toString(), "Save");
                    lReturnValue = 300;
                    Toast.makeText(mContext, "Sync Will Run for 5 Minutes!", Toast.LENGTH_LONG).show();
                    break;
                case 4: //10 Mins
                    //ShowMsg(dropdown.getSelectedItem().toString(), "Save");
                    lReturnValue = 600;
                    Toast.makeText(mContext, "Sync Will Run for 10 Minutes!", Toast.LENGTH_LONG).show();
                    break;
                case 5: //15 mins
                    //ShowMsg(dropdown.getSelectedItem().toString(), "Save");
                    lReturnValue = 900;
                    Toast.makeText(mContext, "Sync Will Run for 15 Minutes!", Toast.LENGTH_LONG).show();
                    break;
                case 6: //20 mins
                    //ShowMsg(dropdown.getSelectedItem().toString(), "Save");
                    lReturnValue = 1200;
                    Toast.makeText(mContext, "Sync Will Run for 20 Minutes!", Toast.LENGTH_LONG).show();
                    break;
                case 7: //30 mins
                    //ShowMsg(dropdown.getSelectedItem().toString(), "Save");
                    lReturnValue = 1800;
                    Toast.makeText(mContext, "Sync Will Run for 30 Minutes!", Toast.LENGTH_LONG).show();
                    break;



            }

        }else if(HourMinute == HOURS)
        {

            //lSyncDuration = 60;  //Default is 1 Minute i.e. 60 Seconds
            switch (iSelectedItem)
            {
                case 0: //30 Mins
                    //ShowMsg(dropdown.getSelectedItem().toString(), "Save");
                    lReturnValue = 30;
                    Toast.makeText(mContext, "Sync Will Run Every 30 Mins!", Toast.LENGTH_LONG).show();
                    break;
                case 1: //1 Hr
                    //ShowMsg(dropdown.getSelectedItem().toString(), "Save");
                    lReturnValue = 60;
                    Toast.makeText(mContext, "Sync Will Run Every 1 Hr!", Toast.LENGTH_LONG).show();
                    break;
                case 2: //2 Hrs
                    //ShowMsg(dropdown.getSelectedItem().toString(), "Save");
                    lReturnValue = 2*60;
                    Toast.makeText(mContext, "Sync Will Run Every 2 Hrs!", Toast.LENGTH_LONG).show();
                    break;
                case 3: //3 Hrs
                    //ShowMsg(dropdown.getSelectedItem().toString(), "Save");
                    lReturnValue = 3*60;
                    Toast.makeText(mContext, "Sync Will Run Every 3 Hrs!", Toast.LENGTH_LONG).show();
                    break;
                case 4: //5 Hrs
                    //ShowMsg(dropdown.getSelectedItem().toString(), "Save");
                    lReturnValue = 5*60;
                    Toast.makeText(mContext, "Sync Will Run Every 5 Hrs!", Toast.LENGTH_LONG).show();
                    break;
                case 5: //10 Hrs
                    //ShowMsg(dropdown.getSelectedItem().toString(), "Save");
                    lReturnValue = 10*60;
                    Toast.makeText(mContext, "Sync Will Run Every 10 Hrs!", Toast.LENGTH_LONG).show();
                    break;
                case 6: //20 mins
                    //ShowMsg(dropdown.getSelectedItem().toString(), "Save");
                    lReturnValue = 15*60;
                    Toast.makeText(mContext, "Sync Will Run Every 15 Hrs!", Toast.LENGTH_LONG).show();
                    break;
                case 7: //24 Hrs
                    //ShowMsg(dropdown.getSelectedItem().toString(), "Save");
                    lReturnValue = 24*60;
                    Toast.makeText(mContext, "Sync Will Run Every 24 Hrs!", Toast.LENGTH_LONG).show();
                    break;

                case 8:
                    //ShowMsg(dropdown.getSelectedItem().toString(), "Save");
                    lReturnValue = 2;
                    Toast.makeText(mContext, "Sync Will Run Every 2 mins!", Toast.LENGTH_LONG).show();
                    break;
            }
        }

        return lReturnValue;
    }


}
