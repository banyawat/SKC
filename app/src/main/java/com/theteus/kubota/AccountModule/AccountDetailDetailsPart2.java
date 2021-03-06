package com.theteus.kubota.AccountModule;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.theteus.kubota.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by whorangester on 7/14/16.
 */
public class AccountDetailDetailsPart2 extends Fragment implements View.OnTouchListener, View.OnFocusChangeListener, AdapterView.OnItemSelectedListener {
    //Constant
    private static final String REPLACEMENT_STRING = "- - -";
    //Content
    private JSONObject mAccount;
    private JSONObject editBuffer;
    private AccountDetailMain parent;
    private List<String> shippingMethod;
    private List<String> freightTerms;
    //View
    private TextView descriptionText;
    private TextView emailText;
    private TextView bulkEmailText;
    private TextView phoneText;
    private TextView faxText;
    private TextView mailText;
    private TextView shippingText;
    private TextView freightText;
    private EditText descriptionEdit;
    private Spinner shippingEdit;
    private Spinner freightEdit;
    private InputMethodManager keyboard;

    public AccountDetailDetailsPart2() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        shippingMethod = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.account_shipping_method)));
        shippingMethod.set(0, "- - -");
        freightTerms = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.account_freight_terms)));
        freightTerms.set(0, "- - -");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_detail_details_part_2, container, false);

        descriptionText = (TextView) view.findViewById(R.id.description);
        emailText = (TextView) view.findViewById(R.id.email);
        bulkEmailText = (TextView) view.findViewById(R.id.bulk_email);
        phoneText = (TextView) view.findViewById(R.id.phone);
        faxText = (TextView) view.findViewById(R.id.fax);
        mailText = (TextView) view.findViewById(R.id.mail);
        shippingText = (TextView) view.findViewById(R.id.shipping_method);
        freightText = (TextView) view.findViewById(R.id.freight_terms);
        descriptionEdit = (EditText) view.findViewById(R.id.description_edit);
        shippingEdit = (Spinner) view.findViewById(R.id.shipping_method_edit);
        freightEdit = (Spinner) view.findViewById(R.id.freight_terms_edit);
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
            basicLoad(AccountSchema.DESCRIPTION, descriptionText, descriptionEdit);
            switchLoad(AccountSchema.CONTACT_PREFERENCE_EMAIL, emailText, "Not Allow", "Allow");
            switchLoad(AccountSchema.CONTACT_PREFERENCE_BULK_EMAIL, bulkEmailText, "Not Allow", "Allow");
            switchLoad(AccountSchema.CONTACT_PREFERENCE_PHONE, phoneText, "Not Allow", "Allow");
            switchLoad(AccountSchema.CONTACT_PREFERENCE_FAX, faxText, "Not Allow", "Allow");
            switchLoad(AccountSchema.CONTACT_PREFERENCE_MAIL, mailText, "Not Allow", "Allow");
            spinnerLoad(AccountSchema.SHIPPING_METHOD, shippingText, shippingEdit, shippingMethod);
            spinnerLoad(AccountSchema.FREIGHT_TERMS, freightText, freightEdit, freightTerms);
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
            case R.id.description:
                basicFocusRequest(AccountSchema.DESCRIPTION, descriptionText, descriptionEdit);
                break;
            case R.id.email:
                switching(AccountSchema.CONTACT_PREFERENCE_EMAIL, emailText, "Not Allow", "Allow");
                break;
            case R.id.bulk_email:
                switching(AccountSchema.CONTACT_PREFERENCE_BULK_EMAIL, bulkEmailText, "Not Allow", "Allow");
                break;
            case R.id.phone:
                switching(AccountSchema.CONTACT_PREFERENCE_PHONE, phoneText, "Not Allow", "Allow");
                break;
            case R.id.fax:
                switching(AccountSchema.CONTACT_PREFERENCE_FAX, faxText, "Not Allow", "Allow");
                break;
            case R.id.mail:
                switching(AccountSchema.CONTACT_PREFERENCE_MAIL, mailText, "Not Allow", "Allow");
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
                case R.id.description_edit:
                    basicFocusLoss(AccountSchema.DESCRIPTION, descriptionText, descriptionEdit);
                    break;
            }
    }
    // For Spinner Item Select Interaction
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.shipping_method_edit:
                spinnerFocusLoss(AccountSchema.SHIPPING_METHOD, shippingText, shippingMethod, position);
                break;
            case R.id.freight_terms_edit:
                spinnerFocusLoss(AccountSchema.FREIGHT_TERMS, freightText, freightTerms, position);
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
    }
}
