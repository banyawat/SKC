package com.theteus.kubota.AccountModule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.theteus.kubota.R;

import org.json.JSONException;
import org.json.JSONObject;

public class AccountForm01 extends Fragment {
    private EditText mAccount_form1_name;
    private EditText mAccount_form1_parent;
    private EditText mAccount_form1_ticker;
    private EditText mAccount_form1_phone;
    private EditText mAccount_form1_fax;
    private EditText mAccount_form1_web;


    public AccountForm01() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_form01, container, false);
        findViewsById(view);

        return view;
    }

    private void findViewsById(View view){
        mAccount_form1_name = (EditText) view.findViewById(R.id.account_form1_name);
        mAccount_form1_parent = (EditText) view.findViewById(R.id.account_form1_parent);
        mAccount_form1_ticker = (EditText) view.findViewById(R.id.account_form1_ticker);
        mAccount_form1_phone = (EditText) view.findViewById(R.id.account_form1_phone);
        mAccount_form1_fax = (EditText) view.findViewById(R.id.account_form1_fax);
        mAccount_form1_web = (EditText) view.findViewById(R.id.account_form1_web);
    }

    public JSONObject getAllData(){
        JSONObject args = new JSONObject();
        String Account_form1_name;
        String Account_form1_parent;
        String Account_form1_ticker;
        String Account_form1_phone;
        String Account_form1_fax;
        String Account_form1_web;

        Account_form1_name = mAccount_form1_name.getText().toString();
        Account_form1_parent = mAccount_form1_parent.getText().toString();
        Account_form1_ticker = mAccount_form1_ticker.getText().toString();
        Account_form1_phone = mAccount_form1_phone.getText().toString();
        Account_form1_fax = mAccount_form1_fax.getText().toString();
        Account_form1_web = mAccount_form1_web.getText().toString();

        try {
            args.put("Name", Account_form1_name);
            args.put("TickerSymbol", Account_form1_ticker);
            args.put("Telephone1", Account_form1_phone);
            args.put("Fax", Account_form1_fax);
            args.put("WebSiteURL", Account_form1_web);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return args;
    }
}
