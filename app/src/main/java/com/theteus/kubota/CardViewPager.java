package com.theteus.kubota;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;

import java.util.ArrayList;

public class CardViewPager implements TabHost.OnTabChangeListener {
    private FragmentTabHost mTabHost;
    private ScreenSlidePagerAdapter mPagerAdapter;
    public ViewPager mPager;

    FloatingActionButton nextButton;

    ArrayList<Fragment> viewList;
    ArrayList<String> tabNameList;

    Fragment mainFragment;
    View mainView;

    public CardViewPager(Fragment mainFragment, View mainView){
        viewList = new ArrayList<>();
        tabNameList = new ArrayList<>();
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
        initFloatingButton(mainView.findViewById(nextButtonViewId));
        Log.i("TAG", "Current: "+mTabHost.getCurrentTab()+", Size: "+mTabHost.getTabWidget().getTabCount());
        if(mTabHost.getCurrentTab()+1==mTabHost.getTabWidget().getTabCount())
            nextButton.setImageResource(R.drawable.ic_check);
        else
            nextButton.setImageResource(R.drawable.ic_arrow_forward);
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
        mPagerAdapter = new ScreenSlidePagerAdapter(mainFragment.getChildFragmentManager());
        for(Fragment frag: viewList)
            mPagerAdapter.addPage(frag);
        mPager.setOffscreenPageLimit(viewList.size()-1);
        mPager.setAdapter(mPagerAdapter);

    }

    private void initFloatingButton(View floatingButtonView){
        if(floatingButtonView!=null) {
            nextButton = (FloatingActionButton) floatingButtonView;
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToNextPage();
                }
            });
        }
    }

    public void goToNextPage(){
        mPager.setCurrentItem(mPager.getCurrentItem() + 1);
        if(mPager.getCurrentItem()+1==mPager.getChildCount())
            nextButton.setImageResource(R.drawable.ic_check);
        else
            nextButton.setImageResource(R.drawable.ic_arrow_forward);
    }

    public void setTabIndicatorNumber(int position){
        this.mTabHost.setCurrentTab(position);
        if(position==mTabHost.getChildCount()+1)
            nextButton.setImageResource(R.drawable.ic_check);
        else
            nextButton.setImageResource(R.drawable.ic_arrow_forward);
    }

    public void initFloatingButtonMethod(View.OnClickListener v){
        nextButton.setOnClickListener(v);
    }

    @Override
    public void onTabChanged(String tabId) {
        this.mPager.setCurrentItem(this.mTabHost.getCurrentTab());
    }

    public Fragment getFragment(){
        return mPagerAdapter.getItem(mPager.getCurrentItem());
    }

    public Fragment getFragment(int position){
        return mPagerAdapter.getItem(position);
    }

    public boolean isLastPage(){
        if(mPager.getCurrentItem()+1==mPager.getChildCount())
            return true;
        return false;
    }
}
