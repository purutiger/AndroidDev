package com.purutigerdev.purusuni.scheduleme;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by PuruSuni on 03/09/2014.
 */
public class AlarmReceiverForBTEnable extends BroadcastReceiver {

    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {

        mContext = context;
        //ShowMsg("Alarm to Disabled", "Alarm Completed");

        //Set BT Enable
        AlarmScheduler AS = new AlarmScheduler();

        BluetoothAdapter BTAdapter = BluetoothAdapter.getDefaultAdapter();
        if  (BTAdapter== null)
        {
            AS.ShowToast(mContext, "Bluetooth not supported on this device", true);

        }else
        {
                BTAdapter.enable();


            AS.ShowToast(mContext, "Bluetooth Enabled!", true);

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