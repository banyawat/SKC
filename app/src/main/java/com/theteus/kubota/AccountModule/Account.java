package com.theteus.kubota.AccountModule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.theteus.kubota.CardViewPager;
import com.theteus.kubota.NtlmConnection;
import com.theteus.kubota.R;
import com.theteus.kubota.Reference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;

public class Account extends Fragment {
    private CardViewPager accountView;
    private int previousPosition;
    private JSONObject form1JSON;
    private JSONObject form2JSON;
    private JSONObject form3JSON;

    public Account() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        form1JSON = new JSONObject();
        form2JSON = new JSONObject();
        form3JSON = new JSONObject();

        accountView = new CardViewPager(this, view);
        accountView.addFragmentView(new AccountForm01(), "ข้อมูลทั่วไป");
        accountView.addFragmentView(new AccountForm02(), "ที่อยู่");
        accountView.addFragmentView(new AccountForm03(), "รายละเอียด");
        accountView.init(R.id.account_form_pager, R.id.account_nextButton);

        accountView.initFloatingButtonMethod(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(accountView.isLastPage()) {
                    retrieveData(2);
                    postInformation();
                }
                accountView.goToNextPage();
            }
        });
        accountView.mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                retrieveData(previousPosition);
                previousPosition=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return view;
    }

    private void retrieveData(int position){
        switch (position){
            case 0:
                AccountForm01 accountForm01 = (com.theteus.kubota.AccountModule.AccountForm01) accountView.getFragment(0);
                if(accountForm01!=null){
                    form1JSON = accountForm01.getAllData();
                }
                break;
            case 1:
                AccountForm02 accountForm02 = (com.theteus.kubota.AccountModule.AccountForm02) accountView.getFragment(1);
                if(accountForm02!=null){
                    form2JSON = accountForm02.getAllData();
                }
                break;
            case 2:
                AccountForm03 accountForm03 = (com.theteus.kubota.AccountModule.AccountForm03) accountView.getFragment(2);
                if(accountForm03!=null){
                    form3JSON = accountForm03.getAllData();
                }
                break;
            default:
                break;
        }
    }

    private void postInformation() {
        JSONObject request = mergeJSON(form1JSON, form2JSON);
        request = mergeJSON(request, form3JSON);

        NtlmConnection conn = new NtlmConnection(Reference.PROTOCOL, Reference.HOSTNAME, Reference.PORT, Reference.DOMAIN,
                Reference.USERNAME, Reference.PASSWORD, Reference.ORGRANIZATION_PATH);
        try {
            conn.connect();
            conn.authenticate();
            conn.create("Account", request);
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JSONObject mergeJSON(JSONObject json1, JSONObject json2){
        JSONObject merged = new JSONObject();
        JSONObject[] objs = new JSONObject[] { json1, json2 };
        for (JSONObject obj : objs) {
            Iterator it = obj.keys();
            while (it.hasNext()) {
                String key = (String)it.next();
                try {
                    merged.put(key, obj.get(key));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return merged;
    }
}
