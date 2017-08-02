package com.android.eightleaves.workout.core;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private SparseArray<Fragment> mFragments = new SparseArray<>();

    public SectionsPagerAdapter(FragmentManager fm, SparseArray<Fragment> fragments ) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

}
