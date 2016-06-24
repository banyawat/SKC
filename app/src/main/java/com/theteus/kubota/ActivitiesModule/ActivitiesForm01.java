package com.theteus.kubota.ActivitiesModule;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
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
import java.util.HashMap;
import java.util.Locale;

public class ActivitiesForm01 extends Fragment {
    Spinner activityTypeSpinner, decisionStatusSpinner, decisionTimeSpinner,
        decisionReasonSpinner, decisionReasonNoSpinner;
    EditText subjectEdit, regardingEdit, startDateEdit, dueDateEdit, descriptionEdit;

    private DatePickerDialog startDatePickerDialog, dueDatePickerDialog;
    private SimpleDateFormat dateFormatter;

    public ActivitiesForm01() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activities_form01, container, false);
        findViewsById(view);
        initSpinner(view);
        initDatePick(view);
        initEditText();
        return view;
    }

    private void findViewsById(View view){
        activityTypeSpinner = (Spinner) view.findViewById(R.id.activities_form_type);
        decisionStatusSpinner = (Spinner) view.findViewById(R.id.activities_form_decisionstatus);
        decisionTimeSpinner = (Spinner) view.findViewById(R.id.activities_form_decisiontime);
        decisionReasonSpinner = (Spinner) view.findViewById(R.id.activities_form_decisionreason);
        decisionReasonNoSpinner = (Spinner) view.findViewById(R.id.activities_form_decisionreason_no);
        subjectEdit = (EditText) view.findViewById(R.id.activities_form_subject);
        regardingEdit = (EditText) view.findViewById(R.id.activities_form_regard);
        startDateEdit = (EditText) view.findViewById(R.id.activities_form_startdate);
        startDateEdit.setInputType(InputType.TYPE_NULL);
        dueDateEdit = (EditText) view.findViewById(R.id.activities_form_duedate);
        dueDateEdit.setInputType(InputType.TYPE_NULL);
        descriptionEdit = (EditText) view.findViewById(R.id.activities_form_description);
    }

    private void initSpinner(View view){
        loadSpinnerArray(activityTypeSpinner, R.array.activities_type, view);
        loadSpinnerArray(decisionStatusSpinner, R.array.activities_status, view);
        loadSpinnerArray(decisionReasonSpinner, R.array.activities_reason, view);
        loadSpinnerArray(decisionTimeSpinner, R.array.activities_time, view);
        loadSpinnerArray(decisionReasonNoSpinner, R.array.activities_reason_no, view);
    }

    private void loadSpinnerArray(Spinner spinner, int resID, View view){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(),
                resID, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void initEditText(){
        subjectEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(subjectEdit.getText().length()>0)
                    subjectEdit.setError(null);
            }
        });

        regardingEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(regardingEdit.getText().length()>0)
                    regardingEdit.setError(null);
            }
        });
    }

    private void initDatePick(View view){
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        Calendar newCalendar = Calendar.getInstance();
        startDatePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                startDateEdit.setText(dateFormatter.format(newDate.getTime()));
                startDateEdit.setError(null);
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        dueDatePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dueDateEdit.setText(dateFormatter.format(newDate.getTime()));
                dueDateEdit.setError(null);
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        startDateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDatePickerDialog.show();
            }
        });

        dueDateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dueDatePickerDialog.show();
            }
        });
    }

    public HashMap<String, String> getAndValidateEntry(){
        HashMap<String, String> result = new HashMap<>();
        boolean checkpoint=true;

        result.put("ActivityType", activityTypeSpinner.getSelectedItem().toString());
        String getter = subjectEdit.getText().toString();

        if(getter.length()>0) {
            result.put("Subject", getter);
            subjectEdit.setError(null);
        }
        else {
            checkpoint = false;
            subjectEdit.setError("Require subject");
        }

        getter = regardingEdit.getText().toString();
        if(getter.length()>0) {
            result.put("Regarding", getter);
            regardingEdit.setError(null);
        }
        else {
            checkpoint = false;
            regardingEdit.setError("Require regard");
        }
        
        getter = startDateEdit.getText().toString();
        if(getter.length()>0) {
            result.put("Regarding", getter);
            startDateEdit.setError(null);
        }
        else {
            checkpoint = false;
            startDateEdit.setError("Require start date");
        }

        getter = dueDateEdit.getText().toString();
        if(getter.length()>0) {
            result.put("Regarding", getter);
            dueDateEdit.setError(null);
        }
        else {
            checkpoint = false;
            dueDateEdit.setError("Require due date");
        }

        if(checkpoint)
            return result;
        else
            return null;
    }
}
