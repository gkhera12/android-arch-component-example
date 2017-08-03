package com.android.eightleaves.workout;


import android.app.Application;
import android.content.Context;

import com.android.eightleaves.workout.api.WorkoutService;
import com.android.eightleaves.workout.di.components.DaggerWorkoutComponent;
import com.android.eightleaves.workout.di.components.WorkoutComponent;

public class WorkoutApp extends Application {
    private static WorkoutApp sInstance;
    private WorkoutService workoutService;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        WorkoutComponent workoutComponent = DaggerWorkoutComponent.create();
        workoutService = workoutComponent.getWorkoutService();
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
