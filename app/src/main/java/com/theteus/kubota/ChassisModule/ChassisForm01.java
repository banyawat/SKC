package com.theteus.kubota.ChassisModule;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.theteus.kubota.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ChassisForm01 extends Fragment {
    Spinner carTypeSpinner, carStatusSpinner;

    EditText chassis_form1_status_change_date;

    private DatePickerDialog changeDatePickerDialog;
    private SimpleDateFormat dateFormatter;

    public ChassisForm01() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chassis_form01, container, false);

        findViewsById(view);
        initSpinner(view);
        initDatePicker(view);

        return view;
    }

    private void findViewsById(View view){
        carTypeSpinner = (Spinner) view.findViewById(R.id.chassis_form1_cartype);
        carStatusSpinner = (Spinner) view.findViewById(R.id.chassis_form1_carstatus);
        chassis_form1_status_change_date = (EditText) view.findViewById(R.id.chassis_form1_status_change_date);
        chassis_form1_status_change_date.setInputType(InputType.TYPE_NULL);

    }

    private void initDatePicker(View view){
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        Calendar newCalendar = Calendar.getInstance();
        changeDatePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                chassis_form1_status_change_date.setText(dateFormatter.format(newDate.getTime()));
                chassis_form1_status_change_date.setError(null);
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        chassis_form1_status_change_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDatePickerDialog.show();
            }
        });
    }
    private void initSpinner(View view){
        ArrayAdapter<CharSequence> carTypeAdapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.chassis_type, android.R.layout.simple_spinner_item);
        carTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carTypeSpinner.setAdapter(carTypeAdapter);

        ArrayAdapter<CharSequence> carStatusAdapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.chassis_status, android.R.layout.simple_spinner_item);
        carStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carStatusSpinner.setAdapter(carStatusAdapter);
    }

}
