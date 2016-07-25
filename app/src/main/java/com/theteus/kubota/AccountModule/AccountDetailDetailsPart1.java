package com.theteus.kubota.AccountModule;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.theteus.kubota.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AccountDetailDetailsPart1 extends Fragment  implements View.OnTouchListener, View.OnFocusChangeListener, AdapterView.OnItemSelectedListener {
    //Constant
    private static final String REPLACEMENT_STRING = "- - -";
    //Content
    private JSONObject mAccount;
    private JSONObject editBuffer;
    private AccountDetailMain parent;
    private List<String> industry;
    private List<String> ownership;
    private List<String> paymentTerms;
    private List<String> currencyList;
    private Map<String, String> currencyMap;
    private String currencyBuffer;
    //View
    private TextView industryText;
    private TextView sicCodeText;
    private TextView ownershipText;
    private TextView originatingLeadText;
    private TextView lastCampaignDateText;
    private TextView marketingText;
    private TextView currencyText;
    private TextView creditLimitText;
    private TextView creditHoldText;
    private TextView paymentTermsText;
    private Spinner industryEdit;
    private EditText sicCodeEdit;
    private Spinner ownershipEdit;
    private AutoCompleteTextView currencyEdit;
    private EditText creditLimitEdit;
    private Spinner paymentTermsEdit;
    private InputMethodManager keyboard;

    public AccountDetailDetailsPart1() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        industry = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.account_company_industry)));
        industry.set(0, "- - -");
        ownership = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.account_company_ownership)));
        ownership.set(0, "- - -");
        paymentTerms = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.account_billing_payment)));
        paymentTerms.set(0, "- - -");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_detail_details_part_1, container, false);

        industryText = (TextView) view.findViewById(R.id.industry);
        sicCodeText = (TextView) view.findViewById(R.id.sic_code);
        ownershipText = (TextView) view.findViewById(R.id.ownership);
        originatingLeadText = (TextView) view.findViewById(R.id.originating_lead);
        lastCampaignDateText = (TextView) view.findViewById(R.id.last_campaign);
        marketingText = (TextView) view.findViewById(R.id.marketing_material);
        currencyText = (TextView) view.findViewById(R.id.currency);
        creditLimitText = (TextView) view.findViewById(R.id.credit_limit);
        creditHoldText = (TextView) view.findViewById(R.id.credit_hold);
        paymentTermsText = (TextView) view.findViewById(R.id.payment_terms);
        industryEdit = (Spinner) view.findViewById(R.id.industry_edit);
        sicCodeEdit = (EditText) view.findViewById(R.id.sic_code_edit) ;
        ownershipEdit = (Spinner) view.findViewById(R.id.ownership_edit);
        currencyEdit = (AutoCompleteTextView) view.findViewById(R.id.currency_edit);
        creditLimitEdit = (EditText) view.findViewById(R.id.credit_limit_edit);
        paymentTermsEdit = (Spinner) view.findViewById(R.id.payment_terms_edit);

        keyboard = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        final LinearLayout touchInterceptor = (LinearLayout) view.findViewById(R.id.layout);
        touchInterceptor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Rect outRect = new Rect();
                    View view = getActivity().getCurrentFocus();
                    if(view != null) {
                        view.getGlobalVisibleRect(outRect);
                        if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                            view.clearFocus();
                        }
                    }
                }
                return false;
            }
        });

        if(mAccount != null) {
            spinnerLoad(AccountSchema.INDUSTRY, industryText, industryEdit, industry);
            basicLoad(AccountSchema.SIC_CODE, sicCodeText, sicCodeEdit);
            spinnerLoad(AccountSchema.OWNERSHIP, ownershipText, ownershipEdit, ownership);
            switchLoad(AccountSchema.MARKETING_MATERIAL, marketingText, "Do Not Send", "Send");
            textValueLoad(AccountSchema.CREDIT_LIMIT, creditLimitText, creditLimitEdit);
            autoCompleteLoad(AccountSchema.CURRENCY, currencyText, currencyEdit, currencyList, currencyBuffer);
            switchLoad(AccountSchema.CREDIT_HOLD, creditHoldText, "Yes", "No");
            spinnerLoad(AccountSchema.PAYMENT_TERMS, paymentTermsText, paymentTermsEdit, paymentTerms);

            try {
                //TODO: Add Redirect To Lead
                if(mAccount.getJSONObject(AccountSchema.ORIGINATING_LEAD).isNull("Name")) {
                    String lockedString = "(LOCKED) ";
                    originatingLeadText.setText(lockedString);
                } else {
                    String lockedString = "(LOCKED) " +  mAccount.getJSONObject(AccountSchema.ORIGINATING_LEAD).getString("Name");
                    originatingLeadText.setText(lockedString);
                }

                //TODO: Check How To Display Datetime Format
                if(mAccount.isNull(AccountSchema.LAST_CAMPAIGN_DATE)) {
                    String lockedString = "(LOCKED) ";
                    lastCampaignDateText.setText(lockedString);
                } else {
                    String lockedString = "(LOCKED) " + mAccount.getString(AccountSchema.LAST_CAMPAIGN_DATE);
                    lastCampaignDateText.setText(lockedString);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return view;
    }

    // Value
    private long touchDownMs = 0;
    // For TextView Tap/Press Interaction
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchDownMs = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_UP:
                if ((System.currentTimeMillis() - touchDownMs) > ViewConfiguration.getTapTimeout())
                    press(v);
                else
                    tap(v);
        }
        return true;
    }
    public void press(View v) {
        switch (v.getId()) {
            case R.id.sic_code:
                basicFocusRequest(AccountSchema.SIC_CODE, sicCodeText, sicCodeEdit);
                break;
            case R.id.marketing_material:
                switching(AccountSchema.MARKETING_MATERIAL, marketingText, "Do Not Send", "Send");
                break;
            case R.id.currency:
                autoCompleteFocusRequest(AccountSchema.CURRENCY, currencyText, currencyEdit, currencyBuffer);
                break;
            case R.id.credit_limit:
                textValueFocusRequest(AccountSchema.CREDIT_LIMIT, creditLimitText, creditLimitEdit);
                break;
            case R.id.credit_hold:
                switching(AccountSchema.CREDIT_HOLD, creditHoldText, "Yes", "No");
                break;
        }
    }
    public void tap(View v) {
        // Do Tap Action
    }
    // For EditText Focus Change Interaction
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(!hasFocus)
            switch (v.getId()) {
                case R.id.sic_code_edit:
                    basicFocusLoss(AccountSchema.SIC_CODE, sicCodeText, sicCodeEdit);
                    break;
                case R.id.currency_edit:
                    autoCompleteFocusLoss(AccountSchema.CURRENCY, currencyText, currencyEdit, currencyMap);
                    break;
                case R.id.credit_limit_edit:
                    textValueFocusLoss(AccountSchema.CREDIT_LIMIT, creditLimitText, creditLimitEdit);
                    break;
            }
    }
    // For Spinner Item Select Interaction
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.industry_edit:
                spinnerFocusLoss(AccountSchema.INDUSTRY, industryText, industry, position);
                break;
            case R.id.ownership_edit:
                spinnerFocusLoss(AccountSchema.OWNERSHIP, ownershipText, ownership, position);
                break;
            case R.id.payment_terms_edit:
                spinnerFocusLoss(AccountSchema.PAYMENT_TERMS, paymentTermsText, paymentTerms, position);
                break;
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // On Load Basic Field
    public void basicLoad(String schemaName, TextView textView, EditText editText) {
        try {
            if (editBuffer.has(schemaName)) {
                if(editBuffer.isNull(schemaName)) textView.setText(REPLACEMENT_STRING);
                else textView.setText(editBuffer.getString(schemaName));
                textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.statusRedFont));
                textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            } else {
                if(mAccount.isNull(schemaName)) textView.setText(REPLACEMENT_STRING);
                else textView.setText(mAccount.getString(schemaName));
                textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                textView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            }
            textView.setOnTouchListener(this);
            editText.setOnFocusChangeListener(this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void textValueLoad(String schemaName, TextView textView, EditText editText) {
        try {
            if (editBuffer.has(schemaName)) {
                if(editBuffer.getJSONObject(schemaName).isNull("Value")) textView.setText(REPLACEMENT_STRING);
                else textView.setText(editBuffer.getJSONObject(schemaName).getString("Value"));
                textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.statusRedFont));
                textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            } else {
                if(mAccount.getJSONObject(schemaName).isNull("Value")) textView.setText(REPLACEMENT_STRING);
                else textView.setText(mAccount.getJSONObject(schemaName).getString("Value"));
                textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                textView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            }
            textView.setOnTouchListener(this);
            editText.setOnFocusChangeListener(this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void autoCompleteLoad(String schemaName, TextView textView, final AutoCompleteTextView autoView, List<String> itemList, String keyBuffer) {
        try {
            if (editBuffer.has(schemaName)) {
                if(editBuffer.getJSONObject(schemaName).isNull("Id")) textView.setText(REPLACEMENT_STRING);
                else textView.setText(keyBuffer);
                textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.statusRedFont));
                textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
            } else {
                if(mAccount.getJSONObject(schemaName).isNull("Name")) textView.setText(REPLACEMENT_STRING);
                else textView.setText(mAccount.getJSONObject(schemaName).getString("Name"));
                textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                textView.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, itemList);
            autoView.setAdapter(adapter);

            textView.setOnTouchListener(this);
            autoView.setOnFocusChangeListener(this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void switchLoad(String schemaName, TextView textView, final String labelTrue, final String labelFalse) {
        try {
            if (editBuffer.has(schemaName)) {
                textView.setText(editBuffer.getBoolean(schemaName) ? labelTrue : labelFalse);
                textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.statusRedFont));
                textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            } else {
                textView.setText(mAccount.getBoolean(schemaName) ? labelTrue : labelFalse);
                textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                textView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            }
            textView.setOnTouchListener(this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void spinnerLoad(final String schemaName, final TextView textView, final Spinner spinner, final List<String> valueLabel) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, valueLabel);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        try {
            if (editBuffer.has(schemaName)) {
                if(editBuffer.getJSONObject(schemaName).isNull("Value")) {
                    textView.setText(REPLACEMENT_STRING);
                    spinner.setSelection(0);
                } else {
                    textView.setText(valueLabel.get(editBuffer.getJSONObject(schemaName).getInt("Value")));
                    spinner.setSelection(editBuffer.getJSONObject(schemaName).getInt("Value"));
                }
                textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.statusRedFont));
                textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            } else {
                if(mAccount.getJSONObject(schemaName).isNull("Value")) {
                    textView.setText(REPLACEMENT_STRING);
                    spinner.setSelection(0);
                } else {
                    textView.setText(valueLabel.get(mAccount.getJSONObject(schemaName).getInt("Value")));
                    spinner.setSelection(mAccount.getJSONObject(schemaName).getInt("Value"));
                }
                textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                textView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            }
            textView.setOnTouchListener(this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    // Handle Focus Request
    public void basicFocusRequest(String schemaName, TextView textView, EditText editText) {
        try {
            textView.setVisibility(View.GONE);
            editText.setVisibility(View.VISIBLE);
            if(editBuffer.has(schemaName)) {
                if(editBuffer.isNull(schemaName)) editText.setText("");
                else editText.setText(editBuffer.getString(schemaName));
            } else {
                if(mAccount.isNull(schemaName)) editText.setText("");
                else editText.setText(mAccount.getString(schemaName));
            }
            editText.requestFocus();
            editText.selectAll();
            keyboard.showSoftInput(editText, 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void textValueFocusRequest(String schemaName, TextView textView, EditText editText) {
        try {
            textView.setVisibility(View.GONE);
            editText.setVisibility(View.VISIBLE);
            if(editBuffer.has(schemaName)) {
                if(editBuffer.getJSONObject(schemaName).isNull("Value")) editText.setText("");
                else editText.setText(editBuffer.getJSONObject(schemaName).getString("Value"));
            } else {
                if(mAccount.getJSONObject(schemaName).isNull("Value")) editText.setText("");
                else editText.setText(mAccount.getJSONObject(schemaName).getString("Value"));
            }
            editText.requestFocus();
            editText.selectAll();
            keyboard.showSoftInput(editText, 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void autoCompleteFocusRequest(String schemaName, TextView textView, AutoCompleteTextView autoView, String keyBuffer) {
        try {
            textView.setVisibility(View.GONE);
            autoView.setVisibility(View.VISIBLE);
            if(editBuffer.has(schemaName)) {
                if(editBuffer.isNull(schemaName)) autoView.setText("");
                else autoView.setText(keyBuffer);
            }
            else {
                if(mAccount.getJSONObject(schemaName).isNull("Name")) autoView.setText("");
                else autoView.setText(mAccount.getJSONObject(schemaName).getString("Name"));
            }
            autoView.requestFocus();
            autoView.selectAll();
            keyboard.showSoftInput(autoView, 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    // Handle Focus Loss
    public void basicFocusLoss(String schemaName, TextView textView, EditText editText) {
        String newData = editText.getText().toString();
        if(newData.equals("")) newData = null;
        try {
            if (newData != null && newData.equals(mAccount.getString(schemaName))) {
                if(editBuffer.has(schemaName)) editBuffer.remove(schemaName);
                textView.setText(newData);
                textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                textView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            } else if (newData == null && mAccount.isNull(schemaName)) {
                if(editBuffer.has(schemaName)) editBuffer.remove(schemaName);
                textView.setText(REPLACEMENT_STRING);
                textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                textView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            } else if (newData == null) {
                editBuffer.put(schemaName, JSONObject.NULL);
                textView.setText(REPLACEMENT_STRING);
                textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.statusRedFont));
                textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            } else {
                editBuffer.put(schemaName, newData);
                textView.setText(newData);
                textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.statusRedFont));
                textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        textView.setVisibility(View.VISIBLE);
        editText.setVisibility(View.GONE);
        keyboard.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        parent.toggleSaveButton();
    }
    public void textValueFocusLoss(String schemaName, TextView textView, EditText editText) {
        String newData = editText.getText().toString();
        if(newData.equals("")) newData = null;
        try {
            Log.i("NEWDATA", newData);
            if(newData == null) {
                if(mAccount.getJSONObject(schemaName).isNull("Value")) {
                    editBuffer.remove(schemaName);
                    textView.setText(REPLACEMENT_STRING);
                    textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                    textView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                } else {
                    editBuffer.put(schemaName, new JSONObject().put("Value", JSONObject.NULL));
                    textView.setText(REPLACEMENT_STRING);
                    textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.statusRedFont));
                    textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                }
            } else {
                if(mAccount.getJSONObject(schemaName).isNull("Value")) {
                    editBuffer.put(schemaName, new JSONObject().put("Value", newData));
                    textView.setText(newData);
                    textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.statusRedFont));
                    textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                } else if(Double.parseDouble(newData) == mAccount.getJSONObject(schemaName).getDouble("Value")) {
                    editBuffer.remove(schemaName);
                    textView.setText(newData);
                    textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                    textView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                } else {
                    editBuffer.put(schemaName, new JSONObject().put("Value", newData));
                    textView.setText(newData);
                    textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.statusRedFont));
                    textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        textView.setVisibility(View.VISIBLE);
        editText.setVisibility(View.GONE);
        keyboard.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        parent.toggleSaveButton();
    }
    public void spinnerFocusLoss(String schemaName, TextView textView, List<String> valueLabel, int position) {
        try {
            if (mAccount.getJSONObject(schemaName).isNull("Value")) {
                if(position == 0) {
                    editBuffer.remove(schemaName);
                    textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                    textView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                } else {
                    editBuffer.put(schemaName, new JSONObject().put("Value", position));
                    textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.statusRedFont));
                    textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                }
            } else {
                if(position == 0) {
                    editBuffer.put(schemaName, new JSONObject().put("Value", JSONObject.NULL));
                    textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.statusRedFont));
                    textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                } else if(position != mAccount.getJSONObject(schemaName).getInt("Value")) {
                    editBuffer.put(schemaName, new JSONObject().put("Value", position));
                    textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.statusRedFont));
                    textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                } else {
                    editBuffer.remove(schemaName);
                    textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                    textView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        textView.setText(valueLabel.get(position));
        //textView.setVisibility(View.VISIBLE);
        //spinner.setVisibility(View.GONE);
        parent.toggleSaveButton();
    }
    public void autoCompleteFocusLoss(String schemaName, TextView textView, AutoCompleteTextView autoView, Map<String, String> valueMap) {
        String newData = autoView.getText().toString();
        //if(newData.equals("")) newData = null;
        try {
            if(valueMap.containsKey(newData)) {
                if (valueMap.get(newData).equals(mAccount.getJSONObject(schemaName).getString("Id"))) {
                    if(editBuffer.has(schemaName)) editBuffer.remove(schemaName);
                    textView.setText(newData);
                    textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                    textView.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
                } else {
                    currencyBuffer = newData;
                    editBuffer.put(schemaName, new JSONObject().put("Id", valueMap.get(newData)));
                    textView.setText(newData);
                    textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.statusRedFont));
                    textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
                }
            } else if(newData.isEmpty()) {
                if (mAccount.getJSONObject(schemaName).isNull("Id")) {
                    if(editBuffer.has(schemaName)) editBuffer.remove(schemaName);
                    textView.setText(REPLACEMENT_STRING);
                    textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                    textView.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
                } else {
                    currencyBuffer = null;
                    editBuffer.put(schemaName, new JSONObject().put("Id", JSONObject.NULL));
                    textView.setText(REPLACEMENT_STRING);
                    textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.statusRedFont));
                    textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
                }
            } else {
                Toast.makeText(getContext(),"Record do not exist",Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        textView.setVisibility(View.VISIBLE);
        autoView.setVisibility(View.GONE);
        keyboard.hideSoftInputFromWindow(autoView.getWindowToken(), 0);
        parent.toggleSaveButton();
    }
    // Handle Text Switching
    public void switching(String schemaName, TextView textView, final String labelTrue, final String labelFalse) {
        try {
            if (editBuffer.has(schemaName)) {
                editBuffer.remove(schemaName);
                textView.setText(mAccount.getBoolean(schemaName) ? labelTrue : labelFalse);
                textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                textView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            } else {
                editBuffer.put(schemaName, !mAccount.getBoolean(schemaName));
                textView.setText(editBuffer.getBoolean(schemaName) ? labelTrue : labelFalse);
                textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.statusRedFont));
                textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            }
            textView.setOnTouchListener(this);
            parent.toggleSaveButton();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setParent(AccountDetailMain parent) {
        this.parent = parent;
        this.mAccount = parent.mAccount;
        this.editBuffer = parent.editBuffer;
        this.currencyList = parent.currencyList;
        this.currencyMap = parent.currencyMap;
    }
}
