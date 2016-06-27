package com.theteus.kubota.ContactModule;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import com.theteus.kubota.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ContactForm01 extends Fragment {
    EditText contact_form1_birthdate;

    private DatePickerDialog lastDatePickerDialog;
    private SimpleDateFormat dateFormatter;

    public ContactForm01() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_form01, container, false);
        findViewsById(view);
        initDatePicker(view);

        return view;
    }

    private void findViewsById(View view){
        contact_form1_birthdate = (EditText) view.findViewById(R.id.contact_form1_birthdate);
        contact_form1_birthdate.setInputType(InputType.TYPE_NULL);
    }

    private void initDatePicker(View view){
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        Calendar newCalendar = Calendar.getInstance();
        lastDatePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                contact_form1_birthdate.setText(dateFormatter.format(newDate.getTime()));
                contact_form1_birthdate.setError(null);
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        contact_form1_birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastDatePickerDialog.show();
            }
        });
    }

}
