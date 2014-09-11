package com.purutigerdev.purusuni.scheduleme;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by PuruSuni on 01/09/2014.
 */
public class AlarmReceiverToEnableSync extends BroadcastReceiver {

    private Context mContext;
    @Override
    public void onReceive(Context context, Intent intent)
    {
        //
        mContext = context;
        //ShowMsg("Alarm to Disabled", "Alarm Completed");
        SettingManager SM = new SettingManager(mContext);
        AlarmScheduler AS = new AlarmScheduler();

        int iSyncOnWifiOnly = SM.GetIntValue(SM.USER_CHECK_SYNC_ONLY_ON_WIFI);

        if(iSyncOnWifiOnly > 0)
        {
            if (AS.IsWifiConnected(mContext))
            {
                ContentResolver.setMasterSyncAutomatically(true);

                AS.ShowToast(context, "Sync Enabled!", true);
            }else
            {
                AS.ShowToast(context, "Sync Was Not Enabled because you are Not on WiFI connection!", true);
            }
        }else
        {
            ContentResolver.setMasterSyncAutomatically(true);

            AS.ShowToast(context, "Sync Enabled!", true);

        }



        // here you can start an activity or service depending on your need
        // for ex you can start an activity to vibrate phone or to ring the phone
        /*
        String phoneNumberReciver="9718202185";// phone number to which SMS to be send
        String message="Hi I will be there later, See You soon";// message to send
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumberReciver, null, message, null, null);
        // Show the toast  like in above screen shot
        */


    }

    private void ShowMsg(String  msg1, String msg2) {

        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(mContext);

        dlgAlert.setMessage( msg1 + ": " + msg2);
        dlgAlert.setTitle("Toggler");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();

    }
}
