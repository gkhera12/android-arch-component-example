package com.android.eightleaves.workout.repo;


import com.android.eightleaves.workout.api.WorkoutService;
import com.android.eightleaves.workout.db.entity.WorkoutEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteRepository {

    private static RemoteRepository sInstance;
    private static final Object LOCK = new Object();
    private WorkoutService service;

    public synchronized static RemoteRepository getInstance(WorkoutService service) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new RemoteRepository(service);
                }
            }
        }
        return sInstance;
    }
    private RemoteRepository(WorkoutService service) {
        this.service = service;
    }

    public void getWorkouts(final Callback<List<WorkoutEntity>> workoutCallback) {
        Call workouts = service.getWorkouts();
        workouts.enqueue(new Callback<List<WorkoutEntity>>() {
            @Override
            public void onResponse(Call<List<WorkoutEntity>> call, Response<List<WorkoutEntity>> response) {
                if(response.isSuccessful()){
                    workoutCallback.onResponse(call, response);
                }
            }

            @Override
            public void onFailure(Call<List<WorkoutEntity>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


}
