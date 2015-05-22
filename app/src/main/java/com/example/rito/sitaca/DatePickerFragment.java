package com.example.rito.sitaca;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by rito on 4/14/2015.
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    Calendar c;
    int year = 0;
    int month = 0;
    int day = 0;
    TheListener listener;
    public interface TheListener{
        public void returnDate(String date);
    }

    @Override
    public void setArguments(Bundle savedInstanceState)
    {
        if(savedInstanceState==null) {
            c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH)-1;
            day = c.get(Calendar.DAY_OF_MONTH);
            Log.d("log1", day +" "+ month +" "+year);
        }
        else
        {
            year = Integer.parseInt(savedInstanceState.getString("year"));
            month = Integer.parseInt(savedInstanceState.getString("month"))-1;
            day = Integer.parseInt(savedInstanceState.getString("day"));
        }
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
// Use the current date as the default date in the

        Log.d("Bundle", "" + savedInstanceState);
        listener = (TheListener) getActivity();
// Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = sdf.format(c.getTime());

        if (listener !=   null)
        {
            listener.returnDate(formattedDate);

        }

    }
}