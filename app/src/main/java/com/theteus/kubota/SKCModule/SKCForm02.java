package com.theteus.kubota.SKCModule;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.theteus.kubota.CardViewPager;
import com.theteus.kubota.R;
import com.theteus.kubota.RepeatAddressForm;

public class SKCForm02 extends Fragment implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
    TabHost mTabHost;
    public SKCForm02() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_skcform02, container, false);

        /*mTabHost = (TabHost)view.findViewById(R.id.tabHost);
        mTabHost.setup();
        mTabHost.addTab(mTabHost.newTabSpec("Test").setIndicator("Test").setContent(android.R.id.tabcontent));
        mTabHost.addTab(mTabHost.newTabSpec("Test").setIndicator("Test").setContent(android.R.id.tabcontent));
        mTabHost.addTab(mTabHost.newTabSpec("Test").setIndicator("Test").setContent(android.R.id.tabcontent));*/
        return view;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabChanged(String tabId) {

    }
}
