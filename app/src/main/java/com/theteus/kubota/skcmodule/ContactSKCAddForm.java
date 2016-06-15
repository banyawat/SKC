package com.theteus.kubota.skcmodule;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.theteus.kubota.R;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ContactSKCAddForm extends Fragment {
    private DatePickerDialog birthDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    EditText nameEdit, mobPhoneEdit, emailEdit, birthDateEdit, idNumEdit;
    Button addButton, cancelButton;
    Spinner provinceSpinner;

    private SKCInstance returnData;

    ViewPager mViewPager;

    public ContactSKCAddForm() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_contact_skc_add, container, false);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        findViewsById(view);
        setAction(view);

        return view;
    }

    public void findViewsById(View view){
        nameEdit = (EditText) view.findViewById(R.id.skc_add_name);
        mobPhoneEdit = (EditText) view.findViewById(R.id.skc_add_mobile_phone);
        emailEdit = (EditText) view.findViewById(R.id.skc_add_email);
        birthDateEdit = (EditText) view.findViewById(R.id.skc_add_birthdate);
        birthDateEdit.setInputType(InputType.TYPE_NULL);
        provinceSpinner = (Spinner) view.findViewById(R.id.skc_spinner_province);
        idNumEdit = (EditText) view.findViewById(R.id.skc_add_id);
        addButton = (Button) view.findViewById(R.id.skc_button_add);
        cancelButton = (Button) view.findViewById(R.id.skc_button_cancel);
    }

    public void setAction(View view) {
        Calendar newCalendar = Calendar.getInstance();
        birthDatePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                birthDateEdit.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        birthDateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                birthDatePickerDialog.show();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameStr, telStr, emailStr, idStr;
                nameStr = nameEdit.getText().toString();
                telStr = mobPhoneEdit.getText().toString();
                emailStr = emailEdit.getText().toString();
                idStr = idNumEdit.getText().toString();
                if (isValid(nameStr, telStr, emailStr, idStr)) {
                    returnData = new SKCInstance(nameStr, telStr, emailStr);
                    turnBack();
                }
                else
                    nameEdit.requestFocus();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnBack();
            }
        });

        ArrayAdapter<CharSequence> provinceAdapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.province_array, android.R.layout.simple_spinner_item);
        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        provinceSpinner.setAdapter(provinceAdapter);

    }

    public void turnBack(){
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
    }

    public void setView(ViewPager mViewPager){
        this.mViewPager = mViewPager;
    }

    public SKCInstance getReturnData(){
        return returnData;
    }

    public void clear() {
        nameEdit.setText("");
        nameEdit.setError(null);
        mobPhoneEdit.setText("");
        mobPhoneEdit.setError(null);
        emailEdit.setText("");
        emailEdit.setError(null);
        birthDateEdit.setText("");
        idNumEdit.setText("");
        idNumEdit.setError(null);
        returnData=null;
    }

    protected boolean isValid(String name, String tel, String email, String id){
        boolean valid=true;
        if(id.isEmpty()){
            idNumEdit.setError("ID Number is required");
            valid=false;
        }

        if(email.isEmpty()){
            emailEdit.setError("Email address is required");
            valid=false;
        }

        if(tel.isEmpty()){
            mobPhoneEdit.setError("Mobile phone number is required");
            valid=false;
        }

        if(name.isEmpty()){
            nameEdit.setError("Name is required");
            valid=false;
        }
        return valid;
    }
}
