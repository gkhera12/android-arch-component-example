package com.android.eightleaves.workout;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.android.eightleaves.workout.core.CyclingFragmentController;
import com.android.eightleaves.workout.core.ITabSwitch;


public class MainActivity extends AppCompatActivity implements ITabSwitch {

    private CyclingFragmentController cyclingFragmentController;
    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.fragment_container) != null) {
            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
            mViewPager = (ViewPager) findViewById(R.id.fragment_container);
            cyclingFragmentController = new CyclingFragmentController(navigation, mViewPager,getSupportFragmentManager());
            cyclingFragmentController.initiliaseFragmentPager();
        }
    }

    @Override
    public void switchToTab(int fragmentId) {
       mViewPager.setCurrentItem(fragmentId);
    }
}
