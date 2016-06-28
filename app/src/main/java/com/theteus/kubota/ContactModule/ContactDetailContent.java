package com.theteus.kubota.ContactModule;

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
 * Created by whorangester on 6/28/16.
 */
public class ContactDetailContent extends Fragment implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
    private FragmentTabHost mTabHost;
    private ViewPager mPager;
    private ScreenSlidePagerAdapter mPagerAdapter;
    private ContactInstance mContact;

    public ContactDetailContent() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ContactDetailMain.ARG_PARAM1)) {
            mContact = DummyContactInstance.CONTACT_MAP.get(getArguments().getString(ContactDetailMain.ARG_PARAM1));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        //if (mContact != null) {
            rootView = inflater.inflate(R.layout.fragment_contact_detail_content, container, false);
            setUpTabView(rootView, mContact.id);
        //} else {
        //    rootView = inflater.inflate(R.layout.fragment_contact_detail_blank, container, false);
        //}
        return rootView;
    }

    public void setUpTabView(View view, String contactId) {
        Bundle args = new Bundle();
        args.putString(ContactDetailMain.ARG_PARAM1, contactId);

        mTabHost = (FragmentTabHost) view.findViewById(R.id.contact_detail_tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.contact_detail_tab_content);
        mTabHost.addTab(mTabHost.newTabSpec("GeneralTab").setIndicator("ข้อมูลทั่วไป"), ContactDetailGeneral.class, args);
        mTabHost.addTab(mTabHost.newTabSpec("AddressTab").setIndicator("ที่อยู่"), ContactDetailAddress.class, args);
        mTabHost.addTab(mTabHost.newTabSpec("QuestionnaireTab").setIndicator("แบบสอบถาม"), ContactDetailQuestionnaire.class, args);
        mTabHost.addTab(mTabHost.newTabSpec("ChassisTab").setIndicator("หมายเลขรถ"), ContactDetailChassis.class, args);
        mTabHost.addTab(mTabHost.newTabSpec("LeadTab").setIndicator("Leads"), ContactDetailLead.class, args);
        mTabHost.setOnTabChangedListener(this);

        for (int i = 0; i < mTabHost.getTabWidget().getTabCount(); i++)
            mTabHost.getTabWidget().getChildAt(i).getLayoutParams().height = (int) (48 * this.getResources().getDisplayMetrics().density);

        mPager = (ViewPager) view.findViewById(R.id.contact_detail_pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());

        ContactDetailGeneral generalSection = new ContactDetailGeneral();
        generalSection.setArguments(args);
        mPagerAdapter.addPage(generalSection);

        ContactDetailAddress addressSection = new ContactDetailAddress();
        addressSection.setArguments(args);
        mPagerAdapter.addPage(addressSection);

        ContactDetailQuestionnaire questionnaireSection = new ContactDetailQuestionnaire();
        questionnaireSection.setArguments(args);
        mPagerAdapter.addPage(questionnaireSection);

        ContactDetailChassis chassisSection = new ContactDetailChassis();
        chassisSection.setArguments(args);
        mPagerAdapter.addPage(chassisSection);

        ContactDetailLead leadSection = new ContactDetailLead();
        leadSection.setArguments(args);
        mPagerAdapter.addPage(leadSection);

        mPager.setAdapter(mPagerAdapter);
        mPager.addOnPageChangeListener(this);

        if(getArguments().containsKey(ContactDetailMain.ARG_PARAM2))
            setCurrentTab(getArguments().getInt(ContactDetailMain.ARG_PARAM2));
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
