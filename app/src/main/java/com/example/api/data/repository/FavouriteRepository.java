package com.example.api.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Delete;
import androidx.room.Query;

import com.example.api.common.helper.Utils;
import com.example.api.data.source.local.MyRoomDB;
import com.example.api.data.source.local.favorite.FavoriteDao;
import com.example.api.data.source.local.favorite.FavoriteModel;

import java.util.List;

import okhttp3.internal.Util;

public class FavouriteRepository {
    MyRoomDB db ;
    FavoriteDao favoriteDao;


    public FavouriteRepository(Application application) {
       db = MyRoomDB.getDatabaseInstance(application);
       favoriteDao = db.favoriteDao();
    }

    public void insertFavourite(FavoriteModel favoriteModel){
        Utils.executor.execute(() -> favoriteDao.insertFavourite(favoriteModel));
    }


    public void deleteFavourite(FavoriteModel favoriteModel){
        Utils.executor.execute(() ->favoriteDao.deleteFavourite(favoriteModel));

    }

    public LiveData<List<FavoriteModel>> getAllFavourites(){
        return favoriteDao.getAllFavourites();
    }
    public List<FavoriteModel> getAllFavouritesList(){
        return favoriteDao.getAllFavouritesList();
    }

    public List<Integer> getAllFavouritesIds(){
        return favoriteDao.getAllFavouritesIds();
    }
}
