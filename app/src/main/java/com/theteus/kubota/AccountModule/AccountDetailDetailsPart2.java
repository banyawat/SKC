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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.theteus.kubota.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by whorangester on 7/14/16.
 */
public class AccountDetailDetailsPart2 extends Fragment implements View.OnTouchListener, View.OnFocusChangeListener {
    //Constant
    private static final String REPLACEMENT_STRING = "- - -";
    //Content
    private JSONObject mAccount;
    private JSONObject editBuffer;
    private AccountDetailMain parent;
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
    private InputMethodManager keyboard;

    public AccountDetailDetailsPart2() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
        descriptionEdit = (EditText) view.findViewById(R.id.description_edit);
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

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(!hasFocus)
            switch (v.getId()) {
                case R.id.description_edit:
                    basicFocusLoss(AccountSchema.DESCRIPTION, descriptionText, descriptionEdit);
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

    public void setmAccount(JSONObject mAccount) { this.mAccount = mAccount; }
    public void setEditBuffer(JSONObject editBuffer) { this.editBuffer = editBuffer; }
    public void setParent(AccountDetailMain parent) { this.parent = parent; }
}
