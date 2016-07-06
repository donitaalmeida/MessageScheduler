package com.bignerdranch.android.messagescheduler;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * Created by donita on 02-07-2016.
 */
public class MessageActivity extends AppCompatActivity {
    String contact;
    private PendingIntent pendingIntent;
    private static final int PERMISSIONS_REQUEST_SEND_MESSAGE = 125;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_layout);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            contact = extras.getString("contact");
        }


        TextView toContact = (TextView) findViewById(R.id.toContact);
        toContact.setText(contact);

        Button button = (Button) findViewById(R.id.btnSchedule);

    }


    public void open(View view){

            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Are you sure,You wanted to make decision");

            alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // Check the SDK version and whether the permission is already granted or not.
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.SEND_SMS}, PERMISSIONS_REQUEST_SEND_MESSAGE);
                        //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
                        }

                        DatePicker dp = (DatePicker) findViewById(R.id.datePicker);
                        TimePicker tp = (TimePicker) findViewById(R.id.timePicker);

                        int year = dp.getYear();
                        int month = dp.getMonth();
                        int day = dp.getDayOfMonth();
                        int hour = tp.getCurrentHour();
                        int min = tp.getCurrentMinute();

                        Date date = new Date(year - 1900, month, day, hour, min);
                        Date today = new Date();
                        if (date.compareTo(today) >= 0) {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                            String datetime = dateFormat.format(date);


                            EditText et = (EditText) findViewById(R.id.message);
                            String smsNumber = contact.split(" <")[1];
                            smsNumber = smsNumber.substring(0, smsNumber.length() - 1);

                            String smsMessage = et.getText() + "";
                            Log.d("toname", smsNumber);
                            Log.d("toNumber", smsNumber);

                            SQLiteDatabase db = openOrCreateDatabase("MessageSchedule", MODE_PRIVATE, null);

                            db.execSQL("CREATE TABLE IF NOT EXISTS MessageSchedule( _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                    "toName VARCHAR, toNumber, message VARCHAR, datetime VARCHAR);");
                            db.execSQL("INSERT INTO MessageSchedule(toName, toNumber, message, datetime) VALUES('" + contact.split(" <")[0] + "', '" + smsNumber + "'," +
                                    "'" + et.getText() + "', '" + datetime + "');");
                            //Alarm code starts here
                            int NOTIF_ID = (int) Calendar.getInstance().getTimeInMillis();
                            Intent myIntent = new Intent(MessageActivity.this, MyAlarmReceiver.class);

                            Bundle bundle = new Bundle();
                            bundle.putCharSequence("extraSmsNumber", smsNumber);
                            bundle.putCharSequence("extraSmsText", smsMessage);
                            myIntent.putExtras(bundle);
                            myIntent.putExtra("id",NOTIF_ID);

                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), NOTIF_ID, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                            // pendingIntent = PendingIntent.getService(MessageActivity.this, 0, myIntent, 0);

                            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                            Calendar calendar = Calendar.getInstance();

                            calendar.set(year, month, day, hour, min);
                            Log.d("calender", dateFormat.format(calendar.getTime()));
                            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                            Toast.makeText(getBaseContext(),
                                    "Scheduled message for \n" +
                                            "smsNumber = " + smsNumber,
                                    Toast.LENGTH_LONG).show();


                            // TODO have to close current activity and goto main activity
                            // TODO have to check how to close app and msg gets sent

                            Intent i = new Intent(MessageActivity.this, MainActivity.class);

                            startActivity(i);

                        } else {
                            Toast.makeText(getBaseContext(),
                                    "Please Enter a date in future",
                                    Toast.LENGTH_LONG).show();
                        }

                }


            });
            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        Intent intent;
        switch (item.getItemId()) {
            case R.id.home:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.create:
                intent = new Intent(this, CreateActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
