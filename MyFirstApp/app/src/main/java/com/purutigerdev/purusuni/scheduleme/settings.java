package com.purutigerdev.purusuni.scheduleme;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;



public class settings extends Activity implements View.OnClickListener{

    private CheckBox chkToastNotification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        Resources res = getResources();
        int color = res.getColor(R.color.actionbar_background);

        ColorDrawable colorDrawable = new ColorDrawable(color);
        ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(colorDrawable);

        chkToastNotification = (CheckBox) findViewById(R.id.chkShowToastNotifications);
        chkToastNotification.setOnClickListener(this);
        SettingManager SM = new SettingManager(this);
        int iShowToast = SM.GetIntValue(SM.DISABLE_SHOW_TOAST);
        if(iShowToast == SM.CHECKED)
        {
            chkToastNotification.setChecked(true);
        }else
        {
            chkToastNotification.setChecked(false);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.faq, menu);
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

    @Override
    public void onClick(View v)
    {
        final int id = v.getId();
        SettingManager SM = new SettingManager(v.getContext());
        AlarmScheduler AS = new AlarmScheduler();
        //Toast.makeText(v.getContext(), "Here here here!!!",Toast.LENGTH_LONG ).show();
        boolean checked = ((CheckBox) v).isChecked();
        switch (id) {
            case R.id.chkShowToastNotifications:
                if (checked)
                {
                    SM.SetNameValue(SM.DISABLE_SHOW_TOAST, SM.CHECKED);
                    AS.ShowToast(v.getContext(), "This will disable some of the 'non-critical' toast notifications.", true);
                    AS.ShowToast(v.getContext(), "Some toast notifications related to schedules will still be shown", true);
                }

                else
                {
                    SM.SetNameValue(SM.DISABLE_SHOW_TOAST, SM.UNCHECKED);
                }

                break;
                //Toast.makeText(v.getContext(), "Toast Notification Enable/Disable",Toast.LENGTH_LONG ).show();

        }
    }
}
