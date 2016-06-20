package com.theteus.kubota;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by user on 6/10/2016.
 */
public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
    ArrayList<Fragment> fragmentList;

    public ScreenSlidePagerAdapter(FragmentManager fm) {
        super(fm);
        fragmentList = new ArrayList<Fragment>();
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void addPage(Fragment fragment){
        fragmentList.add(fragment);
    }

    public void clearPage(){
        fragmentList.clear();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
