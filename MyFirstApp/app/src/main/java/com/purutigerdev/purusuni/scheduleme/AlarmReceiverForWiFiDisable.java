package com.purutigerdev.purusuni.scheduleme;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.widget.Toast;

/**
 * Created by PuruSuni on 03/09/2014.
 */
public class AlarmReceiverForWiFiDisable extends BroadcastReceiver {

    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {

        mContext = context;


        int iUserCheckDisableWifiOnlyIfWifiIsDisConnected;

        SettingManager SM = new SettingManager(mContext);
        AlarmScheduler AS = new AlarmScheduler();

        //AS.ShowToast(context, "Here here here!!", true);


        //Retrieve stored USER_CHECK_DISABLE_WIFI_ONLY_IF_WIFI_IS_DISCONNECTED in Prefs
        iUserCheckDisableWifiOnlyIfWifiIsDisConnected = SM.GetIntValue(SM.USER_CHECK_DISABLE_WIFI_ONLY_IF_WIFI_IS_DISCONNECTED) ;

        //1. Disable Wifi only if Wifi is DISConnected
        //2. If Wifi is Connected then Rechedule Wifi Disable check in 30 mins from now
        //3. If Wifi is Disconnected then disable wifi as per schedule
        //4. If Wifi is Disconnected then set the original alarm back
        //AS.ShowToast(context, "Here here here!! ------- " + iUserCheckDisableWifiOnlyIfWifiIsDisConnected, true);
        if(iUserCheckDisableWifiOnlyIfWifiIsDisConnected == SM.CHECKED)
        {
            //AS.ShowToast(context, "Here here here!! ------- " + iUserCheckDisableWifiOnlyIfWifiIsDisConnected, true);
            if(!AS.IsWifiConnected(mContext)) //If Wifi is  DisConnected then disable the Wifi as per schedule
            {
                WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
                wifiManager.setWifiEnabled(false);
                AS.ShowToast(mContext, "Wifi Disabled!", true);
                //Set Alarm to Original Alarm Enable schedule
                int startTime = SM.GetIntValue(SM.WIFI_ON_TIME);
                int endTime = SM.GetIntValue(SM.WIFI_OFF_TIME);

                AS.SetSchedule(mContext, startTime, endTime, AS.REQUESTOR_WIFI, false);


            }else   //Otherwise Reschedule ALarm in next 30 mins to see if Wifi can be Disabled
            {

                //AS.ShowToast(context, "Here here here!! ------- Rescheduling - - - " + iUserCheckDisableWifiOnlyIfWifiIsDisConnected, true);
                //Reschdule this Alarm in next 30 mins

                AS.SetAlarm(mContext, 30*60, AS.RQ_WIFIDISABLE);

                AS.ShowToast(mContext, "Wifi is Connected, so will not be disabled. Will Check in 30 mins!", true);
            }

        }else   //If user hasn't checked 'Disable Data Only if Wifi Connected' then just Disable data
        {
            WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
            wifiManager.setWifiEnabled(false);
            AS.ShowToast(mContext, "Wifi Disabled!", true);

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
}