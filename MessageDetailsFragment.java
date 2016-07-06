package com.bignerdranch.android.messagescheduler;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by donita on 04-07-2016.
 */
public class MessageDetailsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Bundle extras = this.getArguments();
        String contact="";
        String name="";
        String message="";
        String datetime="";
        View view = inflater.inflate(R.layout.details_fragment, container, false);
        if (extras != null) {
            contact = extras.getString("data");
            name=extras.getString("name");
            message=extras.getString("message");
            datetime=extras.getString("datetime");
        }
        TextView recipient_name=(TextView)view.findViewById(R.id.recipient_name);
        recipient_name.setText(name);
        TextView recipient_number=(TextView)view.findViewById(R.id.recipient_number);
        contact=contact.replace(">","");

        recipient_number.setText(contact);

        TextView body=(TextView)view.findViewById(R.id.body);
        body.setText(message);
        TextView date=(TextView)view.findViewById(R.id.datetime);
        date.setText(datetime);
       // setRetainInstance(true);
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null){

        }

    }
}
