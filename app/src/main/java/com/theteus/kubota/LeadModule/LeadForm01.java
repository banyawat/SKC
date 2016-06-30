package com.theteus.kubota.LeadModule;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.theteus.kubota.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class LeadForm01 extends Fragment {
    EditText prefixEdit, nameEdit, surnameEdit, idNumEdit, birthDateEdit, phoneEdit, mobile1Edit, mobile2Edit;
    RadioGroup genderRadioGroup, personTypeRadioGroup;
    TextView ageText;

    private DatePickerDialog birthDatePickerDialog;
    private SimpleDateFormat dateFormatter;

    public LeadForm01() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        View view = inflater.inflate(R.layout.fragment_lead_form01, container, false);

        findViewsById(view);
        initDatePick(view);

        return view;
    }

    private void findViewsById(View view){
        prefixEdit = (EditText) view.findViewById(R.id.lead_form1_prefix);
        nameEdit =  (EditText) view.findViewById(R.id.lead_form1_name);
        surnameEdit = (EditText) view.findViewById(R.id.lead_form1_surname);
        idNumEdit = (EditText) view.findViewById(R.id.lead_form1_idnum);
        birthDateEdit = (EditText) view.findViewById(R.id.lead_form1_birthdate);
        birthDateEdit.setInputType(InputType.TYPE_NULL);
        phoneEdit = (EditText) view.findViewById(R.id.lead_form1_phone);
        mobile1Edit = (EditText) view.findViewById(R.id.lead_form1_mobphone1);
        mobile2Edit = (EditText) view.findViewById(R.id.lead_form1_mobphone2);

        genderRadioGroup = (RadioGroup) view.findViewById(R.id.lead_form1_gender);
        personTypeRadioGroup = (RadioGroup) view.findViewById(R.id.lead_form1_persontype);

        ageText = (TextView) view.findViewById(R.id.lead_form1_age);
    }

    private void initDatePick(View view){
        Calendar newCalendar = Calendar.getInstance();
        birthDatePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                birthDateEdit.setText(dateFormatter.format(newDate.getTime()));
                ageText.setText(ageCalculator(year, monthOfYear, dayOfMonth));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        birthDateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                birthDatePickerDialog.show();
            }
        });
    }

    private String ageCalculator(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (age<=0){
            birthDateEdit.setError("ใส่วัน/เดือน/ปีเกิด ไม่ถูกต้องนะจ๊ะ");
            return "อายุ";
        }
        else {
            birthDateEdit.setError(null);
            return "อายุ " + String.valueOf(age) + " ขวบ";
        }
    }
}
