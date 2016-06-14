package com.theteus.kubota.skcmodule;

import android.app.DatePickerDialog;
import android.database.DataSetObserver;
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
import android.widget.SpinnerAdapter;

import com.theteus.kubota.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ContactSKCAddForm extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private DatePickerDialog birthDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    EditText nameEdit,phoneEdit,emailEdit,birthDateEdit;
    Button addButton;
    Spinner provinceSpinner;

    private String mParam1;
    private String mParam2;
    private SKCInstance returnData;

    ViewPager mViewPager;

    public ContactSKCAddForm() {
        // Required empty public constructor
    }

    public static ContactSKCAddForm newInstance(String param1, String param2) {
        ContactSKCAddForm fragment = new ContactSKCAddForm();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
        phoneEdit = (EditText) view.findViewById(R.id.skc_add_phone);
        emailEdit = (EditText) view.findViewById(R.id.skc_add_email);
        birthDateEdit = (EditText) view.findViewById(R.id.skc_add_birthdate);
        birthDateEdit.setInputType(InputType.TYPE_NULL);
        provinceSpinner = (Spinner) view.findViewById(R.id.skc_spinner_province);
        addButton = (Button) view.findViewById(R.id.skc_button_add);
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
                String nameStr, telStr, emailStr;
                nameStr = nameEdit.getText().toString();
                telStr = phoneEdit.getText().toString();
                emailStr = emailEdit.getText().toString();
                if (isValid(nameStr, telStr, emailStr)) {
                    returnData = new SKCInstance(nameStr, telStr, emailStr);
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
                }
            }
        });

        ArrayAdapter<CharSequence> provinceAdapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.province_array, android.R.layout.simple_spinner_item);
        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        provinceSpinner.setAdapter(provinceAdapter);

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
        phoneEdit.setText("");
        phoneEdit.setError(null);
        emailEdit.setText("");
        emailEdit.setError(null);
        birthDateEdit.setText("");
        returnData=null;
    }

    protected boolean isValid(String name, String tel, String email){
        boolean valid=true;
        if(name.isEmpty()){
            nameEdit.setError("Name is required");
            valid=false;
        }
        if(tel.isEmpty()){
            phoneEdit.setError("Phone number is required");
            valid=false;
        }
        if(email.isEmpty()){
            emailEdit.setError("Email address is required");
            valid=false;
        }
        return valid;
    }
}
