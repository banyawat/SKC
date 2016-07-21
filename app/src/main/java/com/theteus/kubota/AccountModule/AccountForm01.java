package com.theteus.kubota.AccountModule;

import android.os.AsyncTask;
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

import com.theteus.kubota.OrganizationDataService.AsyncResponse;
import com.theteus.kubota.OrganizationDataService.RetrieveService;
import com.theteus.kubota.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AccountForm01 extends Fragment {
    private final static String NAME_ERROR = "You must provide a name value";
    private final static String FIELD_ID = "Id";
    private final static String QUERY_STRING = "$select=AccountId,Name";
    private final static String FIELD_RESULTS = "results";
    private final static String FIELD_D = "d";
    private EditText mAccount_form1_name;
    private AutoCompleteTextView mAccount_form1_parent;
    private EditText mAccount_form1_ticker;
    private EditText mAccount_form1_phone;
    private EditText mAccount_form1_fax;
    private EditText mAccount_form1_web;
    private List<String> parentSearchId;
    private List<String> parentSearchName;

    public AccountForm01() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_form01, container, false);
        getParentAutoComplete();
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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(mAccount_form1_name!=null) {
                    if (s.length() != 0)
                        mAccount_form1_name.setError(null);
                    else
                        mAccount_form1_name.setError("You muse provide a name value");
                }
            }
        });
        mAccount_form1_parent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if(mAccount_form1_parent!=null&&parentSearchName!=null){
                    if (parentSearchName.contains(s.toString()) || s.length() == 0)
                        mAccount_form1_parent.setError(null);
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
        if(mAccount_form1_name.length()==0){
            mAccount_form1_name.setError(NAME_ERROR);
            return null;
        }
        Account_form1_parent = mAccount_form1_parent.getText().toString();
        if (Account_form1_parent.length() != 0) {
            if (mAccount_form1_parent.getError() == null)
                Account_form1_parent = parentSearchId.get(parentSearchName.indexOf(Account_form1_parent));
            else
                return null;
        }
        Account_form1_ticker = mAccount_form1_ticker.getText().toString();
        Account_form1_phone = mAccount_form1_phone.getText().toString();
        Account_form1_fax = mAccount_form1_fax.getText().toString();
        Account_form1_web = mAccount_form1_web.getText().toString();

        try {
            if(Account_form1_name.length()!=0)
                args.put(AccountSchema.ACCOUNT_NAME, Account_form1_name);
            if(Account_form1_ticker.length()!=0)
                args.put(AccountSchema.TICKER_SYMBOL, Account_form1_ticker);
            if (Account_form1_parent.length() != 0)
                args.put(AccountSchema.PARENT_ACCOUNT, new JSONObject().put(FIELD_ID, Account_form1_parent));
            if(Account_form1_phone.length()!=0)
                args.put(AccountSchema.MAIN_PHONE, Account_form1_phone);
            if(Account_form1_fax.length()!=0)
                args.put(AccountSchema.FAX, Account_form1_fax);
            if(Account_form1_web.length()!=0)
                args.put(AccountSchema.WEBSITE, Account_form1_web);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return args;
    }

    private void getParentAutoComplete(){
        parentSearchId = new ArrayList<>();
        parentSearchName = new ArrayList<>();
        new RetrieveService(getActivity(), new AsyncResponse() {
            @Override
            public void onFinishTask(JSONObject result) {
                try {
                    JSONArray result1 = result.getJSONObject(FIELD_D).getJSONArray(FIELD_RESULTS);
                    for(int i=0;i<result1.length();i++){
                        JSONObject jsObj = result1.getJSONObject(i);
                        parentSearchName.add(jsObj.get(AccountSchema.ACCOUNT_NAME).toString());
                        parentSearchId.add(jsObj.get(AccountSchema.IDENTIFIER).toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        })
                .setEntity(AccountSchema.ENTITY_NAME)
                .setQueryString(QUERY_STRING)
                .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}
