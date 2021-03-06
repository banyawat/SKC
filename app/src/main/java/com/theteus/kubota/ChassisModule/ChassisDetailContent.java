package com.theteus.kubota.ChassisModule;

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

/**
 * Created by whorangester on 6/29/16.
 */
public class ChassisDetailContent extends Fragment implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
    private ScreenSlidePagerAdapter mPagerAdapter;
    private ChassisInstance mChassis;
    //View
    private View rootView;
    private FragmentTabHost mTabHost;
    private ViewPager mPager;

    public ChassisDetailContent() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ChassisDetailMain.ARG_PARAM1)) {
            mChassis = DummyChassisInstance.CHASSIS_MAP.get(getArguments().getString(ChassisDetailMain.ARG_PARAM1));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mChassis != null) {
            rootView = inflater.inflate(R.layout.fragment_general_detail_tab, container, false);
            setUpTabView();
        } else {
            rootView = inflater.inflate(R.layout.fragment_general_detail_blank, container, false);
        }
        return rootView;
    }

    public void setUpTabView() {
        Bundle args = new Bundle();
        args.putString(ChassisDetailMain.ARG_PARAM1, getArguments().getString(ChassisDetailMain.ARG_PARAM1));

        mTabHost = (FragmentTabHost) rootView.findViewById(R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.tab_content);
        mTabHost.addTab(mTabHost.newTabSpec("GeneralTab").setIndicator("ข้อมูลทั่วไป"), ChassisDetailGeneral.class, args);
        mTabHost.addTab(mTabHost.newTabSpec("ContractTab").setIndicator("สัญญา"), ChassisDetailContract.class, args);
        mTabHost.addTab(mTabHost.newTabSpec("VipTab").setIndicator("วีไอพีการ์ด"), ChassisDetailVip.class, args);
        mTabHost.setOnTabChangedListener(this);

        for (int i = 0; i < mTabHost.getTabWidget().getTabCount(); i++)
            mTabHost.getTabWidget().getChildAt(i).getLayoutParams().height = (int) (48 * this.getResources().getDisplayMetrics().density);

        mPager = (ViewPager) rootView.findViewById(R.id.detail_pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());

        ChassisDetailGeneral generalSection = new ChassisDetailGeneral();
        generalSection.setArguments(args);
        mPagerAdapter.addPage(generalSection);

        ChassisDetailContract contractSection = new ChassisDetailContract();
        contractSection.setArguments(args);
        mPagerAdapter.addPage(contractSection);

        ChassisDetailVip vipSection = new ChassisDetailVip();
        vipSection.setArguments(args);
        mPagerAdapter.addPage(vipSection);

        mPager.setAdapter(mPagerAdapter);
        mPager.addOnPageChangeListener(this);

        if(getArguments().containsKey(ChassisDetailMain.ARG_PARAM2))
            setCurrentTab(getArguments().getInt(ChassisDetailMain.ARG_PARAM2));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        this.mTabHost.setCurrentTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

    @Override
    public void onTabChanged(String tabId) {
        this.mPager.setCurrentItem(this.mTabHost.getCurrentTab());
    }

    public void setCurrentTab(int tabNumber) {
        mPager.setCurrentItem(tabNumber);
    }
}
