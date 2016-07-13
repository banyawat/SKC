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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.theteus.kubota.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AccountForm03 extends Fragment {
    private Button menu1, menu2;
    private LinearLayout layoutLoader;
    private View viewPart1, viewPart2;
    private int pageID = 0;

    private Spinner mAccount_form3_1_industry;
    private EditText mAccount_form3_1_sic;
    private Spinner mAccount_form3_1_ownership;
    private EditText mAccount_form3_1_originate_lead;
    private EditText mAccount_form3_1_last_date;
    private RadioGroup mAccount_form3_1_marketmaterial;
    private EditText mAccount_form3_1_currency;
    private EditText mAccount_form3_1_creditlimit;
    private RadioGroup mAccount_form3_1_credithold;
    private Spinner mAccount_form3_1_paymentterm;

    private CheckBox mAccount_form3_2_checkemail;
    private CheckBox mAccount_form3_2_checkbulkemail;
    private CheckBox mAccount_form3_2_checkphone;
    private CheckBox mAccount_form3_2_checkfax;
    private CheckBox mCheckmail;
    private Spinner mAccount_form3_2_shippingmethod;
    private RadioGroup mAccount_form3_2_freight;
    private EditText mAccount_form3_2_description;

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

        mAccount_form3_1_industry = (Spinner) viewPart1.findViewById(R.id.account_form3_1_industry);
        mAccount_form3_1_sic = (EditText) view.findViewById(R.id.account_form3_1_sic);
        mAccount_form3_1_ownership = (Spinner) view.findViewById(R.id.account_form3_1_ownership);
        mAccount_form3_1_originate_lead = (EditText) view.findViewById(R.id.account_form3_1_originate_lead);
        mAccount_form3_1_last_date = (EditText) view.findViewById(R.id.account_form3_1_last_date);
        mAccount_form3_1_marketmaterial = (RadioGroup) view.findViewById(R.id.account_form3_1_marketmaterial);
        mAccount_form3_1_currency = (EditText) view.findViewById(R.id.account_form3_1_currency);
        mAccount_form3_1_creditlimit = (EditText) view.findViewById(R.id.account_form3_1_creditlimit);
        mAccount_form3_1_credithold = (RadioGroup) view.findViewById(R.id.account_form3_1_credithold);
        mAccount_form3_1_paymentterm = (Spinner) view.findViewById(R.id.account_form3_1_paymentterm);

        ArrayAdapter<CharSequence> industryAdapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.account_company_industry, android.R.layout.simple_spinner_item);
        industryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mAccount_form3_1_industry.setAdapter(industryAdapter);

        ArrayAdapter<CharSequence> ownershipAdapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.account_company_ownership, android.R.layout.simple_spinner_item);
        ownershipAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mAccount_form3_1_ownership.setAdapter(ownershipAdapter);

        ArrayAdapter<CharSequence> billingAdapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.account_billing_payment, android.R.layout.simple_spinner_item);
        billingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mAccount_form3_1_paymentterm.setAdapter(billingAdapter);

        mAccount_form3_1_last_date.setInputType(InputType.TYPE_NULL);
        initDatePick(view);
    }

    private void initPage2(View view){
        mAccount_form3_2_checkemail = (CheckBox) view.findViewById(R.id.account_form3_2_checkemail);
        mAccount_form3_2_checkbulkemail = (CheckBox) view.findViewById(R.id.account_form3_2_checkbulkemail);
        mAccount_form3_2_checkphone = (CheckBox) view.findViewById(R.id.account_form3_2_checkphone);
        mAccount_form3_2_checkfax = (CheckBox) view.findViewById(R.id.account_form3_2_checkfax);
        mCheckmail = (CheckBox) view.findViewById(R.id.checkmail);
        mAccount_form3_2_shippingmethod = (Spinner) view.findViewById(R.id.account_form3_2_shippingmethod);
        mAccount_form3_2_freight = (RadioGroup) view.findViewById(R.id.account_form3_2_freight);
        mAccount_form3_2_description = (EditText) view.findViewById(R.id.account_form3_2_description);

        ArrayAdapter<CharSequence> shipAdapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.account_shipping_method, android.R.layout.simple_spinner_item);
        shipAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mAccount_form3_2_shippingmethod.setAdapter(shipAdapter);
    }

    private void initDatePick(View view){
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        Calendar newCalendar = Calendar.getInstance();
        lastDatePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                mAccount_form3_1_last_date.setText(dateFormatter.format(newDate.getTime()));
                mAccount_form3_1_last_date.setError(null);
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        mAccount_form3_1_last_date.setOnClickListener(new View.OnClickListener() {
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

    public JSONObject getAllData(){
        JSONObject args = new JSONObject();
        JSONObject metadata = new JSONObject();
        int Account_form3_1_industry=51;
        String Account_form3_1_sic;
        int Account_form3_1_ownership;
        //String Account_form3_1_originate_lead;
        //String Account_form3_1_last_date;
        boolean Account_form3_1_marketmaterial = true;
        //String Account_form3_1_currency;
        boolean Account_form3_1_credithold = false;
        //String Account_form3_1_creditlimit;
        int Account_form3_1_paymentterm;

        boolean Account_form3_2_checkemail;
        boolean Account_form3_2_checkbulkemail;
        boolean Account_form3_2_checkphone;
        boolean Account_form3_2_checkfax;
        boolean Checkmail;
        int Account_form3_2_shippingmethod;
        int Account_form3_2_freight;
        String Account_form3_2_description;

        RadioButton tempButton = null;

        Account_form3_1_industry = mAccount_form3_1_industry.getSelectedItemPosition();
        Account_form3_1_sic = mAccount_form3_1_sic.getText().toString();
        Account_form3_1_ownership = mAccount_form3_1_ownership.getSelectedItemPosition();
        //Account_form3_1_originate_lead = mAccount_form3_1_originate_lead.getText().toString();
        //Account_form3_1_last_date = mAccount_form3_1_last_date.getText().toString();
        tempButton = (RadioButton) viewPart1.findViewById(mAccount_form3_1_marketmaterial.getCheckedRadioButtonId());
        if(tempButton!=null) {
            if(tempButton.getText().toString()=="Send")
                Account_form3_1_marketmaterial = true;
            else
                Account_form3_1_marketmaterial = false;
        }
        //Account_form3_1_currency = mAccount_form3_1_currency.getText().toString();
        //Account_form3_1_creditlimit = mAccount_form3_1_creditlimit.getText().toString();
        tempButton = (RadioButton) viewPart1.findViewById(mAccount_form3_1_credithold.getCheckedRadioButtonId());
        if(tempButton!=null) {
            if (tempButton.getText().toString() == "Yes")
                Account_form3_1_credithold = false;
            else
                Account_form3_1_credithold = true;
        }

        Account_form3_1_paymentterm = mAccount_form3_1_paymentterm.getSelectedItemPosition();

        Account_form3_2_checkemail = mAccount_form3_2_checkemail.isChecked();
        Account_form3_2_checkbulkemail = mAccount_form3_2_checkbulkemail.isChecked();
        Account_form3_2_checkphone = mAccount_form3_2_checkphone.isChecked();
        Account_form3_2_checkfax = mAccount_form3_2_checkfax.isChecked();
        Checkmail = mCheckmail.isChecked();
        Account_form3_2_shippingmethod = mAccount_form3_2_shippingmethod.getSelectedItemPosition();
        tempButton = (RadioButton) viewPart2.findViewById(mAccount_form3_2_freight.getCheckedRadioButtonId());
        if(tempButton!=null) {
            if (tempButton.getText() == getResources().getString(R.string.fob))
                Account_form3_2_freight = 1;
            else
                Account_form3_2_freight = 2;
        }
        else
            Account_form3_2_freight = 0;
        Account_form3_2_description = mAccount_form3_2_description.getText().toString();

        try {
            args.put("IndustryCode", new JSONObject().put("Value", Account_form3_1_industry)); //"{\"Value\":\"50\"}"
            args.put("SIC", Account_form3_1_sic);
            args.put("OwnershipCode", new JSONObject().put("Value", Account_form3_1_ownership));
            args.put("DoNotSendMM", Account_form3_1_marketmaterial); //true = do not send
            args.put("CreditOnHold", Account_form3_1_credithold); //true = yes
            args.put("PaymentTermsCode",new JSONObject().put("Value", Account_form3_1_paymentterm));

            args.put("DoNotEMail", Account_form3_2_checkemail); //false = allow
            args.put("DoNotBulkEMail", Account_form3_2_checkbulkemail); //true = do not allow
            args.put("DoNotPhone", Account_form3_2_checkphone);
            args.put("DoNotFax", Account_form3_2_checkfax);
            args.put("DoNotPostalMail", Checkmail);
            args.put("Address1_ShippingMethodCode", new JSONObject().put("Value", Account_form3_2_shippingmethod));
            args.put("Address1_FreightTermsCode", new JSONObject().put("Value", Account_form3_2_freight));
            args.put("Description", Account_form3_2_description);
        } catch (JSONException e){
            e.printStackTrace();
        }
        return args;
    }
}
