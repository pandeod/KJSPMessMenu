package com.example.onkarpande.kjspmessmenu;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.Switch;

/**
 * Created by Onkar Pande on 3/10/2018.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0: return new MenuDay1();

            case 1: return new MenuDay2();

            case 2: return new MenuDay3();

            case 3:return new MenuDay4();

            case 4:return new MenuDay5();

            case 5:return new MenuDay6();

            default:return new MenuDay7();
        }
 }

    @Override
    public int getCount() {
        return 7;
    }
}
