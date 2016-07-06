package com.bignerdranch.android.messagescheduler;

import android.app.ListFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;


/**
 * Created by donita on 03-07-2016.
 */
public class MessageListFragment extends ListFragment implements AdapterView.OnItemClickListener {
    private MessageDbAdapter dbHelper;
    private SimpleCursorAdapter dataAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.list_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new MessageDbAdapter(getActivity().getBaseContext());
        dbHelper.open();
        displayListView();

    }

    private void displayListView() {
        Cursor cursor = dbHelper.fetchAllMessageSchedule();
        // The desired columns to be bound
        String[] columns = new String[] {"toName",  "datetime"};

        // the XML defined views which the data will be bound to
        int[] to = new int[] {
                R.id.name,
                R.id.datetime,
        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information

        dataAdapter = new SimpleCursorAdapter(
                getActivity(), R.layout.message_info,
                cursor,
                columns,
                to,
                0);

        setListAdapter(dataAdapter);
        getListView().setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        Cursor cursor = (Cursor) getListView().getItemAtPosition(position);

        String number = cursor.getString(cursor.getColumnIndexOrThrow("toNumber"));
        String name=cursor.getString(cursor.getColumnIndexOrThrow("toName"));
        String message=cursor.getString(cursor.getColumnIndexOrThrow("message"));
        String datetime=cursor.getString(cursor.getColumnIndexOrThrow("datetime"));
        ImageView icon=(ImageView)view.findViewById(R.id.icon);
        icon.setImageResource(R.drawable.openmessage);

        Bundle arguments = new Bundle();
        arguments.putString("data", number);
        arguments.putString("message",message);
        arguments.putString("name",name);
        arguments.putString("datetime",datetime);

        MessageDetailsFragment fragment = new MessageDetailsFragment();
        fragment.setArguments(arguments);
        getFragmentManager().beginTransaction().replace(R.id.fragment2, fragment).commit();

    }

}
