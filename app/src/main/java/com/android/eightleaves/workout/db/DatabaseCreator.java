package com.android.eightleaves.workout.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;


import org.reactivestreams.Subscription;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.android.eightleaves.workout.db.AppDatabase.DATABASE_NAME;


/**
 * Creates the {@link AppDatabase} asynchronously, exposing a LiveData object to notify of creation.
 */
public class DatabaseCreator {

    private static DatabaseCreator sInstance;

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    private AppDatabase mDb;

    private final AtomicBoolean mInitializing = new AtomicBoolean(true);

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private final CompositeDisposable disposables;

    public DatabaseCreator(Context context, CompositeDisposable disposables) {
        this.disposables = disposables;
        createDb(context);
    }

    public synchronized static DatabaseCreator getInstance(Context context, CompositeDisposable disposables) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new DatabaseCreator(context, disposables);
                }
            }
        }
        return sInstance;
    }

    /** Used to observe when the database initialization is done */
    public LiveData<Boolean> isDatabaseCreated() {
        return mIsDatabaseCreated;
    }

    @Nullable
    public AppDatabase getDatabase() {
        return mDb;
    }

    /**
     * Creates or returns a previously-created database.
     * <p>
     * Although this uses an AsyncTask which currently uses a serial executor, it's thread-safe.
     */
    public void createDb(final Context context) {

        Log.d("DatabaseCreator", "Creating DB from " + Thread.currentThread().getName());

        if (!mInitializing.compareAndSet(true, false)) {
            return; // Already initializing
        }

        mIsDatabaseCreated.setValue(false);// Trigger an update to show a loading screen.
        Observable<AppDatabase> dbObservable = Observable.fromCallable(new Callable<AppDatabase>() {
            @Override
            public AppDatabase call() throws Exception {
                context.deleteDatabase(DATABASE_NAME);

                // Build the database!
                AppDatabase db = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, DATABASE_NAME).build();
                DatabaseInitUtil.initializeDb(db);
                return db;
            }
        });
        disposables.add(dbObservable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith  (
                        new DisposableObserver<AppDatabase>(){

                            @Override
                            public void onNext(AppDatabase appDatabase) {
                                mDb = appDatabase;
                               mIsDatabaseCreated.setValue(true);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        }
                ));
    }
}
