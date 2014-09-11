package com.example.purusuni.myfirstapp;

import android.app.AlarmManager;
import android.app.DownloadManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by PuruSuni on 02/09/2014.
 */

public class AlarmScheduler
{
    final int RQ_SYNCENABLE = 1;
    final int RQ_SYNCDISABLE = 2;
    final int RQ_WIFIENABLE = 3;
    final int RQ_WIFIDISABLE = 4;
    final int RQ_DATAENABLE = 5;
    final int RQ_DATADISABLE = 6;
    final int RQ_BTENABLE = 7;
    final int RQ_BTDISABLE = 8;

    final int REQUESTOR_WIFI = 1;
    final int REQUESTOR_DATA = 2;
    final int REQUESTOR_BT = 3;

    public void SetSyncSchedule(Context context, int SyncEveryXSeconds,int SyncForSeconds )
    {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        int syncevery = SyncEveryXSeconds*1000;

        Calendar cal = Calendar.getInstance();

        //SYNC SCHEDULE ENABLE
        // create an Intent and set the class which will execute when Alarm triggers, here we have
        Intent intentAlarmSyncEnable = new Intent(context, AlarmReceiverToEnableSync.class);

        // create the Alarm Manager for SncEnable


        //Request Code 1 is used for Schedule Sync Enable Alarm. Other Alarms will use their own Request Code. So at any point in time we will have
        //only one alarm per request i.e.
        // Sync on for, Schedule sync. Schedule wifi, Schedule Data etc.
        PendingIntent displayIntentSyncEnable = PendingIntent.getBroadcast(context, RQ_SYNCENABLE, intentAlarmSyncEnable, PendingIntent.FLAG_UPDATE_CURRENT);

        //set the alarm for particular time
        Long timeToEnableSync = cal.getTimeInMillis()+1000; //1 second from now

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeToEnableSync, syncevery, displayIntentSyncEnable);


        //TO DISABLE SCHEDULED SYNC
        // create an Intent and set the class which will execute when Alarm triggers, here we have
        Intent intentAlarmSyncDisable = new Intent(context, AlarmReceiverToDisableSync.class);


        //Request Code 2 is used for Schedule Sync Disable Alarm. Other Alarms will use their own Request Code. So at any point in time we will have
        //only one alarm per request i.e.
        // Sync on for, Schedule sync. Schedule wifi, Schedule Data etc.
        PendingIntent displayIntentSyncDisable = PendingIntent.getBroadcast(context, RQ_SYNCDISABLE, intentAlarmSyncDisable, PendingIntent.FLAG_UPDATE_CURRENT);

