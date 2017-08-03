package com.android.eightleaves.workout.workout.viewmodel;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;

import com.android.eightleaves.workout.WorkoutApp;
import com.android.eightleaves.workout.db.DatabaseCreator;
import com.android.eightleaves.workout.db.entity.WorkoutEntity;
import com.android.eightleaves.workout.repo.LocalRepository;
import com.android.eightleaves.workout.repo.RemoteRepository;
import com.android.eightleaves.workout.repo.WorkoutRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class WorkoutViewModel extends AndroidViewModel {
    private static final MutableLiveData ABSENT = new MutableLiveData();
    private WorkoutRepository mWorkoutRepository;

    {
        //noinspection unchecked
        ABSENT.setValue(null);
    }
    private LiveData<List<WorkoutEntity>> mObservableWorkouts;

    public WorkoutViewModel(final Application application) {
        super(application);

        final RemoteRepository remoteRepository = RemoteRepository.getInstance(((WorkoutApp)application).getWorkoutService());
        final DatabaseCreator databaseCreator;

        //Todo Dagger injection for this
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
                            //Todo  Dagger injection for this
                            mWorkoutRepository = WorkoutRepository.getInstance(remoteRepository, LocalRepository.getInstance(application.getApplicationContext()));
                            return mWorkoutRepository.getWorkoutsData();
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

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        public Factory(@NonNull Application application) {
            mApplication = application;

        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new WorkoutViewModel(mApplication);
        }
    }
}
