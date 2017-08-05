package com.android.eightleaves.workout.repo;


import android.arch.lifecycle.LiveData;
import android.content.Context;


import com.android.eightleaves.workout.db.DatabaseCreator;
import com.android.eightleaves.workout.db.dao.WorkoutDao;
import com.android.eightleaves.workout.db.entity.WorkoutEntity;

import java.util.List;

public class LocalRepository {

    private static final Object LOCK = new Object();
    private static LocalRepository sInstance;
    private WorkoutDao workoutDao ;

    public synchronized static LocalRepository getInstance(Context context, WorkoutDao workoutDao) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new LocalRepository(context, workoutDao);
                }
            }
        }
        return sInstance;
    }

    private LocalRepository(Context context, WorkoutDao workoutDao) {
        this.workoutDao = workoutDao;
    }

    public LiveData<List<WorkoutEntity>> getWorkouts() {
        return workoutDao.loadAllWorkouts();
    }

    public void saveWorkouts(List<WorkoutEntity> workouts) {
        workoutDao.insertAll(workouts);
    }
}
