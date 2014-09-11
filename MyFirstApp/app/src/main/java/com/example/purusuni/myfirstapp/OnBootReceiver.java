package com.example.purusuni.myfirstapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by PuruSuni on 02/09/2014.
 */
public class OnBootReceiver extends BroadcastReceiver
{
    //This is needed because all alarms are destroyed on device reboot
            @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {


                /*************FOR SYNC SCHEDULE **************/

                int iSelectedItem;
                int iSyncInterval;
                int iSyncDuration;


                SettingManager SM = new SettingManager(context);
                AlarmScheduler AS = new AlarmScheduler();

                //Read Persistent Sync Schedule Settings

                iSelectedItem = SM.GetIntValue(SM.SYNC_INTERVAL);
                if (iSelectedItem > -1)
                {
                    iSyncInterval = SM.GetSyncDropDownValue(iSelectedItem, SM.HOURS)*60;

                    iSelectedItem = SM.GetIntValue(SM.SYNC_DURATION);

                    if (iSelectedItem > -1) {
                        iSyncDuration = SM.GetSyncDropDownValue(iSelectedItem, SM.MINUTES);

                        // Set the alarms again.
                        AS.SetSyncSchedule(context, iSyncInterval, iSyncDuration);
                    }
                }


                /*************FOR Wifi SCHEDULE **************/
                int iWifiStartTime = SM.GetIntValue(SM.WIFI_ON_TIME);
                if (iWifiStartTime > -1)
                {
                    int iWifiStopTime = SM.GetIntValue(SM.WIFI_OFF_TIME);
                    AS.SetWifiSchedule(context, iWifiStartTime, iWifiStopTime);
                }

                /*************FOR BLUETOOTH SCHEDULE **************/
                int iBTStartTime = SM.GetIntValue(SM.BT_ON_TIME);
                if (iBTStartTime > -1)
                {
                    int iBTStopTime = SM.GetIntValue(SM.BT_OFF_TIME);
                    AS.SetWifiSchedule(context, iBTStartTime, iBTStopTime);
                }



                /************* FOR DATA SCHEDULE **************/

                int iDataStartTime = SM.GetIntValue(SM.DATA_ON_TIME);
                if (iDataStartTime > -1)
                {
                    int iDataStopTime = SM.GetIntValue(SM.DATA_OFF_TIME);
                    AS.SetWifiSchedule(context, iDataStartTime, iDataStopTime);
                }

            }
        }

}
