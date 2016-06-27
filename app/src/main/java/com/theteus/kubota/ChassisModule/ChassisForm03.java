package com.theteus.kubota.ChassisModule;


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

public class ChassisForm03 extends Fragment {
    private DatePickerDialog startDatePickerDialog, endDatePickerDialog;
    private SimpleDateFormat dateFormatter;

    EditText chassis_form3_vipstartdate, chassis_form3_vipenddate;

    public ChassisForm03() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chassis_form03, container, false);

        findViewsById(view);
        initDatePicker(view);

        return view;
    }

    private void findViewsById(View view){
        chassis_form3_vipstartdate = (EditText) view.findViewById(R.id.chassis_form3_vipstartdate);
        chassis_form3_vipenddate = (EditText) view.findViewById(R.id.chassis_form3_vipenddate);
        chassis_form3_vipstartdate.setInputType(InputType.TYPE_NULL);
        chassis_form3_vipenddate.setInputType(InputType.TYPE_NULL);
    }

    private void initDatePicker(View view){
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        Calendar newCalendar = Calendar.getInstance();
        startDatePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                chassis_form3_vipstartdate.setText(dateFormatter.format(newDate.getTime()));
                chassis_form3_vipstartdate.setError(null);
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        endDatePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                chassis_form3_vipenddate.setText(dateFormatter.format(newDate.getTime()));
                chassis_form3_vipenddate.setError(null);
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        chassis_form3_vipstartdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDatePickerDialog.show();
            }
        });

        chassis_form3_vipenddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endDatePickerDialog.show();
            }
        });
    }

}
