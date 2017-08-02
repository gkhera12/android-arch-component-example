package com.android.eightleaves.workout.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;


import com.android.eightleaves.workout.db.entity.WorkoutEntity;

import java.util.List;

@Dao
public interface WorkoutDao {

    @Query("SELECT * FROM workouts")
    LiveData<List<WorkoutEntity>> loadAllWorkouts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<WorkoutEntity> workouts);

    @Query("select * from workouts where id = :workoutId")
    LiveData<WorkoutEntity> loadWorkout(int workoutId);

    @Query("select * from workouts where id = :workoutId")
    WorkoutEntity loadWorkoutSync(int workoutId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOne(WorkoutEntity workout);
}
