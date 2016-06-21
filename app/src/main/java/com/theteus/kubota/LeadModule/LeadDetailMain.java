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

/**
 * Created by whorangester on 6/21/16.
 */
public class LeadDetailMain extends Fragment implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
    private FragmentTabHost mTabHost;
    private ViewPager mPager;
    private ScreenSlidePagerAdapter mPagerAdapter;

    public LeadDetailMain() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lead_detail_main, container, false);

        mTabHost = (FragmentTabHost) view.findViewById(R.id.lead_detail_tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.lead_detail_tab_content);
        mTabHost.addTab(mTabHost.newTabSpec("DealerTab").setIndicator("Dealer Section"), LeadDetailDealer.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("GeneralTab").setIndicator("ข้อมูลทั่วไป"), LeadDetailGeneral.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("AddressTab").setIndicator("ที่อยู่"), LeadDetailAddress.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("WishlistTab").setIndicator("สินค้าที่สนใจ"), LeadDetailWishlist.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("ActivitiesTab").setIndicator("กิจกรรม"), LeadDetailActivities.class, null);
        mTabHost.setOnTabChangedListener(this);

        for (int i = 0; i < mTabHost.getTabWidget().getTabCount(); i++)
            mTabHost.getTabWidget().getChildAt(i).getLayoutParams().height = (int) (48 * this.getResources().getDisplayMetrics().density);

        mPager = (ViewPager) view.findViewById(R.id.lead_detail_pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
        mPagerAdapter.addPage(new LeadDetailDealer());
        mPagerAdapter.addPage(new LeadDetailGeneral());
        mPagerAdapter.addPage(new LeadDetailAddress());
        mPagerAdapter.addPage(new LeadDetailWishlist());
        mPagerAdapter.addPage(new LeadDetailActivities());
        mPager.setAdapter(mPagerAdapter);
        mPager.addOnPageChangeListener(this);

        return view;
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
}
