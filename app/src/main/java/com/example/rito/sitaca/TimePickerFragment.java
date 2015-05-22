package com.example.rito.sitaca;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by theo on 4/17/2015.
 */
public class TimePickerFragment extends DialogFragment
    implements TimePickerDialog.OnTimeSetListener {
    int hour, minute;
    Calendar c;
    TheListener listener;

    public interface TheListener{
        public void returnTime (String time);
    }

    @Override
    public void setArguments(Bundle savedInstanceState)
    {
        if(savedInstanceState==null) {
            c = Calendar.getInstance();
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);
        }
        else
        {
            hour = Integer.parseInt(savedInstanceState.getString("hour"));
            minute = Integer.parseInt(savedInstanceState.getString("minute"));
        }
    }
    @Override
    public void onTimeSet(TimePicker view, int hour, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String formattedTime = sdf.format(c.getTime());

        if (listener !=   null)
        {
            listener.returnTime(formattedTime);
        }
    }

    public Dialog onCreateDialog(Bundle savedInstance){
        listener = (TheListener)getActivity();
        return new TimePickerDialog(getActivity(), this, hour, minute, true);
    }
}
