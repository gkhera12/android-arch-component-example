package com.android.eightleaves.workout.db;


import com.android.eightleaves.workout.db.entity.WorkoutEntity;

import java.util.ArrayList;
import java.util.List;

/** Generates dummy data and inserts them into the database */
class DatabaseInitUtil {

    static void initializeDb(AppDatabase db) {
        List<WorkoutEntity> workouts= new ArrayList<>();

        generateData(workouts);

        insertData(db, workouts);
    }

    private static void generateData(List<WorkoutEntity> workouts) {
        for (int i = 0; i < 1; i++) {
            WorkoutEntity workout = new WorkoutEntity();
            workout.setId(i+1);
            workout.setName("Coffee Ride");
            workout.setTotalTime("30 to 90 min");
            workout.setWorkoutType("Base Building");
            workout.setTrainingLevel("Easy");
            workout.setTerrain("Flat to Rolling");
            workouts.add(workout);

        }


    }

    private static void insertData(AppDatabase db, List<WorkoutEntity> workouts) {
        db.beginTransaction();
        try {
            db.workoutDao().insertAll(workouts);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }
}
