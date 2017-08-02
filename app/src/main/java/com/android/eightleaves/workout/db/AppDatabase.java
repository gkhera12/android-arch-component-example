package com.android.eightleaves.workout.db;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.android.eightleaves.workout.db.converter.DateConverter;
import com.android.eightleaves.workout.db.dao.WorkoutDao;
import com.android.eightleaves.workout.db.entity.WorkoutEntity;

@Database(entities = {WorkoutEntity.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    static final String DATABASE_NAME = "workout-db";

    public abstract WorkoutDao workoutDao();

}
