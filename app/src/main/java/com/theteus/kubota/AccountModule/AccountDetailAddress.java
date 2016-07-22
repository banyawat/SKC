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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.theteus.kubota.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.List;
import java.util.Map;

/**
 * Created by whorangester on 7/13/16.
 */
public class AccountDetailAddress extends Fragment implements View.OnTouchListener, View.OnFocusChangeListener {
    //Constant
    private static final String REPLACEMENT_STRING = "- - -";
    //Content
    private JSONObject mAccount;
    private JSONObject editBuffer;
    private AccountDetailMain parent;
    //View
    private TextView street1Text;
    private TextView street2Text;
    private TextView street3Text;
    private TextView cityText;
    private TextView stateText;
    private TextView postalText;
    private TextView countryText;
    private EditText street1Edit;
    private EditText street2Edit;
    private EditText street3Edit;
    private EditText cityEdit;
    private EditText stateEdit;
    private EditText postalEdit;
    private EditText countryEdit;
    private InputMethodManager keyboard;

    public AccountDetailAddress() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_detail_address, container, false);

        street1Text = (TextView) view.findViewById(R.id.street_1);
        street2Text = (TextView) view.findViewById(R.id.street_2);
        street3Text = (TextView) view.findViewById(R.id.street_3);
        cityText = (TextView) view.findViewById(R.id.city);
        stateText = (TextView) view.findViewById(R.id.state);
        postalText = (TextView) view.findViewById(R.id.postal_code);
        countryText = (TextView) view.findViewById(R.id.country);
        street1Edit = (EditText) view.findViewById(R.id.street_1_edit);
        street2Edit = (EditText) view.findViewById(R.id.street_2_edit) ;
        street3Edit = (EditText) view.findViewById(R.id.street_3_edit);
        cityEdit = (EditText) view.findViewById(R.id.city_edit);
        stateEdit = (EditText) view.findViewById(R.id.state_edit);
        postalEdit = (EditText) view.findViewById(R.id.postal_code_edit);
        countryEdit = (EditText) view.findViewById(R.id.country_edit);
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
            basicLoad(AccountSchema.ADDRESS_STREET1, street1Text, street1Edit);
            basicLoad(AccountSchema.ADDRESS_STREET2, street2Text, street2Edit);
            basicLoad(AccountSchema.ADDRESS_STREET3, street3Text, street3Edit);
            basicLoad(AccountSchema.ADDRESS_CITY, cityText, cityEdit);
            basicLoad(AccountSchema.ADDRESS_STATE, stateText, stateEdit);
            basicLoad(AccountSchema.ADDRESS_POSTAL_CODE, postalText, postalEdit);
            basicLoad(AccountSchema.ADDRESS_COUNTRY, countryText, countryEdit);
        }

        return view;
    }

    // Value
    private long touchDownMs = 0;

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
            case R.id.street_1:
                basicFocusRequest(AccountSchema.ADDRESS_STREET1, street1Text, street1Edit);
                break;
            case R.id.street_2:
                basicFocusRequest(AccountSchema.ADDRESS_STREET2, street2Text, street2Edit);
                break;
            case R.id.street_3:
                basicFocusRequest(AccountSchema.ADDRESS_STREET3, street3Text, street3Edit);
                break;
            case R.id.city:
                basicFocusRequest(AccountSchema.ADDRESS_CITY, cityText, cityEdit);
                break;
            case R.id.state:
                basicFocusRequest(AccountSchema.ADDRESS_STATE, stateText, stateEdit);
                break;
            case R.id.postal_code:
                basicFocusRequest(AccountSchema.ADDRESS_POSTAL_CODE, postalText, postalEdit);
                break;
            case R.id.country:
                basicFocusRequest(AccountSchema.ADDRESS_COUNTRY, countryText, countryEdit);
                break;

        }
    }
    public void tap(View v) {
        // Do Tap Action
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(!hasFocus)
            switch (v.getId()) {
                case R.id.street_1_edit:
                    basicFocusLoss(AccountSchema.ADDRESS_STREET1, street1Text, street1Edit);
                    break;
                case R.id.street_2_edit:
                    basicFocusLoss(AccountSchema.ADDRESS_STREET2, street2Text, street2Edit);
                    break;
                case R.id.street_3_edit:
                    basicFocusLoss(AccountSchema.ADDRESS_STREET3, street3Text, street3Edit);
                    break;
                case R.id.city_edit:
                    basicFocusLoss(AccountSchema.ADDRESS_CITY, cityText, cityEdit);
                    break;
                case R.id.state_edit:
                    basicFocusLoss(AccountSchema.ADDRESS_STATE, stateText, stateEdit);
                    break;
                case R.id.postal_code_edit:
                    basicFocusLoss(AccountSchema.ADDRESS_POSTAL_CODE, postalText, postalEdit);
                    break;
                case R.id.country_edit:
                    basicFocusLoss(AccountSchema.ADDRESS_COUNTRY, countryText, countryEdit);
                    break;

            }
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
    // Handle Focus Request
    public void basicFocusRequest(String schemaName, TextView textView, EditText editText) {
        try {
            textView.setVisibility(View.GONE);
            editText.setVisibility(View.VISIBLE);
            if(editBuffer.has(schemaName)) {
                if(editBuffer.isNull(schemaName)) editText.setText("");
                else editText.setText(editBuffer.getString(schemaName));
            }
            else {
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

    public void setParent(AccountDetailMain parent) {
        this.parent = parent;
        this.mAccount = parent.mAccount;
        this.editBuffer = parent.editBuffer;
    }
}
