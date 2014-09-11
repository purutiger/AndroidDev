package com.example.purusuni.myfirstapp;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.audiofx.BassBoost;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.SyncStateContract;
import android.util.Config;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import static java.lang.Class.forName;

//How to Turn off, Turn on wifi in android using code |


/*
MyActivity Main Class - Extends Activity for enablig the view and also
Implements OnClickListener Interface to catch OnClocik of all the buttons
 */
public class MyActivity extends Activity implements View.OnClickListener {

    private Switch toggleWiFi;
    private Switch toggleData;
    private Switch toggleBT;
    private Switch toggleNFC;
    private Switch toggleSync;
    private Button btnSaveSync1;
    private Spinner ddSync1;
    private Spinner ddSyncHr;
    private Spinner ddSyncMin;
    private Button btnSaveSync2;
    private Button btnCancelAll;
    private Spinner ddWifiOnTime;
    private Spinner ddWifiOffTime;
    private Button btnSaveWifi;
    private Spinner ddDataOnTime;
    private Spinner ddDataOffTime;
    private Button btnSaveData;
    private Spinner ddBTOnTime;
    private Spinner ddBTOffTime;
    private Button btnSaveBT;

    //private Button btnCaButton
    private SettingManager mSettingManager = new SettingManager(this);

    //OnRestart is needed to refresh the UI if the user presses the homebutton and comes back
    @Override protected void onRestart()
    {
        super.onRestart();

        InitializeControls();
        SetStates();


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        InitializeControls();


        SetStates();
        ToggleWifi();
        ToggleData();
        ToggleBT();
        ToggleNFC();
        ToggleSync();

      }

