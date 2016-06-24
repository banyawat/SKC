package com.theteus.kubota.AccountModule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.theteus.kubota.CardViewPager;
import com.theteus.kubota.R;

public class Account extends Fragment {
    public Account() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        CardViewPager accountView = new CardViewPager(this, view);
        accountView.addFragmentView(new AccountForm01(), "ข้อมูลทั่วไป");
        accountView.addFragmentView(new AccountForm02(), "ที่อยู่");
        accountView.addFragmentView(new AccountForm03(), "รายละเอียด");
        accountView.init(R.id.account_form_pager, R.id.account_nextButton);

        return view;
    }
}
