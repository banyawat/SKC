package com.theteus.kubota.AccountModule;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.theteus.kubota.CardViewPager;
import com.theteus.kubota.Home;
import com.theteus.kubota.OrganizationDataService.AsyncResponse;
import com.theteus.kubota.OrganizationDataService.CreateService;
import com.theteus.kubota.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class Account extends Fragment {
    private final static String GENERAL_MENU = "ข้อมูลทั่วไป";
    private final static String ADDRESS_MENU = "ที่อยู่";
    private final static String DETAIL_MENU = "รายละเอียด";
    private final static String ACCOUNT_ENTITY = "Account";
    private final static String TEXT_ERROR = "Get some error(s)!";
    private CardViewPager accountView;
    private int previousPosition;
    private JSONObject form1JSON;
    private JSONObject form2JSON;
    private JSONObject form3JSON;

    public Account() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        form1JSON = new JSONObject();
        form2JSON = new JSONObject();
        form3JSON = new JSONObject();
        accountView = new CardViewPager(this, view);
        initView();
        return view;
    }

    private void initView(){
        accountView.addFragmentView(new AccountForm01(), GENERAL_MENU);
        accountView.addFragmentView(new AccountForm02(), ADDRESS_MENU);
        accountView.addFragmentView(new AccountForm03(), DETAIL_MENU);
        accountView.init(R.id.account_form_pager, R.id.account_nextButton);
        accountView.initFloatingButtonMethod(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accountView.isLastPage()) {
                    retrieveData(2);
                    if(form3JSON == null)
                        warning();
                    else
                        postInformation();
                }
                accountView.goToNextPage();
            }
        });

        accountView.mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {
                int temp = previousPosition;
                previousPosition = position;
                retrieveData(temp);
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    private void retrieveData(int position){
        switch (position){
            case 0:
                AccountForm01 accountForm01 = (com.theteus.kubota.AccountModule.AccountForm01) accountView.getFragment(0);
                if(accountForm01!=null){
                    form1JSON = accountForm01.getAllData();
                    if(form1JSON == null){
                        accountView.mPager.setCurrentItem(0);
                        previousPosition=0;
                        warning();
                    }
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
                    if(form3JSON == null){
                        accountView.mPager.setCurrentItem(2);
                        previousPosition=2;
                        warning();
                    }
                }
                break;
            default:
                break;
        }
    }

    private void postInformation() {
        JSONObject request = mergeJSON(form1JSON, form2JSON);
        request = mergeJSON(request, form3JSON);
        new CreateService(getActivity(), new AsyncResponse() {
            @Override
            public void onFinishTask(JSONObject result) {
                Home home = (Home) getActivity();
                home.goTo(5);
            }
        })
                .setEntity(ACCOUNT_ENTITY)
                .setJSONEntry(request)
                .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private static JSONObject mergeJSON(JSONObject json1, JSONObject json2){
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

    private void warning(){
        Toast.makeText(getContext(), TEXT_ERROR, Toast.LENGTH_SHORT).show();
    }
}
