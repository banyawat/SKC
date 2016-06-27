package com.theteus.kubota.AccountModule;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.theteus.kubota.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AccountForm03 extends Fragment {
    Button menu1, menu2;
    LinearLayout layoutLoader;
    View viewPart1, viewPart2;
    int pageID = 0;

    EditText m_account_form3_1_last_date;

    Spinner industrySpinner, ownershipSpinner, billingPaymentSpinner, shippingMethodSpinner;

    private DatePickerDialog lastDatePickerDialog;
    private SimpleDateFormat dateFormatter;

    public AccountForm03() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_form03, container, false);

        findViewsById(view);
        initButton(view);
        initPage1(viewPart1);
        initPage2(viewPart2);

        return view;
    }

    private void findViewsById(View view){
        menu1 = (Button) view.findViewById(R.id.account_form3_menu1);
        menu2 = (Button) view.findViewById(R.id.account_form3_menu2);
        layoutLoader = (LinearLayout) view.findViewById(R.id.account_form3_layoutloader);
        viewPart1 = LayoutInflater.from(view.getContext()).inflate(
                R.layout.account_form03_1, null);
        viewPart2 = LayoutInflater.from(view.getContext()).inflate(
                R.layout.account_form03_2, null);
        layoutLoader.addView(viewPart1);
    }

    private void initPage1(View view){
        industrySpinner = (Spinner) view.findViewById(R.id.account_form3_1_industry);
        ArrayAdapter<CharSequence> industryAdapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.account_company_industry, android.R.layout.simple_spinner_item);
        industryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        industrySpinner.setAdapter(industryAdapter);

        ownershipSpinner = (Spinner) view.findViewById(R.id.account_form3_1_ownership);
        ArrayAdapter<CharSequence> ownershipAdapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.account_company_ownership, android.R.layout.simple_spinner_item);
        ownershipAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ownershipSpinner.setAdapter(ownershipAdapter);

        billingPaymentSpinner = (Spinner) view.findViewById(R.id.account_form3_1_paymentterm);
        ArrayAdapter<CharSequence> billingAdapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.account_billing_payment, android.R.layout.simple_spinner_item);
        billingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        billingPaymentSpinner.setAdapter(billingAdapter);

        m_account_form3_1_last_date = (EditText) view.findViewById(R.id.account_form3_1_last_date);
        m_account_form3_1_last_date.setInputType(InputType.TYPE_NULL);
        initDatePick(view);
    }

    private void initPage2(View view){
        shippingMethodSpinner = (Spinner) view.findViewById(R.id.account_form3_2_shippingmethod);
        ArrayAdapter<CharSequence> shipAdapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.account_shipping_method, android.R.layout.simple_spinner_item);
        shipAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shippingMethodSpinner.setAdapter(shipAdapter);
    }

    private void initDatePick(View view){
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        Calendar newCalendar = Calendar.getInstance();
        lastDatePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                m_account_form3_1_last_date.setText(dateFormatter.format(newDate.getTime()));
                m_account_form3_1_last_date.setError(null);
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        m_account_form3_1_last_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastDatePickerDialog.show();
            }
        });
    }

    private void initButton(final View view){
        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (pageID){
                    case 0:
                        break;
                    case 1:
                        layoutLoader.removeAllViews();
                        layoutLoader.addView(viewPart1);
                        pageID=0;

                        menu1.setBackgroundResource(R.drawable.vertical_tab_active);
                        menu2.setBackgroundResource(R.drawable.vertical_tab_inactive);
                        break;
                    default:
                        break;
                }
            }
        });

        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (pageID){
                    case 0:
                        layoutLoader.removeAllViews();
                        layoutLoader.addView(viewPart2);
                        pageID=1;

                        menu1.setBackgroundResource(R.drawable.vertical_tab_inactive);
                        menu2.setBackgroundResource(R.drawable.vertical_tab_active);
                        break;
                    case 1:
                        break;
                    default:
                        break;
                }
            }
        });
    }

}
