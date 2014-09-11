package com.purutigerdev.purusuni.scheduleme;

import android.content.ContentResolver;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by PuruSuni on 01/09/2014.
 */
public class SyncFor
{

        Timer timer;


    public SyncFor(int seconds)
    {
        timer = new Timer();
        timer.schedule(new RemindTask(), seconds * 1000);
    }

        class RemindTask extends TimerTask
        {
            public void run()
            {
                ContentResolver.setMasterSyncAutomatically(false);
                //System.out.println("Time's up!");
                timer.cancel(); //Terminate the timer thread
            }
        }

    }



