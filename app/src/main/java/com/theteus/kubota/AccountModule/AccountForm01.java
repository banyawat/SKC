package com.theteus.kubota.AccountModule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.theteus.kubota.NtlmConnection;
import com.theteus.kubota.R;
import com.theteus.kubota.Reference;

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

    public AccountForm01() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_form01, container, false);

        findViewsById(view);
        getParentAutoComplete();

        return view;
    }

    private void findViewsById(final View view){
        mAccount_form1_name = (EditText) view.findViewById(R.id.account_form1_name);
        mAccount_form1_parent = (AutoCompleteTextView) view.findViewById(R.id.account_form1_parent);
        mAccount_form1_ticker = (EditText) view.findViewById(R.id.account_form1_ticker);
        mAccount_form1_phone = (EditText) view.findViewById(R.id.account_form1_phone);
        mAccount_form1_fax = (EditText) view.findViewById(R.id.account_form1_fax);
        mAccount_form1_web = (EditText) view.findViewById(R.id.account_form1_web);

        mAccount_form1_parent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mAccount_form1_parent.showDropDown();
                return false;
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
        Account_form1_parent = parentSearchId.get(parentSearchName.indexOf(Account_form1_parent));
        Account_form1_ticker = mAccount_form1_ticker.getText().toString();
        Account_form1_phone = mAccount_form1_phone.getText().toString();
        Account_form1_fax = mAccount_form1_fax.getText().toString();
        Account_form1_web = mAccount_form1_web.getText().toString();

        try {
            args.put("Name", Account_form1_name);
            args.put("TickerSymbol", Account_form1_ticker);
            args.put("ParentAccountId", new JSONObject().put("Id", Account_form1_parent));
            args.put("Telephone1", Account_form1_phone);
            args.put("Fax", Account_form1_fax);
            args.put("WebSiteURL", Account_form1_web);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return args;
    }

    private void getParentAutoComplete(){
        parentSearchId = new ArrayList<>();
        parentSearchName = new ArrayList<>();
        NtlmConnection conn = new NtlmConnection(Reference.PROTOCOL, Reference.HOSTNAME, Reference.PORT, Reference.DOMAIN,
                Reference.USERNAME, Reference.PASSWORD, Reference.ORGRANIZATION_PATH);
        try {
            conn.connect();
            conn.authenticate();
            NtlmConnection.Response response = conn.retrieve("Account", null, "$select=AccountId,Name");
            JSONArray result = parseBody(response.getResponseBody());
            for(int i=0;i<result.length();i++){
                JSONObject jsObj = result.getJSONObject(i);
                parentSearchName.add(jsObj.get("Name").toString());
                parentSearchId.add(jsObj.get("AccountId").toString());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, parentSearchName);
            mAccount_form1_parent.setAdapter(adapter);
        } catch (JSONException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JSONArray parseBody(String body){
        JSONObject json;
        JSONArray jsonResult = null;
        try {
            json = new JSONObject(body);
            json = new JSONObject(json.get("d").toString());
            jsonResult = new JSONArray(json.get("results").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonResult;
    }
}
