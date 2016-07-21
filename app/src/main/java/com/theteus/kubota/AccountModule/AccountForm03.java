package com.theteus.kubota.AccountModule;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.theteus.kubota.OrganizationDataService.AsyncResponse;
import com.theteus.kubota.OrganizationDataService.RetrieveService;
import com.theteus.kubota.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AccountForm03 extends Fragment {
    private final static String CURRENCY_ERROR = "No such a specified currency";
    private final static String CREDITLIMIT_ERROR = "Need to enter currency first";
    private final static String FIELD_VALUE = "Value";
    private final static String FIELD_ID = "Id";
    private final static String FIELD_TRANSACTIONCURRENCY = "TransactionCurrency";
    private final static String QUERY_AUTOCOMPLETE = "$select=TransactionCurrencyId,CurrencyName,CurrencySymbol";
    private final static String FIELD_CURRENCYNAME = "CurrencyName";
    private final static String FIELD_RESULTS = "results";
    private final static String FIELD_D = "d";

    private Button menu1, menu2;
    private LinearLayout layoutLoader;
    private View viewPart1, viewPart2;
    private int pageID = 0;
    private Spinner mAccount_form3_1_industry;
    private EditText mAccount_form3_1_sic;
    private Spinner mAccount_form3_1_ownership;
    private EditText mAccount_form3_1_last_date;
    private RadioGroup mAccount_form3_1_marketmaterial;
    private EditText mAccount_form3_1_creditlimit;
    private RadioGroup mAccount_form3_1_credithold;
    private Spinner mAccount_form3_1_paymentterm;
    private AutoCompleteTextView currencyIdSearchText;
    private CheckBox mAccount_form3_2_checkemail;
    private CheckBox mAccount_form3_2_checkbulkemail;
    private CheckBox mAccount_form3_2_checkphone;
    private CheckBox mAccount_form3_2_checkfax;
    private CheckBox mAccount_form3_2_checkpostalmail;
    private Spinner mAccount_form3_2_shippingmethod;
    private RadioGroup mAccount_form3_2_freight;
    private EditText mAccount_form3_2_description;
    private DatePickerDialog lastDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    private List<String> currencySearchListName;
    private List<String> currencySearchListId;

    public AccountForm03() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_form03, container, false);
        getCurrencyAutoComplete();
        findViewsById(view);
        initButton();
        initPage1();
        initPage2();
        return view;
    }

    @SuppressLint("InflateParams")
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

    private void initPage1(){
        mAccount_form3_1_industry = (Spinner) viewPart1.findViewById(R.id.account_form3_1_industry);
        mAccount_form3_1_sic = (EditText) viewPart1.findViewById(R.id.account_form3_1_sic);
        mAccount_form3_1_ownership = (Spinner) viewPart1.findViewById(R.id.account_form3_1_ownership);
        mAccount_form3_1_last_date = (EditText) viewPart1.findViewById(R.id.account_form3_1_last_date);
        mAccount_form3_1_marketmaterial = (RadioGroup) viewPart1.findViewById(R.id.account_form3_1_marketmaterial);
        mAccount_form3_1_creditlimit = (EditText) viewPart1.findViewById(R.id.account_form3_1_creditlimit);
        mAccount_form3_1_credithold = (RadioGroup) viewPart1.findViewById(R.id.account_form3_1_credithold);
        mAccount_form3_1_paymentterm = (Spinner) viewPart1.findViewById(R.id.account_form3_1_paymentterm);

        ArrayAdapter<CharSequence> industryAdapter = ArrayAdapter.createFromResource(viewPart1.getContext(),
                R.array.account_company_industry, android.R.layout.simple_spinner_item);
        industryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mAccount_form3_1_industry.setAdapter(industryAdapter);

        ArrayAdapter<CharSequence> ownershipAdapter = ArrayAdapter.createFromResource(viewPart1.getContext(),
                R.array.account_company_ownership, android.R.layout.simple_spinner_item);
        ownershipAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mAccount_form3_1_ownership.setAdapter(ownershipAdapter);

        ArrayAdapter<CharSequence> billingAdapter = ArrayAdapter.createFromResource(viewPart1.getContext(),
                R.array.account_billing_payment, android.R.layout.simple_spinner_item);
        billingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mAccount_form3_1_paymentterm.setAdapter(billingAdapter);

        mAccount_form3_1_last_date.setInputType(InputType.TYPE_NULL);
        initDatePick(viewPart1);

        currencyIdSearchText = (AutoCompleteTextView) viewPart1.findViewById(R.id.account_form3_1_currencysearch);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, currencySearchListName);
        currencyIdSearchText.setAdapter(adapter);
        currencyIdSearchText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                currencyIdSearchText.showDropDown();
                return false;
            }
        });
        setTextChangedValidate();
    }

    private void setTextChangedValidate(){
        currencyIdSearchText.addTextChangedListener(new TextWatcher() { //validate after search field hs changed
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if(currencySearchListName.contains(s.toString())){
                    currencyIdSearchText.setError(null);
                    mAccount_form3_1_creditlimit.setError(null);
                }
                else{
                    if(s.length() == 0)
                        currencyIdSearchText.setError(null);
                    else
                        currencyIdSearchText.setError(CURRENCY_ERROR);
                    if(mAccount_form3_1_creditlimit.getText().length() != 0)
                        mAccount_form3_1_creditlimit.setError(CREDITLIMIT_ERROR);
                    else
                        mAccount_form3_1_creditlimit.setError(null);
                }
            }
        });
        mAccount_form3_1_creditlimit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (mAccount_form3_1_creditlimit != null) {
                    if (s.length() != 0) {
                        if (currencyIdSearchText.getText().toString().length() == 0 || currencyIdSearchText.getError() != null)
                            mAccount_form3_1_creditlimit.setError(CREDITLIMIT_ERROR);
                    } else
                        mAccount_form3_1_creditlimit.setError(null);
                }
            }
        });
    }

    private void initPage2(){
        mAccount_form3_2_checkemail = (CheckBox) viewPart2.findViewById(R.id.account_form3_2_checkemail);
        mAccount_form3_2_checkbulkemail = (CheckBox) viewPart2.findViewById(R.id.account_form3_2_checkbulkemail);
        mAccount_form3_2_checkphone = (CheckBox) viewPart2.findViewById(R.id.account_form3_2_checkphone);
        mAccount_form3_2_checkfax = (CheckBox) viewPart2.findViewById(R.id.account_form3_2_checkfax);
        mAccount_form3_2_checkpostalmail = (CheckBox) viewPart2.findViewById(R.id.checkmail);
        mAccount_form3_2_shippingmethod = (Spinner) viewPart2.findViewById(R.id.account_form3_2_shippingmethod);
        mAccount_form3_2_freight = (RadioGroup) viewPart2.findViewById(R.id.account_form3_2_freight);
        mAccount_form3_2_description = (EditText) viewPart2.findViewById(R.id.account_form3_2_description);

        ArrayAdapter<CharSequence> shipAdapter = ArrayAdapter.createFromResource(viewPart2.getContext(),
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

    private void initButton(){
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
        int Account_form3_1_industry;
        int Account_form3_1_ownership;
        int Account_form3_1_paymentterm;
        int Account_form3_2_shippingmethod;
        int Account_form3_2_freight;
        boolean Account_form3_1_marketmaterial = true;
        boolean Account_form3_1_credithold = false;
        boolean Account_form3_2_checkemail;
        boolean Account_form3_2_checkbulkemail;
        boolean Account_form3_2_checkphone;
        boolean Account_form3_2_checkfax;
        boolean Account_form3_2_checkpostalmail;
        String Account_form3_1_sic;
        String Account_form3_1_currency;
        String Account_form3_1_creditlimit;
        String Account_form3_2_description;
        RadioButton tempButton;

        Account_form3_1_industry = mAccount_form3_1_industry.getSelectedItemPosition();
        Account_form3_1_sic = mAccount_form3_1_sic.getText().toString();
        Account_form3_1_ownership = mAccount_form3_1_ownership.getSelectedItemPosition();
        tempButton = (RadioButton) viewPart1.findViewById(mAccount_form3_1_marketmaterial.getCheckedRadioButtonId());
        if(tempButton!=null)
            Account_form3_1_marketmaterial = !(tempButton.getText().toString().equals("Send"));
        Account_form3_1_currency = currencyIdSearchText.getText().toString();
        if(Account_form3_1_currency.length()!=0) {
            if (currencyIdSearchText.getError() == null)
                Account_form3_1_currency = currencySearchListId.get(currencySearchListName.indexOf(Account_form3_1_currency));
            else
                return null;
        }
        Account_form3_1_creditlimit = mAccount_form3_1_creditlimit.getText().toString();
        tempButton = (RadioButton) viewPart1.findViewById(mAccount_form3_1_credithold.getCheckedRadioButtonId());
        if(tempButton!=null)
            Account_form3_1_credithold = tempButton.getText().toString().equals("Yes");
        Account_form3_1_paymentterm = mAccount_form3_1_paymentterm.getSelectedItemPosition();
        Account_form3_2_checkemail = !mAccount_form3_2_checkemail.isChecked();
        Account_form3_2_checkbulkemail = !mAccount_form3_2_checkbulkemail.isChecked();
        Account_form3_2_checkphone = !mAccount_form3_2_checkphone.isChecked();
        Account_form3_2_checkfax = !mAccount_form3_2_checkfax.isChecked();
        Account_form3_2_checkpostalmail = !mAccount_form3_2_checkpostalmail.isChecked();
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

        try {   //put data field into JSONObject
            if(Account_form3_1_industry!=0)
                args.put(AccountSchema.INDUSTRY, new JSONObject().put(FIELD_VALUE, Account_form3_1_industry)); //"{\"Value\":\"50\"}"
            if(Account_form3_1_sic.length()!=0)
                args.put(AccountSchema.SIC_CODE, Account_form3_1_sic);
            if(Account_form3_1_ownership!=0)
                args.put(AccountSchema.OWNERSHIP, new JSONObject().put(FIELD_VALUE, Account_form3_1_ownership));
            if(Account_form3_1_currency.length()!=0) {
                args.put(AccountSchema.CURRENCY, new JSONObject().put(FIELD_ID, Account_form3_1_currency));
                if (Account_form3_1_creditlimit.length() != 0)
                    args.put(AccountSchema.CREDIT_LIMIT, new JSONObject().put(FIELD_VALUE, Account_form3_1_creditlimit));
            }
            args.put(AccountSchema.MARKETING_MATERIAL, Account_form3_1_marketmaterial); //true = do not send
            args.put(AccountSchema.CREDIT_HOLD, Account_form3_1_credithold); //true = yes
            if(Account_form3_1_paymentterm!=0)
                args.put(AccountSchema.PAYMENT_TERMS,new JSONObject().put(FIELD_VALUE, Account_form3_1_paymentterm));
            args.put(AccountSchema.CONTACT_PREFERENCE_EMAIL, Account_form3_2_checkemail); //false = allow
            args.put(AccountSchema.CONTACT_PREFERENCE_BULK_EMAIL, Account_form3_2_checkbulkemail); //true = do not allow
            args.put(AccountSchema.CONTACT_PREFERENCE_PHONE, Account_form3_2_checkphone);
            args.put(AccountSchema.CONTACT_PREFERENCE_FAX, Account_form3_2_checkfax);
            args.put(AccountSchema.CONTACT_PREFERENCE_MAIL, Account_form3_2_checkpostalmail);
            if(Account_form3_2_shippingmethod!=0)
                args.put(AccountSchema.SHIPPING_METHOD, new JSONObject().put(FIELD_VALUE, Account_form3_2_shippingmethod));
            if(Account_form3_2_freight!=0)
                args.put(AccountSchema.FREIGHT_TERMS, new JSONObject().put(FIELD_VALUE, Account_form3_2_freight));
            if(Account_form3_2_description.length()!=0)
                args.put(AccountSchema.DESCRIPTION, Account_form3_2_description);
        } catch (JSONException e){
            e.printStackTrace();
        }
        return args;
    }

    private void getCurrencyAutoComplete(){
        currencySearchListName = new ArrayList<>();
        currencySearchListId = new ArrayList<>();
        new RetrieveService(getActivity(), new AsyncResponse() {
            @Override
            public void onFinishTask(JSONObject result) {
            try {
                JSONArray result1 = result.getJSONObject(FIELD_D).optJSONArray(FIELD_RESULTS);
                for(int i=0;i<result1.length();i++){
                    JSONObject jsObj = result1.getJSONObject(i);
                    currencySearchListName.add(jsObj.get(FIELD_CURRENCYNAME).toString());
                    currencySearchListId.add(jsObj.get(AccountSchema.CURRENCY).toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
                }
        })
                .setEntity(FIELD_TRANSACTIONCURRENCY)
                .setQueryString(QUERY_AUTOCOMPLETE)
                .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}
