package com.android.eightleaves.workout.repo;


import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import com.android.eightleaves.workout.WorkoutApp;
import com.android.eightleaves.workout.db.entity.WorkoutEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Singleton to provide single source for the data
 * It will manage the calling of Webservice and
 * Database updation or syncing.
*/

public class WorkoutRepository {

    private static WorkoutRepository sInstance;

    private static final Object LOCK = new Object();
    private final RemoteRepository remoteRepository;
    private final LocalRepository localRepository;

    private Context context;

    public synchronized static WorkoutRepository getInstance(RemoteRepository remoteRepository, LocalRepository localRepository) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new WorkoutRepository(remoteRepository, localRepository);
                }
            }
        }
        return sInstance;
    }

    private WorkoutRepository(RemoteRepository remoteRepository, LocalRepository localRepository) {
        this.remoteRepository = remoteRepository;
        this.localRepository = localRepository;
    }

    public LiveData<List<WorkoutEntity>> getWorkoutsData(){
        fetchData(new RemoteWorkoutsListener() {
            @Override
            public void onRemoteWorkoutsReceived(List<WorkoutEntity> workoutEntities) {
                insertWorkouts(workoutEntities);
            }
        });
        return localRepository.getWorkouts();
    }

    private void insertWorkouts(final List<WorkoutEntity> workoutEntities) {
        new AsyncTask<Context, Void, Void>() {
            @Override
            protected Void doInBackground(Context... params) {
                localRepository.saveWorkouts(workoutEntities);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }.execute(WorkoutApp.getContext());
    }

    private void fetchData(final RemoteWorkoutsListener remoteWorkoutsListener) {
        remoteRepository.getWorkouts(new Callback<List<WorkoutEntity>>() {
            @Override
            public void onResponse(Call<List<WorkoutEntity>> call, Response<List<WorkoutEntity>> response) {
                remoteWorkoutsListener.onRemoteWorkoutsReceived(response.body());
            }

            @Override
            public void onFailure(Call<List<WorkoutEntity>> call, Throwable t) {

            }
        });
    }

    interface RemoteWorkoutsListener {
        void onRemoteWorkoutsReceived(List<WorkoutEntity> workoutEntities);
    }
}
