package com.theteus.kubota.AccountModule;

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

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class AccountDetailContent extends Fragment implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
    //Content
    private JSONObject mAccount;
    private AccountDetailMain parent;
    //View
    private View rootView;
    private FragmentTabHost mTabHost;
    private ViewPager mPager;

    public AccountDetailContent() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mAccount != null) {
            rootView = inflater.inflate(R.layout.fragment_detail_tab, container, false);
            setUpTabView();
        } else {
            rootView = inflater.inflate(R.layout.fragment_detail_blank, container, false);
        }
        return rootView;
    }

    private void setUpTabView() {
        mTabHost = (FragmentTabHost) rootView.findViewById(R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.tab_content);
        mTabHost.addTab(mTabHost.newTabSpec("GeneralTab").setIndicator("ข้อมูลทั่วไป"), AccountDetailGeneral.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("AddressTab").setIndicator("ที่อยู่"), AccountDetailAddress.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("DetailsTab1").setIndicator("รายละเอียด (I)"), AccountDetailDetailsPart1.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("DetailsTab2").setIndicator("รายละเอียด (II)"), AccountDetailDetailsPart2.class, null);
        mTabHost.setOnTabChangedListener(this);

        for (int i = 0; i < mTabHost.getTabWidget().getTabCount(); i++)
            mTabHost.getTabWidget().getChildAt(i).getLayoutParams().height = (int) (48 * this.getResources().getDisplayMetrics().density);

        mPager = (ViewPager) rootView.findViewById(R.id.detail_pager);
        ScreenSlidePagerAdapter mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());

        AccountDetailGeneral generalSection = new AccountDetailGeneral();
        generalSection.setParent(parent);
        mPagerAdapter.addPage(generalSection);

        AccountDetailAddress addressSection = new AccountDetailAddress();
        addressSection.setParent(parent);
        mPagerAdapter.addPage(addressSection);

        AccountDetailDetailsPart1 detailSection1 = new AccountDetailDetailsPart1();
        detailSection1.setParent(parent);
        mPagerAdapter.addPage(detailSection1);

        AccountDetailDetailsPart2 detailSection2 = new AccountDetailDetailsPart2();
        detailSection2.setParent(parent);
        mPagerAdapter.addPage(detailSection2);

        mPager.setAdapter(mPagerAdapter);
        mPager.addOnPageChangeListener(this);

        if(getArguments().containsKey(AccountDetailMain.ARG_PARAM2))
            setCurrentTab(getArguments().getInt(AccountDetailMain.ARG_PARAM2));
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
    public void onTabChanged(String tabId) { this.mPager.setCurrentItem(this.mTabHost.getCurrentTab()); }

    public void setCurrentTab(int tabNumber) { mPager.setCurrentItem(tabNumber); }

    public void setmAccount(JSONObject mAccount) { this.mAccount = mAccount; }
    public void setParent(AccountDetailMain parent) { this.parent = parent; }
}
