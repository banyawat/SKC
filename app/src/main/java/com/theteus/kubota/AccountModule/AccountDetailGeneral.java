package com.theteus.kubota.AccountModule;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
import java.util.Map;

public class AccountDetailGeneral extends Fragment implements View.OnTouchListener {
    //Constant
    private static final String REPLACEMENT_STRING = "- - -";
    //Content
    private JSONObject mAccount;
    //View
    private TextView accountNameText;
    private TextView parentAccountText;
    private TextView tickerText;
    private TextView phoneText;
    private TextView faxText;
    private TextView websiteText;
    private EditText accountNameEdit;
    private EditText tickerEdit;
    private InputMethodManager keyboard;

    public AccountDetailGeneral() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {

        if(getArguments() != null  && getArguments().containsKey(AccountDetailMain.ARG_PARAM2))
        try {
            mAccount = new JSONObject(getArguments().getString(AccountDetailMain.ARG_PARAM2));
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        tickerEdit = (EditText) view.findViewById(R.id.ticker_edit);
        keyboard = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        LinearLayout touchInterceptor = (LinearLayout) view.findViewById(R.id.layout);
        touchInterceptor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (accountNameEdit.isFocused()) {
                        Rect outRect = new Rect();
                        accountNameEdit.getGlobalVisibleRect(outRect);
                        if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                            accountNameText.setVisibility(View.VISIBLE);
                            accountNameEdit.setVisibility(View.GONE);
                            accountNameEdit.clearFocus();
                            keyboard.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        }
                    } else if (tickerEdit.isFocused()) {
                        Rect outRect = new Rect();
                        tickerEdit.getGlobalVisibleRect(outRect);
                        if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                            tickerText.setVisibility(View.VISIBLE);
                            tickerEdit.setVisibility(View.GONE);
                            tickerEdit.clearFocus();
                            keyboard.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        }
                    }
                }
                return false;
            }
        });

        if(mAccount != null) {
            try {
                if(mAccount.isNull(AccountSchema.ACCOUNT_NAME)) accountNameText.setText(REPLACEMENT_STRING);
                else accountNameText.setText(mAccount.getString(AccountSchema.ACCOUNT_NAME));
                accountNameText.setOnTouchListener(this);

                if(mAccount.getJSONObject(AccountSchema.PARENT_ACCOUNT).isNull("Name")) parentAccountText.setText(REPLACEMENT_STRING);
                else {
                    parentAccountText.setText(mAccount.getJSONObject(AccountSchema.PARENT_ACCOUNT).optString("Name"));
                    parentAccountText.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
                }
                parentAccountText.setOnTouchListener(this);

                if(mAccount.isNull(AccountSchema.TICKER_SYMBOL)) tickerText.setText(REPLACEMENT_STRING);
                else tickerText.setText(mAccount.getString(AccountSchema.TICKER_SYMBOL));
                tickerText.setOnTouchListener(this);

                if(mAccount.isNull(AccountSchema.MAIN_PHONE)) phoneText.setText(REPLACEMENT_STRING);
                else phoneText.setText(mAccount.getString(AccountSchema.MAIN_PHONE));

                if(mAccount.isNull(AccountSchema.FAX)) faxText.setText(REPLACEMENT_STRING);
                else faxText.setText(mAccount.getString(AccountSchema.FAX));

                if(mAccount.isNull(AccountSchema.WEBSITE)) websiteText.setText(REPLACEMENT_STRING);
                else websiteText.setText(mAccount.getString(AccountSchema.WEBSITE));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return view;
    }

    public void redirect(String accountId, int pageNumber) {
        Home home = (Home) getActivity();
        ScreenSlidePagerAdapter mPagerAdapter = home.getmPagerAdapter();

        AccountDetailMain fragment = new AccountDetailMain();
        Bundle args = new Bundle();
        if(accountId != null) args.putString(AccountDetailMain.ARG_PARAM1, accountId);
        if(pageNumber >= 0 && pageNumber <= 3) args.putInt(AccountDetailMain.ARG_PARAM3, 0);
        fragment.setArguments(args);
        mPagerAdapter.setPage(0, fragment);
        mPagerAdapter.notifyDataSetChanged();
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
        try {
            switch (v.getId()) {
                case R.id.account_name:
                    accountNameText.setVisibility(View.GONE);
                    accountNameEdit.setVisibility(View.VISIBLE);
                    accountNameEdit.setText(mAccount.getString(AccountSchema.ACCOUNT_NAME));
                    accountNameEdit.requestFocus();
                    accountNameEdit.selectAll();
                    keyboard.showSoftInput(accountNameEdit, 0);
                    break;
                case R.id.ticker:
                    tickerText.setVisibility(View.GONE);
                    tickerEdit.setVisibility(View.VISIBLE);
                    if(mAccount.isNull(AccountSchema.TICKER_SYMBOL)) tickerEdit.setText("");
                    else tickerEdit.setText(mAccount.getString(AccountSchema.TICKER_SYMBOL));
                    tickerEdit.requestFocus();
                    tickerEdit.selectAll();
                    keyboard.showSoftInput(tickerEdit, 0);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void tap(View v) {
        switch (v.getId()) {
            case R.id.parent_account:
                try {
                    Log.i("TOUCH", "DOWN");
                    if(!mAccount.getJSONObject(AccountSchema.PARENT_ACCOUNT).isNull("Name"))
                        redirect(mAccount.optJSONObject(AccountSchema.PARENT_ACCOUNT).getString("Id"), 0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
    }
}
