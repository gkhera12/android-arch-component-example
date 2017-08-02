package com.android.eightleaves.workout.workout.viewmodel;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.content.Context;

import com.android.eightleaves.workout.db.DatabaseCreator;
import com.android.eightleaves.workout.db.entity.WorkoutEntity;
import com.android.eightleaves.workout.repo.WorkoutRepository;
import com.android.eightleaves.workout.repo.LocalRepository;
import com.android.eightleaves.workout.repo.RemoteRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class WorkoutViewModel extends AndroidViewModel {
    private static final MutableLiveData ABSENT = new MutableLiveData();
    {
        //noinspection unchecked
        ABSENT.setValue(null);
    }
    private LiveData<List<WorkoutEntity>> mObservableWorkouts;

    private WorkoutRepository workoutRepository;

    public WorkoutViewModel(final Application application) {
        super(application);
        final DatabaseCreator databaseCreator;
        databaseCreator = DatabaseCreator.getInstance(this.getApplication());
        LiveData<Boolean> databaseCreated = databaseCreator.isDatabaseCreated();
        final Context context = getApplication().getApplicationContext();

        mObservableWorkouts = Transformations.switchMap(databaseCreated,
                new Function<Boolean, LiveData<List<WorkoutEntity>>>() {
                    @Override
                    public LiveData<List<WorkoutEntity>> apply(Boolean isDbCreated) {
                        if (!Boolean.TRUE.equals(isDbCreated)) { // Not needed here, but watch out for null
                            //noinspection unchecked
                            return ABSENT;
                        } else {
                            //noinspection ConstantConditions
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(new Date());
                            cal.add(Calendar.DAY_OF_YEAR, -1);
                            workoutRepository = WorkoutRepository.getInstance(RemoteRepository.getInstance(), LocalRepository.getInstance(context));
                            return workoutRepository.getWorkoutsData();
                        }
                    }
        });
    }

    /**
     * Expose the LiveData Products query so the UI can observe it.
     */
    public LiveData<List<WorkoutEntity>> getWorkouts() {
        return mObservableWorkouts;
    }

}
