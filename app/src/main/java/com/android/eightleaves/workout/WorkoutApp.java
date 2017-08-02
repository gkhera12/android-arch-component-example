package com.android.eightleaves.workout;


import android.app.Application;
import android.content.Context;

public class WorkoutApp extends Application {
    private static WorkoutApp sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static WorkoutApp getInstance() {
        if (sInstance == null) {
            sInstance = new WorkoutApp();
        }
        return sInstance;
    }

    public static Context getContext() {
        return getInstance().getApplicationContext();
    }
}
