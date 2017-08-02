package com.android.eightleaves.workout.core;


import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.MenuItem;

import com.android.eightleaves.workout.R;
import com.android.eightleaves.workout.workout.view.WorkoutFragment;


public class CyclingFragmentController implements ViewPager.OnPageChangeListener{
    private FragmentManager fragmentManager;
    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private BottomNavigationView navigation;
    private MenuItem prevMenuItem;
    private SparseArray<Fragment> mFragments;

    public final static int WORKOUT_FRAGMENT_INDEX = 0;
    public final static int PROGRAM_FRAGMENT_INDEX = 1;
    public final static int TRAINING_LOGS_FRAGMENT_INDEX = 2;
    public final static int STATS_FRAGMENT_INDEX = 3;

    public CyclingFragmentController(BottomNavigationView navigation, ViewPager viewPager, FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        this.navigation = navigation;
        this.mViewPager = viewPager;
    }

    public void initiliaseFragmentPager(){
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setFragments();
        mSectionsPagerAdapter = new SectionsPagerAdapter(fragmentManager, mFragments);
        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(this);
    }

    private void setFragments() {
        mFragments = new SparseArray<>();
        mFragments.put(WORKOUT_FRAGMENT_INDEX, new WorkoutFragment());
    }

    @Override
    public void onPageSelected(int position) {
        if (prevMenuItem != null) {
            prevMenuItem.setChecked(false);
        }
        else
        {
            navigation.getMenu().getItem(0).setChecked(false);
        }
        navigation.getMenu().getItem(position).setChecked(true);
        prevMenuItem = navigation.getMenu().getItem(position);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_workout:
                    mViewPager.setCurrentItem(WORKOUT_FRAGMENT_INDEX);
                    return true;
            }
            return false;
        }
    };


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageScrollStateChanged(int state) {}


}
