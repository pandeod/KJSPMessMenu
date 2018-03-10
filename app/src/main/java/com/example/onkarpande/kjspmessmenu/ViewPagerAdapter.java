package com.example.onkarpande.kjspmessmenu;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Onkar Pande on 3/10/2018.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        if(position==0)
        {
            return new MenuDay1();
        }
        else
        {
            return  new MenuDay2();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
