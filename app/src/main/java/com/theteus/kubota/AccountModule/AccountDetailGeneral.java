package com.theteus.kubota.AccountModule;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.theteus.kubota.Home;
import com.theteus.kubota.R;
import com.theteus.kubota.ScreenSlidePagerAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountDetailGeneral extends Fragment implements View.OnTouchListener, View.OnFocusChangeListener {
    //Constant
    private static final String REPLACEMENT_STRING = "- - -";
    //Content
    private JSONObject mAccount;
    private JSONObject editBuffer;
    private String parentAccountBuffer;
    private List<String> mAccountNameList;
    private Map<String, String> mAccountIdMap;
    private AccountDetailMain parent;
    //View
    private TextView accountNameText;
    private TextView parentAccountText;
    private TextView tickerText;
    private TextView phoneText;
    private TextView faxText;
    private TextView websiteText;
    private EditText accountNameEdit;
    private AutoCompleteTextView parentAccountEdit;
    private EditText tickerEdit;
    private EditText phoneEdit;
    private EditText faxEdit;
    private EditText websiteEdit;
    private InputMethodManager keyboard;

    public AccountDetailGeneral() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_detail_general, container, false);

        accountNameText = (TextView) view.findViewById(R.id.account_name);
        parentAccountText = (TextView) view.findViewById(R.id.parent_account);
        tickerText = (TextView) view.findViewById(R.id.ticker);
        phoneText = (TextView) view.findViewById(R.id.phone);
        faxText = (TextView) view.findViewById(R.id.fax);
        websiteText = (TextView) view.findViewById(R.id.website);
        accountNameEdit = (EditText) view.findViewById(R.id.account_name_edit);
        parentAccountEdit = (AutoCompleteTextView) view.findViewById(R.id.parent_account_edit) ;
        tickerEdit = (EditText) view.findViewById(R.id.ticker_edit);
        phoneEdit = (EditText) view.findViewById(R.id.phone_edit);
        faxEdit = (EditText) view.findViewById(R.id.fax_edit);
        websiteEdit = (EditText) view.findViewById(R.id.website_edit);
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
            basicLoad(AccountSchema.ACCOUNT_NAME, accountNameText, accountNameEdit);
            autoCompleteLoad(AccountSchema.PARENT_ACCOUNT, parentAccountText, parentAccountEdit, mAccountNameList, parentAccountBuffer);
            basicLoad(AccountSchema.TICKER_SYMBOL, tickerText, tickerEdit);
            basicLoad(AccountSchema.MAIN_PHONE, phoneText, phoneEdit);
            basicLoad(AccountSchema.FAX, faxText, faxEdit);
            basicLoad(AccountSchema.WEBSITE, websiteText, websiteEdit);
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
            case R.id.account_name:
                basicFocusRequest(AccountSchema.ACCOUNT_NAME, accountNameText, accountNameEdit);
                break;
            case R.id.ticker:
                basicFocusRequest(AccountSchema.TICKER_SYMBOL, tickerText, tickerEdit);
                break;
            case R.id.parent_account:
                autoCompleteFocusRequest(AccountSchema.PARENT_ACCOUNT, parentAccountText, parentAccountEdit, parentAccountBuffer);
                break;
            case R.id.phone:
                basicFocusRequest(AccountSchema.MAIN_PHONE, phoneText, phoneEdit);
                break;
            case R.id.fax:
                basicFocusRequest(AccountSchema.FAX, faxText, faxEdit);
                break;
            case R.id.website:
                basicFocusRequest(AccountSchema.WEBSITE, websiteText, websiteEdit);
                break;
        }
    }
    public void tap(View v) {
        switch (v.getId()) {
            case R.id.parent_account:
                try {
                    Log.i("TOUCH", "DOWN");
                    if(!mAccount.getJSONObject(AccountSchema.PARENT_ACCOUNT).isNull("Name"))
                        redirect(mAccount.optJSONObject(AccountSchema.PARENT_ACCOUNT).getString("Id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(!hasFocus)
            switch (v.getId()) {
                case R.id.account_name_edit:
                    basicFocusLoss(AccountSchema.ACCOUNT_NAME, accountNameText, accountNameEdit);
                    break;
                case R.id.parent_account_edit:
                    autoCompleteFocusLoss(AccountSchema.PARENT_ACCOUNT, parentAccountText, parentAccountEdit, mAccountIdMap, parentAccountBuffer);
                    break;
                case R.id.ticker_edit:
                    basicFocusLoss(AccountSchema.TICKER_SYMBOL, tickerText, tickerEdit);
                    break;
                case R.id.phone_edit:
                    basicFocusLoss(AccountSchema.MAIN_PHONE, phoneText, phoneEdit);
                    break;
                case R.id.fax_edit:
                    basicFocusLoss(AccountSchema.FAX, faxText, faxEdit);
                    break;
                case R.id.website_edit:
                    basicFocusLoss(AccountSchema.WEBSITE, websiteText, websiteEdit);
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
    public void autoCompleteFocusLoss(String schemaName, TextView textView, AutoCompleteTextView autoView, Map<String, String> valueMap, String keyBuffer) {
        String newData = autoView.getText().toString();
        if(newData.equals("")) newData = null;
        try {
            if (newData != null && valueMap.get(newData).equals(mAccount.getJSONObject(schemaName).getString("Id"))) {
                if(editBuffer.has(schemaName)) editBuffer.remove(schemaName);
                textView.setText(newData);
                textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                textView.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
            } else if (newData == null && mAccount.getJSONObject(schemaName).isNull("Id")) {
                if(editBuffer.has(schemaName)) editBuffer.remove(schemaName);
                textView.setText(REPLACEMENT_STRING);
                textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                textView.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
            } else if (newData == null) {
                parentAccountBuffer = null;
                editBuffer.put(schemaName, new JSONObject().put("Id", JSONObject.NULL));
                textView.setText(REPLACEMENT_STRING);
                textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.statusRedFont));
                textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
            } else if (valueMap.containsKey(newData)) {
                parentAccountBuffer = newData;
                editBuffer.put(schemaName, new JSONObject().put("Id", valueMap.get(newData)));
                textView.setText(newData);
                textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.statusRedFont));
                textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        textView.setVisibility(View.VISIBLE);
        autoView.setVisibility(View.GONE);
        keyboard.hideSoftInputFromWindow(autoView.getWindowToken(), 0);
        parent.toggleSaveButton();
    }

    public void setmAccount(JSONObject mAccount) { this.mAccount = mAccount; }
    public void setEditBuffer(JSONObject editBuffer) { this.editBuffer = editBuffer; }
    public void setParent(AccountDetailMain parent) { this.parent = parent; }
    public void setmAccountNameList(List<String> nameList) { this.mAccountNameList = nameList; }
    public void setmAccountIdMap(Map<String, String> idMap) { this.mAccountIdMap = idMap; }
    public void redirect(String accountId) {
        Home home = (Home) getActivity();
        ScreenSlidePagerAdapter mPagerAdapter = home.getmPagerAdapter();

        AccountDetailMain fragment = new AccountDetailMain();
        Bundle args = new Bundle();
        if(accountId != null) args.putString(AccountDetailMain.ARG_PARAM1, accountId);
        fragment.setArguments(args);
        mPagerAdapter.setPage(0, fragment);
        mPagerAdapter.notifyDataSetChanged();
    }
}
