package com.example.api.data.source.local;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.api.data.source.local.favorite.FavoriteDao;
import com.example.api.data.source.local.favorite.FavoriteModel;
import com.example.api.data.source.local.history.HistoryDao;
import com.example.api.data.source.local.history.SearchHistoryModel;
import com.example.api.models.GenresModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {SearchHistoryModel.class  , FavoriteModel.class}, version = 5
       )
public abstract class MyRoomDB extends RoomDatabase {

    public abstract HistoryDao historyDao();
    public abstract FavoriteDao favoriteDao();

    private static volatile MyRoomDB INSTANCE;
    public static MyRoomDB getDatabaseInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (MyRoomDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    MyRoomDB.class, "movies_db").fallbackToDestructiveMigration()
                            .build();

                }
            }
        }
        return INSTANCE;
    }

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }
    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }
    @Override
    public void clearAllTables() {

    }
}
