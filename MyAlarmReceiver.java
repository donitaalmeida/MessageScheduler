package com.bignerdranch.android.messagescheduler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Toast;


/**
 * Created by donita on 06-07-2016.
 */
public class MyAlarmReceiver extends BroadcastReceiver {
    String smsNumberToSend, smsTextToSend;
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            smsNumberToSend = (String) bundle.getCharSequence("extraSmsNumber");
            smsTextToSend = (String) bundle.getCharSequence("extraSmsText");

            try {


                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(smsNumberToSend, null, smsTextToSend, null, null);
                Toast.makeText(context, "Message has been sent to " + smsNumberToSend, Toast.LENGTH_LONG).show();
            } catch (Exception e) {

            }
        }
    }

}
