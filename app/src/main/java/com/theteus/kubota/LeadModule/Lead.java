package com.theteus.kubota.LeadModule;

import android.os.Bundle;
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
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FragmentTabHost mTabHost;

    private ViewPager mPager;
    private ScreenSlidePagerAdapter mPagerAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Lead() {
        // Required empty public constructor
    }

    public static Lead newInstance(String param1, String param2) {
        Lead fragment = new Lead();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lead, container, false);

        initTabHost(view);
        initViewPager(view);

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
        mPager = (ViewPager) view.findViewById(R.id.lead_form_pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
        mPagerAdapter.addPage(new LeadForm01());
        mPagerAdapter.addPage(new LeadForm02());
        mPagerAdapter.addPage(new LeadForm03());
        mPager.setAdapter(mPagerAdapter);
        mPager.setOnPageChangeListener(this);
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
