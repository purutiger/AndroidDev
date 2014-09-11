package com.example.purusuni.myfirstapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static java.lang.Class.forName;

/**
 * Created by PuruSuni on 31/08/2014.
 */
// Needs the following permissions:
// - "android.permission.MODIFY_PHONE_STATE"

public final class DataConManager
{
    private TelephonyManager m_telManager = null;
    private ConnectivityManager m_conManager = null;
    private Context m_context = null;
    // ------------------------------------------------------
    // ------------------------------------------------------
    public DataConManager(Context context)
    {
        try
        {
            // Get phone and connectivity services
            m_telManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
            m_conManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
            m_context = context;
        }
        catch (Exception e)
        {
            m_telManager = null;
            m_conManager = null;
        }
    }

    public void EnableData(boolean enabled) {
        final ConnectivityManager conman = (ConnectivityManager) m_context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Class conmanClass = null;
        try {
            conmanClass = forName(conman.getClass().getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Field connectivityManagerField = null;

        try {
            connectivityManagerField = conmanClass.getDeclaredField("mService");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        connectivityManagerField.setAccessible(true);
        Object connectivityManager = null;
        try {
            connectivityManager = connectivityManagerField.get(conman);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Class connectivityManagerClass = null;
        try {
            connectivityManagerClass = forName(connectivityManager.getClass().getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Method setMobileDataEnabledMethod = null;
        try {
            setMobileDataEnabledMethod = connectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        setMobileDataEnabledMethod.setAccessible(true);

        try {
            setMobileDataEnabledMethod.invoke(connectivityManager, enabled);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    // ------------------------------------------------------
    // ------------------------------------------------------
    boolean switchState(boolean enable) throws InvocationTargetException, IllegalAccessException {
        boolean bRes = false;

        // Data Connection mode (only if correctly initialized)
        if (m_telManager != null)
        {
           // try
            //{
                // Will be used to invoke hidden methods with reflection
                Class cTelMan = null;
                Method getITelephony = null;
                Object oTelephony = null;
                Class cTelephony = null;
                Method action = null;

                // Get the current object implementing ITelephony interface
                cTelMan = m_telManager.getClass();
            try {
                getITelephony = cTelMan.getDeclaredMethod("getITelephony");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                return bRes;
            }
            getITelephony.setAccessible(true);
                oTelephony = getITelephony.invoke(m_telManager);

                // Call the enableDataConnectivity/disableDataConnectivity method
                // of Telephony object
                cTelephony = oTelephony.getClass();
                if (enable)
                {
                    try {
                        action = cTelephony.getMethod("enableDataConnectivity");
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                        return bRes;
                    }
                }
                else
                {
                    try {
                        action = cTelephony.getMethod("disableDataConnectivity");
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                        return bRes;
                    }
                }
                action.setAccessible(true);
                bRes = (Boolean)action.invoke(oTelephony);
            }
            //catch (Exception e)
            //{
            //    bRes = false;
            //}
        //}

        return bRes;
    }

    // ------------------------------------------------------
    // ------------------------------------------------------
    public boolean isEnabled()
    {
        boolean bRes = false;

        // Data Connection mode (only if correctly initialized)
        if (m_conManager != null)
        {
            try
            {
                // Get Connectivity Service state
                NetworkInfo netInfo = m_conManager.getNetworkInfo(0);

                // Data is enabled if state is CONNECTED
                bRes = (netInfo.getState() == NetworkInfo.State.CONNECTED);
            }
            catch (Exception e)
            {
                bRes = false;
            }
        }

        return bRes;
    }
}