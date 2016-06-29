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
 * Created by whorangester on 6/23/16.
 */
public class LeadDetailContent extends Fragment implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
    private FragmentTabHost mTabHost;
    private ViewPager mPager;
    private ScreenSlidePagerAdapter mPagerAdapter;
    private LeadInstance mLead;

    public LeadDetailContent() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(LeadDetailMain.ARG_PARAM1)) {
            mLead = DummyLeadInstance.LEAD_MAP.get(getArguments().getString(LeadDetailMain.ARG_PARAM1));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //LayoutTransition lt = new LayoutTransition();
        //lt.setDuration(50);
        //container.setLayoutTransition(lt);

        View rootView;
        if (mLead != null) {
            rootView = inflater.inflate(R.layout.fragment_lead_detail_content, container, false);
            setUpTabView(rootView, mLead.id);
        } else {
            rootView = inflater.inflate(R.layout.fragment_detail_blank, container, false);
        }
        return rootView;
    }

    public void setUpTabView(View view, String leadId) {
        Bundle args = new Bundle();
        args.putString(LeadDetailMain.ARG_PARAM1, leadId);

        mTabHost = (FragmentTabHost) view.findViewById(R.id.lead_detail_tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.lead_detail_tab_content);
        mTabHost.addTab(mTabHost.newTabSpec("DealerTab").setIndicator("Dealer Section"), LeadDetailDealer.class, args);
        mTabHost.addTab(mTabHost.newTabSpec("GeneralTab").setIndicator("ข้อมูลทั่วไป"), LeadDetailGeneral.class, args);
        mTabHost.addTab(mTabHost.newTabSpec("AddressTab").setIndicator("ที่อยู่"), LeadDetailAddress.class, args);
        mTabHost.addTab(mTabHost.newTabSpec("WishlistTab").setIndicator("สินค้าที่สนใจ"), LeadDetailWishlist.class, args);
        mTabHost.addTab(mTabHost.newTabSpec("ActivitiesTab").setIndicator("กิจกรรม"), LeadDetailActivities.class, args);
        mTabHost.setOnTabChangedListener(this);

        for (int i = 0; i < mTabHost.getTabWidget().getTabCount(); i++)
            mTabHost.getTabWidget().getChildAt(i).getLayoutParams().height = (int) (48 * this.getResources().getDisplayMetrics().density);

        mPager = (ViewPager) view.findViewById(R.id.lead_detail_pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());

        LeadDetailDealer dealerSection = new LeadDetailDealer();
        dealerSection.setArguments(args);
        mPagerAdapter.addPage(dealerSection);

        LeadDetailGeneral generalSection = new LeadDetailGeneral();
        generalSection.setArguments(args);
        mPagerAdapter.addPage(generalSection);

        LeadDetailAddress addressSection = new LeadDetailAddress();
        addressSection.setArguments(args);
        mPagerAdapter.addPage(addressSection);

        LeadDetailWishlist wishlistSection = new LeadDetailWishlist();
        wishlistSection.setArguments(args);
        mPagerAdapter.addPage(wishlistSection);

        LeadDetailActivities activitiesSection = new LeadDetailActivities();
        activitiesSection.setArguments(args);
        mPagerAdapter.addPage(activitiesSection);

        mPager.setAdapter(mPagerAdapter);
        mPager.addOnPageChangeListener(this);

        if(getArguments().containsKey(LeadDetailMain.ARG_PARAM2))
            setCurrentTab(getArguments().getInt(LeadDetailMain.ARG_PARAM2));
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
