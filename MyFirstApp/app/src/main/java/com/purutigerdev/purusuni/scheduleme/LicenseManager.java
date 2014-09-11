package com.purutigerdev.purusuni.scheduleme;

/**
 * Created by PuruSuni on 04/09/2014.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import com.google.android.vending.licensing.*;
import com.google.android.vending.licensing.LicenseCheckerCallback;
import android.view.MotionEvent;
import android.widget.TextView;


public abstract class LicenseManager extends Activity {

    static boolean licensed = true;
    static boolean didCheck = false;
    static boolean checkingLicense = false;
    static final String BASE64_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhkK/NHsYsIp0Afl6K6NwZdkjF9qiKXX0SG0oD4h5AL5JreT69EyJN9bKIAf6XKwv1Rv+5TdRU0ckDZRbsl0sq3KEVgEncp8EqcXgVTl/1MBZD7977O3k/2oQ5lCwMIopFHKq98si2zprjS0Zn/XZ2rcYRHpWsefU2vyC77rQTQPSIONWb44yRpJKKQuJcojgu2zGBtE8x9S/SJiEmZeiiZop9oFrLuuNXLVguxyOFg1tzVFuj2FyRJynWVaqMbKJbKKzVz5JSJN0OcXpShKNHfUjfRX8HJurVNAr8HkaibhmZmM+znstXL4JS8SLeac7pB1jgMJJum3zkKB75MblnQIDAQAB";

    LicenseCheckerCallback mLicenseCheckerCallback;
    LicenseChecker mChecker;

    Handler mHandler;

    SharedPreferences prefs;

    // REPLACE WITH YOUR OWN SALT , THIS IS FROM EXAMPLE
    private static final byte[] SALT = new byte[]{
            -33, 44, 25, -111, -128, -32, 54, -63, 13, 88, -95, -45, 77, -31, -117, -113, -11, 32, -64,
            21
    };

    private void displayResult(final String result) {
        mHandler.post(new Runnable() {
            public void run() {

                setProgressBarIndeterminateVisibility(false);

            }
        });
    }

    protected void doCheck() {

        didCheck = false;
        checkingLicense = true;
        setProgressBarIndeterminateVisibility(true);

        mChecker.checkAccess(mLicenseCheckerCallback);
    }

    protected void checkLicense() {

        Log.i("LICENSE", "checkLicense");
        mHandler = new Handler();

        // Try to use more data here. ANDROID_ID is a single point of attack.
        String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        // Library calls this when it's done.
        mLicenseCheckerCallback = new MyLicenseCheckerCallback();
        // Construct the LicenseChecker with a policy.
        mChecker = new LicenseChecker(
                this, new ServerManagedPolicy(this,
                new AESObfuscator(SALT, getPackageName(), deviceId)),
                BASE64_PUBLIC_KEY);

//        mChecker = new LicenseChecker(
//                this, new StrictPolicy(),
//                BASE64_PUBLIC_KEY);

        doCheck();
    }

    protected class MyLicenseCheckerCallback implements LicenseCheckerCallback {

        @Override
        public void allow(int reason) {
            Log.i("LICENSE", "allow");
            if (isFinishing()) {
                // Don't update UI if Activity is finishing.
                return;
            }
            // Should allow user access.
            displayResult(getString(R.string.allow));
            licensed = true;
            checkingLicense = false;
            didCheck = true;

        }

        @Override
        public void dontAllow(int reason) {
            Log.i("LICENSE", "dontAllow");
            if (isFinishing()) {
                // Don't update UI if Activity is finishing.
                return;
            }
            displayResult(getString(R.string.dont_allow));
            licensed = false;
            // Should not allow access. In most cases, the app should assume
            // the user has access unless it encounters this. If it does,
            // the app should inform the user of their unlicensed ways
            // and then either shut down the app or limit the user to a
            // restricted set of features.
            // In this example, we show a dialog that takes the user to Market.
            checkingLicense = false;
            didCheck = true;

        }


        public void applicationError(int errorCode) {
            Log.i("LICENSE", "error: " + errorCode);
            if (isFinishing()) {
                // Don't update UI if Activity is finishing.
                return;
            }
            licensed = false;
            // This is a polite way of saying the developer made a mistake
            // while setting up or calling the license checker library.
            // Please examine the error code and fix the error.
            String result = String.format(getString(R.string.application_error), errorCode);
            checkingLicense = false;
            didCheck = true;


            //displayResult(result);

        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mChecker != null) {
            Log.i("LIcense", "distroy checker");
            mChecker.onDestroy();
        }
    }

}