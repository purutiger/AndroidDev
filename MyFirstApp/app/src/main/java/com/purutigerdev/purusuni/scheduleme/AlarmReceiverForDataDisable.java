package com.purutigerdev.purusuni.scheduleme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.widget.Toast;

/**
 * Created by PuruSuni on 03/09/2014.
 */
public class AlarmReceiverForDataDisable extends BroadcastReceiver {

    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {

        mContext = context;
        //ShowMsg("Alarm to Disabled", "Alarm Completed");

        //Set Data off

        int iUserCheckDisableDataOnlyIfWifiIsConnected;

        SettingManager SM = new SettingManager(mContext);
        AlarmScheduler AS = new AlarmScheduler();

        //TODO: Store this value USER_CHECK_DISABLE_DATA_ONLY_IF_WIFI_IS_CONNECTED in Prefs
        iUserCheckDisableDataOnlyIfWifiIsConnected = SM.GetIntValue(SM.USER_CHECK_DISABLE_DATA_ONLY_IF_WIFI_IS_CONNECTED) ;

        //1. Disable Data only if Wifi is Connected
        //2. If Wifi is DisConnected then Rechedule Data Disable check 30 mins from now
        //3. If Wifi is connected then disable data
        //4. If Wifi is connected then set the original alarm back
        if(iUserCheckDisableDataOnlyIfWifiIsConnected == SM.CHECKED)
        {
            if(AS.IsWifiConnected(mContext)) //If Wifi is  Connected then disable the data
            {
                DataConManager dataConManager = new DataConManager(mContext);
                dataConManager.EnableData(false);

                //Set Alarm to Original Data Disable schedule
                int startTime = SM.GetIntValue(SM.DATA_ON_TIME);
                int endTime = SM.GetIntValue(SM.DATA_OFF_TIME);

                AS.SetSchedule(mContext, startTime, endTime, AS.REQUESTOR_DATA, false);

                AS.ShowToast(mContext, "Data Disabled!", true);
            }else   //Otherwise Reschedule ALarm in next 30 mins to see if Data can be Disabled
            {

                //Reschdule this Alarm in next 30 mins

                AS.SetAlarm(mContext, 30*60, AS.RQ_DATADISABLE);
                AS.ShowToast(mContext, "Wifi is DisConnected, So Data was not disabled. Will check again in 30 mins", true);
            }

        }else   //If user hasn't checked 'Disable Data Only if Wifi Connected' then just Disable data
        {
            DataConManager dataConManager = new DataConManager(mContext);
            dataConManager.EnableData(false);
            AS.ShowToast(mContext, "Data Disabled!", true);
        }




        //Toast.makeText(context, "Data Disabled", Toast.LENGTH_LONG).show();

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