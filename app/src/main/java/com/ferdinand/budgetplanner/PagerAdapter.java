package com.ferdinand.budgetplanner;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by ferdinand on 10/9/17.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    int noOfTabs;

    public PagerAdapter(FragmentManager fm,int not){
        super(fm);
        this.noOfTabs=not;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                tab0 tb0 = new tab0();
                return tb0;
            case 1:
                tab1 tb1 = new tab1();
                return tb1;
            case 2:
                tab2 tb2 = new tab2();
                return tb2;
            case 3:
                tab3 tb3 = new tab3();
                return tb3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return noOfTabs;
    }
}
