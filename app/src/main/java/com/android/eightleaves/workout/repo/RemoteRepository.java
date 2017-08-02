package com.android.eightleaves.workout.repo;


import com.android.eightleaves.workout.BuildConfig;
import com.android.eightleaves.workout.api.WorkoutService;
import com.android.eightleaves.workout.db.entity.WorkoutEntity;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteRepository {

    private static RemoteRepository sInstance;
    private Retrofit retrofit;
    private static final String API_URL = "http://demo9254418.mockable.io/";
    private static final Object LOCK = new Object();

    public synchronized static RemoteRepository getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new RemoteRepository();
                }
            }
        }
        return sInstance;
    }
    private RemoteRepository() {
        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder();
        if(BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(interceptor).build();
        }

        httpClient.build();
        retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void getWorkouts(final Callback<List<WorkoutEntity>> workoutCallback) {
        WorkoutService service = retrofit.create(WorkoutService.class);
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
