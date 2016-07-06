package com.bignerdranch.android.messagescheduler;
/**
 * Created by user on 04-Jul-16.
 */
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.widget.Toast;

public class MyAlarmService extends Service {

    String smsNumberToSend, smsTextToSend;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub

        Toast.makeText(this, "MyAlarmService.onCreate()", Toast.LENGTH_LONG).show();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        Toast.makeText(this, "MyAlarmService.onBind()", Toast.LENGTH_LONG).show();
        return null;
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Toast.makeText(this, "MyAlarmService.onDestroy()", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);

        Bundle bundle = intent.getExtras();
        smsNumberToSend = (String) bundle.getCharSequence("extraSmsNumber");
        smsTextToSend = (String) bundle.getCharSequence("extraSmsText");

        try {


            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(smsNumberToSend, null, smsTextToSend, null, null);
            Toast.makeText(this,
                    "Message has been sent to " + smsNumberToSend,
                    Toast.LENGTH_LONG).show();
        }
        catch (Exception e){

        }
    }


    @Override
    public boolean onUnbind(Intent intent) {
        // TODO Auto-generated method stub
        Toast.makeText(this, "MyAlarmService.onUnbind()", Toast.LENGTH_LONG).show();
        return super.onUnbind(intent);
    }

}