package com.android.eightleaves.workout.di;


import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

@Module
public class NetworkModule {

    @Provides
    @UserScope
    public OkHttpClient okHttpClient(HttpLoggingInterceptor loggingInterceptor){
        return  new OkHttpClient().newBuilder()
                .addInterceptor(loggingInterceptor)
                .build();
    }

    @Provides
    @UserScope
    public HttpLoggingInterceptor httpLoggingInterceptor(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }
}
