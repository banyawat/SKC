package com.theteus.kubota;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.theteus.kubota.SKCModule.SKCForm01;
import com.theteus.kubota.SKCModule.SKCForm02;
import com.theteus.kubota.SKCModule.SKCForm03;

import java.util.ArrayList;

public class CardViewPager implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
    private FragmentTabHost mTabHost;

    private ViewPager mPager;
    private ScreenSlidePagerAdapter mPagerAdapter;

    FloatingActionButton nextButton;

    ArrayList<Fragment> viewList;
    ArrayList<String> tabNameList;

    Fragment mainFragment;
    View mainView;

    public CardViewPager(Fragment mainFragment, View mainView){
        viewList = new ArrayList<Fragment>();
        tabNameList = new ArrayList<String>();
        this.mainView = mainView;
        this.mainFragment = mainFragment;
    }

    public void addFragmentView(Fragment subFragment, String tabName){
        viewList.add(subFragment);
        tabNameList.add(tabName);
    }

    public void init(int pagerViewId, int nextButtonViewId){
        initTabHost(mainFragment, mainView);
        initViewPager(mainView.findViewById(pagerViewId));
        initFLoatingButton(mainView.findViewById(nextButtonViewId));
    }

    public void init(int pagerViewId){
        initTabHost(mainFragment, mainView);
        initViewPager(mainView.findViewById(pagerViewId));
    }

    private void initTabHost(Fragment mainFragment, View view){
        mTabHost = (FragmentTabHost)view.findViewById(android.R.id.tabhost);
        mTabHost.setup(mainFragment.getActivity(), mainFragment.getChildFragmentManager(), android.R.id.tabcontent);

        for(int i=0;i<viewList.size();i++){
            mTabHost.addTab(mTabHost.newTabSpec("Tab").setIndicator(tabNameList.get(i)),
                    viewList.get(i).getClass(), null);
        }
        mTabHost.setOnTabChangedListener(this);
        TabWidget widget = mTabHost.getTabWidget();
        for(int i=0;i<widget.getChildCount();i++){
            widget.getChildAt(i).setBackgroundResource(R.drawable.tab_indicator_ab_erodoi);
        }
    }

    private void initViewPager(View pagerView){

        mPager = (ViewPager) pagerView;
        mPagerAdapter = new ScreenSlidePagerAdapter(mainFragment.getFragmentManager());
        for(Fragment frag: viewList)
            mPagerAdapter.addPage(frag);
        mPager.setOffscreenPageLimit(viewList.size()-1);
        mPager.setAdapter(mPagerAdapter);
        mPager.setOnPageChangeListener(this);
    }

    private void initFLoatingButton(View floatingButtonView){
        if(floatingButtonView!=null) {
            nextButton = (FloatingActionButton) floatingButtonView;
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                }
            });
        }
    }

    public void setOrientation(){
        mTabHost.getTabWidget().setOrientation(LinearLayout.VERTICAL);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        this.mTabHost.setCurrentTab(position);
        if(position==mTabHost.getChildCount()+1)
            nextButton.setImageResource(R.drawable.ic_check);
        else
            nextButton.setImageResource(R.drawable.ic_arrow_forward);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabChanged(String tabId) {
        this.mPager.setCurrentItem(this.mTabHost.getCurrentTab());
    }


}