    private void InitializeControls()
    {

        String[] items;
        ArrayAdapter<String> adapter;

        btnSaveSync1 = (Button) findViewById(R.id.btnSave1);
        btnSaveSync1.setOnClickListener(this);

        btnSaveSync2 = (Button) findViewById(R.id.btnSave2);
        btnSaveSync2.setOnClickListener(this);

        btnSaveWifi = (Button) findViewById(R.id.btnSaveWifi);
        btnSaveWifi.setOnClickListener(this);

        btnSaveData = (Button) findViewById(R.id.btnSaveData);
        btnSaveData.setOnClickListener(this);

        btnSaveBT = (Button) findViewById(R.id.btnSaveBT);
        btnSaveBT.setOnClickListener(this);

        btnCancelAll = (Button) findViewById(R.id.btnCancelAllSchedules);
        btnCancelAll.setOnClickListener(this);

        //Log.i(DISPLAY_SERVICE, "Activity is created");

        ddSync1 = (Spinner)findViewById(R.id.spnMinutes);
        items = new String[]{"0.5 mins", "1 min", "2 mins", "5 mins", "10 min (recommended)", "15 mins", "20 mins", "30 mins"};
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        ddSync1.setAdapter(adapter);

        ddSyncMin = (Spinner)findViewById(R.id.spnMinutes2);
        items = new String[]{"0.5 mins", "1 min", "2 mins", "5 mins", "10 mins (recommended)", "15 mins", "20 mins", "30 mins"};
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        ddSyncMin.setAdapter(adapter);

        ddSyncHr = (Spinner)findViewById(R.id.spnSyncHrs);
        items = new String[]{"30 mins", "1 Hr", "2 Hrs", "3 Hrs", "5 Hrs (recommended)", "10 Hrs", "15 Hrs", "1 Day", "2 min (for testing)"};
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        ddSyncHr.setAdapter(adapter);

        ddWifiOnTime= (Spinner)findViewById(R.id.spnWifiOnTime);
        items = new String[]{"00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        ddWifiOnTime.setAdapter(adapter);

        ddWifiOffTime= (Spinner)findViewById(R.id.spnWifiOffTime);
        items = new String[]{"00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        ddWifiOffTime.setAdapter(adapter);

        ddDataOnTime= (Spinner)findViewById(R.id.spnDataOnTime);
        items = new String[]{"00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        ddDataOnTime.setAdapter(adapter);

        ddDataOffTime = (Spinner)findViewById(R.id.spnDataOffTime);
        items = new String[]{"00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        ddDataOffTime.setAdapter(adapter);

        ddBTOnTime= (Spinner)findViewById(R.id.spnBTOnTime);
        items = new String[]{"00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        ddBTOnTime.setAdapter(adapter);

        ddBTOffTime = (Spinner)findViewById(R.id.spnBTOffTime);
        items = new String[]{"00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        ddBTOffTime.setAdapter(adapter);

        /*READ Persistent Sync Setting*/
        int iSelectedItem = mSettingManager.GetIntValue(mSettingManager.SYNC_DURATION);
        Boolean bSyncScheduled = false;
        if(iSelectedItem > -1)
        {
            ddSyncMin.setSelection(iSelectedItem);
            bSyncScheduled = true;
        }

        iSelectedItem = mSettingManager.GetIntValue(mSettingManager.SYNC_INTERVAL);
        if(iSelectedItem > -1)
        {
            ddSyncHr.setSelection(iSelectedItem);
            bSyncScheduled = true;
        }
        if(bSyncScheduled)
        {
            TextView tv = (TextView) findViewById(R.id.txtSyncView);
            tv.setTextColor(Color.GREEN);
            tv.setText("Schedule Sync - ON");
        }else
        {

            TextView tv = (TextView) findViewById(R.id.txtSyncView);
            tv.setTextColor(Color.BLACK);
            tv.setText("Schedule Sync - OFF");
         }

        /*READ Persistent Wifi Setting*/
        iSelectedItem = mSettingManager.GetIntValue(mSettingManager.WIFI_ON_TIME);
        Boolean bWifiScheduled = false;
        if(iSelectedItem > -1)
        {
            ddWifiOnTime.setSelection(iSelectedItem);
            bWifiScheduled = true;
        }

        iSelectedItem = mSettingManager.GetIntValue(mSettingManager.WIFI_OFF_TIME);
        if(iSelectedItem > -1)
        {
            ddWifiOffTime.setSelection(iSelectedItem);
            bWifiScheduled = true;
        }
        if(bWifiScheduled)
        {
            TextView tv = (TextView) findViewById(R.id.txtWifiView);
            tv.setTextColor(Color.GREEN);
            tv.setText("Schedule Wifi - ON");
        }else
        {

            TextView tv = (TextView) findViewById(R.id.txtWifiView);
            tv.setTextColor(Color.BLACK);
            tv.setText("Schedule Wifi - OFF");
        }

        /*READ FOR DATA*/

        iSelectedItem = mSettingManager.GetIntValue(mSettingManager.DATA_ON_TIME);
        Boolean bDataScheduled = false;
        if(iSelectedItem > -1)
        {
            ddDataOnTime.setSelection(iSelectedItem);
            bDataScheduled = true;
        }

        iSelectedItem = mSettingManager.GetIntValue(mSettingManager.DATA_OFF_TIME);
        if(iSelectedItem > -1)
        {
            ddDataOffTime.setSelection(iSelectedItem);
            bDataScheduled = true;
        }
        if(bDataScheduled)
        {
            TextView tv = (TextView) findViewById(R.id.txtDataView);
            tv.setTextColor(Color.GREEN);
            tv.setText("Schedule Data - ON");
        }else
        {

            TextView tv = (TextView) findViewById(R.id.txtDataView);
            tv.setTextColor(Color.BLACK);
            tv.setText("Schedule Data - OFF");
        }

         /*READ FOR BT*/

        iSelectedItem = mSettingManager.GetIntValue(mSettingManager.BT_ON_TIME);
        Boolean bBTScheduled  = false;
        if(iSelectedItem > -1)
        {
            ddBTOnTime.setSelection(iSelectedItem);
            bBTScheduled = true;
        }

        iSelectedItem = mSettingManager.GetIntValue(mSettingManager.BT_OFF_TIME);
        if(iSelectedItem > -1)
        {
            ddBTOffTime.setSelection(iSelectedItem);
            bBTScheduled  = true;
        }
        if(bBTScheduled )
        {
            TextView tv = (TextView) findViewById(R.id.txtBTView);
            tv.setTextColor(Color.GREEN);
            tv.setText("Schedule Bluetooth- ON");
        }else
        {

            TextView tv = (TextView) findViewById(R.id.txtBTView);
            tv.setTextColor(Color.BLACK);
            tv.setText("Schedule Bluetooth  - OFF");
        }



        /*Code to wakeup app so that we can read persistent settings in the OnBootReceiver Class*/

        ComponentName receiver = new ComponentName(this, OnBootReceiver.class);
        PackageManager pm = getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

    }


    //Call this when user clicks on the Cancel All Schedule Button
    private void CancelOnBootAndAlarms()
    {
        /* Cancel Schedules (Alarms)*/
        AlarmScheduler AS = new AlarmScheduler();

        AS.CancelAllSchedules(this);

        Toast.makeText(getApplicationContext(), "All Schedules Cancelled!", Toast.LENGTH_LONG).show();

        /*Empty the Persistent Store*/
        mSettingManager.ClearAll();
        InitializeControls();

        /*Close OnBoot Listener*/

        ComponentName receiver = new ComponentName(this, OnBootReceiver.class);
        PackageManager pm = getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }
    @Override
    public void onClick(View v) {
        final int id = v.getId();
        int lSelectedItem;
        int lSyncDurationSeconds;
        int lSyncEveryXSeconds;
        AlarmScheduler AS = new AlarmScheduler();

        switch (id)
        {
            case R.id.btnSave1:
                // your code for button1 here
               // ShowMsg("We are Here On Botton click", "Save");

                lSelectedItem = (int)ddSync1.getSelectedItemId();
                lSyncDurationSeconds = mSettingManager.GetSyncDropDownValue(lSelectedItem,mSettingManager.MINUTES);  //Default is 1 Minute i.e. 60 Seconds

                SetSync(v, lSyncDurationSeconds);
                break;

            case R.id.btnSave2:
                // your code for button2 here

                lSelectedItem = (int)ddSyncHr.getSelectedItemId();
                lSyncEveryXSeconds = mSettingManager.GetSyncDropDownValue(lSelectedItem,mSettingManager.HOURS)*60; //default is 1 hr
                //Save the Selected Item for later Retrieval
                mSettingManager.SetNameValue(mSettingManager.SYNC_INTERVAL, lSelectedItem);

                lSelectedItem = (int)ddSyncMin.getSelectedItemId();
                lSyncDurationSeconds = mSettingManager.GetSyncDropDownValue(lSelectedItem,mSettingManager.MINUTES);    //Default is 1 Minute i.e. 60 Seconds
                mSettingManager.SetNameValue(mSettingManager.SYNC_DURATION, lSelectedItem);


                AS.SetSyncSchedule(this, lSyncEveryXSeconds, lSyncDurationSeconds);

                //Set the text t indicate that schedule is on
                TextView tv = (TextView) findViewById(R.id.txtSyncView);
                tv.setText("Schedule Sync - ON");
                tv.setTextColor(Color.GREEN);

                break;

            case R.id.btnSaveWifi:


                int iWifiStartTime = (int)ddWifiOnTime.getSelectedItemId();
                int iWifiStopTime = (int)ddWifiOffTime.getSelectedItemId();

                //Save the Selected Item for later Retrieval
                mSettingManager.SetNameValue(mSettingManager.WIFI_ON_TIME, iWifiStartTime);

                mSettingManager.SetNameValue(mSettingManager.WIFI_OFF_TIME, iWifiStopTime);

                AS.SetSchedule(this, iWifiStartTime, iWifiStopTime, AS.REQUESTOR_WIFI);


                //Set the text t indicate that schedule is on
                tv = (TextView) findViewById(R.id.txtWifiView);
                tv.setText("Schedule Wifi - ON");
                tv.setTextColor(Color.GREEN);

                break;

            case R.id.btnSaveData:

                int iDataStartTime = (int)ddDataOnTime.getSelectedItemId();
                int iDataStopTime = (int)ddDataOffTime.getSelectedItemId();

                //Save the Selected Item for later Retrieval
                mSettingManager.SetNameValue(mSettingManager.DATA_ON_TIME, iDataStartTime);

                mSettingManager.SetNameValue(mSettingManager.DATA_OFF_TIME, iDataStopTime);

                AS.SetSchedule(this, iDataStartTime, iDataStopTime, AS.REQUESTOR_DATA);


                //Set the text t indicate that schedule is on
                tv = (TextView) findViewById(R.id.txtDataView);
                tv.setText("Schedule Data - ON");
                tv.setTextColor(Color.GREEN);

                break;

            case R.id.btnSaveBT:

                int iBTStartTime = (int)ddBTOnTime.getSelectedItemId();
                int iBTStopTime = (int)ddBTOffTime.getSelectedItemId();

                //Save the Selected Item for later Retrieval
                mSettingManager.SetNameValue(mSettingManager.BT_ON_TIME, iBTStartTime);

                mSettingManager.SetNameValue(mSettingManager.BT_OFF_TIME, iBTStopTime);

                AS.SetSchedule(this, iBTStartTime, iBTStopTime, AS.REQUESTOR_BT);


                //Set the text t indicate that schedule is on
                tv = (TextView) findViewById(R.id.txtBTView);
                tv.setText("Schedule Bluetooth - ON");
                tv.setTextColor(Color.GREEN);

                break;

            case R.id.btnCancelAllSchedules:

                CancelOnBootAndAlarms();

                break;
            // even more buttons here


        }
    }



    public void SetSync(View V, int durationInSeconds)
    {

        // time at which alarm will be scheduled here alarm is scheduled at 1 day from current time,
        // we fetch  the current time in milliseconds and added 1 day time
        // i.e. 24*60*60*1000= 86,400,000   milliseconds in a day
        Calendar cal = Calendar.getInstance();
        Long time = cal.getTimeInMillis() + durationInSeconds*1000;

        // create an Intent and set the class which will execute when Alarm triggers, here we have
        // given AlarmReciever in the Intent, the onRecieve() method of this class will execute when
        // alarm triggers and
        //we will write the code to send SMS inside onRecieve() method pf Alarmreciever class
        Intent intentAlarm = new Intent(this, AlarmReceiverToDisableSync.class);
        //intentAlarm.setData(Uri.parse("custom://" + alarm.ID));
        //intentAlarm.setAction(String.valueOf(alarm.ID));
        // create the object
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //Request Code 0 is used for SetSync. Other Alarms will use their own Request Code. So at any point in time we will have
        //only one alarm per request i.e.
        // Sync on for, Schedule sync. Schedule wifi, Schedule Data etc.
        PendingIntent displayIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT);

        //set the alarm for particular time
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, displayIntent);
        //Toast.makeText(this, "Alarm Scheduled for Tommrrow", Toast.LENGTH_LONG).show();

    }

   public void ShowMsg(String  msg1, String msg2) {

        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);

        dlgAlert.setMessage( msg1 + ": " + msg2);
        dlgAlert.setTitle("Toggler");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();

   }


   private void ToggleWifi()
   {
       toggleWiFi = (Switch) findViewById(R.id.swtWiFiState);

       WifiManager wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
       if(wifiManager.isWifiEnabled())
       {
           toggleWiFi.setChecked(true);
           toggleWiFi.setText("Wifi is ON");
       }else
       {
           toggleWiFi.setChecked(false);
           toggleWiFi.setText("Wifi is OFF");
       }

       toggleWiFi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if (isChecked) {
                   ToggleWifi(true);
                   Toast.makeText(getApplicationContext(), "Wi-Fi Enabled!", Toast.LENGTH_LONG).show();
                   toggleWiFi.setText("Wifi is ON");
               } else {
                   ToggleWifi(false);
                   Toast.makeText(getApplicationContext(), "Wi-Fi Disabled!", Toast.LENGTH_LONG).show();
                   toggleWiFi.setText("Wifi is OFF");
               }
           }
       });
   }