        //set the alarm for particular time
        Long timeToDisableSync = timeToEnableSync + SyncForSeconds*1000;

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeToDisableSync, syncevery + SyncForSeconds*1000, displayIntentSyncDisable);

    }

    public void SetWifiSchedule(Context context, int startTime, int endTime)
    {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        int syncevery = 24*60*60*1000;

        Calendar WifiEnableTime= Calendar.getInstance();
        WifiEnableTime.set(Calendar.HOUR_OF_DAY, startTime);
        WifiEnableTime.set(Calendar.MINUTE, 0);
        WifiEnableTime.set(Calendar.SECOND, 0);
        //SYNC SCHEDULE ENABLE
        // create an Intent and set the class which will execute when Alarm triggers, here we have
        Intent intentWifiAlarmEnable = new Intent(context, AlarmReceiverForWiFiEnable.class);

        // create the Alarm Manager for SncEnable


        //Request Code 3 is used for Schedule Wifi Enable Alarm. Other Alarms will use their own Request Code. So at any point in time we will have
        //only one alarm per request i.e.
        // Sync on for, Schedule sync. Schedule wifi, Schedule Data etc.
        PendingIntent displayIntentWifiEnable = PendingIntent.getBroadcast(context, RQ_WIFIENABLE, intentWifiAlarmEnable, PendingIntent.FLAG_UPDATE_CURRENT);

        //set the alarm for particular time
        //Long timeToEnableSync = cal.getTimeInMillis()+1000; //1 second from now

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, WifiEnableTime.getTimeInMillis(), syncevery, displayIntentWifiEnable);


        //TO DISABLE SCHEDULED SYNC

        Calendar WifiDisableTime= Calendar.getInstance();
        WifiDisableTime.set(Calendar.HOUR_OF_DAY, endTime);
        WifiDisableTime.set(Calendar.MINUTE, 0);
        WifiDisableTime.set(Calendar.SECOND, 0);
        // create an Intent and set the class which will execute when Alarm triggers, here we have
        Intent intentAlarmWifiDisable = new Intent(context, AlarmReceiverForWiFiDisable.class);


        //Request Code 4 is used for Schedule Wifi Disable Alarm. Other Alarms will use their own Request Code. So at any point in time we will have
        //only one alarm per request code i.e.
        // Sync on for, Schedule sync. Schedule wifi, Schedule Data etc.
        PendingIntent displayIntentWifiDisable = PendingIntent.getBroadcast(context, RQ_WIFIDISABLE, intentAlarmWifiDisable, PendingIntent.FLAG_UPDATE_CURRENT);

        //set the alarm for particular time
        //Long timeToDisableSync = timeToEnableSync + SyncForSeconds*1000;

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, WifiDisableTime.getTimeInMillis(), syncevery, displayIntentWifiDisable);
    }


    public void SetSchedule(Context context, int startTime, int endTime, int iRequestor)
    {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        final int INTERVAL_DAY = 24*60*60*1000;

        Calendar ScheduleEnableTime = Calendar.getInstance();
        ScheduleEnableTime.set(Calendar.HOUR_OF_DAY, startTime);
        ScheduleEnableTime.set(Calendar.MINUTE, 0);
        ScheduleEnableTime.set(Calendar.SECOND, 0);


        Calendar ScheduleDisableTime= Calendar.getInstance();
        ScheduleDisableTime.set(Calendar.HOUR_OF_DAY, endTime);
        ScheduleDisableTime.set(Calendar.MINUTE, 0);
        ScheduleDisableTime.set(Calendar.SECOND, 0);

        //SYNC SCHEDULE ENABLE
        // create an Intent and set the class which will execute when Alarm triggers, here we have

        Intent intentAlarmEnable = null;
        Intent intentAlarmDisable = null;
        int RequestIDEnable = 0;
        int RequestIDDisable = 0;
        String sToast = "";

        switch (iRequestor)
        {
            case REQUESTOR_WIFI:
                intentAlarmEnable = new Intent(context, AlarmReceiverForWiFiEnable.class);
                intentAlarmDisable = new Intent(context, AlarmReceiverForWiFiDisable.class);
                RequestIDEnable = RQ_WIFIENABLE;
                RequestIDDisable = RQ_WIFIDISABLE;
                sToast = "Wifi will be Enabled every day at " + startTime + ":00 hrs and Disabled at " + endTime + ":00 hrs.";
                break;

            case REQUESTOR_DATA:

                intentAlarmEnable = new Intent(context, AlarmReceiverForDataEnable.class);
                intentAlarmDisable = new Intent(context, AlarmReceiverForDataDisable.class);

                RequestIDEnable = RQ_DATAENABLE;
                RequestIDDisable = RQ_DATADISABLE;

                sToast = "Data will be Enabled every day at " + startTime + ":00 hrs and Disabled at " + endTime + ":00 hrs.";

                break;

            case REQUESTOR_BT:


                intentAlarmEnable = new Intent(context, AlarmReceiverForBTEnable.class);
                intentAlarmDisable = new Intent(context, AlarmReceiverForBTDisable.class);

                RequestIDEnable = RQ_BTENABLE;
                RequestIDDisable = RQ_BTDISABLE;

                sToast = "Bluetooth will be Enabled every day at " + startTime + ":00 hrs and Disabled at " + endTime + ":00 hrs.";

                break;
        }


        PendingIntent displayIntentEnable = PendingIntent.getBroadcast(context, RequestIDEnable, intentAlarmEnable, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent displayIntentDisable = PendingIntent.getBroadcast(context, RequestIDDisable, intentAlarmDisable, PendingIntent.FLAG_UPDATE_CURRENT);

        Long lEnableTimeMillis = ScheduleEnableTime.getTimeInMillis();
        Long lDisableTimeMillis = ScheduleDisableTime.getTimeInMillis();

        //If current time is greater than the Alarm being set, then set the alarm for next day
        if(lEnableTimeMillis < System.currentTimeMillis())
        {
            lEnableTimeMillis = lEnableTimeMillis + INTERVAL_DAY ;
        }

        if(lDisableTimeMillis  < System.currentTimeMillis())
        {
            lDisableTimeMillis  = lDisableTimeMillis + INTERVAL_DAY ;
        }
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, lEnableTimeMillis, AlarmManager.INTERVAL_DAY, displayIntentEnable);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, lDisableTimeMillis, AlarmManager.INTERVAL_DAY, displayIntentDisable);


        //Toast.makeText(context, "Hour of the day is : " + Calendar.HOUR_OF_DAY, Toast.LENGTH_LONG).show();
        //Toast.makeText(context, "Hour of the day is HOUR : " + Calendar.HOUR, Toast.LENGTH_LONG).show();
        //Toast.makeText(context, "Hour of the day is TIME E: " + ScheduleEnableTime.getTime(), Toast.LENGTH_LONG).show();
        //Toast.makeText(context, "Hour of the day is TIME D: " + ScheduleDisableTime.getTime(), Toast.LENGTH_LONG).show();

        Toast.makeText(context, sToast, Toast.LENGTH_LONG).show();
    }

    public void CancelAllSchedules(Context context)
    {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent SyncEnableAlarmIntent = new Intent (context, AlarmReceiverToEnableSync.class);
        Intent SyncDisableAlarmIntent = new Intent (context, AlarmReceiverToDisableSync.class);

        PendingIntent SyncEnablePI = PendingIntent.getBroadcast(context, RQ_SYNCENABLE, SyncEnableAlarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent SyncDisablePI = PendingIntent.getBroadcast(context, RQ_SYNCDISABLE, SyncDisableAlarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent WifiStartIntent = new Intent(context, AlarmReceiverForWiFiEnable.class);
        Intent WifiStopIntent = new Intent(context, AlarmReceiverForWiFiDisable.class);

        PendingIntent WifiEnablePI = PendingIntent.getBroadcast(context, RQ_WIFIENABLE, WifiStartIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent WifiDisablePI = PendingIntent.getBroadcast(context, RQ_WIFIDISABLE, WifiStopIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent DataStartIntent = new Intent(context, AlarmReceiverForDataEnable.class);
        Intent DataStopIntent = new Intent(context, AlarmReceiverForDataDisable.class);

        PendingIntent DataEnablePI = PendingIntent.getBroadcast(context, RQ_DATAENABLE, DataStartIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent DataDisablePI = PendingIntent.getBroadcast(context, RQ_DATADISABLE, DataStopIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        Intent BTStartIntent = new Intent(context, AlarmReceiverForBTEnable.class);
        Intent BTStopIntent = new Intent(context, AlarmReceiverForBTDisable.class);

        PendingIntent BTEnablePI = PendingIntent.getBroadcast(context, RQ_BTENABLE, BTStartIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent BTDisablePI = PendingIntent.getBroadcast(context, RQ_BTDISABLE, BTStopIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        alarmManager.cancel(SyncEnablePI);
        alarmManager.cancel(SyncDisablePI);

        alarmManager.cancel(WifiEnablePI);
        alarmManager.cancel(WifiDisablePI);

        alarmManager.cancel(DataEnablePI);
        alarmManager.cancel(DataDisablePI);

        alarmManager.cancel(BTEnablePI);
        alarmManager.cancel(BTDisablePI);

    }
}
