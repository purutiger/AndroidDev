package com.purutigerdev.purusuni.scheduleme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by PuruSuni on 03/09/2014.
 */
public class AlarmReceiverForDataEnable extends BroadcastReceiver {

    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {

        mContext = context;
        //ShowMsg("Alarm to Disabled", "Alarm Completed");


        int iUserCheckEnableDataOnlyIfWifiIsDisConnected;

        SettingManager SM = new SettingManager(mContext);
        AlarmScheduler AS = new AlarmScheduler();

        //TODO: Store this value USER_CHECK_ENABLE_DATA_ONLY_IF_WIFI_IS_DISCONNECTED in Prefs
        iUserCheckEnableDataOnlyIfWifiIsDisConnected = SM.GetIntValue(SM.USER_CHECK_ENABLE_DATA_ONLY_IF_WIFI_IS_DISCONNECTED) ;

       //1. Enable Data only if Wifi is NOT Connected
        //2. If Wifi is Connected then Rechedule Data Enable check 30 mins from now
        //3. If Wifi is disconnected then enable data
        //4. If Wifi is connected then set the original alarm back
        if(iUserCheckEnableDataOnlyIfWifiIsDisConnected == SM.CHECKED)
        {
            if(!AS.IsWifiConnected(mContext)) //If Wifi disconnected then Enable data
            {
                DataConManager dataConManager = new DataConManager(mContext);
                dataConManager.EnableData(true);

                //Set Alarm to Original Data Enable schedule
                int startTime = SM.GetIntValue(SM.DATA_ON_TIME);
                int endTime = SM.GetIntValue(SM.DATA_OFF_TIME);

                AS.SetSchedule(mContext, startTime, endTime, AS.REQUESTOR_DATA, false);

                AS.ShowToast(mContext, "Data Enabled!", true);

            }else   //Otherwise Rechedule to check if data can be enabled in next 10 mins
            {
                //Reschdule this Alarm in next 30 mins

                AS.SetAlarm(mContext, 30*60, AS.RQ_DATAENABLE);
                AS.ShowToast(mContext, "Wifi is Connected, So Data was not enabled. Will check again in 30 mins", true);

            }

        }else   //If user hasn't checked 'Enable Data Only if Wifi Disconnected' then just enable data
        {
            DataConManager dataConManager = new DataConManager(mContext);
            dataConManager.EnableData(true);
            AS.ShowToast(mContext, "Data Enabled!", true);
        }




        //Toast.makeText(context, "Data Enabled", Toast.LENGTH_LONG).show();

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