    private void ToggleSync()
    {
        toggleSync = (Switch) findViewById(R.id.swtSync);

        boolean bSyncState;
        try{
            bSyncState = ContentResolver.getMasterSyncAutomatically();
            //ShowMsg("Sync State Read - " , "");
        }catch (Exception e){
            //return;
            bSyncState = false;
            //ShowMsg("Sync", "Disabled due to Error");
        }

        if(bSyncState)
        {
            ddSync1.setEnabled(true);
            btnSaveSync1.setEnabled(true);
            toggleSync.setChecked(true);
            toggleSync.setText("Sync is ON");
            //ShowMsg("Sync", "Enabled Normally");
        }else
        {
            ddSync1.setEnabled(false);
            btnSaveSync1.setEnabled(false);
            toggleSync.setChecked(false);
            toggleSync.setText("Sync is OFF");
            //ShowMsg("Sync", "Disabled Normally");
        }


        toggleSync.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    try {
                        ContentResolver.setMasterSyncAutomatically(true);
                        ddSync1.setEnabled(true);
                        btnSaveSync1.setEnabled(true);
                        Toast.makeText(getApplicationContext(), "Sync Enabled! Use the dropdown to choose the duration of the sync", Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), "Be aware that longer duration you choose higher will be battery drain", Toast.LENGTH_LONG).show();
                        toggleSync.setText("Sync is ON");
                        //ShowMsg("Sync", "Enabled Normally");
                    } catch (Exception e) {
                        //return;
                        //toggleSync.setEnabled(false);
                        //Toast.makeText(getApplicationContext(), "Sync Disabled!", Toast.LENGTH_LONG).show();
                        //toggleSync.setText("Sync is OFF");
                    }

                } else {
                    try {
                        ContentResolver.setMasterSyncAutomatically(false);
                        ddSync1.setEnabled(false);
                        btnSaveSync1.setEnabled(false);
                        Toast.makeText(getApplicationContext(), "Sync Disabled!", Toast.LENGTH_LONG).show();
                        toggleSync.setText("Sync is OFF");
                    } catch (Exception e) {
                        //return;
                        //toggleSync.setEnabled(false);
                        //Toast.makeText(getApplicationContext(), "Sync Disabled!", Toast.LENGTH_LONG).show();
                        //toggleSync.setText("Sync is OFF");
                    }
                }
            }
        });
    }

    private void SetStates()
    {
        //Wifi
        toggleWiFi = (Switch) findViewById(R.id.swtWiFiState);
        WifiManager wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        if(wifiManager.isWifiEnabled())
        {
            toggleWiFi.setChecked(true);
            toggleWiFi.setText("Wifi is ON");
        }else
        {
            toggleWiFi.setChecked(false);
            toggleWiFi.setText("Wifi is OFF");
        }

        //2G/3G Data
        toggleData = (Switch) findViewById(R.id.swtData);
        DataConManager dataConManager = new DataConManager(getApplicationContext());

        boolean bDataEnabled= dataConManager.isEnabled();

        if(bDataEnabled)
        {
            toggleData.setChecked(true);
            toggleData.setText("Data is ON");
        }else
        {
            toggleData.setChecked(false);
            toggleData.setText("Data is OFF");
        }

        //Bluetooth
        toggleBT = (Switch) findViewById(R.id.swtBlueTooth);
        BluetoothAdapter BTAdapter = BluetoothAdapter.getDefaultAdapter();
        if  (BTAdapter== null) {
            Toast.makeText(getApplicationContext(), "Bluetooth is not supported on this device", Toast.LENGTH_LONG);
            toggleBT.setEnabled(false);

        }else {

            if (BTAdapter.isEnabled()) {
                toggleBT.setChecked(true);
                toggleBT.setText("Bluetooth is ON");
            } else {
                toggleBT.setChecked(false);
                toggleBT.setText("Bluetooth is OFF");
            }
        }

        //NFC
        toggleNFC = (Switch) findViewById(R.id.swtNFC);
        NfcAdapter NFCAdapter = NfcAdapter.getDefaultAdapter(getApplicationContext());
        if  (NFCAdapter== null) {
            Toast.makeText(getApplicationContext(), "NFC is not supported on this device", Toast.LENGTH_LONG);
            toggleNFC.setEnabled(false);

        }else {

            if (NFCAdapter.isEnabled()) {
                toggleNFC.setChecked(true);
                toggleNFC.setText("NFC is ON");
            } else {
                toggleNFC.setChecked(false);
                toggleNFC.setText("NFC is OFF");
            }
        }

        //SYNC

        toggleSync = (Switch) findViewById(R.id.swtSync);
        boolean bSyncState;
        try {
            bSyncState = ContentResolver.getMasterSyncAutomatically();
        }
        catch(Exception e)
        {
            bSyncState=false;
        }
            if (bSyncState) {
                toggleSync.setChecked(true);
                toggleSync.setText("Sync is ON");
                ddSync1.setEnabled(true);
                btnSaveSync1.setEnabled(true);
            } else {
                toggleSync.setChecked(false);
                toggleSync.setText("Sync is OFF");
                ddSync1.setEnabled(false);
                btnSaveSync1.setEnabled(false);
            }

    }
    private void ToggleData()
    {
        toggleData = (Switch) findViewById(R.id.swtData);

        DataConManager dataConManager = new DataConManager(getApplicationContext());

        boolean bDataEnabled= dataConManager.isEnabled();

        if(bDataEnabled)
        {
            toggleData.setChecked(true);
            toggleData.setText("Data is ON");
        }else
        {
            toggleData.setChecked(false);
            toggleData.setText("Data is OFF");
        }

        toggleData.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DataConManager dataConManager = new DataConManager(getApplicationContext());
                if (isChecked) {

                    dataConManager.EnableData(true);
                    Toast.makeText(getApplicationContext(), "Data Enabled!", Toast.LENGTH_LONG).show();
                    toggleData.setText("Data is ON");
                } else {
                    dataConManager.EnableData(false);
                    Toast.makeText(getApplicationContext(), "Data Disabled!", Toast.LENGTH_LONG).show();
                    toggleData.setText("Data is OFF");
                }
            }
        });
    }

    private void ToggleBT()
    {
        toggleBT = (Switch) findViewById(R.id.swtBlueTooth);

        BluetoothAdapter BTAdapter = BluetoothAdapter.getDefaultAdapter();
        boolean bBTEnabled =false;

        if  (BTAdapter== null) {
            Toast.makeText(getApplicationContext(), "Bluetooth not supported on this device", Toast.LENGTH_LONG);
            toggleBT.setEnabled(false);

        }else {

            bBTEnabled = BTAdapter.isEnabled();
        }
        if(bBTEnabled)
        {
            toggleBT.setChecked(true);
            toggleBT.setText("Bluetooth is ON");
        }else
        {
            toggleBT.setChecked(false);
            toggleBT.setText("Bluetooth is OFF");
        }

        toggleBT.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                BluetoothAdapter BTAdapter = BluetoothAdapter.getDefaultAdapter();
                if  (BTAdapter== null) {
                    Toast.makeText(getApplicationContext(), "Bluetooth not supported on this device", Toast.LENGTH_LONG);
                    toggleBT.setEnabled(false);

                }else {
                    if (isChecked) {

                        BTAdapter.enable();

                        Toast.makeText(getApplicationContext(), "Bluetooth Enabled!", Toast.LENGTH_LONG).show();
                        toggleBT.setText("Bluetooth is ON");
                    } else {
                        BTAdapter.disable();
                        Toast.makeText(getApplicationContext(), "Bluetooth Disabled!", Toast.LENGTH_LONG).show();
                        toggleBT.setText("Bluetooth is OFF");
                    }
                }
            }
        });
    }

    private void ToggleNFC()
    {
        toggleNFC = (Switch) findViewById(R.id.swtNFC);

        NfcAdapter NFCAdapter = NfcAdapter.getDefaultAdapter(getApplicationContext());
        boolean bNFCEnabled =false;

        if  (NFCAdapter == null) {
            Toast.makeText(getApplicationContext(), "NFC is not supported on this device", Toast.LENGTH_LONG);
            toggleNFC.setEnabled(false);

        }else {

            bNFCEnabled = NFCAdapter.isEnabled();
        }
        if(bNFCEnabled)
        {
            toggleNFC.setChecked(true);
            toggleNFC.setText("NFC is ON");
        }else
        {
            toggleNFC.setChecked(false);
            toggleNFC.setText("NFC is OFF");
        }

        toggleNFC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                NfcAdapter NFCAdapter = NfcAdapter.getDefaultAdapter(getApplicationContext());
                if  (NFCAdapter== null) {
                    Toast.makeText(getApplicationContext(), "NFC is not supported on this device", Toast.LENGTH_LONG);
                    toggleNFC.setEnabled(false);

                }else {

                    Intent setNFC = new Intent(Settings.ACTION_NFC_SETTINGS);
                    startActivity(setNFC);
                    SetStates();

                }
            }
        });
    }


        public void ToggleWifi(boolean bState) {

        WifiManager wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        if (bState == true && !wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);


        } else if (bState == false && wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(false);
        }
    }
    }


/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    */

