package com.theteus.kubota.skcmodule;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by user on 6/9/2016.
 */
public class SKCPagerAdaper  extends FragmentPagerAdapter {
    private final int PAGE_NUM=2;
    Context context;
    public SKCPagerAdaper(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    public int getCount() {
        return PAGE_NUM;
    }

    public Fragment getItem(int position) {
        if(position == 0)
            return new ContactSKC();
        else if(position == 1)
            return new ContactSKCAddForm();
        return null;
    }
}
