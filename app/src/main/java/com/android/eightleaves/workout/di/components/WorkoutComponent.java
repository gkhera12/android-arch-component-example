package com.android.eightleaves.workout.di.components;


import com.android.eightleaves.workout.api.WorkoutService;
import com.android.eightleaves.workout.di.scopes.UserScope;
import com.android.eightleaves.workout.di.modules.WorkoutServiceModule;

import dagger.Component;

@UserScope
@Component (modules = {WorkoutServiceModule.class})
public interface WorkoutComponent {
        WorkoutService getWorkoutService();
}
