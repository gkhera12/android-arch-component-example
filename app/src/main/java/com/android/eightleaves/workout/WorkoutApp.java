package com.android.eightleaves.workout;


import android.app.Application;
import android.content.Context;

import com.android.eightleaves.workout.api.WorkoutService;
import com.android.eightleaves.workout.di.AppComponent;
import com.android.eightleaves.workout.di.DaggerAppComponent;

public class WorkoutApp extends Application {
    private static WorkoutApp sInstance;
    private WorkoutService workoutService;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        AppComponent component = DaggerAppComponent.create();
        workoutService = component.getWorkoutService();
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

    public WorkoutService getWorkoutService(){
        return workoutService;
    }
}
