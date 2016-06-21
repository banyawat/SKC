package com.theteus.kubota.LeadModule;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.theteus.kubota.R;
import com.theteus.kubota.ScreenSlidePagerAdapter;

public class Lead extends Fragment implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
    private FragmentTabHost mTabHost;

    private ViewPager mPager;
    private ScreenSlidePagerAdapter mPagerAdapter;

    FloatingActionButton nextButton;

    LeadForm01 lead1;
    LeadForm02 lead2;
    LeadForm03 lead3;

    public Lead() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lead, container, false);

        initTabHost(view);
        initViewPager(view);
        initFLoatingButton(view);

        return view;
    }

    private void initTabHost(View view){
        mTabHost = (FragmentTabHost)view.findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), android.R.id.tabcontent);
        mTabHost.addTab(mTabHost.newTabSpec("Tab1").setIndicator("ทั่วไป"),
                LeadForm01.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("Tab2").setIndicator("ที่อยู่"),
                LeadForm02.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("Tab3").setIndicator("สินค้าที่สนใจ"),
                LeadForm03.class, null);
        mTabHost.setOnTabChangedListener(this);
    }

    private void initViewPager(View view){
        lead1 = new LeadForm01();
        lead2 = new LeadForm02();
        lead3 = new LeadForm03();

        mPager = (ViewPager) view.findViewById(R.id.lead_form_pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
        mPagerAdapter.addPage(lead1);
        mPagerAdapter.addPage(lead2);
        mPagerAdapter.addPage(lead3);
        mPager.setOffscreenPageLimit(2);
        mPager.setAdapter(mPagerAdapter);
        mPager.setOnPageChangeListener(this);
    }

    private void initFLoatingButton(View view){
        nextButton = (FloatingActionButton) view.findViewById(R.id.lead_nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(mPager.getCurrentItem()+1);
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        this.mTabHost.setCurrentTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabChanged(String tabId) {
        this.mPager.setCurrentItem(this.mTabHost.getCurrentTab());
    }
}
