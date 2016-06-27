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

/**
 * A simple {@link Fragment} subclass.
 */
public class ChassisForm02 extends Fragment {
    Spinner saleCondition;

    EditText chassis_form2_purchase_date;
    private DatePickerDialog buyDatePickerDialog;
    private SimpleDateFormat dateFormatter;

    public ChassisForm02() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chassis_form02, container, false);

        findViewsById(view);
        initSpinner(view);
        initDatePicker(view);

        return view;
    }

    private void findViewsById(View view) {
        saleCondition = (Spinner) view.findViewById(R.id.chassis_form2_salecondition);
        chassis_form2_purchase_date = (EditText) view.findViewById(R.id.chassis_form2_purchase_date);
        chassis_form2_purchase_date.setInputType(InputType.TYPE_NULL);
    }

    private void initSpinner(View view) {
        ArrayAdapter<CharSequence> saleConditionAdapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.chassis_sale_condition, android.R.layout.simple_spinner_item);
        saleConditionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        saleCondition.setAdapter(saleConditionAdapter);
    }

    private void initDatePicker(View view) {
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        Calendar newCalendar = Calendar.getInstance();
        buyDatePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                chassis_form2_purchase_date.setText(dateFormatter.format(newDate.getTime()));
                chassis_form2_purchase_date.setError(null);
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        chassis_form2_purchase_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyDatePickerDialog.show();
            }
        });
    }
}
