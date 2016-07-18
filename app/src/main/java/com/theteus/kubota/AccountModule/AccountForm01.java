package com.theteus.kubota.AccountModule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.theteus.kubota.NtlmConnection;
import com.theteus.kubota.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AccountForm01 extends Fragment {
    private EditText mAccount_form1_name;
    private AutoCompleteTextView mAccount_form1_parent;
    private EditText mAccount_form1_ticker;
    private EditText mAccount_form1_phone;
    private EditText mAccount_form1_fax;
    private EditText mAccount_form1_web;

    private List<String> parentSearchId;
    private List<String> parentSearchName;

    public AccountForm01() {}

    public AccountForm01(NtlmConnection conn) {
        getParentAutoComplete(conn);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_form01, container, false);
        findViewsById(view);
        return view;
    }

    private void findViewsById(final View view){
        mAccount_form1_name = (EditText) view.findViewById(R.id.account_form1_name);
        mAccount_form1_parent = (AutoCompleteTextView) view.findViewById(R.id.account_form1_parent);
        mAccount_form1_ticker = (EditText) view.findViewById(R.id.account_form1_ticker);
        mAccount_form1_phone = (EditText) view.findViewById(R.id.account_form1_phone);
        mAccount_form1_fax = (EditText) view.findViewById(R.id.account_form1_fax);
        mAccount_form1_web = (EditText) view.findViewById(R.id.account_form1_web);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, parentSearchName);
        mAccount_form1_parent.setAdapter(adapter);

        mAccount_form1_parent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mAccount_form1_parent.showDropDown();
                return false;
            }
        });

        setTextChangedValidate();
    }

    private void setTextChangedValidate() {
        mAccount_form1_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(mAccount_form1_name!=null) {
                    if (s.length() != 0)
                        mAccount_form1_name.setError(null);
                    else
                        mAccount_form1_name.setError("You muse provide a name value");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mAccount_form1_parent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(mAccount_form1_parent!=null&&parentSearchName!=null){
                    if (parentSearchName.contains(s.toString()) || s.length() == 0) {
                        mAccount_form1_parent.setError(null);
                    }
                    else
                        mAccount_form1_parent.setError("No records found");
                }
            }
        });
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
        if(Account_form1_parent.length()!=0)
            Account_form1_parent = parentSearchId.get(parentSearchName.indexOf(Account_form1_parent));
        Account_form1_ticker = mAccount_form1_ticker.getText().toString();
        Account_form1_phone = mAccount_form1_phone.getText().toString();
        Account_form1_fax = mAccount_form1_fax.getText().toString();
        Account_form1_web = mAccount_form1_web.getText().toString();

        try {
            args.put("Name", Account_form1_name);
            if(Account_form1_ticker.length()!=0)
                args.put("TickerSymbol", Account_form1_ticker);
            args.put("ParentAccountId", new JSONObject().put("Id", Account_form1_parent));
            if(Account_form1_phone.length()!=0)
                args.put("Telephone1", Account_form1_phone);
            if(Account_form1_fax.length()!=0)
                args.put("Fax", Account_form1_fax);
            if(Account_form1_web.length()!=0)
                args.put("WebSiteURL", Account_form1_web);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return args;
    }

    private void getParentAutoComplete(NtlmConnection conn){
        parentSearchId = new ArrayList<>();
        parentSearchName = new ArrayList<>();
        try {
            NtlmConnection.Response response = conn.retrieve("Account", null, "$select=AccountId,Name");
            JSONArray result = new JSONObject(response.getResponseBody()).getJSONObject("d").optJSONArray("results");
            for(int i=0;i<result.length();i++){
                JSONObject jsObj = result.getJSONObject(i);
                parentSearchName.add(jsObj.get("Name").toString());
                parentSearchId.add(jsObj.get("AccountId").toString());
            }
        } catch (JSONException | IOException e){
            e.printStackTrace();
        }
    }
}
