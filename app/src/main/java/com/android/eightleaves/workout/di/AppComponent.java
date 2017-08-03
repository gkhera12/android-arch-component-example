package com.android.eightleaves.workout.di;

import com.android.eightleaves.workout.api.WorkoutService;

import dagger.Component;

@UserScope
@Component (modules = {WorkoutServiceModule.class})
public interface AppComponent {
   WorkoutService getWorkoutService();
}
